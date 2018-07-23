package com.codecrete.sqlms.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Eliot Morris
 */
public class DeleteInstruction {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(DeleteInstruction.class);
    
    private String table;
    
    public DeleteInstruction() {}
    
    /**
     * Getter method for table variable.
     *
     * @return the table object
     */
    public String getTable() {
        return this.table;
    }
    
    /**
     * Setter method for table variable.
     *
     * @param table a table object
     */
    public void setTable(String table) {
        this.table = table;
    }
}
