package com.codecrete.sqlms.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 *
 */
@Configuration
@ComponentScan({
        "com.codecrete.sqlms.controller",
        "com.codecrete.sqlms.model",
        "com.codecrete.sqlms.repository",
        "com.codecrete.sqlms.service"
})
@Import({
        DataSourceConfiguration.class,
        ModelConfiguration.class,
        SecurityConfiguration.class,
        ServiceConfiguration.class
})
@PropertySource({"classpath:application.properties"})
public class ApplicationConfiguration {
    
    private static final Logger LOG = LogManager.getLogger(ApplicationConfiguration.class);


}
