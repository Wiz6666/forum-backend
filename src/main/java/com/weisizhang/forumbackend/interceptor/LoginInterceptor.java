package com.weisizhang.forumbackend.interceptor;

import com.weisizhang.forumbackend.config.AppConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${login.url}")
    private String loginUrl;

    /**
     *
     * @return true:继续流程<br/> false:中断流程
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取Session
        HttpSession session = request.getSession(false);
        //判断Session是否有效
        if (session != null && session.getAttribute(AppConfig.USER_SESSION) != null) {
            return true;
        }
        //Session没有,校验不通过, 跳转到登录界面
        //如果拦截器未来被手动 new 时拿不到配置值导致空指针，即使未注入也能跳回登录页
        String redirectUrl = (loginUrl == null || loginUrl.isBlank()) ? "/sign-in.html" : loginUrl;
        response.sendRedirect(redirectUrl);
        //中断流程
        return false;
    }
}
