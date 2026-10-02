package com.hoangtien2k3.ecommerce.repository.favourite;

import com.hoangtien2k3.ecommerce.model.favourite.Favourite;
import com.hoangtien2k3.ecommerce.model.favourite.FavouriteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRepository extends JpaRepository<Favourite, FavouriteId> {

}
