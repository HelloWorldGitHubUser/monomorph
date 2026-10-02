package com.hoangtien2k3.ecommerce.repository.shipping;

import com.hoangtien2k3.ecommerce.model.shipping.OrderItem;
import com.hoangtien2k3.ecommerce.model.shipping.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemId> {

}
