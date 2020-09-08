package com.cjx.securityajax.controller;

import com.cjx.securityajax.entity.SysUser;
import com.cjx.securityajax.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping({"/","/index"})
    public String index(){
        return "index";
    }

    @GetMapping("/root")
    public String toRoot(){
        return "root";
    }

    @GetMapping("/manager")
    public String toManager(){
        return "manager";
    }

    @PostMapping("/register")
    public String register(SysUser user){
        userService.saveUser(user);
        return "register";
    }


}
