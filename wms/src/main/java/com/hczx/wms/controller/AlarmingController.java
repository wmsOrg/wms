package com.hczx.wms.controller;

import com.alibaba.fastjson.JSONObject;
import com.hczx.wms.entity.alarmingentities.AlarmingInfoEntity;
import com.hczx.wms.entity.common.DataGirdResultEntity;
import com.hczx.wms.entity.common.WmsOperateResponseEntity;
import com.hczx.wms.entity.enquipmententities.EquipmentInfoEntity;
import com.hczx.wms.entity.planentities.PlanContentQueryEntity;
import com.hczx.wms.entity.planentities.PlanQueryEntity;
import com.hczx.wms.entity.schemeequipmentrelaentities.EquipmentsInSchemeEntity;
import com.hczx.wms.entity.schemeequipmentrelaentities.PlanIncreaseRequestEntity;
import com.hczx.wms.model.UserModel;
import com.hczx.wms.service.AlarmingOperateService;
import com.hczx.wms.service.AuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: AlarmingController
 * @Description:
 * @Date: 2020/9/10 10:14
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@RestController
@RequestMapping("/wms")
public class AlarmingController {

    private Logger logger = LoggerFactory.getLogger(AlarmingController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AlarmingOperateService alarmingOperateService;

    /**
     * 查询警情列表
     *
     * @param page
     * @param size
     * @param id
     * @param name
     * @param level
     * @param category
     * @param beginTime
     * @param endTime
     * @param validState
     * @return
     */
    @RequestMapping(value = "/alarmingList",method = RequestMethod.GET)
    public DataGirdResultEntity alarmingList(@RequestParam("page") Integer page,
                                             @RequestParam("limit") Integer size,
                                             @RequestParam("id") String id,
                                             @RequestParam("name") String name,
                                             @RequestParam("level") Integer level,
                                             @RequestParam("category") String category,
                                             @RequestParam("beginTime") String beginTime,
                                             @RequestParam("endTime") String endTime,
                                             @RequestParam("validState") String validState) {

        AlarmingInfoEntity alarmingInfoEntity = new AlarmingInfoEntity();
        alarmingInfoEntity.setId(id);
        alarmingInfoEntity.setName(name);
        alarmingInfoEntity.setLevel(level);
        alarmingInfoEntity.setCategory(category);
        alarmingInfoEntity.setBeginTime(beginTime);
        alarmingInfoEntity.setEndTime(endTime);
        alarmingInfoEntity.setValidState(validState);

        DataGirdResultEntity dataGirdResultEntity = alarmingOperateService.listAlarmInfo((page-1)*size, size, alarmingInfoEntity);

        return dataGirdResultEntity;

    }

    /**
     * 登记警情
     * @param alarmingInfoEntity
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/alarmingIncrease",method = RequestMethod.POST)
    public WmsOperateResponseEntity alarmingIncrease(@RequestBody AlarmingInfoEntity alarmingInfoEntity , HttpServletResponse response, HttpServletRequest request) {

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (alarmingInfoEntity == null){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "新增警情失败: 警情请求报文不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(alarmingInfoEntity.getName())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "新增设备失败: 警情名称不能为空！");
            return wmsOperateResponseEntity;
        }

        if (alarmingInfoEntity.getLevel() == null){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "新增设备失败: 警情级别不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(alarmingInfoEntity.getCategory())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "新增设备失败: 警情类别不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(alarmingInfoEntity.getValidState()) || !alarmingInfoEntity.getValidState().equals("on")){
            alarmingInfoEntity.setValidState("0");
        }else{
            alarmingInfoEntity.setValidState("1");
        }

        UserModel userModel = (UserModel)request.getSession().getAttribute("LOGIN_USER");
        alarmingInfoEntity.setCreateUserId(userModel.getId());
        alarmingInfoEntity.setCreateUserName(userModel.getName());

        //登记设备操作
        wmsOperateResponseEntity = alarmingOperateService.registAlarming(alarmingInfoEntity);
        return wmsOperateResponseEntity;

    }

    /**
     * 警情绑定对应的方案中的设备
     *
     * @param requestBody
     * @return
     */
    @RequestMapping(value = "/alarmingBindEquipmentsInScheme",method = RequestMethod.POST)
    public WmsOperateResponseEntity alarmingBindEquipmentsInScheme(@RequestBody PlanIncreaseRequestEntity requestBody) {

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (requestBody == null){
            logger.error("预案新增异常：请求实体不能为空！");
            return authenticationService.packageOpeaterResponseBean("4", false, "预案新增异常：请求实体不能为空！");
        }

        if (StringUtils.isBlank(requestBody.getPlanName())){
            logger.error("预案新增异常：预案名称不能为空！");
            return authenticationService.packageOpeaterResponseBean("4", false, "预案新增异常：预案名称不能为空！");
        }

        if (StringUtils.isBlank(requestBody.getAlarmingId())){
            logger.error("预案新增异常：警情唯一标识不能为空！");
            return authenticationService.packageOpeaterResponseBean("4", false, "预案新增异常：警情唯一标识不能为空！");
        }

        if (StringUtils.isBlank(requestBody.getSchemeId())){
            logger.error("预案新增异常：方案唯一标识不能为空！");
            return authenticationService.packageOpeaterResponseBean("4", false, "预案新增异常：方案唯一标识不能为空！");
        }

        if (requestBody.getDatas() == null || requestBody.getDatas().isEmpty()){
            logger.error("预案新增异常：请求实体不能为空！");
            return authenticationService.packageOpeaterResponseBean("4", false, "预案新增异常：预案所绑定设备不能为空！");
        }

        String planName = requestBody.getPlanName();
        String alarmingId = requestBody.getAlarmingId();
        String schemeId = requestBody.getSchemeId();
        List<EquipmentsInSchemeEntity> equipmentsInSchemeEntities = requestBody.getDatas();

//        try{
//            equipmentsInSchemeEntities = JSONObject.parseArray(requestBody,EquipmentsInSchemeEntity.class);
//        }catch (Exception e){
//            logger.error("警情绑定设备异常：请求转换实体异常：",e);
//            return authenticationService.packageOpeaterResponseBean("4", false, "警情绑定设备异常：请求转换实体出错！");
//        }


        wmsOperateResponseEntity = alarmingOperateService.alarmingBindEquipmentsInScheme(planName,alarmingId,schemeId,equipmentsInSchemeEntities);



        return wmsOperateResponseEntity;

    }

    /**
     * 预案查询
     *
     * @param planQueryEntity
     * @return
     */
    @RequestMapping(value = "/planContentList",method = RequestMethod.POST)
    public WmsOperateResponseEntity planContentList(@RequestBody PlanContentQueryEntity planQueryEntity,HttpServletRequest request){

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (StringUtils.isBlank(planQueryEntity.getSchemeId())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "预案所绑定的设备查询失败：无法查询到具体方案唯一标识！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(planQueryEntity.getAlarmingId())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "预案所绑定的设备查询失败：无法查询到具体警情唯一标识！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(planQueryEntity.getPlanId())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "预案所绑定的设备查询失败：无法查询到具体预案唯一标识！");
            return wmsOperateResponseEntity;
        }

        wmsOperateResponseEntity = alarmingOperateService.planList(planQueryEntity);
        return wmsOperateResponseEntity;

    }

