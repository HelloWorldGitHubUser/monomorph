package com.goodskill.entity.mongo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * 库存实体
 * @author techa03
 * @date 2020/5/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Order {

    @Id
    private String id;

     /**
     * 秒杀活动id
     */
    private Long seckillId;

    /**
     * 用户手机号
     */
    private String userPhone;

    /**
     * 状态
     */
    private Byte status;

    private LocalDateTime createTime;

    /**
     * 服务器ip
     */
    private String serverIp;

    /**
     * 用户ip
     */
    private String userIp;

    /**
     * 用户id
     */
    private String userId;

    private String goodsName;

    private String goodsTitle;

    private String goodsImg;

    private Double seckillPrice;

    private String stateDesc;

    private String alipayTradeNo;

    private LocalDateTime payCompleteTime;
}
