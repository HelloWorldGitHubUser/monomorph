package com.hoangtien2k3.ecommerce.service.impl;

import com.hoangtien2k3.ecommerce.dto.ProductDto;
import com.hoangtien2k3.ecommerce.event.ProductDataChangeEvent;
import com.hoangtien2k3.ecommerce.exception.wrapper.ProductNotFoundException;
import com.hoangtien2k3.ecommerce.helper.ProductMappingHelper;
import com.hoangtien2k3.ecommerce.model.product.Product;
import com.hoangtien2k3.ecommerce.repository.product.CategoryRepository;
import com.hoangtien2k3.ecommerce.repository.product.ProductRepository;
import com.hoangtien2k3.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public List<ProductDto> findAll() {
        log.info("ProductDto List, service, fetch all products");
        return productRepository.findAll()
                .stream()
                .map(ProductMappingHelper::map)
                .distinct()
                .toList();
    }

    @Override
    public ProductDto findById(Integer productId) {
        log.info("ProductDto, service; fetch product by id");
        return productRepository.findById(productId)
                .map(ProductMappingHelper::map)
                .orElseThrow(() -> new ProductNotFoundException(String.format("Product with id[%d] not found", productId)));
    }

    @Override
    public ProductDto save(ProductDto productDto) {
        log.info("ProductDto, service; save product");
        try {
            Product product = ProductMappingHelper.map(productDto);
            resolveCategory(product);
            product = productRepository.save(product);
            eventPublisher.publishEvent(new ProductDataChangeEvent(this, product.getProductId().longValue(), ProductDataChangeEvent.Operation.CREATE));
            return ProductMappingHelper.map(product);
        } catch (DataIntegrityViolationException e) {
            log.error("Error saving product: Data integrity violation", e);
            throw new ProductNotFoundException("Error saving product: Data integrity violation", e);
        } catch (Exception e) {
            log.error("Error saving product", e);
            throw new ProductNotFoundException("Error saving product", e);
        }
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        log.info("ProductDto, service; update product");

        Product existingProduct = productRepository.findById(productDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productDto.getProductId()));

        BeanUtils.copyProperties(productDto, existingProduct, "productId", "categoryDto");

        if (productDto.getCategoryDto() != null && productDto.getCategoryDto().getCategoryId() != null) {
            existingProduct.setCategory(categoryRepository.findById(productDto.getCategoryDto().getCategoryId())
                    .orElseThrow(() -> new ProductNotFoundException(
                            "Category not found with id: " + productDto.getCategoryDto().getCategoryId())));
        }

        Product updatedProduct = productRepository.save(existingProduct);
        eventPublisher.publishEvent(new ProductDataChangeEvent(this, updatedProduct.getProductId().longValue(), ProductDataChangeEvent.Operation.UPDATE));

        return ProductMappingHelper.map(updatedProduct);
    }

    @Override
    public ProductDto update(Integer productId, ProductDto productDto) {
        log.info("ProductDto, service; update product with productId");

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

        BeanUtils.copyProperties(productDto, existingProduct, "productId", "category");

        if (productDto.getCategoryDto() != null && productDto.getCategoryDto().getCategoryId() != null) {
            existingProduct.setCategory(categoryRepository.findById(productDto.getCategoryDto().getCategoryId())
                    .orElseThrow(() -> new ProductNotFoundException(
                            "Category not found with id: " + productDto.getCategoryDto().getCategoryId())));
        }

        Product updatedProduct = productRepository.save(existingProduct);
        eventPublisher.publishEvent(new ProductDataChangeEvent(this, updatedProduct.getProductId().longValue(), ProductDataChangeEvent.Operation.UPDATE));

        return ProductMappingHelper.map(updatedProduct);
    }

    @Override
    public void deleteById(Integer productId) {
        log.info("Void, service; delete product by id");
        this.productRepository.delete(ProductMappingHelper.map(this.findById(productId)));
        eventPublisher.publishEvent(new ProductDataChangeEvent(this, productId.longValue(), ProductDataChangeEvent.Operation.DELETE));
    }

    private void resolveCategory(Product product) {
        if (product.getCategory() != null && product.getCategory().getCategoryId() != null) {
            product.setCategory(categoryRepository.findById(product.getCategory().getCategoryId())
                    .orElseThrow(() -> new ProductNotFoundException(
                            "Category not found with id: " + product.getCategory().getCategoryId())));
        }
    }

}
