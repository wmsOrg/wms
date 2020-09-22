package com.hczx.wms.service.impl;

import com.hczx.wms.dao.PlanDao;
import com.hczx.wms.dao.SchemeDao;
import com.hczx.wms.dao.SchemeEquipmentRelaDao;
import com.hczx.wms.entity.common.Parameters;
import com.hczx.wms.entity.common.WmsOperateResponseEntity;
import com.hczx.wms.entity.enquipmententities.EquipmentLv1inPlanEntity;
import com.hczx.wms.entity.planentities.AllEquipmentsInPlanEntity;
import com.hczx.wms.entity.planentities.CategoryEntity;
import com.hczx.wms.entity.planentities.PlanContentQueryEntity;
import com.hczx.wms.entity.planentities.PlanQueryEntity;
import com.hczx.wms.entity.schemeentities.SchemeBindEquipmentEntity;
import com.hczx.wms.entity.schemeentities.SchemeEditEntity;
import com.hczx.wms.entity.schemeentities.SchemeIncreaseEntity;
import com.hczx.wms.entity.schemeequipmentrelaentities.EquipmentsInSchemeEntity;
import com.hczx.wms.entity.schemeequipmentrelaentities.SchemeRelaEquipmentsEntity;
import com.hczx.wms.model.SchemeEquipmentRelaModel;
import com.hczx.wms.model.SchemeModel;
import com.hczx.wms.mybatisplusserveice.SchemeEquipmentRelaService;
import com.hczx.wms.mybatisplusserveice.SchemeService;
import com.hczx.wms.service.AlarmingOperateService;
import com.hczx.wms.service.AuthenticationService;
import com.hczx.wms.service.SchemeOperateService;
import com.hczx.wms.util.ImageUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: SchemeServiceImpl
 * @Description: 方案编辑实现接口
 * @Date: 2020/9/9 10:44
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Service
public class SchemeOperateServiceImpl implements SchemeOperateService {

    private Logger logger = LoggerFactory.getLogger(SchemeOperateServiceImpl.class);

    @Autowired
    private SchemeService schemeService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SchemeDao schemeDao;

    @Autowired
    private SchemeEquipmentRelaService schemeEquipmentRelaService;

    @Autowired
    private SchemeEquipmentRelaDao schemeEquipmentRelaDao;

    @Autowired
    private Parameters parameters;

    @Autowired
    private PlanDao planDao;

    @Autowired
    private AlarmingOperateService alarmingOperateService;

