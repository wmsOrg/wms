package com.hczx.wms.mybatisplusserveice;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hczx.wms.entity.authenticationentities.AuthenticationEntity;
import com.hczx.wms.entity.authenticationentities.UserAllPermissionEntity;
import com.hczx.wms.model.UserModel;

/**
 * @ClassName: UserService
 * @Description: 用户服务层接口
 * @Date: 2020/9/1 10:14
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public interface UserService extends IService<UserModel> {

    /**
     * 新增注册用户
     *
     * @param authenticationEntity
     * @return
     */
    boolean saveRegisterUser(AuthenticationEntity authenticationEntity);

    /**
     * 查询某用户的 角色  菜单列表   权限列表
     *
     * @param username
     * @return
     */
    UserAllPermissionEntity getUserPermission(String username);

}
