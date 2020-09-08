package com.cjx.securityajax.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.cjx.securityajax.dao.UserDao;
import com.cjx.securityajax.entity.SysUser;
import com.cjx.securityajax.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;


    @Override
    public void saveUser(SysUser user) {
        String password = user.getPassword();
        //新建用户时加密密码
        String encodePassword = new BCryptPasswordEncoder().encode(password);
        user.setPassword(encodePassword);
        //分配id
        user.setUid(IdUtil.simpleUUID());
        userDao.save(user);
    }

    @Override
    public UserDetails loadUserByUserName(String username) {
        return userDao.findUserByUsername(username);
    }
}
