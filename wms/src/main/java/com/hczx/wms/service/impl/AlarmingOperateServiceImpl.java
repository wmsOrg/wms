package com.hczx.wms.service.impl;

import com.hczx.wms.dao.AlarmingDao;
import com.hczx.wms.dao.SchemeDao;
import com.hczx.wms.entity.alarmingentities.AlarmingInfoEntity;
import com.hczx.wms.entity.common.DataGirdResultEntity;
import com.hczx.wms.entity.common.WmsOperateResponseEntity;
import com.hczx.wms.entity.enquipmententities.EquipmentLv1inPlanEntity;
import com.hczx.wms.entity.planentities.AllEquipmentsInPlanEntity;
import com.hczx.wms.entity.planentities.CategoryEntity;
import com.hczx.wms.entity.planentities.PlanContentQueryEntity;
import com.hczx.wms.entity.schemeequipmentrelaentities.EquipmentsInSchemeEntity;
import com.hczx.wms.model.AlarmSchemeEquipmentRelaModel;
import com.hczx.wms.model.PlanModel;
import com.hczx.wms.mybatisplusserveice.AlarmSchemeEquipmentRelaService;
import com.hczx.wms.mybatisplusserveice.AlarmingService;
import com.hczx.wms.service.AlarmingOperateService;
import com.hczx.wms.service.AuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: AlarmingServiceImpl
 * @Description: 警情操作实现
 * @Date: 2020/9/10 10:33
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Service
public class AlarmingOperateServiceImpl implements AlarmingOperateService {

    private Logger logger = LoggerFactory.getLogger(AlarmingOperateServiceImpl.class);

    @Autowired
    private AlarmingDao alarmingDao;

    @Autowired
    private AlarmingService alarmingService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AlarmSchemeEquipmentRelaService alarmSchemeEquipmentRelaService;

    @Autowired
    private SchemeDao schemeDao;

    /**
     * 查询警情信息
     *
     * @param page
     * @param size
     * @param alarmingInfoEntity
     * @return
     */
    @Override
    public DataGirdResultEntity listAlarmInfo(Integer page, Integer size, AlarmingInfoEntity alarmingInfoEntity) {

        List<AlarmingInfoEntity> alarmingInfoEntities  = null;
        Long totalCount = 0l;
        try {
            alarmingInfoEntities = alarmingDao.listAlarmingInfo(page, size, alarmingInfoEntity);
            totalCount = alarmingDao.countAlarmingInfo(alarmingInfoEntity);
        } catch (Exception e) {
            logger.error("查询警情信息异常：",e);
            alarmingInfoEntities = new ArrayList<>();
            return DataGirdResultEntity.error(e.getMessage(),alarmingInfoEntities,Long.valueOf(0));
        }

        if (alarmingInfoEntities == null || alarmingInfoEntities.isEmpty()){
            alarmingInfoEntities = new ArrayList<>();
        }

        return DataGirdResultEntity.success(alarmingInfoEntities, totalCount);
    }

    /**
     * 警情绑定对应的方案中的设备
     *
     * @param planName
     * @param alarmId
     * @param schemeId
     * @param equipmentsInSchemeEntities
     * @return
     */
    @Override
    public WmsOperateResponseEntity alarmingBindEquipmentsInScheme(String planName, String alarmId, String schemeId, List<EquipmentsInSchemeEntity> equipmentsInSchemeEntities) {
        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        int size = equipmentsInSchemeEntities.size();

        List<AlarmSchemeEquipmentRelaModel> alarmSchemeEquipmentRelaModels = new ArrayList<>();

        String planId = UUID.randomUUID().toString();
        Date date = new Date();

        PlanModel planModel = new PlanModel();
        planModel.setId(planId);
        planModel.setPlanName(planName);
        planModel.setCreateTime(date);
        planModel.setUseState("0");
        planModel.setSchemeId(schemeId);
        planModel.setAlarmingId(alarmId);

        //封装警情方案设备关联表实体
        for (int i = 0; i < size; i++) {
            EquipmentsInSchemeEntity equipmentsInSchemeEntity = equipmentsInSchemeEntities.get(i);
            AlarmSchemeEquipmentRelaModel alarmSchemeEquipmentRelaModel = new AlarmSchemeEquipmentRelaModel();
            alarmSchemeEquipmentRelaModel.setId(UUID.randomUUID().toString());
            alarmSchemeEquipmentRelaModel.setAlarmingId(equipmentsInSchemeEntity.getAlarmingId());
            alarmSchemeEquipmentRelaModel.setSchemeId(equipmentsInSchemeEntity.getSchemeId());
            alarmSchemeEquipmentRelaModel.setPlanId(planId);
            alarmSchemeEquipmentRelaModel.setSeRelaId(equipmentsInSchemeEntity.getSerId());
            alarmSchemeEquipmentRelaModel.setCreateTime(date);
            alarmSchemeEquipmentRelaModels.add(alarmSchemeEquipmentRelaModel);
        }

        if (planModel == null){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "警情绑定对应的方案中的设备失败：封装预案实体为空！");
            return wmsOperateResponseEntity;
        }

