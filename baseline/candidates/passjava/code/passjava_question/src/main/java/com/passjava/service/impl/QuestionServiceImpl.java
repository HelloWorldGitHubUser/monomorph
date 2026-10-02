package com.passjava.service.impl;
import com.passjava.dao.QuestionDao;
import com.passjava.dto.QuestionEsModel;
import com.passjava.entity.QuestionEntity;
import com.passjava.entity.TypeEntity;
import com.passjava.monomorph.dto.generated.client.SearchService;
import com.passjava.service.IQuestionService;
import com.passjava.service.ITypeService;
import com.passjava.utils.PageUtils;
import com.passjava.utils.Query;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("questionService")
public class QuestionServiceImpl extends ServiceImpl<QuestionDao, QuestionEntity> implements IQuestionService {
    @Autowired
    ITypeService typeService;

    @Autowired(required = false)
    SearchService searchService;

    @Override
    public IPage<QuestionEntity> queryPage1(IPage<QuestionEntity> page, Map<String, Object> params) {
        return baseMapper.selectPage1(page, params);
    }

    @Override
    public List<QuestionEntity> list(String type) {
        return baseMapper.listForApp(type);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = ((String) (params.get("key")));
        QueryWrapper<QuestionEntity> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.eq("id", key).or().like("title", key).or().like("answer", key);
        }
        String type = ((String) (params.get("type")));
        if (!StringUtils.isEmpty(type)) {
            queryWrapper.eq("type", type);
        }
        IPage<QuestionEntity> page = this.page(new Query<QuestionEntity>().getPage(params), queryWrapper);
        return new PageUtils(page);
    }

    @Override
    public QuestionEntity info(Long id) {
        return getById(id);
    }

    @Override
    public boolean saveQuestion(QuestionEntity question) {
        boolean saveResult = save(question);
        if (searchService != null) {
            saveEs(question);
        }
        return saveResult;
    }

    @Override
    public boolean updateQuestion(QuestionEntity question) {
        updateById(question);
        if (searchService != null) {
            saveEs(question);
        }
        return true;
    }

    @Override
    public boolean createQuestion(QuestionEntity question) {
        return save(question);
    }

    private void saveEs(QuestionEntity question) {
        QuestionEsModel esModel = new QuestionEsModel();
        BeanUtils.copyProperties(question, esModel);
        TypeEntity typeEntity = typeService.getById(question.getType());
        if (typeEntity != null) {
            esModel.setTypeName(typeEntity.getType());
        }
        searchService.saveQuestion(esModel);
    }
}