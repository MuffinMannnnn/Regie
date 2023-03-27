package com.javasm.reggie.filfter;

import com.alibaba.fastjson.JSON;
import com.javasm.reggie.common.BaseContext;
import com.javasm.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:Jesse
 * @Version:1.0
 * @Date:2023/3/2 - 17:51
 * @Since:jdk1.8
 * @Description:
 */
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    //路径配配齐，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1.本次请求的URI
        String requestURI = request.getRequestURI();

        log.info("拦截到请求:{}",requestURI);


        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",//运行common路径下的不拦截
                "/user/sendMsg",//移动端发送短信
                "/user/login" //移动端登录


        };

        //2.本段本次请求是否需要处理
        boolean check = check(requestURI, urls);

        //3.如果不需要处理，则直接放行
        if (check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        //4.判断登录状态，如果已登录，则直接放行【针对管理员】
        if (request.getSession().getAttribute("employee") != null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("employee"));

            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

//            long id = Thread.currentThread().getId();
//            log.info("线程id为：{}" ,id);

            filterChain.doFilter(request,response);
            return;
        }

        //4.判断登录状态，如果已登录，则直接放行【针对普通用户】
        if (request.getSession().getAttribute("user") != null){
            log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("user"));

            Long id = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(id);

//            long id = Thread.currentThread().getId();
//            log.info("线程id为：{}" ,id);

            filterChain.doFilter(request,response);
            return;
        }



        log.info("用户未登录");
        //5.如果未登录，则返回登录结果;通过输出流方式向客户端页面相应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

        return;

    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param requestURI
     * @return
     */
    public boolean check(String requestURI,String[] urls){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }

        }
        return false;
    }

}
