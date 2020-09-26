package com.hczx.wms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hczx.wms.model.SysRoleModel;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName: SysRoleDao
 * @Description: 角色持久层
 * @Date: 2020/9/25 14:30
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleModel> {
}
