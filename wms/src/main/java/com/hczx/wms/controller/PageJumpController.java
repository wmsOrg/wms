package com.hczx.wms.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hczx.wms.dao.PlanDao;
import com.hczx.wms.entity.alarmingentities.AlarmingInfoEntity;
import com.hczx.wms.entity.authenticationentities.UserAllPermissionEntity;
import com.hczx.wms.entity.cacheentities.EquipmentCacheEntity;
import com.hczx.wms.entity.planentities.PlanEquipmentsEntity;
import com.hczx.wms.framework.cache.EquipmentCache;
import com.hczx.wms.model.AlarmRecordModel;
import com.hczx.wms.model.EquipmentModel;
import com.hczx.wms.model.PlanModel;
import com.hczx.wms.model.UserModel;
import com.hczx.wms.service.AlarmingOperateService;
import com.hczx.wms.service.AuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/wms")
public class PageJumpController {

    @Autowired
    private AlarmingOperateService alarmingOperateService;

    @Autowired
    private AuthenticationService authenticationService;


    /**
     * 跳转首页
     *
     * @return
     */
    @RequestMapping("/toIndex")
    public String toIndex() {

        UserAllPermissionEntity userAllPermissionEntity =  authenticationService.getInfo();
        return "html/index";
    }

    /**
     * 跳转登录页面
     *
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "html/login";
    }

    /**
     * 跳转登录页面1
     *
     * @return
     */
    @RequestMapping("/toLogin1")
    public String toLogin1() {
        return "html/login1";
    }

    /**
     * 跳转欢迎页面
     *
     * @return
     */
    @RequestMapping("/toWelcome")
    public String toWelcome() {
        return "html/welcome";
    }

    /**
     * 跳转设备管理界面
     *
     * @return
     */
    @RequestMapping("/toArticleList")
    public String toArticleList() {
        return "articlelist";
    }

    /**
     * 跳转警情管理界面
     *
     * @return
     */
    @RequestMapping("/toAlarmingList")
    public String toAlarmingList() {
        return "alarminglist";
    }

    /**
     * 跳转设备详情界面
     *
     * @return
     */
    @RequestMapping("/toArticleDetail")
    public String toArticleDetail() {
        return "articledetail";
    }

    /**
     * 跳转设备添加界面
     *
     * @return
     */
    @RequestMapping("/toArticleAdd")
    public String toArticleAdd() {
        return "articleAdd";
    }

    /**
     * 跳转预案清单界面
     *
     * @return
     */
    @RequestMapping("/toPlanList")
    public String toPlanList() {
        return "planlist";
    }

    /**
     * 跳转设备编辑界面
     *
     * @return
     */
    @RequestMapping("/toArticleEdit")
    public String toArticleEdit(Model model, @RequestParam("id") String id) {

        EquipmentModel equipmentModel = new EquipmentModel().selectById(id);

        model.addAttribute("equipmentModel",equipmentModel);
        return "articleedit";
    }

    /**
     * 跳转警情绑定方案界面
     *
     * @return
     */
    @RequestMapping("/toBindSchemes")
    public String toBindSchemes(Model model,@RequestParam("alarmingId") String alarmingId) {

        model.addAttribute("alarmingId",alarmingId);
        return "bindschemes";
    }

    /**
     * 跳转警情方案使用面
     *
     * @return
     */
    @RequestMapping("/toUseSchemes")
    public String toUseSchemes(Model model,@RequestParam("alarmingId") String alarmingId) {

        model.addAttribute("alarmingId",alarmingId);
        return "useschemes";
    }

    /**
     * 跳转警情绑定设备界面
     *
     * @return
     */
    @RequestMapping("/toBindEquipments")
    public String toBindEquipments(@RequestParam("schemeId") String schemeId,
                                   @RequestParam("alarmingId") String alarmingId,
                                   Model model) {

        model.addAttribute("schemeId",schemeId);
        model.addAttribute("alarmingId",alarmingId);

        return "bindequipments";
    }

    /**
     * 跳转预案详情界面
     *
     * @return
     */
    @RequestMapping("/toInfoPlan")
    public String toInfoPlan(@RequestParam("planId") String planId,
                             @RequestParam("alarmingId") String alarmingId,
                             @RequestParam("schemeId") String schemeId,
                             @RequestParam("operation") String operation,
                             Model model) {

        model.addAttribute("planId",planId);
        model.addAttribute("alarmingId",alarmingId);
        model.addAttribute("schemeId",schemeId);
        model.addAttribute("operation",operation);

        return "infoplan";
    }

