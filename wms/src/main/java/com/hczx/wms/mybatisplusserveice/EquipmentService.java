package com.hczx.wms.mybatisplusserveice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hczx.wms.entity.enquipmententities.EquipmentInfoEntity;
import com.hczx.wms.model.EquipmentModel;

import java.util.List;

/**
 * @ClassName: EquipmentService
 * @Description: 设备服务层接口
 * @Date: 2020/9/1 10:13
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public interface EquipmentService extends IService<EquipmentModel> {

    /**
     * 简易登记设备
     *
     * @param equipmentInfoEntity
     * @return
     */
    boolean saveRegisterEquipment(EquipmentInfoEntity equipmentInfoEntity);

    /**
     * 根据id集合批量删除设备
     *
     * @param ids
     * @return
     */
    boolean removeEquipmentByIds(List<String> ids);

    /**
     * 根据id更新设备信息
     *
     * @param equipmentInfoEntity
     * @return
     */
    boolean updateEditEquipment(EquipmentInfoEntity equipmentInfoEntity);

    /**
     * 根据Rfid集合批量更新入库状态
     *
     * @param inboundState
     * @param equipmenmtRfids
     */
    boolean updateInboundStateBatchByRfids(String inboundState, List<String> equipmenmtRfids);

    /**
     * 根据id集合关联设备
     * @param ids
     * @param linkNo
     * @return
     */
    boolean linkEquipmentByIds(List<String> ids, String linkNo);
}
