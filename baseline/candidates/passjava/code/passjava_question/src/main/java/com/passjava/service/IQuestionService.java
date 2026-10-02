package com.passjava.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.passjava.utils.PageUtils;
import com.passjava.entity.QuestionEntity;

import java.util.List;
import java.util.Map;

/**
 * 题目服务
 */
public interface IQuestionService extends IService<QuestionEntity> {

    IPage<QuestionEntity> queryPage1(IPage<QuestionEntity> page, Map<String, Object> params);

    List<QuestionEntity> list(String type);

    PageUtils queryPage(Map<String, Object> params);

    QuestionEntity info(Long id);

    boolean saveQuestion(QuestionEntity question);

    boolean updateQuestion(QuestionEntity question);

    boolean createQuestion(QuestionEntity question);
}