    /**
     * 跳转预案详情启用界面
     *
     * @return
     */
    @Transactional
    @RequestMapping("/toStartPlan")
    public String toStartPlan(@RequestParam("planId") String planId,
                              @RequestParam("alarmingId") String alarmingId,
                              @RequestParam("schemeId") String schemeId,
                              @RequestParam("operation") String operation,
                              HttpServletRequest request,
                              Model model) {

        model.addAttribute("planId",planId);
        model.addAttribute("alarmingId",alarmingId);
        model.addAttribute("schemeId",schemeId);
        model.addAttribute("operation",operation);

        alarmingOperateService.startPlan(planId,schemeId,alarmingId,request,model);

        return "startplan";
    }

    /**
     * 跳转预案详情启用界面
     *
     * @return
     */
    @Transactional
    @RequestMapping("/toClosePlan")
    public String toClosePlan(@RequestParam("planId") String planId,
                              @RequestParam("alarmingId") String alarmingId,
                              @RequestParam("schemeId") String schemeId,
                              @RequestParam("operation") String operation,
                              HttpServletRequest request,
                              Model model) {

        model.addAttribute("planId",planId);
        model.addAttribute("alarmingId",alarmingId);
        model.addAttribute("schemeId",schemeId);
        model.addAttribute("operation",operation);

        alarmingOperateService.closePlan(planId,schemeId,alarmingId,request,model);

        return "closeplan";
    }

    /**
     * 跳转方案列表界面
     *
     * @return
     */
    @RequestMapping("/toSchemeList")
    public String toSchemeList() {
        return "schemelist";
    }

    /**
     * 跳转设备绑定可视化编辑界面
     *
     * @return
     */
    @RequestMapping("/toVisibleEditLayout")
    public String toVisibleEditLayout(@RequestParam("schemeId") String schemeId,Model model) {
        model.addAttribute("schemeId",schemeId);
        return "visiableEditLayout";
    }

    /**
     * 跳转方案编辑界面
     *
     * @return
     */
    @RequestMapping("/toSchemeAdd")
    public String toSchemeAdd() {
        return "schemeadd";
    }

    /**
     * 跳转设备绑定界面
     *
     * @return
     */
    @RequestMapping("/toEquipmentBind")
    public String toEquipmentBind(@RequestParam("rowCounts") String rowCounts,
                                  @RequestParam("columnCounts") String columnCounts,
                                  Model model) {
        if (StringUtils.isBlank(rowCounts)){
            model.addAttribute("error","方案编辑界面行数为空");
            return "error";
        }
        if (StringUtils.isBlank(columnCounts)){
            model.addAttribute("error","方案编辑界面列数为空");
            return "error";
        }
        model.addAttribute("rowCounts",rowCounts);
        model.addAttribute("columnCounts",columnCounts);
        return "equipmentbind";
    }

    /**
     * 跳转出入库界面
     *
     * @return
     */
    @RequestMapping("/toInventoryList")
    public String toInventoryList(@RequestParam("planId") String planId,
                             @RequestParam("alarmingId") String alarmingId,
                             @RequestParam("schemeId") String schemeId,
                             Model model) {

        model.addAttribute("planId",planId);
        model.addAttribute("alarmingId",alarmingId);
        model.addAttribute("schemeId",schemeId);

        return "inventorylist";
    }

    /**
     * 跳转方案详情界面
     *
     * @return
     */
    @RequestMapping("/toSchemeInfo")
    public String toSchemeInfo(@RequestParam("schemeId") String schemeId,
                               Model model) {

        model.addAttribute("schemeId",schemeId);

        return "schemeinfo";
    }

    /**
     * 跳转方案详情界面
     *
     * @return
     */
    @RequestMapping("/toAlarmingAdd")
    public String toAlarmingAdd() {

        return "alarmingadd";
    }

    /**
     * 跳转方案详情编辑界面
     *
     * @return
     */
    @RequestMapping("/toAlarmingEdit")
    public String toAlarmingEdit(@RequestParam("id") String id,
                                 @RequestParam("name") String name,
                                 @RequestParam("level") String level,
                                 @RequestParam("category") String category,
                                 @RequestParam("createUserId") String createUserId,
                                 @RequestParam("createTime") String createTime,
                                 @RequestParam("createUserName") String createUserName,
                                 @RequestParam("validState") String validState,
                                 @RequestParam("discribetion") String discribetion,
                                 Model model) {



        model.addAttribute("id",id);
        model.addAttribute("name",name);
        model.addAttribute("level",level);
        model.addAttribute("category",category);
        model.addAttribute("createUserId",createUserId);
        model.addAttribute("createTime",createTime);
        model.addAttribute("createUserName",createUserName);
        model.addAttribute("validState",validState);
        model.addAttribute("discribetion",discribetion);

        return "alarmingedit";
    }



}
