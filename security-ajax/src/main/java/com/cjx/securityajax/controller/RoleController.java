package com.cjx.securityajax.controller;

import com.cjx.securityajax.entity.SysRole;
import com.cjx.securityajax.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping
    public String save(SysRole role){
        roleService.saveRole(role);
        return "saveRole";
    }

    /**
     * 给角色授权
     * @param uid
     * @param rid
     * @return
     */
    @PutMapping("/grant")
    public String grant(String uid,Long rid){
        if(roleService.grant(uid, rid)){
            return "success";
        }else {
            return "failure";
        }
    }

    /**
     * 撤销角色权限
     * @param uid
     * @param rid
     * @return
     */
    @DeleteMapping("/revoke")
    public String revoke(String uid,Long rid){

        if(roleService.revoke(uid, rid)){
            return "success";
        }else {
            return "failure";
        }

    }


}
