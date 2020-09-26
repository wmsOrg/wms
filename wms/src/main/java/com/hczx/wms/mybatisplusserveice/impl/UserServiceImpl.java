package com.hczx.wms.mybatisplusserveice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hczx.wms.dao.UserDao;
import com.hczx.wms.entity.authenticationentities.AuthenticationEntity;
import com.hczx.wms.entity.authenticationentities.UserAllPermissionEntity;
import com.hczx.wms.entity.authenticationentities.UserPermissionEntity;
import com.hczx.wms.model.UserModel;
import com.hczx.wms.mybatisplusserveice.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName: UserServiceImpl
 * @Description: 用户服务层实现
 * @Date: 2020/9/1 10:14
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserModel> implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    /**
     * 新增注册用户
     *
     * @param authenticationEntity
     * @return
     */
    @Override
    @Transactional
    public boolean saveRegisterUser(AuthenticationEntity authenticationEntity) {
        UserModel userModel = new UserModel();
        userModel.setId(UUID.randomUUID().toString());
        userModel.setLoginName(authenticationEntity.getUserName());
        userModel.setPassword(authenticationEntity.getPassword());
        userModel.setMobile(authenticationEntity.getMobile());
        userModel.setCreateTime(new Date());
        boolean flag = true;
        //新增用户
        try{
            flag = userModel.insert();
        }catch (Exception e){
            logger.error("新增注册用户异常：",e);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }


        return flag;
    }

    /**
     * 查询某用户的 角色  菜单列表   权限列表
     *
     * @param username
     * @return
     */
    @Override
    public UserAllPermissionEntity getUserPermission(String username) {
        List<UserPermissionEntity> userPermissions = userDao.getUserPermission(username);

        UserAllPermissionEntity userAllPermissionEntity = new UserAllPermissionEntity();

        if (userPermissions != null && !userPermissions.isEmpty()) {
            List<UserPermissionEntity> admins = userPermissions.stream().filter(o->!StringUtils.isBlank(o.getRoleId()) && o.getRoleId().equals("1")).collect(Collectors.toList());
            if (admins != null && !admins.isEmpty()){
                //查询所有菜单  所有权限
                Set<String> menuList = userDao.getAllMenu();
                Set<String> permissionList = userDao.getAllPermission();
//                Map<String, Set<String>> permissionLists = permissionList.stream().filter(o->!StringUtils.isBlank(o.getMenuCode()) && !StringUtils.isBlank(o.getPermissionCode())).collect(Collectors.toMap(UserPermissionEntity::getMenuCode,
//                        p ->  {
//                            Set<String> getPermissionList = new HashSet<>();
//                            getPermissionList.add(p.getPermissionCode());
//                            return getPermissionList;
//                        },
//                        (Set<String> value1, Set<String> value2) -> {
//                            value1.addAll(value2);
//                            return value1;
//                        }
//                ));

                userAllPermissionEntity.setMenuList(menuList);
                userAllPermissionEntity.setPermissionList(permissionList);
                String roleId = admins.get(0).getRoleId();
                String roleName = admins.get(0).getRoleName();
                userAllPermissionEntity.setUserId(admins.get(0).getUserId());
                userAllPermissionEntity.setNickName(admins.get(0).getNickname());

                Map<String,String> role = new HashMap<>();
                role.put(roleId,roleName);
                userAllPermissionEntity.setRole(role);

            }else{

                Map<String,String> roleMap = userPermissions.stream().filter(o->!StringUtils.isBlank(o.getRoleId())&&!StringUtils.isBlank(o.getRoleName())).collect(Collectors.toMap(UserPermissionEntity::getRoleId,UserPermissionEntity::getRoleName,(v1,v2)->v1));

                Set<String> menuList = userPermissions.stream().filter(o->!StringUtils.isBlank(o.getMenuCode())).map(UserPermissionEntity::getMenuCode).collect(Collectors.toSet());
                Set<String> permissionList = userPermissions.stream().filter(o->!StringUtils.isBlank(o.getMenuCode())).map(UserPermissionEntity::getPermissionCode).collect(Collectors.toSet());
//                Map<String, Set<String>> permissionList = userPermissions.stream().filter(o->!StringUtils.isBlank(o.getMenuCode())).collect(Collectors.toMap(UserPermissionEntity::getMenuCode,
//                        p ->  {
//                            Set<String> getPermissionList = new HashSet<>();
//                            getPermissionList.add(p.getPermissionCode());
//                            return getPermissionList;
//                        },
//                        (Set<String> value1, Set<String> value2) -> {
//                            value1.addAll(value2);
//                            return value1;
//                        }
//                ));
                userAllPermissionEntity.setMenuList(menuList);
                userAllPermissionEntity.setPermissionList(permissionList);
                userAllPermissionEntity.setUserId(userPermissions.get(0).getUserId());
                userAllPermissionEntity.setNickName(userPermissions.get(0).getNickname());
                userAllPermissionEntity.setRole(roleMap);
            }
        }else{
            return null;
        }
        return userAllPermissionEntity;
    }
}
