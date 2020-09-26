package com.hczx.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hczx.wms.model.SysRolePermissionModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName: SysRolePermissionDao
 * @Description: 角色权限持久层
 * @Date: 2020/9/25 14:31
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Mapper
public interface SysRolePermissionDao extends BaseMapper<SysRolePermissionModel> {
}
