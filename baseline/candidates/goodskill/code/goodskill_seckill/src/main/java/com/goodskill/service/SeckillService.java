package com.goodskill.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.goodskill.dto.*;
import com.goodskill.vo.SeckillVO;

import java.io.IOException;
import java.io.Serializable;

/**
 * 秒杀服务
 *
 * @author heng
 * @date 2016/7/16
 */

public interface SeckillService {

    /**
     * 获取秒杀活动列表
     *
     * @param pageNum   页码
     * @param pageSize  每页数量
     * @param goodsName 商品名称，模糊匹配
     * @return
     */
    PageDTO<SeckillVO> getSeckillList(int pageNum, int pageSize, String goodsName);

    /**
     * 暴露秒杀活动url
     *
     * @param seckillId 秒杀活动id
     * @return 活动信息
     */
    ExposerDTO exportSeckillUrl(long seckillId);

    /**
     * 根据秒杀id删除成功记录
     *
     * @param seckillId 秒杀活动id
     */
    void deleteSuccessKillRecord(long seckillId);

    /**
     * 执行秒杀，通过同步来控制并发
     *
     * @param requestDto     秒杀请求
     * @param strategyNumber 秒杀策略编码
     */
    void execute(SeckillMockRequestDTO requestDto, int strategyNumber);

    /**
     * 获取成功秒杀记录数
     *
     * @param seckillId 秒杀活动id
     */
    long getSuccessKillCount(Long seckillId);

    /**
     * 准备秒杀商品数量
     *
     * @param seckillId    秒杀商品id
     * @param seckillCount 秒杀数量
     * @param taskId 任务id
     */
    void prepareSeckill(Long seckillId, int seckillCount, String taskId);

    SeckillVO findById(Serializable seckillId);

    boolean saveOrUpdateSeckill(SeckillVO seckill);

    boolean removeBySeckillId(Serializable seckillId);

    boolean save(SeckillVO seckill);

    /**
     * 减商品库存
     *
     * @param successKilled
     * @return 1代表成功，小于1为失败
     */
    int reduceNumber(SuccessKilledDTO successKilled);

    int reduceNumberInner(SuccessKilledDTO successKilled);

    /**
     * 减商品库存（预检查版本）
     *
     * @param successKilled 秒杀成功DTO
     * @return 更新行数
     */
    int reduceNumberWithPreCheck(SuccessKilledDTO successKilled);

    /**
     * 获取二维码
     *
     * @param fileName 二维码图片名称
     * @return SeckillResponseDto
     */
    SeckillResponseDTO getQrcode(String fileName) throws IOException;

    /**
     * 根据秒杀id获取秒杀活动信息
     *
     * @param seckillId
     * @return
     */
    SeckillInfoDTO getInfoById(Serializable seckillId);

    /**
     * 结束秒杀操作
     *
     * @param seckillId 秒杀ID
     * @return boolean 结束秒杀是否成功
     */
    boolean endSeckill(Long seckillId);

}


