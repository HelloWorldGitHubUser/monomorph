package com.goodskill.executor;

/**
 * @author heng
 */
public interface SeckillExecutor {

    /**
     * 秒杀内部处理方法
     *
     * @param seckillId
     * @param userPhone
     * @param note
     * @param taskId
     */
    void dealSeckill(long seckillId, String userPhone, String note, String taskId);

    /**
     * 秒杀内部处理方法（支持预检查库存的场景）
     * 用于 SynchronizedLockStrategy 等需要先查询库存再执行的场景
     *
     * @param seckillId       秒杀活动id
     * @param userPhone       秒杀用户手机号
     * @param note            秒杀备注信息
     * @param taskId          秒杀任务id
     * @param hasStockChecked 是否已经检查过库存（true=有库存，执行减库存；false=无库存，发送完成通知）
     */
    void dealSeckillWithPreCheck(long seckillId, String userPhone, String note, String taskId, boolean hasStockChecked);
}


