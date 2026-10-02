package com.hoangtien2k3.ecommerce.repository.notification;

import com.hoangtien2k3.ecommerce.model.notification.PaymentRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRecordRepository extends MongoRepository<PaymentRecord, Integer> {

}
