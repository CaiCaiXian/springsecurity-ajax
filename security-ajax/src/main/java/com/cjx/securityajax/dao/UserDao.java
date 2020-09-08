package com.cjx.securityajax.dao;


import com.cjx.securityajax.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<SysUser,String> , JpaSpecificationExecutor<SysUser> {

    /**
     * 通过用户名获取用户实体
     * @param userName
     * @return
     */
    SysUser findUserByUsername(String userName);
}
