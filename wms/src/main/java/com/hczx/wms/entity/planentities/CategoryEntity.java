package com.hczx.wms.entity.planentities;

/**
 * @ClassName: CategoryEntity
 * @Description: 柜内设备
 * @Date: 2020/9/16 10:55
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class CategoryEntity {

    /**一级设备所在行数*/
    private Integer rowNum;

    /**一级设备所在列数*/
    private Integer columnNum;

    /**一级设备唯一标识*/
    private String equipmentIdLv1;

    /**一级设备名称*/
    private String equipmentNameLv1;

    /**一级设备选中状态*/
    private String equipmentSelectedLv1;

    /**一级设备入库状态*/
    private String equipmentInLv1;

    /**二级设备数量*/
    private Integer equipmentLv2Number;

    /**二级设备出库数量*/
    private Integer equipmentLv2OutNumber;

    /**一级设备入库数量*/
    private Integer equipmentLv2InNumber;

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

    public String getEquipmentInLv1() {
        return equipmentInLv1;
    }

    public void setEquipmentInLv1(String equipmentInLv1) {
        this.equipmentInLv1 = equipmentInLv1;
    }

    public Integer getEquipmentLv2Number() {
        return equipmentLv2Number;
    }

    public void setEquipmentLv2Number(Integer equipmentLv2Number) {
        this.equipmentLv2Number = equipmentLv2Number;
    }

    public Integer getEquipmentLv2OutNumber() {
        return equipmentLv2OutNumber;
    }

    public void setEquipmentLv2OutNumber(Integer equipmentLv2OutNumber) {
        this.equipmentLv2OutNumber = equipmentLv2OutNumber;
    }

    public Integer getEquipmentLv2InNumber() {
        return equipmentLv2InNumber;
    }

    public void setEquipmentLv2InNumber(Integer equipmentLv2InNumber) {
        this.equipmentLv2InNumber = equipmentLv2InNumber;
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
