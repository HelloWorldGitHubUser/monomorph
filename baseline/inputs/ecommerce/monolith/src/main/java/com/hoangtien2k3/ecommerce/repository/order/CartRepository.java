package com.hoangtien2k3.ecommerce.repository.order;

import com.hoangtien2k3.ecommerce.model.order.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

}
