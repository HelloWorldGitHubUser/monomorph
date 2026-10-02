package com.hoangtien2k3.ecommerce.repository.user;

import com.hoangtien2k3.ecommerce.model.user.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface UserRepositoryPaging extends PagingAndSortingRepository<User, Long> {
    @Query("SELECT u FROM User u")
    List<User> findAll(Sort sort);
}
