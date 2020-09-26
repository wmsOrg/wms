package com.hczx.wms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hczx.wms.dao.AlarmRecordDao;
import com.hczx.wms.dao.AlarmingDao;
import com.hczx.wms.dao.PlanDao;
import com.hczx.wms.dao.SchemeDao;
import com.hczx.wms.entity.alarmingentities.AlarmingInfoEntity;
import com.hczx.wms.entity.cacheentities.EquipmentCacheEntity;
import com.hczx.wms.entity.common.DataGirdResultEntity;
import com.hczx.wms.entity.common.WmsOperateResponseEntity;
import com.hczx.wms.entity.enquipmententities.EquipmentLv1inPlanEntity;
import com.hczx.wms.entity.planentities.AllEquipmentsInPlanEntity;
import com.hczx.wms.entity.planentities.CategoryEntity;
import com.hczx.wms.entity.planentities.PlanContentQueryEntity;
import com.hczx.wms.entity.planentities.PlanEquipmentsEntity;
import com.hczx.wms.entity.schemeequipmentrelaentities.EquipmentsInSchemeEntity;
import com.hczx.wms.framework.cache.EquipmentCache;
import com.hczx.wms.model.*;
import com.hczx.wms.mybatisplusserveice.AlarmRecordService;
import com.hczx.wms.mybatisplusserveice.AlarmSchemeEquipmentRelaService;
import com.hczx.wms.mybatisplusserveice.AlarmingService;
import com.hczx.wms.service.AlarmingOperateService;
import com.hczx.wms.service.AuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private PlanDao planDao;

    @Autowired
    private AlarmRecordService alarmRecordService;

    @Autowired
    private AlarmRecordDao alarmRecordDao;

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

            boolean flag= true;

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
                if (StringUtils.isBlank(equipmentLv1Id)){
                    continue;
                }
                //计算总数
                List<EquipmentLv1inPlanEntity> tempTotalEquipmentLv1inPlanEntities = equipmentLv1ContentsinPlanEntities.stream().filter(o->!StringUtils.isBlank(o.getEquipmentIdLv1())&&o.getEquipmentIdLv1().equals(equipmentLv1Id) && !StringUtils.isBlank(o.getEquipmentIdLv2()) ).collect(Collectors.toList());
                int totalNum = 0;
                if (tempTotalEquipmentLv1inPlanEntities != null && !tempTotalEquipmentLv1inPlanEntities.isEmpty()){

                    totalNum = tempTotalEquipmentLv1inPlanEntities.size();
                }
                //计算出库总数
                List<EquipmentLv1inPlanEntity> tempOutEquipmentLv1inPlanEntities = equipmentLv1ContentsinPlanEntities.stream().filter(o->!StringUtils.isBlank(o.getEquipmentIdLv1())&&o.getEquipmentIdLv1().equals(equipmentLv1Id) && !StringUtils.isBlank(o.getEquipmentIdLv2()) && (StringUtils.isBlank(o.getEquipmentInLv2()) || o.getEquipmentInLv2().equals("0"))).collect(Collectors.toList());
                int outNum = 0;
                if (tempOutEquipmentLv1inPlanEntities != null && !tempOutEquipmentLv1inPlanEntities.isEmpty()){

                    outNum = tempOutEquipmentLv1inPlanEntities.size();
                }
                //计算入库总数
                List<EquipmentLv1inPlanEntity> tempInEquipmentLv1inPlanEntities = equipmentLv1ContentsinPlanEntities.stream().filter(o->!StringUtils.isBlank(o.getEquipmentIdLv1())&&o.getEquipmentIdLv1().equals(equipmentLv1Id) && !StringUtils.isBlank(o.getEquipmentIdLv2()) && (!StringUtils.isBlank(o.getEquipmentInLv2()) && o.getEquipmentInLv2().equals("1"))).collect(Collectors.toList());
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

    /**
     * 编辑警情
     *
     * @param alarmingInfoEntity
     * @return
     */
    @Override
    public WmsOperateResponseEntity editAlarming(AlarmingInfoEntity alarmingInfoEntity) {
        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        //编辑警情

        boolean flag = alarmingService.editAlarming(alarmingInfoEntity);
        if (!flag){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "编辑警情失败：更新警情信息至数据库失败！");
            return wmsOperateResponseEntity;

        }else{

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "编辑警情成功！");
            return wmsOperateResponseEntity;

        }
    }

    /**
     * 作废警情
     *
     * @param ids
     * @return
     */
    @Override
    public WmsOperateResponseEntity delAlarming(List<String> ids) {

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        //编辑警情

        boolean flag = alarmingService.delAlarming(ids);
        if (!flag){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "编辑警情失败：更新警情信息至数据库失败！");
            return wmsOperateResponseEntity;

        }else{

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "编辑警情成功！");
            return wmsOperateResponseEntity;

        }


    }

    /**
     * 启动警情
     *
     * @param planId
     * @param schemeId
     * @param alarmingId
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void startPlan(String planId, String schemeId, String alarmingId, HttpServletRequest request, Model model) {

        QueryWrapper<PlanModel> planModelQueryWrapper = new QueryWrapper<>();
        planModelQueryWrapper.eq("id",planId).eq("useState","1");
        PlanModel planModelExit = new PlanModel().selectOne(planModelQueryWrapper);
        if (planModelExit == null || StringUtils.isBlank(planModelExit.getId())) {

            PlanModel planModel = new PlanModel();
            planModel.setId(planId);
            planModel.setUseState("1");


            boolean flag = planModel.updateById();
            if (flag) {

//                UserModel userModel = (UserModel) request.getSession().getAttribute("LOGIN_USER");
                List<PlanEquipmentsEntity> planEquipmentsLv1Entities = null;
                //一级设备
                try {
                    planEquipmentsLv1Entities = alarmingDao.queryInOutEquipmentLv1(planId,null);
                } catch (Exception e) {
                    logger.error("查询方案"+planId+"中待出库一级设备情况发生异常：",e);

                    return;
                }

                int totalNum = 0;

                if (planEquipmentsLv1Entities != null && !planEquipmentsLv1Entities.isEmpty()) {

                    List<String> allEquipmentIds = planEquipmentsLv1Entities.stream().map(PlanEquipmentsEntity::getEquipmentId).distinct().collect(Collectors.toList());
                    QueryWrapper<EquipmentModel> queryWrapper = new QueryWrapper<>();
                    queryWrapper.in("id", allEquipmentIds).eq("validState", "1");
                    List<EquipmentModel> allEquipmentModelsLv2 = new EquipmentModel().selectList(queryWrapper);

                    if (allEquipmentModelsLv2 != null && !allEquipmentModelsLv2.isEmpty()){
                        totalNum = planEquipmentsLv1Entities.size() + allEquipmentModelsLv2.size();
                    }else{
                        totalNum = planEquipmentsLv1Entities.size();
                    }
                }else{
                    totalNum = 0;
                }


                AlarmRecordModel alarmRecordModel = new AlarmRecordModel();
                alarmRecordModel.setId(UUID.randomUUID().toString());
                alarmRecordModel.setSchemeId(planId);
                alarmRecordModel.setOccurDate(new Date());
//                alarmRecordModel.setCreated_by(userModel.getId());
                alarmRecordModel.setSchemeCount(totalNum);
                alarmRecordModel.setCreatedDate(new Date());
                alarmRecordModel.insert();

                model.addAttribute("alarmRecordId", alarmRecordModel.getId());

                List<PlanEquipmentsEntity> planEquipmentsEntityList = planDao.queryTreatTheCasesEquipments(planId, "1");
                EquipmentCacheEntity equipmentCacheEntity = new EquipmentCacheEntity();
                if (planEquipmentsEntityList != null && !planEquipmentsEntityList.isEmpty()) {

                    int planEquipmentsEntityListSize = planEquipmentsEntityList.size();

                    for (int j = 0; j < planEquipmentsEntityListSize; j++) {
                        planEquipmentsEntityList.get(j).setWaitOperatingFlag(true);
                        planEquipmentsEntityList.get(j).setAlarmRecordId(alarmRecordModel.getId());
                    }

                    List<String> parentEquipmentIds = planEquipmentsEntityList.stream().filter(o -> !StringUtils.isBlank(o.getEquipmentId())).map(PlanEquipmentsEntity::getEquipmentId).distinct().collect(Collectors.toList());


                    if (parentEquipmentIds != null && !parentEquipmentIds.isEmpty()) {
                        QueryWrapper<EquipmentModel> queryWrapper = new QueryWrapper<>();
                        queryWrapper.in("equipmentPreId", parentEquipmentIds);
                        List<EquipmentModel> equipmentModels = new EquipmentModel().selectList(queryWrapper);
                        if (equipmentModels != null && !equipmentModels.isEmpty()) {
                            int size = equipmentModels.size();
                            for (int i = 0; i < size; i++) {
                                PlanEquipmentsEntity planEquipmentsEntity = new PlanEquipmentsEntity();
                                planEquipmentsEntity.setPlanId(planId);
                                planEquipmentsEntity.setAlarmingId(alarmingId);
                                planEquipmentsEntity.setSchemeId(schemeId);
                                planEquipmentsEntity.setEquipmentId(equipmentModels.get(i).getId());
                                planEquipmentsEntity.setEquipmentRfid(equipmentModels.get(i).getEquipmentRfid());
                                planEquipmentsEntity.setEquipmentInBoundState(equipmentModels.get(i).getInboundState());
                                planEquipmentsEntity.setAlarmRecordId(alarmRecordModel.getId());
                                planEquipmentsEntity.setWaitOperatingFlag(true);
                                planEquipmentsEntityList.add(planEquipmentsEntity);
                            }
                        }
                    }


                    equipmentCacheEntity.setKey("TreatTheCases");
                    equipmentCacheEntity.setValue(planEquipmentsEntityList);
                    EquipmentCache.putCache(planId, equipmentCacheEntity);
                }
            }
        }else{

            AlarmRecordModel alarmRecordModel = alarmRecordDao.queryCurrentlarmRecordByPlanId(planId);
            if (alarmRecordModel != null){
                model.addAttribute("alarmRecordId", alarmRecordModel.getId());
            }

            List<PlanEquipmentsEntity> planEquipmentsEntityList = planDao.queryTreatTheCasesEquipments(planId, "1");
            EquipmentCacheEntity equipmentCacheEntity = new EquipmentCacheEntity();
            if (planEquipmentsEntityList != null && !planEquipmentsEntityList.isEmpty()) {

                int planEquipmentsEntityListSize = planEquipmentsEntityList.size();

                for (int j = 0; j < planEquipmentsEntityListSize; j++) {
                    planEquipmentsEntityList.get(j).setWaitOperatingFlag(true);
                    planEquipmentsEntityList.get(j).setAlarmRecordId(alarmRecordModel.getId());
                }

                List<String> parentEquipmentIds = planEquipmentsEntityList.stream().filter(o -> !StringUtils.isBlank(o.getEquipmentId())).map(PlanEquipmentsEntity::getEquipmentId).distinct().collect(Collectors.toList());


                if (parentEquipmentIds != null && !parentEquipmentIds.isEmpty()) {
                    QueryWrapper<EquipmentModel> queryWrapper = new QueryWrapper<>();
                    queryWrapper.in("equipmentPreId", parentEquipmentIds);
                    List<EquipmentModel> equipmentModels = new EquipmentModel().selectList(queryWrapper);
                    if (equipmentModels != null && !equipmentModels.isEmpty()) {
                        int size = equipmentModels.size();
                        for (int i = 0; i < size; i++) {
                            PlanEquipmentsEntity planEquipmentsEntity = new PlanEquipmentsEntity();
                            planEquipmentsEntity.setPlanId(planId);
                            planEquipmentsEntity.setAlarmingId(alarmingId);
                            planEquipmentsEntity.setSchemeId(schemeId);
                            planEquipmentsEntity.setEquipmentId(equipmentModels.get(i).getId());
                            planEquipmentsEntity.setEquipmentRfid(equipmentModels.get(i).getEquipmentRfid());
                            planEquipmentsEntity.setEquipmentInBoundState(equipmentModels.get(i).getInboundState());
                            planEquipmentsEntity.setAlarmRecordId(alarmRecordModel.getId());
                            planEquipmentsEntity.setWaitOperatingFlag(true);
                            planEquipmentsEntityList.add(planEquipmentsEntity);
                        }
                    }
                }


                equipmentCacheEntity.setKey("TreatTheCases");
                equipmentCacheEntity.setValue(planEquipmentsEntityList);
                EquipmentCache.putCache(planId, equipmentCacheEntity);
                logger.info("123");
            }

        }
    }

    /**
     * 关闭警情
     *
     * @param planId
     * @param schemeId
     * @param alarmingId
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void closePlan(String planId, String schemeId, String alarmingId, HttpServletRequest request, Model model) {

        QueryWrapper<PlanModel> planModelQueryWrapper = new QueryWrapper<>();
        planModelQueryWrapper.eq("id",planId).eq("useState","1");
        PlanModel planModelExit = new PlanModel().selectOne(planModelQueryWrapper);
        if (planModelExit == null || StringUtils.isBlank(planModelExit.getId())) {
            return;
        }else{
            AlarmRecordModel alarmRecordModel = alarmRecordDao.queryCurrentlarmRecordByPlanId(planId);
            if (alarmRecordModel != null){
                model.addAttribute("alarmRecordId", alarmRecordModel.getId());
            }

            //需要收回的设备
            List<PlanEquipmentsEntity> planEquipmentsEntityList = planDao.queryTreatTheCasesEquipments(planId, "1");

            EquipmentCacheEntity equipmentCacheEntity = new EquipmentCacheEntity();
            if (planEquipmentsEntityList != null && !planEquipmentsEntityList.isEmpty()) {

                int planEquipmentsEntityListSize = planEquipmentsEntityList.size();

                for (int j = 0; j < planEquipmentsEntityListSize; j++) {
                    planEquipmentsEntityList.get(j).setWaitOperatingFlag(true);
                    planEquipmentsEntityList.get(j).setAlarmRecordId(alarmRecordModel.getId());
                }

                List<String> parentEquipmentIds = planEquipmentsEntityList.stream().filter(o -> !StringUtils.isBlank(o.getEquipmentId())).map(PlanEquipmentsEntity::getEquipmentId).distinct().collect(Collectors.toList());


                if (parentEquipmentIds != null && !parentEquipmentIds.isEmpty()) {
                    QueryWrapper<EquipmentModel> queryWrapper = new QueryWrapper<>();
                    queryWrapper.in("equipmentPreId", parentEquipmentIds);
                    List<EquipmentModel> equipmentModels = new EquipmentModel().selectList(queryWrapper);
                    if (equipmentModels != null && !equipmentModels.isEmpty()) {
                        int size = equipmentModels.size();
                        for (int i = 0; i < size; i++) {
                            PlanEquipmentsEntity planEquipmentsEntity = new PlanEquipmentsEntity();
                            planEquipmentsEntity.setPlanId(planId);
                            planEquipmentsEntity.setAlarmingId(alarmingId);
                            planEquipmentsEntity.setSchemeId(schemeId);
                            planEquipmentsEntity.setEquipmentId(equipmentModels.get(i).getId());
                            planEquipmentsEntity.setEquipmentRfid(equipmentModels.get(i).getEquipmentRfid());
                            planEquipmentsEntity.setEquipmentInBoundState(equipmentModels.get(i).getInboundState());
                            planEquipmentsEntity.setAlarmRecordId(alarmRecordModel.getId());
                            planEquipmentsEntity.setWaitOperatingFlag(true);
                            planEquipmentsEntityList.add(planEquipmentsEntity);
                        }
                    }
                }


                equipmentCacheEntity.setKey("SettleTheCases");
                equipmentCacheEntity.setValue(planEquipmentsEntityList);
                EquipmentCache.putCache(planId, equipmentCacheEntity);
            }


        }
    }

    /**
     * 出库完毕
     *
     * @param planQueryEntity
     * @return
     */
    @Transactional
    @Override
    public WmsOperateResponseEntity outover(PlanContentQueryEntity planQueryEntity) {
        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        EquipmentCacheEntity equipmentCacheEntity = EquipmentCache.getCacheInfo(planQueryEntity.getPlanId());
        List<PlanEquipmentsEntity> planEquipmentsEntities = (List<PlanEquipmentsEntity>)equipmentCacheEntity.getValue();
        int outNum = 0;

        if (planEquipmentsEntities!= null && !planEquipmentsEntities.isEmpty()){
            List<PlanEquipmentsEntity> outPlanEquipmentsEntitys = planEquipmentsEntities.stream().filter(o->o.getWaitOperatingFlag().equals(false)).distinct().collect(Collectors.toList());

            if (outPlanEquipmentsEntitys != null && !outPlanEquipmentsEntitys.isEmpty()){
                outNum = outPlanEquipmentsEntitys.size();
            }else{
                outNum = 0;
            }
        }else{
            outNum = 0;
        }


//        List<PlanEquipmentsEntity> planEquipmentsLv1Entities = null;
//        //一级设备
//        try {
//            planEquipmentsLv1Entities = alarmingDao.queryInOutEquipmentLv1(planQueryEntity.getPlanId(),null);
//        } catch (Exception e) {
//            logger.error("查询方案"+planQueryEntity.getPlanId()+"中待出库一级设备情况发生异常：",e);
//            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "查询方案："+planQueryEntity.getPlanId()+"中待出库一级设备情况发生异常:"+e.getMessage());
//            return wmsOperateResponseEntity;
//        }
//
//        int totalNum = 0;
//        int outNum = 0;
//
//        if (planEquipmentsLv1Entities != null && !planEquipmentsLv1Entities.isEmpty()) {
//            List<PlanEquipmentsEntity> outPlanEquipmentsLv1Entities = planEquipmentsLv1Entities.stream().filter(o -> !StringUtils.isBlank(o.getInoutState()) && o.getInoutState().equals("0")).distinct().collect(Collectors.toList());
//
//            List<String> allEquipmentIds = planEquipmentsLv1Entities.stream().map(PlanEquipmentsEntity::getEquipmentId).distinct().collect(Collectors.toList());
//            QueryWrapper<EquipmentModel> queryWrapper = new QueryWrapper<>();
//            queryWrapper.in("id", allEquipmentIds).eq("validState", "1");
//            List<EquipmentModel> allEquipmentModelsLv2 = new EquipmentModel().selectList(queryWrapper);
//
//            if (outPlanEquipmentsLv1Entities != null && !outPlanEquipmentsLv1Entities.isEmpty()) {
//                if (allEquipmentModelsLv2 != null && !allEquipmentModelsLv2.isEmpty()) {
//
//                    List<EquipmentModel> outEquipmentModelsLv2 = allEquipmentModelsLv2.stream().filter(o -> !StringUtils.isBlank(o.getInboundState()) && o.getInboundState().equals("0")).distinct().collect(Collectors.toList());
//                    if (outEquipmentModelsLv2 != null && !outEquipmentModelsLv2.isEmpty()) {
//                        outNum = outEquipmentModelsLv2.size()+outPlanEquipmentsLv1Entities.size();
//
//                    }else{
//                        outNum = outPlanEquipmentsLv1Entities.size();
//                    }
//                }else{
//                    outNum = outPlanEquipmentsLv1Entities.size();
//                }
//            }else{
//                outNum = 0;
//            }
//            totalNum = planEquipmentsLv1Entities.size();
//        }else{
//            totalNum = 0;
//        }

        AlarmRecordModel alarmRecordModel = new AlarmRecordModel();
        alarmRecordModel.setId(planQueryEntity.getAlarmRecordId());
        alarmRecordModel.setOutCount(outNum);
//        alarmRecordModel.setSchemeCount(totalNum);
        alarmRecordModel.updateById();

        wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "方案："+planQueryEntity.getPlanId()+"中设备出库成功关闭");
        EquipmentCache.clearAll();
        return wmsOperateResponseEntity;

    }

    /**
     * 入库完毕
     *
     * @param planQueryEntity
     * @return
     */
    @Transactional
    @Override
    public WmsOperateResponseEntity inover(PlanContentQueryEntity planQueryEntity) {
        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();
        EquipmentCacheEntity equipmentCacheEntity = EquipmentCache.getCacheInfo(planQueryEntity.getPlanId());
        List<PlanEquipmentsEntity> planEquipmentsEntities = (List<PlanEquipmentsEntity>)equipmentCacheEntity.getValue();
        int inNum = 0;

        if (planEquipmentsEntities!= null && !planEquipmentsEntities.isEmpty()){
            List<PlanEquipmentsEntity> inPlanEquipmentsEntitys = planEquipmentsEntities.stream().filter(o->o.getWaitOperatingFlag().equals(false)).distinct().collect(Collectors.toList());

            if (inPlanEquipmentsEntitys != null && !inPlanEquipmentsEntitys.isEmpty()){
                inNum = inPlanEquipmentsEntitys.size();
            }else{
                inNum = 0;
            }
        }else{
            inNum = 0;
        }

//        List<PlanEquipmentsEntity> planEquipmentsLv1Entities = null;
//        //一级设备出库
//        try {
//            planEquipmentsLv1Entities = alarmingDao.queryInOutEquipmentLv1(planQueryEntity.getPlanId(),null);
//        } catch (Exception e) {
//            logger.error("查询方案"+planQueryEntity.getPlanId()+"中待入库一级设备情况发生异常：",e);
//            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "查询方案："+planQueryEntity.getPlanId()+"中待入库一级设备情况发生异常:"+e.getMessage());
//            return wmsOperateResponseEntity;
//        }
//
//        int totalNum = 0;
//        int inNum = 0;
//
//        if (planEquipmentsLv1Entities != null && !planEquipmentsLv1Entities.isEmpty()) {
//            List<PlanEquipmentsEntity> outPlanEquipmentsLv1Entities = planEquipmentsLv1Entities.stream().filter(o -> !StringUtils.isBlank(o.getInoutState()) && o.getInoutState().equals("1")).distinct().collect(Collectors.toList());
//
//            List<String> allEquipmentIds = planEquipmentsLv1Entities.stream().map(PlanEquipmentsEntity::getEquipmentId).distinct().collect(Collectors.toList());
//            QueryWrapper<EquipmentModel> queryWrapper = new QueryWrapper<>();
//            queryWrapper.in("id", allEquipmentIds).eq("validState", "1");
//            List<EquipmentModel> allEquipmentModelsLv2 = new EquipmentModel().selectList(queryWrapper);
//
//            if (outPlanEquipmentsLv1Entities != null && !outPlanEquipmentsLv1Entities.isEmpty()) {
//                if (allEquipmentModelsLv2 != null && !allEquipmentModelsLv2.isEmpty()) {
//
//                    List<EquipmentModel> outEquipmentModelsLv2 = allEquipmentModelsLv2.stream().filter(o -> !StringUtils.isBlank(o.getInboundState()) && o.getInboundState().equals("1")).distinct().collect(Collectors.toList());
//                    if (outEquipmentModelsLv2 != null && !outEquipmentModelsLv2.isEmpty()) {
//                        inNum = outEquipmentModelsLv2.size()+outPlanEquipmentsLv1Entities.size();
//
//                    }else{
//                        inNum = outPlanEquipmentsLv1Entities.size();
//                    }
//                }else{
//                    inNum = outPlanEquipmentsLv1Entities.size();
//                }
//            }else{
//                inNum = 0;
//            }
//            totalNum = planEquipmentsLv1Entities.size();
//        }else{
//            totalNum = 0;
//        }

        AlarmRecordModel alarmRecordModel = new AlarmRecordModel();
        alarmRecordModel.setId(planQueryEntity.getAlarmRecordId());
        alarmRecordModel.setInCount(inNum);
        alarmRecordModel.updateById();

        PlanModel planModel = new PlanModel();
        planModel.setId(planQueryEntity.getPlanId());
        planModel.setUseState("0");
        planModel.updateById();

        wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "方案："+planQueryEntity.getPlanId()+"中设备出库成功关闭");

        EquipmentCache.clearAll();

        return wmsOperateResponseEntity;
    }
}
