package com.codecrete.sqlms.service;

import com.codecrete.sqlms.configuration.UnitTestConfiguration;
import com.codecrete.sqlms.model.BuildReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Eliot Morris
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes=UnitTestConfiguration.class)
public class BuildServiceTest {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(BuildServiceTest.class);
    
    @Autowired
    private BuildService buildService;
    
    @Before
    public void setup() throws Exception {
    }
    
    @After
    public void teardown() throws Exception {
    }
    
    @Test
    public void build() throws Exception {

        // TODO: Unpack domain jar
        
        Path source = Paths.get("");
        String domain = "test";
        BuildReport report = buildService.build(source, domain);
    }
}