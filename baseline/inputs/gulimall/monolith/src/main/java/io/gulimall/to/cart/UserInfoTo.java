package io.gulimall.to.cart;

import lombok.Data;

@Data
public class UserInfoTo {

    private Long userId;

    private String userKey;

    /**
     * 浏览器是否已有 user-key
     */
    private Boolean tempUser = false;
}

