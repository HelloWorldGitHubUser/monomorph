package com.goodskill.service;

import com.goodskill.dto.GoodsDTO;

import java.util.List;

/**
 * @author heng
 */

public interface GoodsEsService {

    /**
     * 保存
     * @param goodsDto
     */
    void save(GoodsDTO goodsDto);

    /**
     * 批量保存
     * @param list
     */
    void saveBatch(List<GoodsDTO> list);

    /**
     * 删除商品
     */
    void delete(GoodsDTO goodsDto);

    /**
     * 根据商品名称检索商品
     * @param input 用户输入的商品关键词
     * @return 分页结果
     */
    List<GoodsDTO> searchWithNameByPage(String input);
}


