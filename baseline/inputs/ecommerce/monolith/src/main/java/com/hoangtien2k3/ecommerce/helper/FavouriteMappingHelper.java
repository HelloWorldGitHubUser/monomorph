package com.hoangtien2k3.ecommerce.helper;

import com.hoangtien2k3.ecommerce.dto.FavouriteDto;
import com.hoangtien2k3.ecommerce.dto.FavouriteUserDto;
import com.hoangtien2k3.ecommerce.dto.response.ProductResponse;
import com.hoangtien2k3.ecommerce.model.favourite.Favourite;

public class FavouriteMappingHelper {

    public static FavouriteDto map(final Favourite favourite) {
        return FavouriteDto.builder()
                .userId(favourite.getUserId())
                .productId(favourite.getProductId())
                .likeDate(favourite.getLikeDate())
                .userDto(
                        FavouriteUserDto.builder()
                                .userId(favourite.getUserId())
                                .build())
                .productDto(
                        ProductResponse.builder()
                                .productId(favourite.getProductId())
                                .build())
                .build();
    }

    public static Favourite map(final FavouriteDto favouriteDto) {
        return Favourite.builder()
                .userId(favouriteDto.getUserId())
                .productId(favouriteDto.getProductId())
                .likeDate(favouriteDto.getLikeDate())
                .build();
    }

}
