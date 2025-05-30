package com.beehyv.nmsreporting.job;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class SessionHijackProtectionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);

        if (session != null) {
            String sessionIp = (String) session.getAttribute("ipAddress");
            String sessionAgent = (String) session.getAttribute("userAgent");

            String currentIp = request.getRemoteAddr();
            String currentAgent = request.getHeader("User-Agent");

            if (sessionIp != null && sessionAgent != null) {
                if (!sessionIp.equals(currentIp) || !sessionAgent.equals(currentAgent)) {
                    session.invalidate();
                    response.sendRedirect(request.getContextPath() + "/login");
                    return;
                }
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
