package com.goodskill.repository.mongo;

import com.goodskill.entity.mongo.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {

    void deleteBySeckillId(Long seckillId);

    long countBySeckillId(Long seckillId);

    Page<Order> findByUserIdOrderByCreateTimeDesc(String userId, Pageable pageable);
}
