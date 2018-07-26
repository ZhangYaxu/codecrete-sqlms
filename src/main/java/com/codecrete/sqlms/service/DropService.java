package com.codecrete.sqlms.service;

import com.codecrete.sqlms.model.DropReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * @author Eliot Morris
 */
@Service
public class DropService {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(DropService.class);
    
    @Autowired
    private ApplicationContext context;
    
    @PreAuthorize("hasRole('ROLE_MULE')")
    public DropReport drop(String schema) {
        return new DropReport();
    }
}
