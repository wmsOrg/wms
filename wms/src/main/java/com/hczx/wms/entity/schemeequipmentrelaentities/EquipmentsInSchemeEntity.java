package com.hczx.wms.entity.schemeequipmentrelaentities;

/**
 * @ClassName: EquipmentsInSchemeEntity
 * @Description: 方案所绑定的设备
 * @Date: 2020/9/11 10:21
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class EquipmentsInSchemeEntity {

    /**预案名称*/
    private String planId;

    /**警情唯一标识*/
    private String alarmingId;

    /**方案唯一标识*/
    private String schemeId;

    /**方案名称*/
    private String schemeName;

    /**方案最大行数*/
    private Integer maxRows;

    /**方案最大列数*/
    private Integer maxColumns;

    /**方案设备关联表唯一标识*/
    private String serId;

    /**设备所在行*/
    private Integer rowNum;

    /**设备所在列*/
    private Integer columnNum;

    /**设备唯一标识*/
    private String equipmentId;

    /**设备Rfid*/
    private String equipmentRfid;

    /**设备名称*/
    private String equipmentName;

    /**设备分类*/
    private String equipmentClass;

    /**设备分类名称*/
    private String equipmentClassName;

    /**设备公司唯一标识*/
    private String equipmentCompanyId;

    /**设备公司名称*/
    private String equipmentCompanyName;

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
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

    public String getSerId() {
        return serId;
    }

    public void setSerId(String serId) {
        this.serId = serId;
    }

    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public Integer getColumnNum() {
        return columnNum;
    }

    public void setColumnNum(Integer columnNum) {
        this.columnNum = columnNum;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

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

    public String getEquipmentClass() {
        return equipmentClass;
    }

    public void setEquipmentClass(String equipmentClass) {
        this.equipmentClass = equipmentClass;
    }

    public String getEquipmentClassName() {
        return equipmentClassName;
    }

    public void setEquipmentClassName(String equipmentClassName) {
        this.equipmentClassName = equipmentClassName;
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

    public String getAlarmingId() {
        return alarmingId;
    }

    public void setAlarmingId(String alarmingId) {
        this.alarmingId = alarmingId;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }
}
