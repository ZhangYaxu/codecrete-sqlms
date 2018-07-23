package com.codecrete.sqlms.model;

import com.codecrete.domain.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Eliot Morris
 */
public class DashboardInstruction {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(DashboardInstruction.class);

    private User user;
    
    public DashboardInstruction() {}
    
    /**
     * Getter method for user variable.
     *
     * @return the user object
     */
    public User getUser() {
        return this.user;
    }
    
    /**
     * Setter method for user variable.
     *
     * @param user a user object
     */
    public void setUser(User user) {
        this.user = user;
    }
}
