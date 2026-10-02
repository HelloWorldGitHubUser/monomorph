package com.goodskill.service.impl;
import com.goodskill.dto.*;
import com.goodskill.entity.mysql.Seckill;
import com.goodskill.entity.mysql.SuccessKilled;
import com.goodskill.enums.Events;
import com.goodskill.enums.GoodsKillStrategyEnum;
import com.goodskill.exception.SeckillCloseException;
import com.goodskill.handler.PreRequestPipeline;
import com.goodskill.mapper.SeckillMapper;
import com.goodskill.mapper.SuccessKilledMapper;
import com.goodskill.service.GoodsService;
import com.goodskill.service.RedisService;
import com.goodskill.service.SeckillService;
import com.goodskill.service.StateMachineService;
import com.goodskill.strategy.GoodsKillStrategy;
import com.goodskill.util.MD5Util;
import com.goodskill.vo.GoodsVO;
import com.goodskill.vo.SeckillVO;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>
 * 秒杀库存表 服务实现类
 * </p>
 *
 * @author heng
 * @since 2019-09-07
 */
@Slf4j
@Service
public class SeckillServiceImpl extends ServiceImpl<SeckillMapper, Seckill> implements SeckillService {
    @Resource
    private com.goodskill.monomorph.id.generated.client.OrderServiceImpl orderService;

    @Resource
    private SuccessKilledMapper successKilledMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private List<GoodsKillStrategy> goodskillStrategies;

    @Resource
    private SeckillService seckillService;

    @Autowired
    private GoodsService goodsService;

    @Resource(name = "taskExecutor")
    private ThreadPoolExecutor taskExecutor;

    @Value("${alipay.qrcodeImagePath:1}")
    private String qrcodeImagePath;

    @Resource
    private SeckillMapper baseMapper;

    @Resource
    private StateMachineService stateMachineService;

    @Resource
    private PreRequestPipeline preRequestPipeline;

    @Override
    public PageDTO<SeckillVO> getSeckillList(int pageNum, int pageSize, String goodsName) {
        String key = (((("seckill:list:" + pageNum) + ":") + pageSize) + ":") + goodsName;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object pageCache = valueOperations.get(key);
        PageDTO<SeckillVO> page;
        if (Objects.isNull(pageCache)) {
            QueryWrapper<Seckill> queryWrapper = new QueryWrapper<>();
            if (StringUtils.isNotBlank(goodsName)) {
                queryWrapper.lambda().like(Seckill::getName, goodsName);
            }
            Page<Seckill> seckillPage = baseMapper.selectPage(new Page(pageNum, pageSize), queryWrapper);
            List<SeckillVO> collect = seckillPage.getRecords().stream().map(it -> {
                SeckillVO seckillVO = new SeckillVO();
                BeanUtils.copyProperties(it, seckillVO);
                GoodsVO byId = goodsService.findById(it.getGoodsId());
                if (Objects.nonNull(byId)) {
                    String photoUrl = byId.getPhotoUrl();
                    seckillVO.setPhotoUrl(photoUrl);
                }
                return seckillVO;
            }).collect(Collectors.toList());
            page = new PageDTO<>();
            page.setCurrent(pageNum);
            page.setSize(pageSize);
            page.setTotal(seckillPage.getTotal());
            page.setRecords(collect);
            valueOperations.set(key, page, 5, TimeUnit.MINUTES);
        } else {
            page = ((PageDTO<SeckillVO>) (pageCache));
        }
        return page;
    }

