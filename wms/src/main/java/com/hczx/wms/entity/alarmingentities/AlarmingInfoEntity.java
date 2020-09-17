package com.hczx.wms.entity.alarmingentities;

/**
 * @ClassName: AlarmingInfoEntity
 * @Description: 请求实体
 * @Date: 2020/9/10 10:21
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class AlarmingInfoEntity {

//    private Integer page;
//
//    private Integer limit;

    /**警情ID*/
    private String id;

    /**警情名称*/
    private String name;

    /**警情级别*/
    private Integer level;

    /**警情类别*/
    private String category;

    /**起始时间*/
    private String beginTime;

    /**结束时间*/
    private String endTime;

    /**警情创建人*/
    private String createUserId;

    /**创建时间*/
    private String createTime;

    /**警情创建人名称*/
    private String createUserName;

    /**生效状态  0：失效   1：生效*/
    private String validState;

    /**描述*/
    private String discribetion;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String getValidState() {
        return validState;
    }

    public void setValidState(String validState) {
        this.validState = validState;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDiscribetion() {
        return discribetion;
    }

    public void setDiscribetion(String discribetion) {
        this.discribetion = discribetion;
    }
}
