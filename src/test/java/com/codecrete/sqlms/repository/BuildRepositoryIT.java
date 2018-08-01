package com.codecrete.sqlms.repository;

import com.codecrete.sqlms.configuration.IntegrationTestConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

/**
 * @author Eliot Morris
 */
@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace=NONE)
@ContextConfiguration(classes=IntegrationTestConfiguration.class)
public class BuildRepositoryIT {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(BuildRepositoryIT.class);
    
    @Autowired
    private BuildRepository repository;
    
    @Test
    public void testExecute() throws Exception {
    
        List<String> batch = new ArrayList<String>() {{
            add(";");
            add(";");
        }};
        String schema = "test";
        Integer statements = this.repository.execute(batch, schema);
        LOG.info("%d Statements executed", statements);
    }
}
