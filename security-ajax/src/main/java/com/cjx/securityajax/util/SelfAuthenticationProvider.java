package com.cjx.securityajax.util;

import com.cjx.securityajax.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义认证器
 */
@Component
public class SelfAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        //获取用户登录输入的信息
        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        //用注册用户时相同的加密方式加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        //根据用户名获取数据库中的用户
        UserDetails userInfo = userService.loadUserByUserName(userName);

        //进行密码比对
        if (!encoder.matches(password,userInfo.getPassword())) {
            throw new BadCredentialsException("用户名密码不正确，请重新登陆！");
        }

        //比对成功返回token令牌
        return new UsernamePasswordAuthenticationToken(userName, password, userInfo.getAuthorities());
    }

    /**
     * 如果该AuthenticationProvider支持传入的Authentication对象，则返回true
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}