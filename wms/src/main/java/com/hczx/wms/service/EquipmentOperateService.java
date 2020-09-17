package com.hczx.wms.service;

import com.hczx.wms.entity.common.DataGirdResultEntity;
import com.hczx.wms.entity.common.WmsOperateResponseEntity;
import com.hczx.wms.entity.enquipmententities.EquipmentInfoEntity;
import com.hczx.wms.entity.enquipmententities.EquipmentZtreeEntity;

import java.util.List;

/**
 * @ClassName: EquipmentService
 * @Description: 设备管理接口
 * @Date: 2020/9/1 15:37
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public interface EquipmentOperateService {

    /**
     * 简易登记设备
     *
     * @param equipmentInfoEntity
     * @return
     */
    WmsOperateResponseEntity registEquipment(EquipmentInfoEntity equipmentInfoEntity);

    /**
     * 查询设备信息
     *
     * @param page
     * @param size
     * @param equipmentInfoEntity
     * @return
     */
    DataGirdResultEntity listEquipment(Integer page, Integer size, EquipmentInfoEntity equipmentInfoEntity);

    /**
     * 删除设备
     *
     * @param equipmentInfoEntities
     * @return
     */
    WmsOperateResponseEntity delEquipment(List<EquipmentInfoEntity> equipmentInfoEntities);

    /**
     * 编辑设备
     *
     * @param equipmentInfoEntity
     * @return
     */
    WmsOperateResponseEntity editEquipment(EquipmentInfoEntity equipmentInfoEntity);

    /**
     * 封装树形装备信息
     *
     * @return
     */
    EquipmentZtreeEntity ListEquipmentZtree();

    /**
     * 根据上级设备id查询二级设备
     *
     * @param equipmentParentId
     * @return
     */
    WmsOperateResponseEntity listEquipmentsLv2(String equipmentParentId);
}
