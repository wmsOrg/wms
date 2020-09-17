package com.hczx.wms.entity.schemeentities;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @ClassName: SchemeIncreasEntity
 * @Description: 方案新增实体
 * @Date: 2020/9/9 10:34
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class SchemeIncreaseEntity {

    private Integer page;

    private Integer size;

    private String id;

    /**方案名称*/
    private String schemeName;

    /**生效状态*/
    private String validState;

    /**级别*/
    private String level;

    /**灾情类别*/
    private String disaster;

    private String createUserId;

    private String createUserName;

    private String createTime;

    /**页面地址*/
    private String imageUrl;

    /**最大层数*/
    private Integer maxRows;

    /**最大列数*/
    private Integer maxColumns;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
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
}
