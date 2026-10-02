package com.goodskill.service;

import com.goodskill.dto.AlipayRequestDTO;
import com.goodskill.dto.AlipayResponseDTO;

import java.util.Map;

public interface AlipayService {
    AlipayResponseDTO createPayOrder(AlipayRequestDTO request);

    String handleCallback(Map<String, String> params);

    boolean verifyCallbackSignature(Map<String, String> params);

    AlipayResponseDTO queryPayStatus(String orderId);
}
