package com.usterka.restapi;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class AccessControlAllowOriginFilter implements javax.servlet.Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "http://localhost:3000");
        //httpServletResponse.addHeader("Access-Control-Allow-Origin", "https://ollo.link");
        //httpServletResponse.addHeader("Access-Control-Allow-Origin", "https://7776.link");// dodane wszystkie naglowki z okreslonymi danymi "gwiazdki" nie sa akceptowane
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        httpServletResponse.addHeader("Access-Control-Max-Age", "3600");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true");

        filterChain.doFilter(servletRequest, servletResponse);
    }
    @Override
    public void destroy() {
    }
}
