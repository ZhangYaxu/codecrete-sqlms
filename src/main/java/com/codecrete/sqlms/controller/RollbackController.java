package com.codecrete.sqlms.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Eliot Morris
 */
@RestController
@RequestMapping("/rollback")
public class RollbackController {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(RollbackController.class);
    
    
    
}
