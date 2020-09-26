package com.hczx.wms.mybatisplusserveice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hczx.wms.dao.AlarmSchemeEquipmentRelaDao;
import com.hczx.wms.entity.schemeequipmentrelaentities.EquipmentsInSchemeEntity;
import com.hczx.wms.model.AlarmSchemeEquipmentRelaModel;
import com.hczx.wms.model.PlanModel;
import com.hczx.wms.mybatisplusserveice.AlarmSchemeEquipmentRelaService;
import com.hczx.wms.mybatisplusserveice.PlanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName: AlarmSchemeEquipmentRelaServiceImpl
 * @Description: 警情方案设备关联服务实现接口
 * @Date: 2020/9/11 15:59
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Service
public class AlarmSchemeEquipmentRelaServiceImpl extends ServiceImpl<AlarmSchemeEquipmentRelaDao, AlarmSchemeEquipmentRelaModel> implements AlarmSchemeEquipmentRelaService {

    private Logger logger = LoggerFactory.getLogger(AlarmSchemeEquipmentRelaServiceImpl.class);

    @Lazy
    @Autowired
    private AlarmSchemeEquipmentRelaService alarmSchemeEquipmentRelaService;

    @Autowired
    private PlanService planService;

    /**
     * 警情绑定方案中的设备
     *
     * @param planModel
     * @param alarmSchemeEquipmentRelaModels
     * @return
     */
    @Transactional
    @Override
    public boolean BindEquipmentsInScheme(PlanModel planModel, List<AlarmSchemeEquipmentRelaModel> alarmSchemeEquipmentRelaModels) {

        if (planModel == null){
            logger.warn("无法将警情绑定方案中的设备警告：待新增的预案实体为空");
            return false;
        }

        if (alarmSchemeEquipmentRelaModels == null || alarmSchemeEquipmentRelaModels.isEmpty()){
            logger.warn("无法将警情绑定方案中的设备警告：待批量新增的警情绑定方案实体集合为空");
            return false;
        }

        try {
            planService.save(planModel);
            alarmSchemeEquipmentRelaService.saveBatch(alarmSchemeEquipmentRelaModels);
        }catch (Exception e){
            logger.error("将警情绑定方案中的设备出现新增操作异常：",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;

    }
}
