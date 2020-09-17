package com.hczx.wms.entity.authenticationentities;

import com.baomidou.mybatisplus.annotation.TableId;

/**
 * @ClassName: AuthenticationEntity
 * @Description: 身份认证实体
 * @Date: 2020/8/31 10:55
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class AuthenticationEntity {

    /**用户名*/
    private String userName;

    /**密码*/
    private String password;

    /**email*/
    private String email;

    /**手机*/
    private String mobile;

    /**确认密码*/
    private String confirmPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
