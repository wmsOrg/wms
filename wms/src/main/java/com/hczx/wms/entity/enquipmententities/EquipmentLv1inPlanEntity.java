package com.hczx.wms.entity.enquipmententities;

/**
 * @ClassName: EquipmentLv1inPlan
 * @Description: 预案中一级设备实体
 * @Date: 2020/9/16 09:39
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class EquipmentLv1inPlanEntity {

    /**总行数*/
    private Integer totalRowNums;

    /**总行数*/
    private Integer totalColumnNums;

    /**行数*/
    private Integer rowNum;

    /**行数*/
    private Integer columnNum;

    /**预案中一级设备唯一标识*/
    private String equipmentIdLv1;

    /**预案中一级设备RFID标识*/
    private String equipmentRfidLv1;

    /**预案中一级设备名称*/
    private String equipmentNameLv1;

    /**预案中一级设备出入库状态*/
    private String equipmentInLv1;

    /**预案中一级设备选中状态*/
    private String equipmentSelectedLv1;

    /**预案中二级设备唯一标识*/
    private String equipmentIdLv2;

    /**预案中二级设备RFID标识*/
    private String equipmentRfidLv2;

    /**预案中二级设备名称*/
    private String equipmentNameLv2;

    /**预案中二级设备出入库状态*/
    private String equipmentInLv2;

    public Integer getTotalRowNums() {
        return totalRowNums;
    }

    public void setTotalRowNums(Integer totalRowNums) {
        this.totalRowNums = totalRowNums;
    }

    public Integer getTotalColumnNums() {
        return totalColumnNums;
    }

    public void setTotalColumnNums(Integer totalColumnNums) {
        this.totalColumnNums = totalColumnNums;
    }

    public String getEquipmentIdLv1() {
        return equipmentIdLv1;
    }

    public void setEquipmentIdLv1(String equipmentIdLv1) {
        this.equipmentIdLv1 = equipmentIdLv1;
    }

    public String getEquipmentNameLv1() {
        return equipmentNameLv1;
    }

    public void setEquipmentNameLv1(String equipmentNameLv1) {
        this.equipmentNameLv1 = equipmentNameLv1;
    }

    public String getEquipmentSelectedLv1() {
        return equipmentSelectedLv1;
    }

    public void setEquipmentSelectedLv1(String equipmentSelectedLv1) {
        this.equipmentSelectedLv1 = equipmentSelectedLv1;
    }

    public String getEquipmentRfidLv1() {
        return equipmentRfidLv1;
    }

    public void setEquipmentRfidLv1(String equipmentRfidLv1) {
        this.equipmentRfidLv1 = equipmentRfidLv1;
    }

    public String getEquipmentIdLv2() {
        return equipmentIdLv2;
    }

    public void setEquipmentIdLv2(String equipmentIdLv2) {
        this.equipmentIdLv2 = equipmentIdLv2;
    }

    public String getEquipmentRfidLv2() {
        return equipmentRfidLv2;
    }

    public void setEquipmentRfidLv2(String equipmentRfidLv2) {
        this.equipmentRfidLv2 = equipmentRfidLv2;
    }

    public String getEquipmentNameLv2() {
        return equipmentNameLv2;
    }

    public void setEquipmentNameLv2(String equipmentNameLv2) {
        this.equipmentNameLv2 = equipmentNameLv2;
    }

    public String getEquipmentInLv1() {
        return equipmentInLv1;
    }

    public void setEquipmentInLv1(String equipmentInLv1) {
        this.equipmentInLv1 = equipmentInLv1;
    }

    public String getEquipmentInLv2() {
        return equipmentInLv2;
    }

    public void setEquipmentInLv2(String equipmentInLv2) {
        this.equipmentInLv2 = equipmentInLv2;
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
}
