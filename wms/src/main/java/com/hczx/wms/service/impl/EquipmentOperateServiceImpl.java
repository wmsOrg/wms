package com.hczx.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hczx.wms.dao.CompanyDao;
import com.hczx.wms.dao.EquipmentDao;
import com.hczx.wms.entity.common.DataGirdResultEntity;
import com.hczx.wms.entity.common.WmsOperateResponseEntity;
import com.hczx.wms.entity.enquipmententities.EquipmentInfoEntity;
import com.hczx.wms.entity.enquipmententities.EquipmentLv2InboundStateEntity;
import com.hczx.wms.entity.enquipmententities.EquipmentZtreeEntity;
import com.hczx.wms.entity.enquipmententities.EquipmentZtreeNode;
import com.hczx.wms.model.EquipmentModel;
import com.hczx.wms.mybatisplusserveice.EquipmentService;
import com.hczx.wms.service.AuthenticationService;
import com.hczx.wms.service.EquipmentOperateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: EquipmentServiceImpl
 * @Description:
 * @Date: 2020/9/1 15:38
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Service
public class EquipmentOperateServiceImpl implements EquipmentOperateService {

    private Logger logger = LoggerFactory.getLogger(EquipmentOperateServiceImpl.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private EquipmentDao equipmentDao;

    @Autowired
    private EquipmentService equipmentService;

    @Autowired
    private CompanyDao companyDao;

    /**
     * 简易登记设备
     *
     * @param equipmentInfoEntity
     * @return
     */
    @Override
    public WmsOperateResponseEntity registEquipment(EquipmentInfoEntity equipmentInfoEntity) {

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        wmsOperateResponseEntity = valideEquipmentRegistInfo(equipmentInfoEntity);

        //新增设备信息
        if (wmsOperateResponseEntity != null && wmsOperateResponseEntity.isFlag()){
            boolean flag = equipmentService.saveRegisterEquipment(equipmentInfoEntity);
            if (!flag){

                wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "设备登记失败：新增设备信息至数据库失败！");
                return wmsOperateResponseEntity;

            }else{

                wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "设备登记成功！");
                return wmsOperateResponseEntity;

            }
        }else{
            return wmsOperateResponseEntity;
        }
    }

    /**
     * 编辑设备信息
     *
     * @param equipmentInfoEntity
     * @return
     */
    @Override
    public WmsOperateResponseEntity editEquipment(EquipmentInfoEntity equipmentInfoEntity) {

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        //编辑设备信息
        boolean flag = equipmentService.updateEditEquipment(equipmentInfoEntity);
        if (!flag){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "设备登记失败：新增设备信息至数据库失败！");
            return wmsOperateResponseEntity;

        }else{

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "设备登记成功！");
            return wmsOperateResponseEntity;

        }

    }

    /**
     * 查询设备信息
     *
     * @param page
     * @param size
     * @param equipmentInfoEntity
     * @return
     */
    @Override
    public DataGirdResultEntity listEquipment(Integer page, Integer size, EquipmentInfoEntity equipmentInfoEntity) {


        List<EquipmentInfoEntity> equipmentInfoEntities  = null;
        Long totalCount = 0L;
        try {
            equipmentInfoEntities = equipmentDao.listEquipmentInfo(page, size, equipmentInfoEntity);

            totalCount = equipmentDao.countEquipmentInfo(equipmentInfoEntity);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("查询设备信息异常：",e);
            equipmentInfoEntities = new ArrayList<>();
            return DataGirdResultEntity.error(e.getMessage(),equipmentInfoEntities,Long.valueOf(0));
        }

        if (equipmentInfoEntities == null || equipmentInfoEntities.isEmpty()){
            equipmentInfoEntities = new ArrayList<>();
        }

        return DataGirdResultEntity.success(equipmentInfoEntities, totalCount);
    }

    /**
     * 删除设备
     *
     * @param equipmentInfoEntities
     * @return
     */
    @Override
    public WmsOperateResponseEntity delEquipment(List<EquipmentInfoEntity> equipmentInfoEntities) {

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();



        //删除设备
        List<String> ids = equipmentInfoEntities.stream().map(EquipmentInfoEntity::getId).distinct().collect(Collectors.toList());
            boolean flag = equipmentService.removeEquipmentByIds(ids);
            if (!flag){

                wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "设备删除失败：从数据库删除该设备失败！");
                return wmsOperateResponseEntity;

            }else{

                wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "设备删除成功！");
                return wmsOperateResponseEntity;

            }


    }

    /**
     * 验证设备注册信息
     *
     * @param equipmentInfoEntity
     * @return
     */
    private WmsOperateResponseEntity valideEquipmentRegistInfo(EquipmentInfoEntity equipmentInfoEntity){

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        //拼装设备号条件构造器
        QueryWrapper<EquipmentModel> equipmentModelQueryWrapper = new QueryWrapper<>();
        equipmentModelQueryWrapper.eq("equipmentRfid", equipmentInfoEntity.getEquipmentRfid());
        //设备号是否已被注册
        List<EquipmentModel> equipmentModels = equipmentDao.selectList(equipmentModelQueryWrapper);
        if (equipmentModels != null && !equipmentModels.isEmpty()){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "设备登记失败：设备号已被注册！");
            return wmsOperateResponseEntity;

        }

        wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, null);
        return wmsOperateResponseEntity;

    }

    /**
     * 封装树形装备信息
     *
     * @return
     */
    @Override
    public EquipmentZtreeEntity ListEquipmentZtree() {

        EquipmentZtreeEntity equipmentZtreeEntity = new EquipmentZtreeEntity();

        List<EquipmentZtreeNode> comEquipmentZtreeNodes = companyDao.listComZtreeInfo();
        List<EquipmentZtreeNode> equipmentZtreeNodes = equipmentDao.zTreeEquipmentInfo();

        if (comEquipmentZtreeNodes == null || comEquipmentZtreeNodes.isEmpty()){
            logger.error("封装树形装备信息失败：查询公司结果为空");
            equipmentZtreeEntity.setErrorFlag(true);
            equipmentZtreeEntity.setErrMsg("查询结果为空");
            equipmentZtreeEntity.setData(null);
            return equipmentZtreeEntity;
        }

        if (equipmentZtreeNodes != null && !equipmentZtreeNodes.isEmpty()){
            comEquipmentZtreeNodes.addAll(equipmentZtreeNodes);
            logger.info("封装树形装备信息成功");
            equipmentZtreeEntity.setErrorFlag(false);
            equipmentZtreeEntity.setErrMsg("成功");
            equipmentZtreeEntity.setData(comEquipmentZtreeNodes);
        }


        return equipmentZtreeEntity;

    }

    /**
     * 根据上级设备id查询二级设备
     *
     * @param equipmentParentId
     * @return
     */
    @Override
    public WmsOperateResponseEntity listEquipmentsLv2(String equipmentParentId) {
        WmsOperateResponseEntity wmsOperateResponseEntity =new WmsOperateResponseEntity();
        List<EquipmentModel> outEquipmentModels = new ArrayList<>();
        List<EquipmentModel> inEquipmentModels = new ArrayList<>();
        EquipmentLv2InboundStateEntity equipmentLv2InboundStateEntity = new EquipmentLv2InboundStateEntity();

        try {
            //出库
            QueryWrapper<EquipmentModel> outQueryWrapper = new QueryWrapper<>();
            outQueryWrapper.eq("equipmentPreId", equipmentParentId).eq("validState", "1").eq("inboundState","0");
            outEquipmentModels = equipmentService.list(outQueryWrapper);

            //入库
            QueryWrapper<EquipmentModel> inQueryWrapper = new QueryWrapper<>();
            inQueryWrapper.eq("equipmentPreId", equipmentParentId).eq("validState", "1").eq("inboundState","1");
            inEquipmentModels = equipmentService.list(inQueryWrapper);
        }catch (Exception e){
            logger.error("根据上级设备id查询二级设备发生异常：",e);
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "根据上级设备id查询二级设备发生异常:"+e.getMessage());
            return wmsOperateResponseEntity;
        }

        if (outEquipmentModels == null || outEquipmentModels.isEmpty()){
            if (inEquipmentModels == null || inEquipmentModels.isEmpty()) {
                wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "根据上级设备id查询二级设备成功");
                equipmentLv2InboundStateEntity.setEquipmentParentId(equipmentParentId);
                equipmentLv2InboundStateEntity.setInEquipmentModelLv2List(null);
                equipmentLv2InboundStateEntity.setOutEquipmentModelLv2List(null);
                wmsOperateResponseEntity.setData(equipmentLv2InboundStateEntity);
                return wmsOperateResponseEntity;
            }else {
                wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "根据上级设备id查询二级设备成功");
                equipmentLv2InboundStateEntity.setEquipmentParentId(equipmentParentId);
                equipmentLv2InboundStateEntity.setInEquipmentModelLv2List(inEquipmentModels);
                equipmentLv2InboundStateEntity.setOutEquipmentModelLv2List(null);
                wmsOperateResponseEntity.setData(equipmentLv2InboundStateEntity);
                return wmsOperateResponseEntity;
            }
        }else{
            if (inEquipmentModels == null || inEquipmentModels.isEmpty()) {
                wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "根据上级设备id查询二级设备成功");
                equipmentLv2InboundStateEntity.setEquipmentParentId(equipmentParentId);
                equipmentLv2InboundStateEntity.setInEquipmentModelLv2List(null);
                equipmentLv2InboundStateEntity.setOutEquipmentModelLv2List(outEquipmentModels);
                wmsOperateResponseEntity.setData(equipmentLv2InboundStateEntity);
                return wmsOperateResponseEntity;
            }else {
                wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "根据上级设备id查询二级设备成功");
                equipmentLv2InboundStateEntity.setEquipmentParentId(equipmentParentId);
                equipmentLv2InboundStateEntity.setInEquipmentModelLv2List(inEquipmentModels);
                equipmentLv2InboundStateEntity.setOutEquipmentModelLv2List(outEquipmentModels);
                wmsOperateResponseEntity.setData(equipmentLv2InboundStateEntity);
                return wmsOperateResponseEntity;
            }
        }
    }
}
