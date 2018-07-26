package com.codecrete.sqlms.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

/**
 * @author Eliot Morris
 */
public class BuildInstruction {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(BuildInstruction.class);

    private String domain;
    
    private Path source;
    
    public BuildInstruction() {}
    
    /**
     * Getter method for domain variable.
     *
     * @return the domain object
     */
    public String getDomain() {
        return this.domain;
    }

    /**
     * Getter method for source variable.
     *
     * @return the source object
     */
    public Path getSource() {
        return this.source;
    }
    
    /**
     * Setter method for domain variable.
     *
     * @param domain a domain object
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }
    
    /**
     * Setter method for source variable.
     *
     * @param source a source object
     */
    public void setSource(Path source) {
        this.source = source;
    }
}
