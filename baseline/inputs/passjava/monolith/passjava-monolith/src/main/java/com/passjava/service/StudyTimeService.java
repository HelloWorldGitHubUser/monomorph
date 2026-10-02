package com.passjava.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.passjava.utils.PageUtils;
import com.passjava.utils.R;
import com.passjava.entity.StudyTimeEntity;

import java.util.Map;

/**
 * 学习时长服务
 */
public interface StudyTimeService extends IService<StudyTimeEntity> {
    PageUtils queryPage(Map<String, Object> params);

    R getMemberStudyTimeListTest(Long id);
}





