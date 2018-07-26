package com.codecrete.sqlms.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Eliot Morris
 */
public class DropInstruction {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(DropInstruction.class);

    private String schema;
    
    public DropInstruction() {}
    
    /**
     * Getter method for schema variable.
     *
     * @return the schema object
     */
    public String getSchema() {
        return this.schema;
    }
    
    /**
     * Setter method for schema variable.
     *
     * @param schema a schema object
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }
    
}
