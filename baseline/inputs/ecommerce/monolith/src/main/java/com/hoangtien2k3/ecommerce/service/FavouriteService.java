package com.hoangtien2k3.ecommerce.service;

import com.hoangtien2k3.ecommerce.dto.FavouriteDto;
import com.hoangtien2k3.ecommerce.model.favourite.FavouriteId;

import java.util.List;

public interface FavouriteService {
    List<FavouriteDto> findAll();
    FavouriteDto findById(final FavouriteId favouriteId);
    FavouriteDto save(final FavouriteDto favouriteDto);
    FavouriteDto update(final FavouriteDto favouriteDto);
    void deleteById(final FavouriteId favouriteId);
}
