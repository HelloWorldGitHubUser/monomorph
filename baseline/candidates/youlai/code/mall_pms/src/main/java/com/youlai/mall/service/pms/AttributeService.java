package com.youlai.mall.service.pms;

import com.baomidou.mybatisplus.extension.service.IService;
import com.youlai.mall.model.pms.entity.PmsCategoryAttribute;
import com.youlai.mall.model.pms.form.PmsCategoryAttributeForm;

public interface AttributeService extends IService<PmsCategoryAttribute> {

    /**
     * 批量保存商品属性
     *
     * @param formData 属性表单数据
     * @return
     */
    boolean saveBatch(PmsCategoryAttributeForm formData);
}