    /**
     * 警情编辑
     *
     * @param alarmingInfoEntity
     * @return
     */
    @RequestMapping(value = "/alarmingEdit",method = RequestMethod.POST)
    public WmsOperateResponseEntity alarmingEdit(@RequestBody AlarmingInfoEntity alarmingInfoEntity){

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (StringUtils.isBlank(alarmingInfoEntity.getId())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "警情编辑失败：具体警情唯一标识不能为空！");
            return wmsOperateResponseEntity;
        }

        if (alarmingInfoEntity.getLevel()== null){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "警情编辑失败：级别不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(alarmingInfoEntity.getCategory())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "警情编辑失败：类别不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(alarmingInfoEntity.getValidState()) || !alarmingInfoEntity.getValidState().equals("on")){
            alarmingInfoEntity.setValidState("0");
        }else{
            alarmingInfoEntity.setValidState("1");
        }

        wmsOperateResponseEntity = alarmingOperateService.editAlarming(alarmingInfoEntity);
        return wmsOperateResponseEntity;

    }

    /**
     * 警情作废
     *
     * @param alarmingInfoEntities
     * @return
     */
    @RequestMapping(value = "/delAlarmings",method = RequestMethod.POST)
    public WmsOperateResponseEntity delAlarmings(@RequestBody List<AlarmingInfoEntity> alarmingInfoEntities){

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (alarmingInfoEntities == null || alarmingInfoEntities.isEmpty()){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "警情作废失败：请求不能为空！");
            return wmsOperateResponseEntity;
        }

        List<String> ids = null;
        try{
            ids = alarmingInfoEntities.stream().map(AlarmingInfoEntity::getId).distinct().collect(Collectors.toList());
        }catch (Exception e){
            logger.error("警情作废失败:",e);
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "警情作废失败："+e.getMessage());
            return wmsOperateResponseEntity;
        }



        wmsOperateResponseEntity = alarmingOperateService.delAlarming(ids);
        return wmsOperateResponseEntity;

    }

    /**
     * 出库完毕操作
     *
     * @param planQueryEntity
     * @return
     */
    @RequestMapping(value = "/outover",method = RequestMethod.POST)
    public WmsOperateResponseEntity outover(@RequestBody PlanContentQueryEntity planQueryEntity){

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (StringUtils.isBlank(planQueryEntity.getSchemeId())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "出库完毕操作失败：无法查询到具体方案唯一标识！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(planQueryEntity.getAlarmingId())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "出库完毕操作失败：无法查询到具体警情唯一标识！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(planQueryEntity.getPlanId())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "出库完毕操作失败：无法查询到具体预案唯一标识！");
            return wmsOperateResponseEntity;
        }

        wmsOperateResponseEntity = alarmingOperateService.outover(planQueryEntity);
        return wmsOperateResponseEntity;

    }

    /**
     * 入库完毕操作
     *
     * @param planQueryEntity
     * @return
     */
    @RequestMapping(value = "/inover",method = RequestMethod.POST)
    public WmsOperateResponseEntity inover(@RequestBody PlanContentQueryEntity planQueryEntity){

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (StringUtils.isBlank(planQueryEntity.getSchemeId())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "入库完毕操作失败：无法查询到具体方案唯一标识！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(planQueryEntity.getAlarmingId())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "入库完毕操作失败：无法查询到具体警情唯一标识！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(planQueryEntity.getPlanId())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "入库完毕操作失败：无法查询到具体预案唯一标识！");
            return wmsOperateResponseEntity;
        }

        wmsOperateResponseEntity = alarmingOperateService.inover(planQueryEntity);
        return wmsOperateResponseEntity;

    }
}
