package org.hdivsamples.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CachingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String url = ((HttpServletRequest)request).getRequestURL().toString();
        if (url.endsWith(".css") || url.endsWith(".js") || url.endsWith(".png") || url.endsWith(".woff")) {
            HttpServletResponse resp = (HttpServletResponse) response;
            resp.setHeader("Cache-Control", "max-age=604800");
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}