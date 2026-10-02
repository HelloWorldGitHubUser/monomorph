package com.goodskill.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.goodskill.config.AlipayConfig;
import com.goodskill.dto.AlipayRequestDTO;
import com.goodskill.dto.AlipayResponseDTO;
import com.goodskill.enums.OrderStatusEnum;
import com.goodskill.service.AlipayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class AlipayServiceImpl implements AlipayService {

    @Autowired
    private AlipayClient alipayClient;
    @Autowired
    private AlipayConfig alipayConfig;
    @Autowired
    private OrderServiceImpl orderService;

    @Override
    public AlipayResponseDTO createPayOrder(AlipayRequestDTO request) {
        AlipayResponseDTO response = new AlipayResponseDTO();
        response.setOrderId(request.getOrderId());
        try {
            if ("wap".equalsIgnoreCase(request.getPayType())) {
                AlipayTradeWapPayRequest payRequest = new AlipayTradeWapPayRequest();
                payRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
                payRequest.setReturnUrl(alipayConfig.getReturnUrl());
                payRequest.setBizContent(buildPayBizContent(request, "QUICK_WAP_WAY"));
                response.setForm(alipayClient.pageExecute(payRequest).getBody());
            } else {
                AlipayTradePagePayRequest payRequest = new AlipayTradePagePayRequest();
                payRequest.setNotifyUrl(alipayConfig.getNotifyUrl());
                payRequest.setReturnUrl(alipayConfig.getReturnUrl());
                payRequest.setBizContent(buildPayBizContent(request, "FAST_INSTANT_TRADE_PAY"));
                response.setForm(alipayClient.pageExecute(payRequest).getBody());
            }
            response.setStatus("SUCCESS");
        } catch (AlipayApiException e) {
            log.error("创建支付宝支付单失败: orderId={}", request.getOrderId(), e);
            response.setStatus("FAILED");
        }
        return response;
    }

    @Override
    public String handleCallback(Map<String, String> params) {
        if (!verifyCallbackSignature(params)) {
            return "failure";
        }
        String orderId = params.get("out_trade_no");
        String tradeStatus = params.get("trade_status");
        if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
            OrderStatusEnum paidStatus = OrderStatusEnum.PAID;
            orderService.updateOrderStatus(orderId, paidStatus.getCode(), paidStatus.getDesc(),
                    params.get("trade_no"), params.get("gmt_payment"));
        }
        return "success";
    }

    @Override
    public boolean verifyCallbackSignature(Map<String, String> params) {
        try {
            return AlipaySignature.rsaCheckV1(params, alipayConfig.getPublicKey(), "UTF-8", "RSA2");
        } catch (AlipayApiException e) {
            log.error("支付宝回调签名验证异常", e);
            return false;
        }
    }

    @Override
    public AlipayResponseDTO queryPayStatus(String orderId) {
        AlipayResponseDTO response = new AlipayResponseDTO();
        response.setOrderId(orderId);
        try {
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            request.setBizContent("{\"out_trade_no\":\"" + orderId + "\"}");
            AlipayTradeQueryResponse queryResponse = alipayClient.execute(request);
            response.setStatus(queryResponse.isSuccess() ? queryResponse.getTradeStatus() : "UNKNOWN");
        } catch (AlipayApiException e) {
            log.error("查询支付宝支付状态失败: orderId={}", orderId, e);
            response.setStatus("UNKNOWN");
        }
        return response;
    }

    private String buildPayBizContent(AlipayRequestDTO request, String productCode) {
        return "{" +
                "\"out_trade_no\":\"" + request.getOrderId() + "\"," +
                "\"total_amount\":\"" + request.getAmount() + "\"," +
                "\"subject\":\"" + request.getSubject() + "\"," +
                "\"product_code\":\"" + productCode + "\"" +
                "}";
    }
}
