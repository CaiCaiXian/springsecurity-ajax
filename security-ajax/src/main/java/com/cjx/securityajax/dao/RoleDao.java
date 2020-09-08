package com.cjx.securityajax.dao;

import com.cjx.securityajax.entity.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends JpaRepository<SysRole,Long>, JpaSpecificationExecutor<SysRole> {

    /**
     * 添加一个角色
     * @param uid
     * @param rid
     * @return
     */
    @Modifying
    @Query(value = "insert into sys_user_role values(?1,?2)",nativeQuery = true)
    int grant(String uid,Long rid);

    /**
     * 删除一个角色
     * @param uid
     * @param rid
     * @return
     */
    @Modifying
    @Query(value = "delete from sys_user_role where uid = ?1 and rid = ?2",nativeQuery = true)
    int revoke(String uid,Long rid);

    /**
     * 获取该角色下的url
     * @param Url
     * @return
     */
    @Query(value = "select r.role_name from sys_role r,sys_role_menus rm,sys_menu m where rm.rid = r.rid and rm.mid = m.mid and m.url =?1",nativeQuery = true)
    List<String> selectRoleNameByUrl(String Url);
}