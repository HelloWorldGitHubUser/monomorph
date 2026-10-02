package com.goodskill.repository.es;

import com.goodskill.entity.es.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author heng
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, String> {

   void deleteByGoodsId(Integer goodsId);

}
