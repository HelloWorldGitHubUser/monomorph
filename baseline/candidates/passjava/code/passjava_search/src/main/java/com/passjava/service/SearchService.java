package com.passjava.service;
import com.passjava.monomorph.dto.generated.client.QuestionEsModel;
import com.passjava.monomorph.dto.generated.client.R;
/**
 * 搜索服务
 */
public interface SearchService {
    /**
     * 保存题目到 ES
     */
    R saveQuestion(QuestionEsModel questionEsModel);

    /**
     * 搜索题目
     */
    Object search(String keyword, Long id, Integer pageNum);
}