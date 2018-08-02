package com.codecrete.sqlms.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Eliot Morris
 */
@Repository
public class BoilerplateRepository {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(BoilerplateRepository.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public BoilerplateRepository() {}
    
    /**
     * Getter method for jdbcTemplate variable.
     *
     * @return the jdbcTemplate object
     */
    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }
    
}
