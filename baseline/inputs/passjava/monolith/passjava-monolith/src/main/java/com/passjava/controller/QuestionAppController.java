package com.passjava.controller;

import com.passjava.utils.R;
import com.passjava.entity.QuestionEntity;
import com.passjava.service.IQuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 题目-App端接口
 *
 * @author 公众号：悟空聊架构
 * @公众号：悟空聊架构
 * @date 2022-11-01 22:34:04
 */
@RestController
@RequestMapping("question/v1/app/question")
@Slf4j
public class QuestionAppController {
    @Autowired
    private IQuestionService questionService;

    /**
     * 查询题目列表
     */
    @RequestMapping("/list/{type}")
    public R list(@PathVariable("type") String type){
        long time = System.currentTimeMillis();
        List<QuestionEntity> list = questionService.list(type);
        log.info("耗时：{}", System.currentTimeMillis() - time);
        return R.ok().put("list", list);
    }


    /**
     * 查询题目答案
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
		QuestionEntity question = questionService.info(id);
        return R.ok().put("question", question);
    }
}

