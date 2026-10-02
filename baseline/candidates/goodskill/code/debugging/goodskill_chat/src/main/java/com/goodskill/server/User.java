package com.goodskill.server;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.goodskill.entity.BaseColEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Chat-local user projection decoded from the authentication token.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends BaseColEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String account;

    private String password;

    private String username;

    private Integer locked;

    private String avatar;

    private LocalDateTime lastLoginTime;

    private String mobile;

    private String emailAddr;
}
