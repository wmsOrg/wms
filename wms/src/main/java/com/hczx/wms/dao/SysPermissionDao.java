package com.hczx.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hczx.wms.model.SysPermissionModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName: SysPermissionDao
 * @Description: 权限持久层
 * @Date: 2020/9/25 14:32
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Mapper
public interface SysPermissionDao extends BaseMapper<SysPermissionModel> {
}
