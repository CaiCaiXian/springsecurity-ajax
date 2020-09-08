package com.cjx.securityajax.service.impl;

import com.cjx.securityajax.dao.RoleDao;
import com.cjx.securityajax.entity.SysRole;
import com.cjx.securityajax.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleDao roleDao;

    @Override
    public boolean grant(String uid, Long rid) {
        if(roleDao.grant(uid,rid) > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean revoke(String uid, Long rid) {
        if(roleDao.revoke(uid,rid) > 0){
            return true;
        }
        return false;
    }

    @Override
    public void saveRole(SysRole role) {
        roleDao.save(role);
    }

    @Override
    public List<String> getRoleNameByUrl(String url) {
        return roleDao.selectRoleNameByUrl(url);
    }
}
