package com.hoangtien2k3.ecommerce.repository.search;

import com.hoangtien2k3.ecommerce.model.search.ProductDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSearchRepository extends ElasticsearchRepository<ProductDocument, Long> {
}
