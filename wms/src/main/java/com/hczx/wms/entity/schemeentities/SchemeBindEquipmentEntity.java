package com.hczx.wms.entity.schemeentities;

/**
 * @ClassName: SchemeBindEquipmentEntity
 * @Description: 方案绑定设备实体
 * @Date: 2020/9/9 15:05
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class SchemeBindEquipmentEntity {

    private Integer rowCounts;

    private Integer columnCounts;

    private String companyId;

    private String equipmentId;

    private String equipmentName;

    public Integer getRowCounts() {
        return rowCounts;
    }

    public void setRowCounts(Integer rowCounts) {
        this.rowCounts = rowCounts;
    }

    public Integer getColumnCounts() {
        return columnCounts;
    }

    public void setColumnCounts(Integer columnCounts) {
        this.columnCounts = columnCounts;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
}
