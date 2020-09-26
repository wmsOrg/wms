package com.hczx.wms.entity.authenticationentities;

import java.util.Objects;

/**
 * @ClassName: UserPermissionEntity
 * @Description: 用户权限实体
 * @Date: 2020/9/25 14:36
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class UserPermissionEntity {

    /**用户id*/
    private String userId;

    /**简称*/
    private String nickname;

    /**角色id*/
    private String roleId;

    /**角色名称*/
    private String roleName;

    /**菜单编码*/
    private String menuCode;

    /**权限编码*/
    private String permissionCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPermissionEntity that = (UserPermissionEntity) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(nickname, that.nickname) &&
                Objects.equals(roleId, that.roleId) &&
                Objects.equals(roleName, that.roleName) &&
                Objects.equals(menuCode, that.menuCode) &&
                Objects.equals(permissionCode, that.permissionCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, nickname, roleId, roleName, menuCode, permissionCode);
    }
}
