package com.hczx.wms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hczx.wms.entity.common.DataGirdResultEntity;
import com.hczx.wms.entity.common.WmsOperateResponseEntity;
import com.hczx.wms.entity.enquipmententities.EquipmentInfoEntity;
import com.hczx.wms.entity.enquipmententities.EquipmentZtreeEntity;
import com.hczx.wms.entity.enquipmententities.EquipmentZtreeNode;
import com.hczx.wms.service.AuthenticationService;
import com.hczx.wms.service.EquipmentOperateService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: EquipmentController
 * @Description: 设备管理模块
 * @Date: 2020/9/1 10:23
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@RestController
@RequestMapping("/wms")
public class EquipmentController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private EquipmentOperateService equipmentOperateService;

    /**
     * 新增设备信息
     *
     * @param equipmentInfoEntity
     * @return
     */
    @RequestMapping("/equipmentIncrease")
    public WmsOperateResponseEntity equipmentIncrease(@RequestBody EquipmentInfoEntity equipmentInfoEntity){

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (equipmentInfoEntity == null){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "新增设备失败: 设备请求报文不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(equipmentInfoEntity.getEquipmentRfid())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "新增设备失败: 设备ID不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(equipmentInfoEntity.getEquipmentName())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "新增设备失败: 设备名称不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(equipmentInfoEntity.getEquipmentGrade())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "新增设备失败: 设备等级不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(equipmentInfoEntity.getEquipmentClass())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "新增设备失败: 设备分类不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(equipmentInfoEntity.getValidState()) || !equipmentInfoEntity.getValidState().equals("on")){
            equipmentInfoEntity.setValidState("0");
        }else{
            equipmentInfoEntity.setValidState("1");
        }

        if (StringUtils.isBlank(equipmentInfoEntity.getEquipmentCompanyId())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "新增设备失败: 设备所属公司ID不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(equipmentInfoEntity.getEquipmentCompanyName())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "新增设备失败: 设备所属公司名称不能为空！");
            return wmsOperateResponseEntity;
        }

        //登记设备操作
        wmsOperateResponseEntity = equipmentOperateService.registEquipment(equipmentInfoEntity);
        return wmsOperateResponseEntity;
    }

    /**
     * 编辑设备信息
     *
     * @param equipmentInfoEntity
     * @return
     */
    @RequestMapping("/equipmentEdit")
    public WmsOperateResponseEntity equipmentEdit(@RequestBody EquipmentInfoEntity equipmentInfoEntity){

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (equipmentInfoEntity == null){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "编辑设备失败: 设备请求报文不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(equipmentInfoEntity.getId())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "编辑设备失败: 设备主索引不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(equipmentInfoEntity.getEquipmentRfid())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "编辑设备失败: 设备ID不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(equipmentInfoEntity.getEquipmentName())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "编辑设备失败: 设备名称不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(equipmentInfoEntity.getEquipmentGrade())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "编辑设备失败: 设备等级不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(equipmentInfoEntity.getEquipmentClass())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "编辑设备失败: 设备分类不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(equipmentInfoEntity.getValidState()) || !equipmentInfoEntity.getValidState().equals("on")){
            equipmentInfoEntity.setValidState("0");
        }else{
            equipmentInfoEntity.setValidState("1");
        }

        if (StringUtils.isBlank(equipmentInfoEntity.getEquipmentCompanyId())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "编辑设备失败: 设备所属公司ID不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(equipmentInfoEntity.getEquipmentCompanyName())){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "编辑设备失败: 设备所属公司名称不能为空！");
            return wmsOperateResponseEntity;
        }

        //登记设备操作
        wmsOperateResponseEntity = equipmentOperateService.editEquipment(equipmentInfoEntity);
        return wmsOperateResponseEntity;
    }

    /**
     * 查询设备信息
     *
     * @param
     * @return
     */
    @RequestMapping("/equipmentList")
    public DataGirdResultEntity equipmentList(@RequestParam("page") Integer page,
                                              @RequestParam("limit") Integer size,
                                              @RequestParam("equipmentRfid") String equipmentRfid,
                                              @RequestParam("equipmentName") String equipmentName,
                                              @RequestParam("equipmentGrade") String equipmentGrade,
                                              @RequestParam("equipmentClass") String equipmentClass,
                                              @RequestParam("equipmentPreId") String equipmentPreId,
                                              @RequestParam("equipmentCompanyId") String equipmentCompanyId,
                                              @RequestParam("equipmentCompanyName") String equipmentCompanyName,
                                              @RequestParam("validState") String validState) {

        EquipmentInfoEntity equipmentInfoEntity = new EquipmentInfoEntity();
        equipmentInfoEntity.setEquipmentRfid(equipmentRfid);
        equipmentInfoEntity.setEquipmentName(equipmentName);
        equipmentInfoEntity.setEquipmentGrade(equipmentGrade);
        equipmentInfoEntity.setEquipmentClass(equipmentClass);
        equipmentInfoEntity.setEquipmentPreId(equipmentPreId);
        equipmentInfoEntity.setEquipmentCompanyId(equipmentCompanyId);
        equipmentInfoEntity.setEquipmentCompanyName(equipmentCompanyName);
//        equipmentInfoEntity.setSchemeId(schemeId);
//        equipmentInfoEntity.setSchemeName(schemeName);
        equipmentInfoEntity.setValidState(validState);
//        equipmentInfoEntity.setOccupyState(occupyState);

        DataGirdResultEntity dataGirdResultEntity = equipmentOperateService.listEquipment((page-1)*size, size, equipmentInfoEntity);

        return dataGirdResultEntity;

    }

    /**
     * 查询一级设备信息并封装成树
     *
     * @param
     * @return
     */
    @RequestMapping("/equipmentZtree")
    public EquipmentZtreeEntity equipmentZtree() {

        EquipmentZtreeEntity equipmentZtreeEntity = equipmentOperateService.ListEquipmentZtree();

        if (equipmentZtreeEntity == null){
            equipmentZtreeEntity = new EquipmentZtreeEntity();
            equipmentZtreeEntity.setData(null);
            equipmentZtreeEntity.setErrorFlag(true);
            equipmentZtreeEntity.setErrMsg("查询信息失败");
        }

        return equipmentZtreeEntity;

    }

    /**
     * 删除设备信息
     *
     * @param
     * @return
     */
    @RequestMapping("/delEquipments")
    public WmsOperateResponseEntity equipmentList(@RequestBody List<EquipmentInfoEntity> equipmentInfoEntities) {
        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (equipmentInfoEntities != null && !equipmentInfoEntities.isEmpty()){

            //调用删除接口
            wmsOperateResponseEntity = equipmentOperateService.delEquipment(equipmentInfoEntities);
            return wmsOperateResponseEntity;

        }else {

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "删除设备失败: 设备删除请求不能为空！");
            return wmsOperateResponseEntity;

        }


    }

    /**
     * 关联设备信息
     *
     * @param
     * @return
     */
    @RequestMapping("/linkEquipments")
    public WmsOperateResponseEntity linkEquipments(@RequestBody List<EquipmentInfoEntity> equipmentInfoEntities) {
        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (equipmentInfoEntities != null && !equipmentInfoEntities.isEmpty()){

            //调用关联接口
            wmsOperateResponseEntity = equipmentOperateService.linkEquipment(equipmentInfoEntities);
            return wmsOperateResponseEntity;

        }else {

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "关联设备失败: 设备关联请求不能为空！");
            return wmsOperateResponseEntity;

        }


    }

    /**
     * 根据关联号查询设备信息
     *
     * @param
     * @return
     */
    @RequestMapping("/searchByLinkingNo")
    public WmsOperateResponseEntity searchByLinkingNo(@RequestParam("linkingNo") String linkingNo, @Param("equipmentId") String equipmentId) {
        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (StringUtils.isBlank(linkingNo)){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "查询设备失败: 设备关联号不能为空！");
            return wmsOperateResponseEntity;
        }

        if (StringUtils.isBlank(equipmentId)){
            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "查询设备失败: 当前设备唯一标识不能为空！");
            return wmsOperateResponseEntity;
        }


        //调用关联接口
        wmsOperateResponseEntity = equipmentOperateService.searchByLinkingNo(linkingNo,equipmentId);
        return wmsOperateResponseEntity;


    }

    /**
     * 查询二级设备
     *
     * @param equipmentParentId
     * @return
     */
    @RequestMapping("/equipmentLv2List")
    public WmsOperateResponseEntity equipmentLv2List(@RequestParam("equipmentParentId") String equipmentParentId) {
        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (!StringUtils.isBlank(equipmentParentId)){

            //调用删除接口
            wmsOperateResponseEntity = equipmentOperateService.listEquipmentsLv2(equipmentParentId);
            return wmsOperateResponseEntity;

        }else {

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "查询二级设备失败: 未接收到上级设备唯一标识！");
            return wmsOperateResponseEntity;

        }


    }

}
