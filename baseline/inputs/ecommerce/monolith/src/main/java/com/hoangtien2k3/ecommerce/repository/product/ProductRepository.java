package com.hoangtien2k3.ecommerce.repository.product;

import com.hoangtien2k3.ecommerce.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
