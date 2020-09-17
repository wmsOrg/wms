package com.hczx.wms.controller;

import com.hczx.wms.entity.common.WmsOperateResponseEntity;
import com.hczx.wms.entity.planentities.PlanQueryEntity;
import com.hczx.wms.entity.schemeentities.SchemeEditEntity;
import com.hczx.wms.entity.schemeentities.SchemeIncreaseEntity;
import com.hczx.wms.entity.schemeequipmentrelaentities.EquipmentsInSchemeEntity;
import com.hczx.wms.model.UserModel;
import com.hczx.wms.mybatisplusserveice.SchemeEquipmentRelaService;
import com.hczx.wms.service.AuthenticationService;
import com.hczx.wms.service.SchemeOperateService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: SchemeController
 * @Description:
 * @Date: 2020/9/9 09:34
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@RestController
@RequestMapping("/wms")
public class SchemeController {

    private Logger logger = LoggerFactory.getLogger(SchemeController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private SchemeOperateService schemeService;


    /**
     * 方案新增
     *
     * @param schemeIncreaseEntity
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = "/schemeIncrease",method = RequestMethod.POST)
    public WmsOperateResponseEntity schemeIncrease(@RequestBody SchemeIncreaseEntity schemeIncreaseEntity, HttpServletResponse response, HttpServletRequest request){

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (schemeIncreaseEntity == null){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "方案新增失败：方案新增信息不能为空！");
            return wmsOperateResponseEntity;

        }

        if (StringUtils.isBlank(schemeIncreaseEntity.getSchemeName())){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "方案新增失败：方案名称不能为空！");
            return wmsOperateResponseEntity;

        }

        if (StringUtils.isBlank(schemeIncreaseEntity.getLevel())){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "方案新增失败：方案级别不能为空！");
            return wmsOperateResponseEntity;

        }

        if (StringUtils.isBlank(schemeIncreaseEntity.getDisaster())){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "方案新增失败：灾情类别不能为空！");
            return wmsOperateResponseEntity;

        }

        if (StringUtils.isBlank(schemeIncreaseEntity.getValidState()) || !schemeIncreaseEntity.getValidState().equals("on")){
            schemeIncreaseEntity.setValidState("0");
        }else{
            schemeIncreaseEntity.setValidState("1");
        }

        UserModel userModel = (UserModel)request.getSession().getAttribute("LOGIN_USER");
        schemeIncreaseEntity.setCreateUserId(userModel.getId());
        schemeIncreaseEntity.setCreateUserName(userModel.getName());
        schemeIncreaseEntity.setMaxRows(0);
        schemeIncreaseEntity.setMaxColumns(0);

        wmsOperateResponseEntity = schemeService.increaseScheme(schemeIncreaseEntity);
        return wmsOperateResponseEntity;

    }

    /**
     * 方案编辑
     *
     * @param schemeEditEntity
     * @return
     */
    @RequestMapping(value = "/schemeEdit",method = RequestMethod.POST)
    public WmsOperateResponseEntity schemeEdit(@RequestBody SchemeEditEntity schemeEditEntity){

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (schemeEditEntity == null){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "方案编辑失败：方案编辑信息不能为空！");
            return wmsOperateResponseEntity;

        }

        if (StringUtils.isBlank(schemeEditEntity.getSchemeId())){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "方案编辑失败：方案唯一标识不能为空！");
            return wmsOperateResponseEntity;

        }

        if (schemeEditEntity.getMaxRows() == null || schemeEditEntity.getMaxRows() == 0){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "方案编辑失败：方案最大行数不能为空或零！");
            return wmsOperateResponseEntity;

        }

        if (schemeEditEntity.getMaxColumns() == null || schemeEditEntity.getMaxColumns() == 0){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "方案编辑失败：方案最大列数不能为空或零！");
            return wmsOperateResponseEntity;

        }

        if (schemeEditEntity.getDatas() == null || schemeEditEntity.getDatas().isEmpty()){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "方案编辑失败：绑定设备单元格不能为空！");
            return wmsOperateResponseEntity;

        }

        //方案绑定设备
        wmsOperateResponseEntity = schemeService.schemeBindEquipments(schemeEditEntity);
        return wmsOperateResponseEntity;

    }

    /**
     * 方案查询
     *
     * @param schemeIncreaseEntity
     * @return
     */
    @RequestMapping(value = "/schemeList",method = RequestMethod.POST)
    public WmsOperateResponseEntity schemeList(@RequestBody SchemeIncreaseEntity schemeIncreaseEntity){

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        wmsOperateResponseEntity = schemeService.listScheme(schemeIncreaseEntity);
        return wmsOperateResponseEntity;

    }

    /**
     * 预案查询
     *
     * @param planQueryEntity
     * @return
     */
    @RequestMapping(value = "/planList",method = RequestMethod.POST)
    public WmsOperateResponseEntity planList(@RequestBody PlanQueryEntity planQueryEntity){

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        wmsOperateResponseEntity = schemeService.planList(planQueryEntity);
        return wmsOperateResponseEntity;

    }

    /**
     * 方案设备查询
     *
     * @param schemeId
     * @return
     */
    @RequestMapping(value = "/searchEquipmentsBySchemeId",method = RequestMethod.GET)
    public WmsOperateResponseEntity searchEquipmentsBySchemeId(@RequestParam("schemeId") String schemeId){

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (StringUtils.isBlank(schemeId)){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "打开页面失败：方案唯一标识不能为空！");
            return wmsOperateResponseEntity;
        }

        wmsOperateResponseEntity = schemeService.listEquipments(schemeId);
        return wmsOperateResponseEntity;

    }

    /**
     * 展示图片
     *
     * @param fileName
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "showImageByPath")
    public void showImageByPath(@RequestParam("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) throws Exception {

        schemeService.getImage(fileName,request,response);

    }


}
