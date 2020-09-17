package com.hczx.wms.mybatisplusserveice.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hczx.wms.dao.UserDao;
import com.hczx.wms.entity.authenticationentities.AuthenticationEntity;
import com.hczx.wms.model.UserModel;
import com.hczx.wms.mybatisplusserveice.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.UUID;

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
}
