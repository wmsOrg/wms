package com.hczx.wms.entity.enquipmententities;

import com.baomidou.mybatisplus.annotation.TableField;

/**
 * @ClassName: EquipmentIncreaseQueryEntity
 * @Description: 设备新增请求实体
 * @Date: 2020/9/1 10:35
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class EquipmentInfoEntity {

//    private Integer page;
//
//    private Integer limit;

    /**设备ID*/
    private String id;

    /**设备ID*/
    private String equipmentRfid;

    /**设备名称*/
    private String equipmentName;

    /**设备等级*/
    private String equipmentGrade;

    /**设备分类*/
    private String equipmentClass;

    /**设备种类名称*/
    private String equipmentClassName;

    /**设备上级id*/
    private String equipmentPreId;

    /**设备所属公司id*/
    private String equipmentCompanyId;

    /**设备所属公司名称*/
    private String equipmentCompanyName;

//    /**方案id*/
//    private String schemeId;
//
//    /**方案名*/
//    private String schemeName;

    /**关联号*/
    private String linkingNo;

    /**生效状态  0：失效   1：生效*/
    private String validState;

    /**占用状态 0：不占用   1：占用*/
    private String occupyState;

    /**入库状态 0：未定义 1：入库  -1：出库*/
    private String inboundState;

    public String getEquipmentRfid() {
        return equipmentRfid;
    }

    public void setEquipmentRfid(String equipmentRfid) {
        this.equipmentRfid = equipmentRfid;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentGrade() {
        return equipmentGrade;
    }

    public void setEquipmentGrade(String equipmentGrade) {
        this.equipmentGrade = equipmentGrade;
    }

    public String getEquipmentClass() {
        return equipmentClass;
    }

    public void setEquipmentClass(String equipmentClass) {
        this.equipmentClass = equipmentClass;
    }

    public String getEquipmentPreId() {
        return equipmentPreId;
    }

    public void setEquipmentPreId(String equipmentPreId) {
        this.equipmentPreId = equipmentPreId;
    }

    public String getEquipmentCompanyId() {
        return equipmentCompanyId;
    }

    public void setEquipmentCompanyId(String equipmentCompanyId) {
        this.equipmentCompanyId = equipmentCompanyId;
    }

    public String getEquipmentCompanyName() {
        return equipmentCompanyName;
    }

    public void setEquipmentCompanyName(String equipmentCompanyName) {
        this.equipmentCompanyName = equipmentCompanyName;
    }

    public String getLinkingNo() {
        return linkingNo;
    }

    public void setLinkingNo(String linkingNo) {
        this.linkingNo = linkingNo;
    }

    public String getValidState() {
        return validState;
    }

    public void setValidState(String validState) {
        this.validState = validState;
    }

    public String getOccupyState() {
        return occupyState;
    }

    public void setOccupyState(String occupyState) {
        this.occupyState = occupyState;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEquipmentClassName() {
        return equipmentClassName;
    }

    public void setEquipmentClassName(String equipmentClassName) {
        this.equipmentClassName = equipmentClassName;
    }

    public String getInboundState() {
        return inboundState;
    }

    public void setInboundState(String inboundState) {
        this.inboundState = inboundState;
    }

    //    public Integer getPage() {
//        return page;
//    }
//
//    public void setPage(Integer page) {
//        this.page = page;
//    }
//
//    public Integer getLimit() {
//        return limit;
//    }
//
//    public void setLimit(Integer limit) {
//        this.limit = limit;
//    }
}
