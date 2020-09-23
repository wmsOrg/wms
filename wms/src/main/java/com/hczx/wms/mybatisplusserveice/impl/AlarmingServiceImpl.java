package com.hczx.wms.mybatisplusserveice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hczx.wms.dao.AlarmingDao;
import com.hczx.wms.entity.alarmingentities.AlarmingInfoEntity;
import com.hczx.wms.model.AlarmingModel;
import com.hczx.wms.model.EquipmentModel;
import com.hczx.wms.mybatisplusserveice.AlarmingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName: AlarmingService
 * @Description: 警情服务层实现
 * @Date: 2020/9/10 10:46
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Service
public class AlarmingServiceImpl extends ServiceImpl<AlarmingDao, AlarmingModel> implements AlarmingService {
    private Logger logger = LoggerFactory.getLogger(AlarmingServiceImpl.class);

    @Autowired
    private AlarmingDao alarmingDao;

    /**
     * 登记警情
     *
     * @param alarmingInfoEntity
     * @return
     */
    @Transactional
    @Override
    public boolean saveRegisterEquipment(AlarmingInfoEntity alarmingInfoEntity) {
        AlarmingModel alarmingModel = new AlarmingModel();

        alarmingModel.setId(UUID.randomUUID().toString());
        alarmingModel.setName(alarmingInfoEntity.getName());
        alarmingModel.setLevel(String.valueOf(alarmingInfoEntity.getLevel()));
        alarmingModel.setCategory(alarmingInfoEntity.getCategory());
        alarmingModel.setDiscribetion(alarmingInfoEntity.getDiscribetion());
        alarmingModel.setValidState(alarmingInfoEntity.getValidState());
        alarmingModel.setCreateUserId(alarmingInfoEntity.getCreateUserId());
        alarmingModel.setCreateUserName(alarmingInfoEntity.getCreateUserName());
        alarmingModel.setCreateTime(new Date());

        boolean flag = true;
        //新增用户
        try{
            flag = alarmingModel.insert();
        }catch (Exception e){
            logger.error("登记警情异常：",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }


        return flag;
    }

    /**
     * 编辑警情
     *
     * @param alarmingInfoEntity
     * @return
     */
    @Transactional
    @Override
    public boolean editAlarming(AlarmingInfoEntity alarmingInfoEntity) {
        AlarmingModel alarmingModel = new AlarmingModel();

        alarmingModel.setId(alarmingInfoEntity.getId());
        alarmingModel.setName(alarmingInfoEntity.getName());
        alarmingModel.setLevel(String.valueOf(alarmingInfoEntity.getLevel()));
        alarmingModel.setCategory(alarmingInfoEntity.getCategory());
        alarmingModel.setDiscribetion(alarmingInfoEntity.getDiscribetion());
        alarmingModel.setValidState(alarmingInfoEntity.getValidState());

        boolean flag = true;
        //新增用户
        try{
            flag = alarmingModel.updateById();
        }catch (Exception e){
            logger.error("编辑警情异常：",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }


        return flag;
    }

    /**
     * 作废警情
     *
     * @param ids
     * @return
     */
    @Transactional
    @Override
    public boolean delAlarming(List<String> ids) {

        boolean flag = true;
        //新增用户
        try{
            flag = alarmingDao.updateValidStateByIds(ids);
        }catch (Exception e){
            logger.error("作废警情异常：",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }


        return flag;
    }
}
