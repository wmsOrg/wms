package com.hczx.wms.entity.authenticationentities;

import java.util.Map;
import java.util.Set;

/**
 * @ClassName: UserAllPermissionEntity
 * @Description: 用户所拥有的所有权限
 * @Date: 2020/9/25 15:01
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class UserAllPermissionEntity {

    private String userId;

    private String nickName;

    private Map<String,String> role;

    private Set<String> menuList;

    private Set<String> permissionList;



    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Map<String, String> getRole() {
        return role;
    }

    public void setRole(Map<String, String> role) {
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<String> getMenuList() {
        return menuList;
    }

    public void setMenuList(Set<String> menuList) {
        this.menuList = menuList;
    }

    public Set<String> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(Set<String> permissionList) {
        this.permissionList = permissionList;
    }
}
