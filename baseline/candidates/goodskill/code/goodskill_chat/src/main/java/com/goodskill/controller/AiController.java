package com.goodskill.controller;

import com.goodskill.service.TongYiService;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ConditionalOnBean(ChatModel.class)
public class AiController {

    @Autowired
    @Qualifier("tongYiSimpleServiceImpl")
    private TongYiService tongYiSimpleService;

    @GetMapping("/example")
    public String completion(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return tongYiSimpleService.completion(message);
    }

}


