package com.codecrete.sqlms.controller;

import com.codecrete.sqlms.model.BoilerplateInstruction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Eliot Morris
 */
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
public class BoilerplateControllerIT {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(BoilerplateControllerIT.class);
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void testSelectProcedure() {
    
        BoilerplateInstruction instruction = new BoilerplateInstruction();
        instruction.setTable("com.codecrete.domain.model.User");
        
        assertThat(this.restTemplate
                .withBasicAuth("tim.santaniello@codecrete.com", "password")
                .postForObject(
                    "http://localhost:8383/boilerplate/procedure/select",
                    instruction,
                    String.class
                )
        ).contains("Boilerplate JSON goes here");
    }
}
