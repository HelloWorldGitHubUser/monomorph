package com.passjava.service;
import com.passjava.entity.StudyTimeEntity;
import com.passjava.monomorph.dto.generated.client.R;
import com.passjava.utils.PageUtils;
import java.util.Map;
import com.baomidou.mybatisplus.extension.service.IService;
/**
 * 学习时长服务
 */
public interface StudyTimeService extends IService<StudyTimeEntity> {
    PageUtils queryPage(Map<String, Object> params);

    R getMemberStudyTimeListTest(Long id);
}