        if (alarmSchemeEquipmentRelaModels == null || alarmSchemeEquipmentRelaModels.isEmpty()){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "警情绑定对应的方案中的设备失败：封装警情方案设备关联表实体集合为空！");
            return wmsOperateResponseEntity;
        }
        //警情绑定对应的方案中的设备
        boolean flag = alarmSchemeEquipmentRelaService.BindEquipmentsInScheme(planModel,alarmSchemeEquipmentRelaModels);
        if (!flag) {

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "设备登记失败：新增设备信息至数据库失败！");
            return wmsOperateResponseEntity;

        } else {

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "设备登记成功！");
            return wmsOperateResponseEntity;

        }
    }

    /**
     * 方案所绑定的设备查询
     *
     * @param planQueryEntity
     * @return
     */
    @Override
    public WmsOperateResponseEntity planList(PlanContentQueryEntity planQueryEntity) {

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        //查询预案一级设备
        List<EquipmentLv1inPlanEntity> equipmentLv1inPlanEntities = null;
        //查询预案中所有设备
        List<EquipmentLv1inPlanEntity> equipmentLv1ContentsinPlanEntities = null;
        try {
            equipmentLv1inPlanEntities = schemeDao.queryEquipmentLv1InPlan(planQueryEntity);
            equipmentLv1ContentsinPlanEntities = schemeDao.queryEquipmentLv1ContentsInPlan(planQueryEntity);
        } catch (Exception e) {

            logger.error("预案所绑定的设备查询异常：",e);
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "预案所绑定的设备查询异常："+e.getMessage());
            return wmsOperateResponseEntity;
        }

        //封装成前台展示格式
        if (equipmentLv1inPlanEntities == null || equipmentLv1inPlanEntities.isEmpty()){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "预案所绑定的设备查询成功！");
            return wmsOperateResponseEntity;

        }else {

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "预案所绑定的设备查询成功！");
            int equipmentLv1inPlanEntitiesSize = equipmentLv1inPlanEntities.size();

            AllEquipmentsInPlanEntity allEquipmentsInPlanEntity = new AllEquipmentsInPlanEntity();

            equipmentLv1inPlanEntities.get(0).getTotalRowNums();
            allEquipmentsInPlanEntity.setAlarmingId(planQueryEntity.getAlarmingId());
            allEquipmentsInPlanEntity.setPlanId(planQueryEntity.getPlanId());
            allEquipmentsInPlanEntity.setSchemeId(planQueryEntity.getSchemeId());
            allEquipmentsInPlanEntity.setTotalColumnNums(equipmentLv1inPlanEntities.get(0).getTotalColumnNums());
            allEquipmentsInPlanEntity.setTotalRowNums(equipmentLv1inPlanEntities.get(0).getTotalRowNums());

            List<CategoryEntity> categoryEntities = new ArrayList<>();
            for (int i = 0; i < equipmentLv1inPlanEntitiesSize; i++){
                CategoryEntity categoryEntity = new CategoryEntity();
                EquipmentLv1inPlanEntity equipmentLv1inPlanEntity = equipmentLv1inPlanEntities.get(i);
                String equipmentLv1Id = equipmentLv1inPlanEntity.getEquipmentIdLv1();
                //计算总数
                List<EquipmentLv1inPlanEntity> tempTotalEquipmentLv1inPlanEntities = equipmentLv1ContentsinPlanEntities.stream().filter(o->o.getEquipmentIdLv1().equals(equipmentLv1Id) && !StringUtils.isBlank(o.getEquipmentIdLv2()) ).collect(Collectors.toList());
                int totalNum = 0;
                if (tempTotalEquipmentLv1inPlanEntities != null && !tempTotalEquipmentLv1inPlanEntities.isEmpty()){

                    totalNum = tempTotalEquipmentLv1inPlanEntities.size();
                }
                //计算出库总数
                List<EquipmentLv1inPlanEntity> tempOutEquipmentLv1inPlanEntities = equipmentLv1ContentsinPlanEntities.stream().filter(o->o.getEquipmentIdLv1().equals(equipmentLv1Id) && !StringUtils.isBlank(o.getEquipmentIdLv2()) && (StringUtils.isBlank(o.getEquipmentInLv2()) || o.getEquipmentInLv2().equals("0"))).collect(Collectors.toList());
                int outNum = 0;
                if (tempOutEquipmentLv1inPlanEntities != null && !tempOutEquipmentLv1inPlanEntities.isEmpty()){

                    outNum = tempOutEquipmentLv1inPlanEntities.size();
                }
                //计算入库总数
                List<EquipmentLv1inPlanEntity> tempInEquipmentLv1inPlanEntities = equipmentLv1ContentsinPlanEntities.stream().filter(o->o.getEquipmentIdLv1().equals(equipmentLv1Id) && !StringUtils.isBlank(o.getEquipmentIdLv2()) && (!StringUtils.isBlank(o.getEquipmentInLv2()) && o.getEquipmentInLv2().equals("1"))).collect(Collectors.toList());
                int inNum = 0;
                if (tempInEquipmentLv1inPlanEntities != null && !tempInEquipmentLv1inPlanEntities.isEmpty()){

                    inNum = tempInEquipmentLv1inPlanEntities.size();
                }
                categoryEntity = packageCategoryEntity(equipmentLv1inPlanEntity, totalNum,inNum,outNum);
                categoryEntities.add(categoryEntity);
            }
            if (categoryEntities != null && !categoryEntities.isEmpty()){
                List<CategoryEntity> tempCategoryEntityList = categoryEntities.stream().sorted(Comparator.comparing(CategoryEntity::getRowNum).thenComparing(CategoryEntity::getColumnNum)).collect(Collectors.toList());
                allEquipmentsInPlanEntity.setCategorys(tempCategoryEntityList);
            }else{
                allEquipmentsInPlanEntity.setCategorys(categoryEntities);
            }

            wmsOperateResponseEntity.setData(allEquipmentsInPlanEntity);
            return wmsOperateResponseEntity;

        }

    }

    /**
     * 封装柜内设备实体
     *
     * @param equipmentLv1inPlanEntity
     * @param totalNum
     * @param InNum
     * @param outNum
     * @return
     */
    @Override
    public CategoryEntity packageCategoryEntity(EquipmentLv1inPlanEntity equipmentLv1inPlanEntity,Integer totalNum, Integer InNum, Integer outNum) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setRowNum(equipmentLv1inPlanEntity.getRowNum());
        categoryEntity.setColumnNum(equipmentLv1inPlanEntity.getColumnNum());
        categoryEntity.setEquipmentIdLv1(equipmentLv1inPlanEntity.getEquipmentIdLv1());
        categoryEntity.setEquipmentNameLv1(equipmentLv1inPlanEntity.getEquipmentNameLv1());
        categoryEntity.setEquipmentInLv1(equipmentLv1inPlanEntity.getEquipmentInLv1());
        categoryEntity.setEquipmentSelectedLv1(equipmentLv1inPlanEntity.getEquipmentSelectedLv1());
        categoryEntity.setEquipmentLv2InNumber(InNum);
        categoryEntity.setEquipmentLv2Number(totalNum);
        categoryEntity.setEquipmentLv2OutNumber(outNum);

        return categoryEntity;
    }

    /**
     * 登记警情
     *
     * @param alarmingInfoEntity
     * @return
     */
    @Override
    public WmsOperateResponseEntity registAlarming(AlarmingInfoEntity alarmingInfoEntity) {
        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        //新增设备信息

        boolean flag = alarmingService.saveRegisterEquipment(alarmingInfoEntity);
        if (!flag){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "警情登记失败：新增警情信息至数据库失败！");
            return wmsOperateResponseEntity;

        }else{

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "警情登记成功！");
            return wmsOperateResponseEntity;

        }

    }
}
