package com.hczx.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hczx.wms.dao.UserDao;
import com.hczx.wms.entity.authenticationentities.AuthenticationEntity;
import com.hczx.wms.entity.common.WmsOperateResponseEntity;
import com.hczx.wms.model.UserModel;
import com.hczx.wms.service.AuthenticationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName: AuthenticationController
 * @Description: 身份认证模块
 * @Date: 2020/8/31 10:48
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@RestController
@RequestMapping("/wms")
public class AuthenticationController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * 登录操作
     *
     * @param authenticationEntity 身份认证实体
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public WmsOperateResponseEntity login(@RequestBody AuthenticationEntity authenticationEntity, HttpServletResponse response, HttpServletRequest request){

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (authenticationEntity == null){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "登陆失败：身份认证信息不能为空！");
            return wmsOperateResponseEntity;

        }

        if (StringUtils.isBlank(authenticationEntity.getUserName())){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "登陆失败：用户账号不能为空！");
            return wmsOperateResponseEntity;

        }

        if (StringUtils.isBlank(authenticationEntity.getPassword())){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "登陆失败：用户密码不能为空！");
            return wmsOperateResponseEntity;

        }

        //拼装账号密码条件构造器
        QueryWrapper<UserModel> userModelQueryWrapper = new QueryWrapper<>();
        userModelQueryWrapper.eq("loginName", authenticationEntity.getUserName()).eq("password", authenticationEntity.getPassword());

        //获取用户
        List<UserModel> userModels = userDao.selectList(userModelQueryWrapper);
        if (userModels == null || userModels.isEmpty()){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "登陆失败：用户不存在！");
            return wmsOperateResponseEntity;

        }else {
            int size = userModels.size();
            if (size == 1){

                wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("9", true, "登陆成功！");
                request.getSession().setAttribute("LOGIN_USER",userModels.get(0));
                return wmsOperateResponseEntity;

            }else if (size >1){

                wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "登陆失败：用户不唯一！");
                return wmsOperateResponseEntity;

            }
        }

        wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "登陆失败！");
        return wmsOperateResponseEntity;

    }

    /**
     * 注册操作
     *
     * @param authenticationEntity 身份认证实体
     * @return
     */
    @RequestMapping("/regist")
    public WmsOperateResponseEntity regist(@RequestBody AuthenticationEntity authenticationEntity){

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        if (authenticationEntity == null){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "注册失败：身份认证信息不能为空！");
            return wmsOperateResponseEntity;

        }

        if (StringUtils.isBlank(authenticationEntity.getUserName())){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "注册失败：用户账号不能为空！");
            return wmsOperateResponseEntity;

        }

        if (StringUtils.isBlank(authenticationEntity.getPassword())){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "注册失败：用户密码不能为空！");
            return wmsOperateResponseEntity;

        }

        if (StringUtils.isBlank(authenticationEntity.getConfirmPassword())){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "注册失败：用户密码确认不能为空！");
            return wmsOperateResponseEntity;

        }

        if (!authenticationEntity.getPassword().equals(authenticationEntity.getConfirmPassword())){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "注册失败：用户密码与用户密码确认不一致！");
            return wmsOperateResponseEntity;

        }

        if (StringUtils.isBlank(authenticationEntity.getMobile())){

            wmsOperateResponseEntity = authenticationService.packageOpeaterResponseBean("4", false, "注册失败：用户手机号码不能为空！");
            return wmsOperateResponseEntity;

        }

        wmsOperateResponseEntity = authenticationService.registUser(authenticationEntity);
        return wmsOperateResponseEntity;

    }

}
