package com.beehyv.nmsreporting.business.impl;

import com.beehyv.nmsreporting.business.AuditLogService;
import com.beehyv.nmsreporting.business.UserService;
import com.beehyv.nmsreporting.dao.AuditLogDao;
import com.beehyv.nmsreporting.model.AuditLog;
import com.beehyv.nmsreporting.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.StringTokenizer;

@Service("auditLogService")
@Transactional
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    AuditLogDao auditLogDao;

    @Autowired
    UserService userService;


    @Override
    public void saveAuditLog(HttpServletRequest request) {
        User currentUser = userService.getCurrentUser();
        String ipAddres = null;
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            ipAddres = request.getRemoteAddr();
        } else {
            // As of https://en.wikipedia.org/wiki/X-Forwarded-For
            // The general format of the field is: X-Forwarded-For: client, proxy1, proxy2 ...
            // we only want the client
            ipAddres = new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
        }

        String url = request.getRequestURI();
        if (request.getQueryString() != null) {
            url +="?" + request.getQueryString();
        }
        AuditLog auditLog = new AuditLog();
        auditLog.setIpAddress(ipAddres);
        auditLog.setDate(new Date());
        auditLog.setUrl(url);
        if (currentUser == null) {
            auditLog.setUserName("None");
        } else {
            auditLog.setUserName(currentUser.getUsername());
        }
        auditLogDao.saveAduitLog(auditLog);

    }
}
