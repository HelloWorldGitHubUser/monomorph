package io.gulimall.service.product;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gulimall.utils.PageUtils;
import io.gulimall.entity.product.AttrAttrgroupRelationEntity;
import io.gulimall.entity.product.AttrEntity;
import io.gulimall.entity.product.ProductAttrValueEntity;
import io.gulimall.vo.product.AttrRespVo;
import io.gulimall.vo.product.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 15:38:37
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params, long catelogId, String attrType);


    void saveAttr(AttrVo attr);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    PageUtils getNoRelationAttr(Long attrgroupId, Map<String, Object> params);

    void saveRelationBatch(List<AttrAttrgroupRelationEntity> attrGroupEntities);

    List<ProductAttrValueEntity> listAttrsforSpu(Long spuId);

    void updateSpuAttrs(Long spuId, List<ProductAttrValueEntity> attrValueEntities);

    List<Long> selectSearchAttrIds(List<Long> attrIds);
}

