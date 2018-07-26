package com.codecrete.sqlms.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public Integer execute(List<String> batch) {
    
        // TODO: Enable / Disable auto commit?
    
        // TODO: set foreign key checks=0
//      jdbcTemplate.getDataSource().getConnection();
    
        // TODO: Change connection to created schema
        
        
        int[] foo = this.jdbcTemplate.batchUpdate(batch.stream().toArray(String[]::new));
    
    
        // TODO: set foreign key checks=1
    
        return foo.length;
    }
    
    
    
}
