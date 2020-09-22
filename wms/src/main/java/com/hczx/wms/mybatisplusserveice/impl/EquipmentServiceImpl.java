package com.hczx.wms.mybatisplusserveice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hczx.wms.dao.EquipmentDao;
import com.hczx.wms.entity.enquipmententities.EquipmentInfoEntity;
import com.hczx.wms.model.EquipmentModel;
import com.hczx.wms.mybatisplusserveice.EquipmentService;
import org.apache.commons.lang3.StringUtils;
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
 * @ClassName: EquipmentServiceImpl
 * @Description: 设备服务层实现
 * @Date: 2020/9/1 10:14
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentDao, EquipmentModel> implements EquipmentService {

    private Logger logger = LoggerFactory.getLogger(EquipmentServiceImpl.class);

    @Autowired
    private EquipmentDao equipmentDao;

    /**
     * 简易登记设备
     *
     * @param equipmentInfoEntity
     * @return
     */
    @Override
    @Transactional
    public boolean saveRegisterEquipment(EquipmentInfoEntity equipmentInfoEntity) {

        EquipmentModel equipmentModel = new EquipmentModel();

        equipmentModel.setId(UUID.randomUUID().toString());
        equipmentModel.setEquipmentRfid(equipmentInfoEntity.getEquipmentRfid());
        equipmentModel.setEquipmentName(equipmentInfoEntity.getEquipmentName());
        equipmentModel.setEquipmentGrade(equipmentInfoEntity.getEquipmentGrade());
        equipmentModel.setEquipmentClass(equipmentInfoEntity.getEquipmentClass());
        equipmentModel.setEquipmentClassName(equipmentInfoEntity.getEquipmentClassName());
        equipmentModel.setEquipmentPreId(equipmentInfoEntity.getEquipmentPreId());
        equipmentModel.setEquipmentCompanyId(equipmentInfoEntity.getEquipmentCompanyId());
        equipmentModel.setEquipmentCompanyName(equipmentInfoEntity.getEquipmentCompanyName());
        equipmentModel.setValidState(equipmentInfoEntity.getValidState());
        equipmentModel.setOccupyState("0");
        equipmentModel.setEquipmentClass(equipmentInfoEntity.getEquipmentClass());
        equipmentModel.setInboundState("0");
        equipmentModel.setCreateTime(new Date());

        boolean flag = true;
        //新增用户
        try{
            flag = equipmentModel.insert();
        }catch (Exception e){
            logger.error("登记设备异常：",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }


        return flag;
    }

    /**
     * 更新设备
     *
     * @param equipmentInfoEntity
     * @return
     */
    @Override
    @Transactional
    public boolean updateEditEquipment(EquipmentInfoEntity equipmentInfoEntity) {

        EquipmentModel equipmentModel = new EquipmentModel();

        equipmentModel.setId(equipmentInfoEntity.getId());
        equipmentModel.setEquipmentRfid(equipmentInfoEntity.getEquipmentRfid());
        equipmentModel.setEquipmentName(equipmentInfoEntity.getEquipmentName());
        equipmentModel.setEquipmentGrade(equipmentInfoEntity.getEquipmentGrade());
        equipmentModel.setEquipmentClass(equipmentInfoEntity.getEquipmentClass());
        equipmentModel.setEquipmentClassName(equipmentInfoEntity.getEquipmentClassName());
        equipmentModel.setEquipmentPreId(equipmentInfoEntity.getEquipmentPreId());
        equipmentModel.setEquipmentCompanyId(equipmentInfoEntity.getEquipmentCompanyId());
        equipmentModel.setEquipmentCompanyName(equipmentInfoEntity.getEquipmentCompanyName());
        equipmentModel.setValidState(equipmentInfoEntity.getValidState());
        if (!StringUtils.isBlank(equipmentInfoEntity.getEquipmentPreId())) {
            equipmentModel.setOccupyState("1");
        }else{
            equipmentModel.setOccupyState("0");
        }
        equipmentModel.setEquipmentClass(equipmentInfoEntity.getEquipmentClass());
        equipmentModel.setInboundState(equipmentInfoEntity.getInboundState());
        equipmentModel.setEditTime(new Date());

        boolean flag = true;
        //新增用户
        try{
            flag = equipmentModel.updateById();
        }catch (Exception e){
            logger.error("编辑设备异常：",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }


        return flag;
    }

    /**
     * 根据Rfid集合批量更新入库状态
     *
     * @param inboundState
     * @param equipmenmtRfids
     */
    @Transactional
    @Override
    public boolean updateInboundStateBatchByRfids(String inboundState, List<String> equipmenmtRfids) {


        boolean flag = true;
        //批量更新入库状态
        try{
            flag = equipmentDao.updateInboundStateBatchByRfids(inboundState, equipmenmtRfids);
        }catch (Exception e){
            logger.error("根据Rfid集合批量更新入库状态：",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }


        return flag;
    }

    /**
     * 根据id集合批量删除设备
     *
     * @param ids
     * @return
     */
    @Override
    @Transactional
    public boolean removeEquipmentByIds(List<String> ids) {
        boolean flag = true;
        //更改设备状态
        try{
            flag = equipmentDao.nullifyByIds(ids);
        }catch (Exception e){
            logger.error("删除设备异常：",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }


        return flag;
    }

    /**
     * 根据id集合关联设备
     *
     * @param ids
     * @param linkNo
     * @return
     */
    @Override
    @Transactional
    public boolean linkEquipmentByIds(List<String> ids, String linkNo) {
        boolean flag = true;
        //更新设备关联号
        try{
            flag = equipmentDao.linkEquipmentByIds(ids, linkNo);
        }catch (Exception e){
            logger.error("删除设备异常：",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }


        return flag;
    }
}
