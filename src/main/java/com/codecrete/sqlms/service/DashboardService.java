package com.codecrete.sqlms.service;

import com.codecrete.domain.model.User;
import com.codecrete.sqlms.model.DashboardReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class DashboardService {
    
    private static final Logger LOG = LogManager.getLogger(DashboardService.class);

    @Autowired
    private ApplicationContext context;

    @PreAuthorize("hasRole('ROLE_MULE')")
    public DashboardReport getDashboard(User user) {
        
        DashboardReport dashboardReport = (DashboardReport) this.context.getBean("dashboard");

        // Lookup recent jobs for specified user



        return dashboardReport;
    }


}
