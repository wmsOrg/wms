package com.hczx.wms.mybatisplusserveice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hczx.wms.entity.schemeequipmentrelaentities.EquipmentsInSchemeEntity;
import com.hczx.wms.model.AlarmSchemeEquipmentRelaModel;
import com.hczx.wms.model.PlanModel;

import java.util.List;

/**
 * @ClassName: AlarmSchemeEquipmentRelaService
 * @Description: 警情方案设备关联服务接口
 * @Date: 2020/9/11 15:58
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public interface AlarmSchemeEquipmentRelaService extends IService<AlarmSchemeEquipmentRelaModel> {

    /**
     * 警情绑定方案中的设备
     *
     * @param planModel
     * @param alarmSchemeEquipmentRelaModels
     * @return
     */
    boolean BindEquipmentsInScheme(PlanModel planModel, List<AlarmSchemeEquipmentRelaModel> alarmSchemeEquipmentRelaModels);
}
