package com.codecrete.sqlms.service;

import com.codecrete.sqlms.configuration.UnitTestConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.DELETE_ON_CLOSE;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * @author Eliot Morris
 */

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
    
    @Test
    public void testDeleteProcedure() throws Exception {

        String sql = this.boilerplateService.deleteProcedure("com.codecrete.domain.model.User");
        System.out.format("SQL: %s%n", sql);
        
        // TODO: Copy to BoilerplateService
        Path tmp = Files.createTempDirectory("tmp");
        Path file = tmp.resolve("deleteUser.sql");
        Files.write(file, sql.getBytes(UTF_8), CREATE_NEW, WRITE, DELETE_ON_CLOSE);
    
    }
    
    @Test
    public void testInsertProcedure() throws Exception {
    
        String sql = this.boilerplateService.insertProcedure("com.codecrete.domain.model.User");
        System.out.println(sql);
    }
    
    @Test
    public void testSelectProcedure() throws Exception {
    
        String sql = this.boilerplateService.selectProcedure("com.codecrete.domain.model.User");
        System.out.println(sql);
    }
    
    @Test
    public void testUpdateProcedure() throws Exception {
        
        String sql = this.boilerplateService.updateProcedure("com.codecrete.domain.model.User");
        System.out.println(sql);
    }
}
