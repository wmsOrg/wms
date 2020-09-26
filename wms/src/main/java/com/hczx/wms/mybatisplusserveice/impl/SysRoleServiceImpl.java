package com.hczx.wms.mybatisplusserveice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hczx.wms.dao.SysRoleDao;
import com.hczx.wms.model.SysRoleModel;
import com.hczx.wms.mybatisplusserveice.SysRoleService;
import org.springframework.stereotype.Service;

/**
 * @ClassName: SysRoleServiceImpl
 * @Description: 角色服务实现
 * @Date: 2020/9/25 14:25
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRoleModel> implements SysRoleService {
}
