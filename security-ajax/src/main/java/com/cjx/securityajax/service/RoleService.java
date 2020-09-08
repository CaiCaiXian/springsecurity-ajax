package com.cjx.securityajax.service;


import com.cjx.securityajax.entity.SysRole;

import java.util.List;


public interface RoleService {

    boolean grant(String uid,Long rid);

    boolean revoke(String uid,Long rid);

    void saveRole(SysRole role);

    List<String> getRoleNameByUrl(String url);
}
