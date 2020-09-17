package com.hczx.wms.service;

import com.hczx.wms.entity.authenticationentities.AuthenticationEntity;
import com.hczx.wms.entity.common.WmsOperateResponseEntity;

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

}
