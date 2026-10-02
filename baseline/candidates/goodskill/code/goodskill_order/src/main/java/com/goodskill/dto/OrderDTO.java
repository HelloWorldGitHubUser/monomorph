package com.goodskill.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author techa03
 * @date 2019/4/3
 */
@Data
public class OrderDTO implements Serializable {
    @Serial
    private static final long serialVersionUID=1L;

    private Long seckillId;

    private String userPhone;

    private Byte status;

    private LocalDateTime createTime;

    private String serverIp;

    private String userIp;

    private String userId;

    private String goodsName;

    private String goodsTitle;

    private String goodsImg;

    private Double seckillPrice;

    private String stateDesc;

    private String alipayTradeNo;

    private LocalDateTime payCompleteTime;

}
