package com.passjava.service;

import com.passjava.utils.R;
import com.passjava.dto.QuestionEsModel;

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





