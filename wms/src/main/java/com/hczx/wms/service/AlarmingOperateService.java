package com.hczx.wms.service;

import com.hczx.wms.entity.alarmingentities.AlarmingInfoEntity;
import com.hczx.wms.entity.common.DataGirdResultEntity;
import com.hczx.wms.entity.common.WmsOperateResponseEntity;
import com.hczx.wms.entity.enquipmententities.EquipmentInfoEntity;
import com.hczx.wms.entity.planentities.PlanContentQueryEntity;
import com.hczx.wms.entity.schemeequipmentrelaentities.EquipmentsInSchemeEntity;

import java.util.List;

/**
 * @ClassName: AlarmingService
 * @Description: 警情操作接口
 * @Date: 2020/9/10 10:33
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public interface AlarmingOperateService {

    /**
     * 查询警情信息
     *
     * @param page
     * @param size
     * @param alarmingInfoEntity
     * @return
     */
    DataGirdResultEntity listAlarmInfo(Integer page, Integer size, AlarmingInfoEntity alarmingInfoEntity);

    /**
     * 警情绑定对应的方案中的设备
     *
     * @param planName
     * @param alarmId
     * @param schemeId
     * @param equipmentsInSchemeEntities
     * @return
     */
    WmsOperateResponseEntity alarmingBindEquipmentsInScheme(String planName, String alarmId, String schemeId, List<EquipmentsInSchemeEntity> equipmentsInSchemeEntities);

    /**
     * 方案所绑定的设备查询
     *
     * @param planQueryEntity
     * @return
     */
    WmsOperateResponseEntity planList(PlanContentQueryEntity planQueryEntity);
}
