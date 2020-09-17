package com.hczx.wms.entity.enquipmententities;

import com.hczx.wms.model.EquipmentModel;

import java.util.List;

/**
 * @ClassName: EquipmentLv2InboundStateEntity
 * @Description: 二级设备出入库状态
 * @Date: 2020/9/16 17:09
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class EquipmentLv2InboundStateEntity {

    /**上级设备Id*/
    private String equipmentParentId;

    /**二级已出库设备*/
    private List<EquipmentModel> outEquipmentModelLv2List;

    /**二级已入库设备*/
    private List<EquipmentModel> inEquipmentModelLv2List;

    public String getEquipmentParentId() {
        return equipmentParentId;
    }

    public void setEquipmentParentId(String equipmentParentId) {
        this.equipmentParentId = equipmentParentId;
    }

    public List<EquipmentModel> getOutEquipmentModelLv2List() {
        return outEquipmentModelLv2List;
    }

    public void setOutEquipmentModelLv2List(List<EquipmentModel> outEquipmentModelLv2List) {
        this.outEquipmentModelLv2List = outEquipmentModelLv2List;
    }

    public List<EquipmentModel> getInEquipmentModelLv2List() {
        return inEquipmentModelLv2List;
    }

    public void setInEquipmentModelLv2List(List<EquipmentModel> inEquipmentModelLv2List) {
        this.inEquipmentModelLv2List = inEquipmentModelLv2List;
    }
}
