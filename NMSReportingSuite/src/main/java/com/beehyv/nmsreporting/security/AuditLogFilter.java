package com.beehyv.nmsreporting.security;

import com.beehyv.nmsreporting.business.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter()
@Component
public class AuditLogFilter extends GenericFilterBean {

    @Autowired
    AuditLogService auditLogService;


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        auditLogService.saveAuditLog((HttpServletRequest) request);
        chain.doFilter(request, response);
       /* Cookie[] cookies = ((HttpServletRequest)request).getCookies();
        for (Cookie cookie : cookies) {
            cookie.setHttpOnly(true);
            ((HttpServletResponse)response).addCookie(cookie);
        }*/

    }
}
