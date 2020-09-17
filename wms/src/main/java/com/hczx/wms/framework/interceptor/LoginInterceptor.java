package com.hczx.wms.framework.interceptor;

import com.hczx.wms.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: LoginInterceptor
 * @Description: 登录拦截器
 * @Date: 2020/9/1 09:59
 * @Author: linsheng.zheng
 * @Version: 1.0
 */
public class LoginInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        try {
            UserModel user = (UserModel) request.getSession().getAttribute("LOGIN_USER");
            if (user != null) {

                logger.info("已登录，放行！");
                return true;
            }
            logger.info("尚未登录，跳转到登录界面");
            response.setHeader("Content-Type", "text/html;charset=UTF-8");
            response.sendRedirect(request.getContextPath() + "toLogin");
        } catch (IOException e) {
//            e.printStackTrace();
            logger.error("登录异常：",e);
        }
        //如果设置为false时，被请求时，拦截器执行到此处将不会继续操作
        //如果设置为true时，请求将会继续执行后面的操作
        return false;

    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("登录中");
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("登录后");
    }
}
