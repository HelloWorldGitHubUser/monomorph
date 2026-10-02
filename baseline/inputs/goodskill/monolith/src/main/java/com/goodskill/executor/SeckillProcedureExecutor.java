package com.goodskill.executor;

import com.goodskill.dto.SeckillMockResponseDTO;
import com.goodskill.dto.SuccessKilledDTO;
import com.goodskill.enums.Events;
import com.goodskill.enums.States;
import com.goodskill.entity.mysql.Seckill;
import com.goodskill.mapper.SeckillMapper;
import com.goodskill.service.StateMachineService;
import com.goodskill.service.SeckillService;
import com.goodskill.service.RedisService;
import com.goodskill.listener.SeckillMockResponseListener;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @author heng
 */
@Slf4j
@Service
public class SeckillProcedureExecutor implements SeckillExecutor {

    @Resource
    private SeckillMapper seckillMapper;
    @Resource
    private SeckillService seckillService;
    @Resource
    private RedisService redisService;
    @Resource
    private StateMachineService stateMachineService;
    @Resource
    private SeckillMockResponseListener seckillMockResponseListener;


    /**
     * 处理用户秒杀请求
     *
     * @param seckillId 秒杀活动id
     * @param userPhone 秒杀用户手机号
     * @param note      秒杀备注信息
     * @param taskId    秒杀任务id
     */
    @Override
    public void dealSeckill(long seckillId, String userPhone, String note, String taskId) {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            SuccessKilledDTO successKilled = new SuccessKilledDTO();
            successKilled.setSeckillId(seckillId);
            successKilled.setUserPhone(userPhone);
            successKilled.setCreateTime(new Date());
            successKilled.setServerIp(localHost.getHostAddress() + ":" + localHost.getHostName());
            if (seckillService.reduceNumber(successKilled) < 1) {
                Seckill seckill = seckillMapper.selectById(seckillId);
                log.debug("#dealSeckill 当前库存：{}，秒杀活动id:{}，商品id:{}", seckill.getNumber(), seckill.getSeckillId(), seckill.getGoodsId());
                if (stateMachineService.checkState(seckillId, States.IN_PROGRESS)) {
                    stateMachineService.feedMachine(Events.ACTIVITY_CALCULATE, seckillId);
                    Boolean endFlag = redisService.setSeckillEndFlag(seckillId, taskId);
                    if (endFlag) {
                        seckillMockResponseListener.handleSeckillResult(
                                SeckillMockResponseDTO.builder().seckillId(seckillId).note(note).status(true).taskId(taskId).build());
                        log.info("#dealSeckill 商品已售罄，最新秒杀信息：{}", seckill);
                    }
                }
                if (seckill.getNumber() <= 0) {
                    log.debug("#dealSeckill 库存不足，无法继续秒杀！");
                }
            }
        } catch (UnknownHostException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 处理用户秒杀请求（支持预检查库存的场景）
     *
     * @param seckillId       秒杀活动id
     * @param userPhone       秒杀用户手机号
     * @param note            秒杀备注信息
     * @param taskId          秒杀任务id
     * @param hasStockChecked 是否已经检查过库存（true=有库存，执行减库存；false=无库存，发送完成通知）
     */
    public void dealSeckillWithPreCheck(long seckillId, String userPhone, String note, String taskId, boolean hasStockChecked) {
        if (hasStockChecked) {
            SuccessKilledDTO successKilled = new SuccessKilledDTO();
            successKilled.setSeckillId(seckillId);
            successKilled.setUserPhone(userPhone);
            successKilled.setCreateTime(new Date());
            successKilled.setStatus(0);
            seckillService.reduceNumberWithPreCheck(successKilled);
        } else {
            Seckill seckill = seckillMapper.selectById(seckillId);
            log.debug("#dealSeckillWithPreCheck 库存不足，当前库存：{}，秒杀活动id:{}，商品id:{}",
                seckill.getNumber(), seckill.getSeckillId(), seckill.getGoodsId());
            if (stateMachineService.checkState(seckillId, States.IN_PROGRESS)) {
                stateMachineService.feedMachine(Events.ACTIVITY_CALCULATE, seckillId);
                Boolean endFlag = redisService.setSeckillEndFlag(seckillId, taskId);
                if (endFlag) {
                    seckillMockResponseListener.handleSeckillResult(
                            SeckillMockResponseDTO.builder()
                                .seckillId(seckillId)
                                .note(note)
                                .status(true)
                                .taskId(taskId)
                                .build());
                    log.info("#dealSeckillWithPreCheck 商品已售罄，最新秒杀信息：{}", seckill);
                }
            }
        }
    }

}
