package com.passjava.service.impl;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.passjava.utils.PageUtils;
import com.passjava.utils.Query;
import com.passjava.utils.R;
import com.passjava.dao.StudyTimeDao;
import com.passjava.entity.StudyTimeEntity;
import com.passjava.service.StudyTimeService;

@Service("studyTimeService")
public class StudyTimeServiceImpl extends ServiceImpl<StudyTimeDao, StudyTimeEntity> implements StudyTimeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StudyTimeEntity> page = this.page(
                new Query<StudyTimeEntity>().getPage(params),
                new QueryWrapper<StudyTimeEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public R getMemberStudyTimeListTest(Long id) {
        StudyTimeEntity studyTimeEntity = new StudyTimeEntity();
        studyTimeEntity.setTotalTime(100);
        studyTimeEntity.setQuesTypeId(1L);
        return R.ok().put("studytime", Arrays.asList(studyTimeEntity));
    }
}





