package com.passjava.controller;

import com.passjava.utils.R;
import com.passjava.dto.QuestionEsModel;
import com.passjava.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 搜索控制器
 */
@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 保存题目到 ES
     */
    @PostMapping("/question/save")
    public R saveQuestion(@RequestBody QuestionEsModel questionEsModel) {
        return searchService.saveQuestion(questionEsModel);
    }

    /**
     * 搜索题目
     */
    @GetMapping("/question/search")
    public Object search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long id,
            @RequestParam(defaultValue = "1") Integer pageNum) {
        return searchService.search(keyword, id, pageNum);
    }
}





