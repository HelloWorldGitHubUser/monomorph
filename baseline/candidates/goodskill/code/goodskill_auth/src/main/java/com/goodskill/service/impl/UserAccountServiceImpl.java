package com.goodskill.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goodskill.entity.mysql.User;
import com.goodskill.mapper.UserMapper;
import com.goodskill.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author heng
 */
@Service
@Slf4j
public class UserAccountServiceImpl extends ServiceImpl<UserMapper, User> implements UserAccountService {


}

