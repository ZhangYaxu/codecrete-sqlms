package com.codecrete.sqlms.controller;

import com.codecrete.sqlms.Application;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.codecrete.utils.FileUtils.getString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Eliot Morris
 */
@RunWith(SpringRunner.class)
@WebMvcTest(Application.class)
public class BoilerplateControllerTest {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(BoilerplateControllerTest.class);
    
    @Autowired
    private WebApplicationContext context;
    
    private MockMvc mvc;
    
    public BoilerplateControllerTest() {}
    
    @Before
    public void setup() {
        
        this.mvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }
    
    @Test
    @WithMockUser(roles="ADMIN", username="tim.santaniello@codecrete.com")
    public void testSelectProcedure() throws Exception {
        
        // Read BoilerplateInstruction from resource file
        String json = getString(BoilerplateControllerTest.class.getResourceAsStream("/json/BoilerplateInstruction.json"));

        mvc.perform(post("/boilerplate/procedure/select")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }
}
