package com.hoangtien2k3.ecommerce.service;

import com.hoangtien2k3.ecommerce.dto.request.ChangePasswordRequest;
import com.hoangtien2k3.ecommerce.dto.request.Login;
import com.hoangtien2k3.ecommerce.dto.request.SignUp;
import com.hoangtien2k3.ecommerce.dto.request.UserDto;
import com.hoangtien2k3.ecommerce.dto.response.JwtResponseMessage;
import com.hoangtien2k3.ecommerce.model.user.User;
import org.springframework.data.domain.Page;

public interface UserService {
    User register(SignUp signUp);

    JwtResponseMessage login(Login signInForm);

    void logout();

    User update(Long userId, SignUp update);

    String changePassword(ChangePasswordRequest request);

    String delete(Long id);

    User findById(Long userId);

    User findByUsername(String userName);

    Page<UserDto> findAllUsers(int page, int size, String sortBy, String sortOrder);
}
