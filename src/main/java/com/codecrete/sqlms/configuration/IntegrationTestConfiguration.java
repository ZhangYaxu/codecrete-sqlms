package com.codecrete.sqlms.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 *
 */
@TestConfiguration
@Import({
        DataSourceConfiguration.class,
        ModelConfiguration.class,
        SecurityConfiguration.class,
        ServiceConfiguration.class
})
@ComponentScan({
        "com.codecrete.sqlms.controller",
        "com.codecrete.sqlms.model",
        "com.codecrete.sqlms.repository",
        "com.codecrete.sqlms.service"
})
public class IntegrationTestConfiguration {
    
    private static final Logger LOG = LogManager.getLogger(IntegrationTestConfiguration.class);
}
