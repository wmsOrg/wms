package com.hczx.wms.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: SysPermissionModel
 * @Description: 权限映射实体
 * @Date: 2020/9/25 14:13
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@TableName("sys_permission")
public class SysPermissionModel extends Model<SysPermissionModel> {

    /**主键*/
    @TableId(value = "id")
    private String id;

    /**菜单编码*/
    @TableField("menu_code")
    private String menuCode;

    /**菜单的中文释义*/
    @TableField("menu_name")
    private String menu_name;

    /**权限的代码/通配符,对应代码中@RequiresPermissions 的value*/
    @TableField("permission_code")
    private String permissionCode;

    /**本权限的中文释义*/
    @TableField("permission_name")
    private String permissionName;

    /**是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选*/
    @TableField("required_permission")
    private Integer requiredPermission;

    /**创建时间*/
    @TableField("create_time")
    private Date createTime;

    /**更新时间*/
    @TableField("update_time")
    private Date updateTime;

    /**生效标识 1：生效 0：失效*/
    @TableField("valid_status")
    private String validStatus;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public Integer getRequiredPermission() {
        return requiredPermission;
    }

    public void setRequiredPermission(Integer requiredPermission) {
        this.requiredPermission = requiredPermission;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(String validStatus) {
        this.validStatus = validStatus;
    }
}
