package com.hczx.wms.mybatisplusserveice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hczx.wms.entity.alarmingentities.AlarmingInfoEntity;
import com.hczx.wms.model.AlarmingModel;

import java.util.List;

/**
 * @ClassName: AlarmingService
 * @Description: 警情服务层接口
 * @Date: 2020/9/10 10:46
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public interface AlarmingService extends IService<AlarmingModel> {

    /**
     * 登记警情
     *
     * @param alarmingInfoEntity
     * @return
     */
    boolean saveRegisterEquipment(AlarmingInfoEntity alarmingInfoEntity);

    /**
     * 编辑警情
     *
     * @param alarmingInfoEntity
     * @return
     */
    boolean editAlarming(AlarmingInfoEntity alarmingInfoEntity);

    /**
     * 作废警情
     *
     * @param ids
     * @return
     */
    boolean delAlarming(List<String> ids);
}
