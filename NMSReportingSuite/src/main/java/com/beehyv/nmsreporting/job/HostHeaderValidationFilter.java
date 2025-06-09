package com.beehyv.nmsreporting.job;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class HostHeaderValidationFilter implements Filter {

    private static final String[] ALLOWED_HOSTS = {
            "kma.mohfw.gov.in",
            "localhost",
            "127.0.0.1",
            "192.168.200.113"
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            String host = ((HttpServletRequest) request).getHeader("Host");
            boolean allowed = false;
            for (String allowedHost : ALLOWED_HOSTS) {
                if (host != null && host.contains(allowedHost)) {
                    allowed = true;
                    break;
                }
            }

            if (!allowed) {
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid Host Header");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
