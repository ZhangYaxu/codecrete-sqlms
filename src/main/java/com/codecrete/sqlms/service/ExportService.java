package com.codecrete.sqlms.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Given a specific mysql database, generate all appropriate sql files needed
 * to recreate the database from scratch.
 *
 * @author Eliot Morris
 */
@Service
public class ExportService {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(ExportService.class);
}
