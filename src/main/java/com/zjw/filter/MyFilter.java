package com.zjw.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @version 1.0
 * @author： 赵静薇
 * @date： 2021-04-16 09:35
 */
public class MyFilter implements Filter {
    
    Set<String> noeCheckUrl = new HashSet<String>();

    //过滤器需要在web.xml
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String noeCheckUrl = filterConfig.getInitParameter("noeCheckUrl");
    }  

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        
    }

    @Override
    public void destroy() {

    }
}
