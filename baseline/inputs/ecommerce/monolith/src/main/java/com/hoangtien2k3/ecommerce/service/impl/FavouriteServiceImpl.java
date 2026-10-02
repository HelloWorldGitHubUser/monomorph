package com.hoangtien2k3.ecommerce.service.impl;

import com.hoangtien2k3.ecommerce.dto.FavouriteDto;
import com.hoangtien2k3.ecommerce.dto.FavouriteUserDto;
import com.hoangtien2k3.ecommerce.dto.ProductDto;
import com.hoangtien2k3.ecommerce.dto.response.ProductResponse;
import com.hoangtien2k3.ecommerce.exception.wrapper.FavouriteNotFoundException;
import com.hoangtien2k3.ecommerce.helper.FavouriteMappingHelper;
import com.hoangtien2k3.ecommerce.model.favourite.FavouriteId;
import com.hoangtien2k3.ecommerce.model.user.User;
import com.hoangtien2k3.ecommerce.repository.favourite.FavouriteRepository;
import com.hoangtien2k3.ecommerce.service.FavouriteService;
import com.hoangtien2k3.ecommerce.service.ProductService;
import com.hoangtien2k3.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FavouriteServiceImpl implements FavouriteService {

    private final FavouriteRepository favouriteRepository;
    private final UserService userService;
    private final ProductService productService;

    @Override
    public List<FavouriteDto> findAll() {
        return favouriteRepository.findAll().stream()
                .map(FavouriteMappingHelper::map)
                .peek(this::enrich)
                .distinct()
                .toList();
    }

    @Override
    public FavouriteDto findById(final FavouriteId favouriteId) {
        return this.favouriteRepository.findById(favouriteId)
                .map(FavouriteMappingHelper::map)
                .map(f -> {
                    enrich(f);
                    return f;
                })
                .orElseThrow(() -> new FavouriteNotFoundException(
                        String.format("Favourite with id: [%s] not found!", favouriteId)));
    }

    @Override
    public FavouriteDto save(final FavouriteDto favouriteDto) {
        return FavouriteMappingHelper.map(this.favouriteRepository
                .save(FavouriteMappingHelper.map(favouriteDto)));
    }

    @Override
    public FavouriteDto update(final FavouriteDto favouriteDto) {
        return FavouriteMappingHelper.map(this.favouriteRepository
                .save(FavouriteMappingHelper.map(favouriteDto)));
    }

    @Override
    public void deleteById(final FavouriteId favouriteId) {
        this.favouriteRepository.deleteById(favouriteId);
    }

    private void enrich(FavouriteDto f) {
        try {
            User user = userService.findById(Long.valueOf(f.getUserId()));
            f.setUserDto(FavouriteUserDto.builder()
                    .userId(f.getUserId())
                    .firstName(user.getFullname())
                    .imageUrl(user.getAvatar())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .build());
        } catch (Exception e) {
            log.error("Error fetching user info: {}", e.getMessage());
        }
        try {
            ProductDto productDto = productService.findById(f.getProductId());
            f.setProductDto(ProductResponse.builder()
                    .productId(productDto.getProductId())
                    .productTitle(productDto.getProductTitle())
                    .imageUrl(productDto.getImageUrl())
                    .sku(productDto.getSku())
                    .priceUnit(productDto.getPriceUnit())
                    .quantity(productDto.getQuantity())
                    .build());
        } catch (Exception e) {
            log.error("Error fetching product info: {}", e.getMessage());
        }
    }
}
