package com.hczx.wms.framework.config;

import com.hczx.wms.framework.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.*;

/**
 * @ClassName: WebConfig
 * @Description: mvc配置
 * @Date: 2020/9/1 09:59
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 不需要登录拦截的url
     */
    final String[] notLoginInterceptPaths = {"/templates/html/**", "/static/**", "/error/**", "/wms/toLogin",  "/login", "/wms/login", "/wms/regist"};


    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        //注册TestInterceptor拦截器
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        //所有路径都被拦截
        registration.addPathPatterns("/**");
        //添加不拦截路径
        registration.excludePathPatterns(notLoginInterceptPaths);

    }

    /**
     * 配置不需要经过controller就跳转到登录页面
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/wms/toLogin").setViewName("login");
        registry.addViewController("/login").setViewName("login");
    }

    /***
     * addResourceLocations指的是文件放置的目录，addResoureHandler指的是对外暴露的访问路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO Auto-generated method stub
        //排除静态资源拦截
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }


}
