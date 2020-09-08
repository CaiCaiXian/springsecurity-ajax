package com.cjx.securityajax.service;

import com.cjx.securityajax.entity.SysUser;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    void saveUser(SysUser user);

    UserDetails loadUserByUserName(String username);
}