    /**
     * 新增方案
     *
     * @param schemeIncreaseEntity
     * @return
     */
    @Override
    public WmsOperateResponseEntity increaseScheme(SchemeIncreaseEntity schemeIncreaseEntity) {

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();



        boolean flag = schemeService.saveScheme(schemeIncreaseEntity);
        if (!flag){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "新增方案失败：新增方案信息至数据库失败！");
            return wmsOperateResponseEntity;

        }else{

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "新增方案成功！");
            return wmsOperateResponseEntity;

        }

    }

    /**
     * 查询方案
     *
     * @param schemeIncreaseEntity
     * @return
     */
    @Override
    public WmsOperateResponseEntity listScheme(SchemeIncreaseEntity schemeIncreaseEntity) {

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (schemeIncreaseEntity == null){
            schemeIncreaseEntity = new SchemeIncreaseEntity();
        }

        //查询
        List<SchemeIncreaseEntity> schemeIncreaseEntityList = null;
        try {
            schemeIncreaseEntityList = schemeDao.listScheme(schemeIncreaseEntity);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("方案查询异常：",e);
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "方案查询异常！");
            schemeIncreaseEntityList = new ArrayList<>();
            wmsOperateResponseEntity.setData(schemeIncreaseEntityList);
            return wmsOperateResponseEntity;
        }


        if (schemeIncreaseEntityList == null || schemeIncreaseEntityList.isEmpty()){

            schemeIncreaseEntityList = new ArrayList<>();

        }

        wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "查询成功！");
        wmsOperateResponseEntity.setData(schemeIncreaseEntityList);
        return wmsOperateResponseEntity;

    }

    /**
     * 方案绑定设备
     *
     * @param schemeEditEntity
     * @return
     */
    @Override
    public WmsOperateResponseEntity schemeBindEquipments(SchemeEditEntity schemeEditEntity) {

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        String partImageUrl = null;
        String imageUrl = "";
        if (StringUtils.isBlank(schemeEditEntity.getImage())){
            imageUrl = "";
        }else{

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String date = simpleDateFormat.format(new Date());
            partImageUrl = date + File.separator + UUID.randomUUID().toString() + ".png";
            imageUrl =  parameters.getVisibleEditLayoutCaptureUrl() + partImageUrl;
            //保存图片到本地
            try {
                ImageUtils.base64ToImage(schemeEditEntity.getImage(),imageUrl);
            } catch (Exception e) {
//                e.printStackTrace();
                logger.error("方案绑定设备图片转换异常:",e);
                wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "方案绑定设备图片转换异常:"+e.getMessage());
                return wmsOperateResponseEntity;
            }




        }

        SchemeModel schemeModel = new SchemeModel();
        schemeModel.setId(schemeEditEntity.getSchemeId());
        schemeModel.setMaxColumns(schemeEditEntity.getMaxColumns());
        schemeModel.setMaxRows(schemeEditEntity.getMaxRows());
        schemeModel.setImageUrl(partImageUrl.replace("\\","/"));

        Date date = new Date();
        int size = schemeEditEntity.getDatas().size();

        List<SchemeEquipmentRelaModel> schemeEquipmentRelaModels = new ArrayList<>();

        //遍历封装需要绑定得设备
        for (int i = 0; i < size; i++) {
            SchemeBindEquipmentEntity schemeBindEquipmentEntity = schemeEditEntity.getDatas().get(i);
            SchemeEquipmentRelaModel schemeEquipmentRelaModel = new SchemeEquipmentRelaModel();
            schemeEquipmentRelaModel.setId(UUID.randomUUID().toString());
            schemeEquipmentRelaModel.setSchemeId(schemeEditEntity.getSchemeId());
            schemeEquipmentRelaModel.setEquipmentId(schemeBindEquipmentEntity.getEquipmentId());
            schemeEquipmentRelaModel.setCreateTime(date);
            schemeEquipmentRelaModel.setRowNum(schemeBindEquipmentEntity.getRowCounts());
            schemeEquipmentRelaModel.setColumnNum(schemeBindEquipmentEntity.getColumnCounts());
            schemeEquipmentRelaModels.add(schemeEquipmentRelaModel);
        }

        //绑定操作
        boolean flag = schemeEquipmentRelaService.bandEquipments(schemeModel, schemeEquipmentRelaModels);
        if (!flag){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "绑定失败：绑定设备信息至数据库失败！");
            return wmsOperateResponseEntity;

        }else{

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "绑定成功！");
            return wmsOperateResponseEntity;

        }
    }

    /**
     * 查询具体方案所绑定的设备
     *
     * @param schemeId
     * @return
     */
    @Override
    public WmsOperateResponseEntity listEquipments(String schemeId) {

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();
        List<EquipmentsInSchemeEntity> equipmentsInSchemeEntities = null;
        try {
            equipmentsInSchemeEntities = schemeEquipmentRelaDao.listEquipmentsBySchemeId(schemeId);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("查询具体方案所绑定的设备异常：",e);
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "查询具体方案所绑定的设备失败：查询具体方案所绑定的设备出现异常！");
        }

        SchemeRelaEquipmentsEntity schemeRelaEquipmentsEntity = new SchemeRelaEquipmentsEntity();

        if (equipmentsInSchemeEntities == null  || equipmentsInSchemeEntities.isEmpty()){
            equipmentsInSchemeEntities = new ArrayList<>();
        }else {
            schemeRelaEquipmentsEntity.setSchemeId(equipmentsInSchemeEntities.get(0).getSchemeId());
            schemeRelaEquipmentsEntity.setMaxColumns(equipmentsInSchemeEntities.get(0).getMaxColumns());
            schemeRelaEquipmentsEntity.setMaxRows(equipmentsInSchemeEntities.get(0).getMaxRows());
            schemeRelaEquipmentsEntity.setDatas(equipmentsInSchemeEntities);
        }



        wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "查询成功！");
        wmsOperateResponseEntity.setData(schemeRelaEquipmentsEntity);
        return wmsOperateResponseEntity;
    }

    /**
     * 展示图片
     *
     * @param fileName
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    public void getImage(String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {

        String imagesPath = parameters.getVisibleEditLayoutCaptureUrl();
        String imgFile = imagesPath + fileName;
        String imgType = fileName.substring(fileName.indexOf('.') + 1 , fileName.length());
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");

        response.setContentType("image/"+imgType);

        BufferedImage bi = null;
        ServletOutputStream out = response.getOutputStream();

        try {
            bi = ImageIO.read(new File(imgFile));
            ImageIO.write(bi, imgType, out);
        } catch (IIOException e) {
            logger.info("无法读取图片:"+imgFile, e);
//            bi = StringHelper.getCrystalImage(1, 1);   //如果没有该文件，则显示默认的透明图片
//            ImageIO.write(bi, "png", out);
            throw new Exception("无法读取图片:",e);   //ServiceException是自己定义的异常，可忽略。
        } finally {
            try {
                out.flush();
            } finally {
                out.close();
            }
        }
    }

    /**
     * 预案查询
     *
     * @param planQueryEntity
     * @return
     */
    @Override
    public WmsOperateResponseEntity planList(PlanQueryEntity planQueryEntity) {
        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (planQueryEntity == null){
            planQueryEntity = new PlanQueryEntity();
        }

        //查询
        List<PlanQueryEntity> planQueryEntities = null;
        try {
            planQueryEntities = planDao.listPlan(planQueryEntity);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("预案查询异常：",e);
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "方案查询异常！");
            planQueryEntities = new ArrayList<>();
            wmsOperateResponseEntity.setData(planQueryEntities);
            return wmsOperateResponseEntity;
        }


        if (planQueryEntities == null || planQueryEntities.isEmpty()){

            planQueryEntities = new ArrayList<>();

        }

        wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "查询成功！");
        wmsOperateResponseEntity.setData(planQueryEntities);
        return wmsOperateResponseEntity;
    }

    /**
     * 方案详情查询
     *
     * @param schemeId
     * @return
     */
    @Override
    public WmsOperateResponseEntity schemeContentList(String schemeId) {
        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        //查询预案一级设备
        List<EquipmentLv1inPlanEntity> equipmentLv1inPlanEntities = null;
        //查询预案中所有设备
        List<EquipmentLv1inPlanEntity> equipmentLv1ContentsinPlanEntities = null;
        try {
            equipmentLv1inPlanEntities = schemeDao.queryEquipmentLv1InScheme(schemeId);
            equipmentLv1ContentsinPlanEntities = schemeDao.queryEquipmentLv1ContentsInScheme(schemeId);
        } catch (Exception e) {

            logger.error("方案所绑定的设备查询异常：",e);
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "方案所绑定的设备查询异常："+e.getMessage());
            return wmsOperateResponseEntity;
        }

        //封装成前台展示格式
        if (equipmentLv1inPlanEntities == null || equipmentLv1inPlanEntities.isEmpty()){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "方案所绑定的设备查询成功！");
            return wmsOperateResponseEntity;

        }else {

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "方案所绑定的设备查询成功！");
            int equipmentLv1inPlanEntitiesSize = equipmentLv1inPlanEntities.size();

            AllEquipmentsInPlanEntity allEquipmentsInPlanEntity = new AllEquipmentsInPlanEntity();

            equipmentLv1inPlanEntities.get(0).getTotalRowNums();
//            allEquipmentsInPlanEntity.setAlarmingId(planQueryEntity.getAlarmingId());
//            allEquipmentsInPlanEntity.setPlanId(planQueryEntity.getPlanId());
            allEquipmentsInPlanEntity.setSchemeId(schemeId);
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
                categoryEntity = alarmingOperateService.packageCategoryEntity(equipmentLv1inPlanEntity, totalNum,inNum,outNum);
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
     * 作废方案
     *
     * @param schemeIds
     * @return
     */
    @Transactional
    @Override
    public WmsOperateResponseEntity removeSchemes(List<String> schemeIds) {
        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        boolean flag = true;
        try {
            flag = schemeDao.removeSchemeByIds(schemeIds);
        } catch (Exception e) {
//            e.printStackTrace();
            logger.error("作废方案异常：",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "作废方案异常："+e.getMessage());
            return wmsOperateResponseEntity;
        }

        if (flag){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "作废方案成功！");
            return wmsOperateResponseEntity;
        }else{
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "作废方案失败！");
            return wmsOperateResponseEntity;
        }

    }
}
