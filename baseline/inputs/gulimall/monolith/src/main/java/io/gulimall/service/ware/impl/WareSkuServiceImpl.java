package io.gulimall.service.ware.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gulimall.exception.NoStockException;
import io.gulimall.vo.SkuHasStockVo;
import io.gulimall.to.mq.OrderTo;
import io.gulimall.utils.PageUtils;
import io.gulimall.utils.Query;
import io.gulimall.entity.product.SkuInfoEntity;
import io.gulimall.entity.order.OrderEntity;
import io.gulimall.enume.OrderStatusEnum;
import io.gulimall.service.order.OrderService;
import io.gulimall.service.product.SkuInfoService;
import io.gulimall.dao.ware.WareSkuDao;
import io.gulimall.entity.ware.WareOrderTaskDetailEntity;
import io.gulimall.entity.ware.WareOrderTaskEntity;
import io.gulimall.entity.ware.WareSkuEntity;
import io.gulimall.enume.WareTaskStatusEnum;
import io.gulimall.service.ware.WareOrderTaskDetailService;
import io.gulimall.service.ware.WareOrderTaskService;
import io.gulimall.service.ware.WareSkuService;
import io.gulimall.vo.OrderItemVo;
import io.gulimall.vo.WareSkuLockVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@Slf4j
@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private WareOrderTaskService wareOrderTaskService;

    @Autowired
    private WareOrderTaskDetailService wareOrderTaskDetailService;

    @Autowired
    @Lazy
    private OrderService orderService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                new QueryWrapper<WareSkuEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void addStock(Long skuId, Long wareId, Integer skuNum) {
        Long count = this.baseMapper.selectCount(
                new QueryWrapper<WareSkuEntity>().eq("sku_id", skuId).eq("ware_id", wareId));
        if (count==0){
            WareSkuEntity wareSkuEntity = new WareSkuEntity();
            wareSkuEntity.setSkuId(skuId);
            wareSkuEntity.setWareId(wareId);
            wareSkuEntity.setStock(skuNum);
            wareSkuEntity.setStockLocked(0);
            //查出skuname并设置
            SkuInfoEntity skuInfo = skuInfoService.getById(skuId);
            if (skuInfo != null) {
                wareSkuEntity.setSkuName(skuInfo.getSkuName());
            }
            this.baseMapper.insert(wareSkuEntity);
        }else {
            this.baseMapper.addstock(skuId, wareId, skuNum);
        }
        log.debug("补充库存 skuId={}, wareId={}, 数量={}", skuId, wareId, skuNum);
    }

    @Override
    public List<SkuHasStockVo> getSkuHasStocks(List<Long> ids) {
        List<SkuHasStockVo> skuHasStockVos = ids.stream().map(id -> {
            SkuHasStockVo skuHasStockVo = new SkuHasStockVo();
            skuHasStockVo.setSkuId(id);
            Integer count = baseMapper.getTotalStock(id);
            skuHasStockVo.setHasStock(count==null?false:count>0);
            return skuHasStockVo;
        }).collect(Collectors.toList());
        return skuHasStockVos;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Boolean orderLockStock(WareSkuLockVo wareSkuLockVo) {

        //因为可能出现订单回滚后，库存锁定不回滚的情况，但订单已经回滚，得不到库存锁定信息，因此要有库存工作单
        WareOrderTaskEntity taskEntity = new WareOrderTaskEntity();
        taskEntity.setOrderSn(wareSkuLockVo.getOrderSn());
        taskEntity.setCreateTime(new Date());
        wareOrderTaskService.save(taskEntity);

        List<OrderItemVo> itemVos = wareSkuLockVo.getLocks();
        List<SkuLockVo> lockVos = itemVos.stream().map((item) -> {
            SkuLockVo skuLockVo = new SkuLockVo();
            skuLockVo.setSkuId(item.getSkuId());
            skuLockVo.setNum(item.getCount());
            //找出所有库存大于商品数的仓库
            List<Long> wareIds = baseMapper.listWareIdsHasStock(item.getSkuId(), item.getCount());
            skuLockVo.setWareIds(wareIds);
            return skuLockVo;
        }).collect(Collectors.toList());

        log.info("开始锁定库存，订单号={}，锁定条目={}", wareSkuLockVo.getOrderSn(), itemVos.size());
        for (SkuLockVo lockVo : lockVos) {
            boolean lock = true;
            Long skuId = lockVo.getSkuId();
            List<Long> wareIds = lockVo.getWareIds();
            //如果没有满足条件的仓库，抛出异常
            if (wareIds == null || wareIds.size() == 0) {
                throw new NoStockException(skuId);
            }else {
                for (Long wareId : wareIds) {
                    Long count=baseMapper.lockWareSku(skuId, lockVo.getNum(), wareId);
                    if (count==0){
                        lock=false;
                    }else {
                        //锁定成功，保存工作单详情
                        WareOrderTaskDetailEntity detailEntity = WareOrderTaskDetailEntity.builder()
                                .skuId(skuId)
                                .skuName("")
                                .skuNum(lockVo.getNum())
                                .taskId(taskEntity.getId())
                                .wareId(wareId)
                                .lockStatus(1).build();
                        wareOrderTaskDetailService.save(detailEntity);
                        lock = true;
                        break;
                    }
                }
            }
            if (!lock) throw new NoStockException(skuId);
        }
        log.info("库存锁定成功，订单号={}", wareSkuLockVo.getOrderSn());
        return true;
    }

    @Override
    public void unlock(OrderTo orderTo) {
        //为防止重复解锁，需要重新查询工作单
        String orderSn = orderTo.getOrderSn();
        WareOrderTaskEntity taskEntity = wareOrderTaskService.getBaseMapper().selectOne((new QueryWrapper<WareOrderTaskEntity>().eq("order_sn", orderSn)));
        //查询出当前订单相关的且处于锁定状态的工作单详情
        List<WareOrderTaskDetailEntity> lockDetails = wareOrderTaskDetailService.list(new QueryWrapper<WareOrderTaskDetailEntity>().eq("task_id", taskEntity.getId()).eq("lock_status", WareTaskStatusEnum.Locked.getCode()));
        for (WareOrderTaskDetailEntity lockDetail : lockDetails) {
            unlockStock(lockDetail.getSkuId(),lockDetail.getSkuNum(),lockDetail.getWareId(),lockDetail.getId());
        }
        log.info("释放订单{}的锁定库存，共{}条", orderSn, lockDetails.size());
    }

    private void unlockStock(Long skuId, Integer skuNum, Long wareId, Long detailId) {
        //数据库中解锁库存数据
        baseMapper.unlockStock(skuId, skuNum, wareId);
        //更新库存工作单详情的状态
        WareOrderTaskDetailEntity detail = WareOrderTaskDetailEntity.builder()
                .id(detailId)
                .lockStatus(2).build();
        wareOrderTaskDetailService.updateById(detail);
    }

    @Override
    public void releaseExpiredLocks(long maxLockMinutes) {
        if (maxLockMinutes <= 0) {
            return;
        }
        Date threshold = Date.from(Instant.now().minus(maxLockMinutes, ChronoUnit.MINUTES));
        // 查询超时的任务
        List<WareOrderTaskEntity> tasks = wareOrderTaskService.list(new QueryWrapper<WareOrderTaskEntity>()
                .lt("create_time", threshold));
        
        int releasedCount = 0;
        for (WareOrderTaskEntity task : tasks) {
            // 只处理还有锁定状态详情的任务
            long lockedDetailCount = wareOrderTaskDetailService.count(
                    new QueryWrapper<WareOrderTaskDetailEntity>()
                            .eq("task_id", task.getId())
                            .eq("lock_status", WareTaskStatusEnum.Locked.getCode()));
            if (lockedDetailCount > 0) {
                OrderEntity order = orderService.getOrderByOrderSn(task.getOrderSn());
                if (order == null || Objects.equals(order.getStatus(), OrderStatusEnum.CANCLED.getCode())) {
                    OrderTo orderTo = new OrderTo();
                    orderTo.setOrderSn(task.getOrderSn());
                    unlock(orderTo);
                    releasedCount++;
                }
            }
        }
        if (releasedCount > 0) {
            log.warn("检测到{}个库存锁定超时任务，已触发释放", releasedCount);
        }
    }

    @Data
    class SkuLockVo{
        private Long skuId;
        private Integer num;
        private List<Long> wareIds;
    }

}
