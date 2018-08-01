package com.codecrete.sqlms.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Eliot Morris
 */
@Repository
public class BuildRepository {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(BuildRepository.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public BuildRepository() {}
    
    
    
    // https://sivalabs.in/2016/03/springboot-working-with-jdbctemplate/
    // ...
    
    // Returns the number of sql statements executed.
    public Integer execute(List<String> batch, String schema) throws SQLException {
    
        // TODO: Enable / Disable auto commit?
//      getJdbcTemplate().getDataSource().getConnection().setAutoCommit(?);
        
        
        // TODO: Make sure we have a single connection going and so our variable is actually set.
        // Disable foreign keys
        getJdbcTemplate().execute("SET FOREIGN_KEY_CHECKS=0");
        SqlRowSet set = getJdbcTemplate().queryForRowSet("SHOW VARIABLES LIKE 'FOREIGN_KEY_CHECKS';");
        set.next();
        LOG.debug("FOREIGN_KEY_CHECKS: {}", set.getString("Value"));
        
        
        // TODO: Change connection to created schema (must be passed)
        this.jdbcTemplate.getDataSource().getConnection().setSchema(schema);
        
        
        // TODO: Because we are executing the unified script in batches do we need to prepend "USE domain;" to every script?
        int[] foo = getJdbcTemplate().batchUpdate(batch.stream().toArray(String[]::new));
        LOG.debug("{} Statements executed.", foo.length);
    
        // Enable foreign keys
        getJdbcTemplate().execute("SET FOREIGN_KEY_CHECKS=1");
        set = getJdbcTemplate().queryForRowSet("SHOW VARIABLES LIKE 'FOREIGN_KEY_CHECKS';");
        set.next();
        LOG.debug("FOREIGN_KEY_CHECKS: {}", set.getString("Value"));
        
        return foo.length;
    }
    
    /**
     * Getter method for jdbcTemplate variable.
     *
     * @return the jdbcTemplate object
     */
    public JdbcTemplate getJdbcTemplate() {
        return this.jdbcTemplate;
    }
    
    
    
}
