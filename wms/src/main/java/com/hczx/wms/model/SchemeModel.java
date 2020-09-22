package com.hczx.wms.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: SchemeModel
 * @Description: 方案实体
 * @Date: 2020/9/9 10:50
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@TableName(value = "scheme")
public class SchemeModel extends Model<SchemeModel> {

    /**主键，UUID*/
    @TableId(value = "id")
    private String id;

    /**方案名称*/
    @TableField(value = "schemeName")
    private String schemeName;

    /**是否生效 1：生效 0：失效*/
    @TableField(value = "validState")
    private String validState;

    /**是否启用 1：启动 0：关闭*/
    @TableField(value = "enableState")
    private String enableState;

    /**创建人id*/
    @TableField(value = "createUserId")
    private String createUserId;

    /**创建人名*/
    @TableField(value = "createUserName")
    private String createUserName;

    /**级别  1：一级 2：二级 ...*/
    @TableField(value = "level")
    private String level;

    /**灾害类型  0：地震  1：火灾  2：洪灾*/
    @TableField(value = "disaster")
    private String disaster;

    /**灾害类型  0：地震  1：火灾  2：洪灾*/
    @TableField(value = "disasterName")
    private String disasterName;

    /**图片路径*/
    @TableField(value = "imageUrl")
    private String imageUrl;

    /**创建时间*/
    @TableField(value = "createTime")
    private Date createTime;

    /**最大层数*/
    @TableField(value = "maxRows")
    private Integer maxRows;

    /**最大列数*/
    @TableField(value = "maxColumns")
    private Integer maxColumns;


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

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getValidState() {
        return validState;
    }

    public void setValidState(String validState) {
        this.validState = validState;
    }

    public String getEnableState() {
        return enableState;
    }

    public void setEnableState(String enableState) {
        this.enableState = enableState;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDisaster() {
        return disaster;
    }

    public void setDisaster(String disaster) {
        this.disaster = disaster;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(Integer maxRows) {
        this.maxRows = maxRows;
    }

    public Integer getMaxColumns() {
        return maxColumns;
    }

    public void setMaxColumns(Integer maxColumns) {
        this.maxColumns = maxColumns;
    }

    public String getDisasterName() {
        return disasterName;
    }

    public void setDisasterName(String disasterName) {
        this.disasterName = disasterName;
    }
}
