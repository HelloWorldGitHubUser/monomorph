package io.gulimall.service.product;

import com.baomidou.mybatisplus.extension.service.IService;
import io.gulimall.utils.PageUtils;
import io.gulimall.entity.product.AttrGroupEntity;
import io.gulimall.vo.product.AttrGroupWithAttrVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author Ethan
 * @email hongshengmo@163.com
 * @date 2020-05-27 15:38:37
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {


    PageUtils queryPage(Map<String, Object> params, long catelogId);

    List<AttrGroupWithAttrVo> getAttrGroupWithAttrByCatelogId(Long catId);
}

