package io.gulimall.service.product.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.gulimall.dao.product.BrandDao;
import io.gulimall.dao.product.CategoryDao;
import io.gulimall.entity.product.BrandEntity;
import io.gulimall.entity.product.CategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.gulimall.utils.PageUtils;
import io.gulimall.utils.Query;

import io.gulimall.dao.product.CategoryBrandRelationDao;
import io.gulimall.entity.product.CategoryBrandRelationEntity;
import io.gulimall.service.product.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();
        BrandEntity brandEntity = brandDao.selectById(brandId);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        this.save(categoryBrandRelation);
    }

    @Override
    public void updateCategory(CategoryEntity category) {
        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
        categoryBrandRelationEntity.setCatelogName(category.getName());
        categoryBrandRelationEntity.setCatelogId(category.getCatId());
        this.update(
                categoryBrandRelationEntity,
                new UpdateWrapper<CategoryBrandRelationEntity>()
                        .eq("catelog_id",category.getCatId()));
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        this.baseMapper.updateBrand(brandId, name);
    }

    @Override
    public List<CategoryBrandRelationEntity> getBrandsByCayId(Long catelogId) {
        List<CategoryBrandRelationEntity> entities = baseMapper.selectList(
                new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catelogId));
        return entities;
    }

}