package io.gulimall.vo.product;

import io.gulimall.entity.product.AttrEntity;
import io.gulimall.entity.product.AttrGroupEntity;
import lombok.Data;

import java.util.List;

@Data
public class AttrGroupWithAttrVo extends AttrGroupEntity {
    private List<AttrEntity> attrs;
}
