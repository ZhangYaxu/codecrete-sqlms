package com.codecrete.sqlms.service;

import com.codecrete.sqlms.model.BuildReport;
import com.codecrete.sqlms.model.RollbackReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

/**
 * Attempt to rollback to previous state by reading a buildReport and systematically
 * undoing all users, triggers, tables, routines, records? and patches recorded.
 *
 * @author Eliot Morris
 */
@Service
public class RollbackService {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(RollbackService.class);
    
    // Attempt to rollback to previous state by using buildReport and systematically
    // undoing all users, triggers, tables, routines, records? and patches recorded
    @PreAuthorize("hasRole('ROLE_MULE')")
    public RollbackReport rollback(BuildReport report) {
        return new RollbackReport();
    }
    
}
