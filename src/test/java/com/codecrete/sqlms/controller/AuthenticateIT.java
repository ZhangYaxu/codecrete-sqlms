package com.codecrete.sqlms.controller;

import com.codecrete.sqlms.configuration.IntegrationTestConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 */
@WebMvcTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes=IntegrationTestConfiguration.class)
public class AuthenticateIT {
    
    private static final Logger LOG = LogManager.getLogger(AuthenticateIT.class);
    
    @Autowired
    WebApplicationContext context;
    
//    @Autowired
//    private MockMvc mvc;
    
    
    private MockMvc mockMvc;

    @Before
    public void setup() {
        
        mockMvc = MockMvcBuilders
                .webAppContextSetup(this.context)
                .apply(springSecurity())
                .build();
    }
    
    // FIX: (AuthenticationCredentialsNotFoundException) Database out of sync
    @Test
    public void testAuthenticate() throws Exception {

//        this.mvc.perform(formLogin("/login").user("tim.santaniello@codecrete.com").password("password"))
//                .andExpect(authenticated().withRoles("USER","MULE","ADMIN","ACTUATOR"));

        this.mockMvc.perform(formLogin().user("tim.santaniello@codecrete.com").password("password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(authenticated());
    }
}
