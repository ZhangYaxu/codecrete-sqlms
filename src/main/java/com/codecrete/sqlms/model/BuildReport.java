package com.codecrete.sqlms.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eliot Morris
 */
public class BuildReport {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(BuildReport.class);

    private List<String> grants = new ArrayList<>();
    
    private List<String> patches = new ArrayList<>();

    private List<String> records = new ArrayList<>();

    private List<String> routines = new ArrayList<>();

    private List<String> schemas = new ArrayList<>();

    private Integer size = 0;
    
    private List<String> tables = new ArrayList<>();

    private List<String> triggers = new ArrayList<>();

    private List<String> users = new ArrayList<>();
    
    public BuildReport() {}
    
    public boolean addGrant(String grant) {
        return getGrants().add(grant);
    }
    
    public boolean addPatch(String patch) {
        return getPatches().add(patch);
    }
    
    public boolean addRecord(String record) {
        return getRecords().add(record);
    }
    
    public boolean addRoutine(String routine) {
        return getRoutines().add(routine);
    }
    
    public boolean addSchema(String schema) {
        return getSchemas().add(schema);
    }
    
    public boolean addTable(String table) {
        return getTables().add(table);
    }
    
    public boolean addTrigger(String trigger) {
        return getTriggers().add(trigger);
    }
    
    public boolean addUser(String user) {
        return getUsers().add(user);
    }
    
    /**
     * Getter method for grants variable.
     *
     * @return the grants object
     */
    public List<String> getGrants() {
        return this.grants;
    }

    /**
     * Getter method for patches variable.
     *
     * @return the patches object
     */
    public List<String> getPatches() {
        return this.patches;
    }
    
    /**
     * Getter method for records variable.
     *
     * @return the records object
     */
    public List<String> getRecords() {
        return this.records;
    }
    
    /**
     * Getter method for routines variable.
     *
     * @return the routines object
     */
    public List<String> getRoutines() {
        return this.routines;
    }
    
    /**
     * Getter method for schemas variable.
     *
     * @return the schemas object
     */
    public List<String> getSchemas() {
        return this.schemas;
    }
    
    /**
     * Getter method for size variable.
     *
     * @return the size object
     */
    public Integer getSize() {
        return this.size;
    }

    /**
     * Getter method for tables variable.
     *
     * @return the tables object
     */
    public List<String> getTables() {
        return this.tables;
    }
    
    /**
     * Getter method for triggers variable.
     *
     * @return the triggers object
     */
    public List<String> getTriggers() {
        return this.triggers;
    }
    
    /**
     * Getter method for users variable.
     *
     * @return the users object
     */
    public List<String> getUsers() {
        return this.users;
    }
    
    /**
     * Setter method for grants variable.
     *
     * @param grants a grants object
     */
    public void setGrants(List<String> grants) {
        this.grants = grants;
    }
    
    /**
     * Setter method for patches variable.
     *
     * @param patches a patches object
     */
    public void setPatches(List<String> patches) {
        this.patches = patches;
    }
    
    /**
     * Setter method for records variable.
     *
     * @param records a records object
     */
    public void setRecords(List<String> records) {
        this.records = records;
    }
    
    /**
     * Setter method for routines variable.
     *
     * @param routines a routines object
     */
    public void setRoutines(List<String> routines) {
        this.routines = routines;
    }
    
    /**
     * Setter method for schemas variable.
     *
     * @param schemas a schemas object
     */
    public void setSchemas(List<String> schemas) {
        this.schemas = schemas;
    }
    
    /**
     * Setter method for size variable.
     *
     * @param size a size object
     */
    public void setSize(Integer size) {
        this.size = size;
    }
    
    /**
     * Setter method for tables variable.
     *
     * @param tables a tables object
     */
    public void setTables(List<String> tables) {
        this.tables = tables;
    }
    
    /**
     * Setter method for triggers variable.
     *
     * @param triggers a triggers object
     */
    public void setTriggers(List<String> triggers) {
        this.triggers = triggers;
    }
    
    /**
     * Setter method for users variable.
     *
     * @param users a users object
     */
    public void setUsers(List<String> users) {
        this.users = users;
    }
}
