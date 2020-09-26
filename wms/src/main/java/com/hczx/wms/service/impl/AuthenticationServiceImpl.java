package com.hczx.wms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hczx.wms.dao.UserDao;
import com.hczx.wms.entity.authenticationentities.AuthenticationEntity;
import com.hczx.wms.entity.authenticationentities.UserAllPermissionEntity;
import com.hczx.wms.entity.common.WmsOperateResponseEntity;
import com.hczx.wms.framework.config.shiro.entity.Constants;
import com.hczx.wms.model.UserModel;
import com.hczx.wms.mybatisplusserveice.UserService;
import com.hczx.wms.service.AuthenticationService;
import com.hczx.wms.util.CommonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: AuthenticationServiceImpl
 * @Description: 认证服务实现
 * @Date: 2020/8/31 11:57
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    /**
     * 简易注册用户
     *
     * @param authenticationEntity
     */
    @Override
    public WmsOperateResponseEntity registUser(AuthenticationEntity authenticationEntity) {

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        //验证注册信息
        wmsOperateResponseEntity = valideRegistInfo(authenticationEntity);

        //新增用户信息
        if (wmsOperateResponseEntity != null && wmsOperateResponseEntity.isFlag()){

            boolean flag = userService.saveRegisterUser(authenticationEntity);
            if (!flag){

                wmsOperateResponseEntity = packageOpeaterResponseBean("4", false, "注册失败：新增用户信息至数据库失败！");
                return wmsOperateResponseEntity;

            }else{

                wmsOperateResponseEntity = packageOpeaterResponseBean("9", true, "注册成功！");
                return wmsOperateResponseEntity;

            }

        }else{
            return wmsOperateResponseEntity;
        }

    }

    /**
     * 验证注册信息
     *
     * @param authenticationEntity
     * @return
     */
    private WmsOperateResponseEntity valideRegistInfo(AuthenticationEntity authenticationEntity){

        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();

        //拼装手机号条件构造器
        QueryWrapper<UserModel> mobileUserModelQueryWrapper = new QueryWrapper<>();
        mobileUserModelQueryWrapper.eq("mobile", authenticationEntity.getMobile());
        //手机号是否已被注册
        List<UserModel> userModels = userDao.selectList(mobileUserModelQueryWrapper);
        if (userModels != null && !userModels.isEmpty()){

            wmsOperateResponseEntity = packageOpeaterResponseBean("4", false, "注册失败：手机号已被注册！");
            return wmsOperateResponseEntity;

        }

        //拼装账号条件构造器
        QueryWrapper<UserModel> loginNameUserModelQueryWrapper = new QueryWrapper<>();
        loginNameUserModelQueryWrapper.eq("loginName", authenticationEntity.getUserName());

        //是否存在该用户名
        List<UserModel> loginNameUserModels = userDao.selectList(loginNameUserModelQueryWrapper);
        if (loginNameUserModels != null && !loginNameUserModels.isEmpty()){

            wmsOperateResponseEntity = packageOpeaterResponseBean("4", false, "注册失败：用户名已被注册！");
            return wmsOperateResponseEntity;

        }

        wmsOperateResponseEntity = packageOpeaterResponseBean("9", true, null);
        return wmsOperateResponseEntity;

    }

    /**
     * 封装响应实体
     *
     * @param code
     * @param flag
     * @param msg
     * @return
     */
    public WmsOperateResponseEntity packageOpeaterResponseBean(String code, boolean flag, String msg){
        WmsOperateResponseEntity wmsOperateResponseEntity = new WmsOperateResponseEntity();
        wmsOperateResponseEntity.setCode(code);
        wmsOperateResponseEntity.setMsg(msg);
        wmsOperateResponseEntity.setFlag(flag);
        return wmsOperateResponseEntity;
    }

    /**
     * 获取用户
     *
     * @param loginName
     * @param password
     * @return
     */
    @Override
    public UserModel getUser(String loginName, String password) {

        //拼装账号密码条件构造器
        QueryWrapper<UserModel> userModelQueryWrapper = new QueryWrapper<>();
        userModelQueryWrapper.eq("loginName", loginName).eq("password", password);

        UserModel userModel = userDao.selectOne(userModelQueryWrapper);

        return userModel;
    }

    /**
     * 查询当前登录用户的权限等信息
     */
    @Override
    public UserAllPermissionEntity getInfo() {
        //从session获取用户信息
        Session session = SecurityUtils.getSubject().getSession();
        UserModel userInfo = (UserModel) session.getAttribute(Constants.SESSION_USER_INFO);
        String username = userInfo.getLoginName();

        UserAllPermissionEntity userAllPermissionEntity = userService.getUserPermission(username);

        if (userAllPermissionEntity != null) {

            session.setAttribute(Constants.SESSION_USER_PERMISSION, userAllPermissionEntity);

//            info.put("userPermission", userAllPermissionEntity);
        }
        return userAllPermissionEntity;
    }

    /**
     * 退出登录
     */
    @Override
    public JSONObject logout() {
        try {
            Subject currentUser = SecurityUtils.getSubject();
            currentUser.logout();
        } catch (Exception e) {
        }
        return CommonUtil.successJson();
    }
}