    @Override
    public ExposerDTO exportSeckillUrl(long seckillId) {
        // 从redis中获取缓存秒杀信息
        Seckill seckill = redisService.getSeckill(seckillId);
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if ((nowTime.getTime() < startTime.getTime()) || (nowTime.getTime() > endTime.getTime())) {
            return new ExposerDTO(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = MD5Util.getMD5(seckillId);
        return new ExposerDTO(true, md5, seckillId);
    }

    @Override
    public void deleteSuccessKillRecord(long seckillId) {
        SuccessKilled example = new SuccessKilled();
        example.setSeckillId(seckillId);
        successKilledMapper.delete(new QueryWrapper<>(example));
    }

    @Override
    public void execute(SeckillMockRequestDTO requestDto, int strategyNumber) {
        goodskillStrategies.stream().filter(n -> n.getClass().getName().startsWith(Objects.requireNonNull(GoodsKillStrategyEnum.stateOf(strategyNumber)).getClassName())).findFirst().ifPresent(n -> n.execute(requestDto));
    }

    /**
     * 获取秒杀成功笔数
     *
     * @param seckillId
     * 		秒杀活动id
     * @return  */
    @Override
    public long getSuccessKillCount(Long seckillId) {
        SuccessKilled example = new SuccessKilled();
        example.setSeckillId(seckillId);
        long count = successKilledMapper.selectCount(new QueryWrapper<>(example));
        if (count == 0) {
            try {
                count = orderService.count(seckillId);
            } catch (Exception e) {
                log.error("mongo服务不可用，请检查！", e);
                throw e;
            }
        }
        return count;
    }

    @Override
    public void prepareSeckill(Long seckillId, int seckillCount, String taskId) {
        SeckillWebMockRequestDTO request = new SeckillWebMockRequestDTO();
        request.setSeckillId(seckillId);
        request.setSeckillCount(seckillCount);
        request.setTaskId(taskId);
        preRequestPipeline.handle(request);
    }

    @Override
    public int reduceNumber(SuccessKilledDTO successKilled) {
        int count = 0;
        try {
            count = seckillService.reduceNumberInner(successKilled);
        } catch (Exception e) {
            log.warn(e.getMessage());
        }
        return count;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int reduceNumberInner(SuccessKilledDTO successKilled) {
        SuccessKilled entity = BeanUtil.copyProperties(successKilled, SuccessKilled.class);
        successKilledMapper.insert(entity);
        Seckill wrapper = new Seckill();
        wrapper.setSeckillId(entity.getSeckillId());
        UpdateWrapper<Seckill> updateWrapper = new UpdateWrapper(wrapper);
        updateWrapper.gt("end_time", entity.getCreateTime());
        updateWrapper.lt("start_time", entity.getCreateTime());
        updateWrapper.gt("number", 0);
        updateWrapper.setSql("number = number - 1");
        int update = baseMapper.update(null, updateWrapper);
        if (update <= 0) {
            throw new SeckillCloseException("seckill is closed");
        } else {
            return update;
        }
    }

    /**
     * 减库存（预检查版本）
     * 用于已经在外部检查过库存的场景（如 SynchronizedLockStrategy）
     * 逻辑：先减库存，再插入成功记录（与 reduceNumberInner 顺序相反）
     *
     * @param successKilled
     * 		秒杀成功DTO
     * @return 更新行数
     */
    @Transactional(rollbackFor = Exception.class)
    public int reduceNumberWithPreCheck(SuccessKilledDTO successKilled) {
        // 1. 先减库存（因为外部已经检查过，所以直接减）
        Seckill wrapper = new Seckill();
        wrapper.setSeckillId(successKilled.getSeckillId());
        UpdateWrapper<Seckill> updateWrapper = new UpdateWrapper<>(wrapper);
        updateWrapper.gt("end_time", successKilled.getCreateTime());
        updateWrapper.lt("start_time", successKilled.getCreateTime());
        updateWrapper.gt("number", 0);// 仍然加上这个条件，防止并发情况下的问题

        updateWrapper.setSql("number = number - 1");
        int update = baseMapper.update(null, updateWrapper);
        if (update <= 0) {
            // 减库存失败，抛异常回滚
            throw new SeckillCloseException("seckill is closed or stock is insufficient");
        }
        // 2. 再插入成功记录
        SuccessKilled entity = BeanUtil.copyProperties(successKilled, SuccessKilled.class);
        successKilledMapper.insert(entity);
        return update;
    }

    /* 原有实现说明：
    reduceNumberInner: 先插入成功记录 → 再减库存
    reduceNumberWithPreCheck: 先减库存 → 再插入成功记录

    区别：
    - reduceNumberInner 适用于"先做再判断"的策略（如 RedissonStrategy）
    - reduceNumberWithPreCheck 适用于"先判断再做"的策略（如 SynchronizedLockStrategy）
     */
    @Override
    public SeckillResponseDTO getQrcode(String fileName) throws IOException {
        SeckillResponseDTO seckillResponseDto = new SeckillResponseDTO();
        if (qrcodeImagePath == null) {
            qrcodeImagePath = System.getProperty("user.dir");
        }
        FileInputStream inputStream = new FileInputStream(((qrcodeImagePath + "/") + fileName) + ".png");
        int b;
        // 二维码一般不超过10KB
        byte[] data = new byte[1024 * 10];
        int i = 0;
        while ((b = inputStream.read()) != (-1)) {
            data[i] = ((byte) (b));
            i++;
        } 
        seckillResponseDto.setData(data);
        return seckillResponseDto;
    }

    @Override
    public SeckillInfoDTO getInfoById(Serializable seckillId) {
        SeckillInfoDTO seckillInfoDTO = new SeckillInfoDTO();
        SeckillVO seckill = seckillService.findById(seckillId);
        GoodsVO goods = goodsService.findById(seckill.getGoodsId());
        BeanUtils.copyProperties(seckill, seckillInfoDTO);
        seckillInfoDTO.setGoodsName(goods.getName());
        return seckillInfoDTO;
    }

    @Override
    public boolean endSeckill(Long seckillId) {
        return stateMachineService.feedMachine(Events.ACTIVITY_END, seckillId);
    }

    @Override
    public boolean removeBySeckillId(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean save(SeckillVO seckill) {
        return baseMapper.insert(BeanUtil.copyProperties(seckill, Seckill.class)) > 0;
    }

    @Override
    public SeckillVO findById(Serializable id) {
        return BeanUtil.copyProperties(super.getById(id), SeckillVO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveOrUpdateSeckill(SeckillVO seckill) {
        return saveOrUpdate(BeanUtil.copyProperties(seckill, Seckill.class));
    }
}