package com.codecrete.sqlms.service;

import com.codecrete.sqlms.configuration.UnitTestConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Eliot Morris
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes=UnitTestConfiguration.class)
public class BoilerplateServiceTest {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(BoilerplateServiceTest.class);
    
    @Autowired
    private BoilerplateService boilerplateService;
    
    @Before
    public void setup() {}
    
    // FIX: (AuthenticationCredentialsNotFoundException) Database out of sync
    // TODO: Uncomment SpringBootTest and RunWith
    @Test
    public void testDeleteProcedure() throws Exception {

        String sql = this.boilerplateService.deleteProcedure("com.codecrete.domain.model.User");

        System.out.println(sql);
        
        // TODO: Write to script file named after the procedure name
    }
    
    @Test
    public void testSelectProcedure() throws Exception {
    
        String sql = this.boilerplateService.deleteProcedure("com.codecrete.domain.model.User");

        System.out.println(sql);
    
    }
    
}
