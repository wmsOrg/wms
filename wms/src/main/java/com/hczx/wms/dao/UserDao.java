package com.hczx.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hczx.wms.entity.authenticationentities.UserPermissionEntity;
import com.hczx.wms.model.UserModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @ClassName: EquipmentDao
 * @Description: 用户持久层接口
 * @Date: 2020/9/1 10:12
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Mapper
public interface UserDao extends BaseMapper<UserModel> {


    /**
     * 查询用户的角色 菜单 权限
     */
    List<UserPermissionEntity> getUserPermission(String username);

    /**
     * 查询所有的菜单
     */
    Set<String> getAllMenu();

    /**
     * 查询所有的权限
     */
    Set<String> getAllPermission();
}
