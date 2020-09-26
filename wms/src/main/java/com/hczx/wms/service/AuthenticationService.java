package com.hczx.wms.service;

import com.alibaba.fastjson.JSONObject;
import com.hczx.wms.entity.authenticationentities.AuthenticationEntity;
import com.hczx.wms.entity.authenticationentities.UserAllPermissionEntity;
import com.hczx.wms.entity.common.WmsOperateResponseEntity;
import com.hczx.wms.model.UserModel;

/**
 * @ClassName: AuthenticationService
 * @Description: 认证服务接口
 * @Date: 2020/8/31 11:57
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public interface AuthenticationService {

    /**
     * 简易注册用户
     *
     * @param authenticationEntity
     * @return
     */
    WmsOperateResponseEntity registUser(AuthenticationEntity authenticationEntity);

    /**
     * 封装认证响应实体
     *
     * @param code
     * @param flag
     * @param msg
     * @return
     */
    WmsOperateResponseEntity packageOpeaterResponseBean(String code, boolean flag, String msg);


    /**
     * 获取用户
     *
     * @param loginName
     * @param password
     * @return
     */
    UserModel getUser(String loginName, String password);

    /**
     * 查询当前登录用户的权限等信息
     * @return
     */
    UserAllPermissionEntity getInfo();

    JSONObject logout();
}
