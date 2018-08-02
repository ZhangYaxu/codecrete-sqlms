package com.codecrete.sqlms.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

/**
 * @author Eliot Morris
 */
@TestConfiguration
@Import({
        ServiceConfiguration.class
})
public class UnitTestConfiguration {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(UnitTestConfiguration.class);
    

}
