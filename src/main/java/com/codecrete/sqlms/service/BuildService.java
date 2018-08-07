package com.codecrete.sqlms.service;

import com.codecrete.sqlms.model.BuildReport;
import com.codecrete.sqlms.repository.BuildRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

import static java.util.stream.Collectors.toList;

/**
 *
 *
 * @author Eliot Morris
 */
@Service
public class BuildService {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(BuildService.class);

    @Autowired
    private ApplicationContext context;
    
    @Autowired
    private Path grantDirectory;

    @Autowired
    private Path patchDirectory;

    @Autowired
    private Path recordDirectory;

    @Autowired
    private Path rootDirectory;

    @Autowired
    private Path routineDirectory;

    @Autowired
    private Path schemaDirectory;

    @Autowired
    private Path tableDirectory;

    @Autowired
    private Path triggerDirectory;

    @Autowired
    private Path userDirectory;

    @Autowired
    private BuildRepository repository;
    
    @Autowired
    private Integer searchDepth;

    @Autowired
    private String scriptExtension;
    
    public BuildService() {}
    
    @PreAuthorize("hasRole('ROLE_MULE')")
    public BuildReport build(Path source, String domain) throws IOException, SQLException {
    
        // TODO: Check if source name ends in .jar and if so unpack?
        
        BuildReport report = new BuildReport();
    
        List<String> batch = new ArrayList<>();
        
        // Append root configured directory to our scripts source path
        Path root = source.resolve(getRootDirectory());

        // Execute schema scripts
        for (Path script : getSchemaScripts(root)) {
            
            String sql = toSql(script, domain);
            report.addSchema(getSchemaName(sql));
            batch.add(sql);
        }
        
        // Create schema users
        for (Path script : getUserScripts(root)) {
    
            String sql = toSql(script, domain);
            report.addUser(getUserName(sql));
            batch.add(sql);
        }
    
        // Disable foreign key checks
        // Locate and execute all script files under the tables subdirectory
        // Enable foreign key checks
        for (Path script : getTableScripts(root)) {
    
            String sql = toSql(script, domain);
            report.addTable(getTableName(sql));
            batch.add(sql);
        }
    
        // Create all stored procedures and functions
        for (Path script : getRoutineScripts(root)) {
            
            String sql = toSql(script, domain);
            report.addRoutine(getRoutineName(sql));
            batch.add(sql);
        }
    
        // Search for all trigger script files located under the triggers
        // subdirectory and execute each of them while recording their names
        for (Path script : getTriggerScripts(root)) {
        
            String sql = toSql(script, domain);
            report.addTrigger(getTriggerName(sql));
            batch.add(sql);
        }
        
        // Run grant scripts
        for (Path script : getGrantScripts(root)) {
        
            String sql = toSql(script, domain);
            report.addGrant(getGrantName(sql));
            batch.add(sql);
        }
        
        // Execute all table record scripts
        for (Path script : getRecordScripts(root)) {
        
            String sql = toSql(script, domain);
            report.addRecord(getRecordName(sql));
            batch.add(sql);
        }
        
        // TODO: Patch scripts require more lifting than just systematic execution!
        // Patch database
        for (Path script : getPatchScripts(root)) {
        
//            String sql = toSql(script, domain);
//            report.addPatch(getPatchName(sql));
//            batch.add(sql);
        }
        
        // Execute the concatenated script and return the number of statements executed.
        report.setSize(repository.execute(batch, domain));
        
        return report;
    }
    
    /**
     * Getter method for context variable.
     *
     * @return the context object
     */
    public ApplicationContext getContext() {
        return this.context;
    }
    
    /**
     * Getter method for grantDirectory variable.
     *
     * @return the grantDirectory object
     */
    public Path getGrantDirectory() {
        return this.grantDirectory;
    }
    
    // TODO: Parse create statements for grant "name"
    private String getGrantName(String sql) { return new String(); }
    
    // TODO: Verify create grant statements?
    private List<Path> getGrantScripts(Path root) throws IOException {
        return getScripts(root.resolve(getGrantDirectory()));
    }
    
    /**
     * Getter method for patchDirectory variable.
     *
     * @return the patchDirectory object
     */
    public Path getPatchDirectory() {
        return this.patchDirectory;
    }
    
    // TODO: Parse create statements for patch "name"
    private String getPatchName(String sql) { return new String(); }
    
    // TODO: Verify create patch statements???
    private List<Path> getPatchScripts(Path root) throws IOException {
        return getScripts(root.resolve(getPatchDirectory()));
    }
    
    /**
     * Getter method for recordDirectory variable.
     *
     * @return the recordDirectory object
     */
    public Path getRecordDirectory() {
        return this.recordDirectory;
    }
    
    // TODO: Parse create statements for record "name"
    private String getRecordName(String sql) { return new String(); }
    
    // TODO: Verify insert record statements? lookup tables unique id and use corresponding value as name
    private List<Path> getRecordScripts(Path root) throws IOException {
        return getScripts(root.resolve(getGrantDirectory()));
    }
    
    /**
     * Getter method for rootDirectory variable.
     *
     * @return the rootDirectory object
     */
    public Path getRootDirectory() {
        return this.rootDirectory;
    }
    
    /**
     * Getter method for routineDirectory variable.
     *
     * @return the routineDirectory object
     */
    public Path getRoutineDirectory() {
        return this.routineDirectory;
    }
    
    // TODO: Parse create statements for routine "name"
    private String getRoutineName(String sql) { return new String(); }
    
    // TODO: Verify create routine statements?
    private List<Path> getRoutineScripts(Path root) throws IOException {
        return getScripts(root.resolve(getRoutineDirectory()));
    }
    
    /**
     * Getter method for schemaDirectory variable.
     *
     * @return the schemaDirectory object
     */
    public Path getSchemaDirectory() {
        return this.schemaDirectory;
    }
    
    // FIX: Rename getSchemaId?
    // TODO: Parse create statements for schema "name"
    private String getSchemaName(String sql) { return new String(); }
    
    //
    private List<Path> getScripts(Path directory) throws IOException {
        
        // File must not be empty and be named with the configured suffix
        BiPredicate<Path,BasicFileAttributes> filter = (path, attributes) -> {
            
            boolean isScript = path.getFileName().toString().endsWith(getScriptExtension());
            boolean notEmpty = attributes.size() > 0;
            
            return isScript && notEmpty;
        };
        
        List<Path> paths = Files.find(directory, getSearchDepth(), filter).collect(toList());
        LOG.debug("Found: {} scripts under directory: {}", paths.size(), directory);
        
        return paths;
    }
    
    // TODO: Verify create schema statements?
    private List<Path> getSchemaScripts(Path root) throws IOException {
        return getScripts(root.resolve(getSchemaDirectory()));
    }

    /**
     * Getter method for scriptExtension variable.
     *
     * @return the scriptExtension object
     */
    public String getScriptExtension() {
        return this.scriptExtension;
    }
    
    /**
     * Getter method for searchDepth variable.
     *
     * @return the searchDepth object
     */
    private Integer getSearchDepth() {
        return this.searchDepth;
    }
    
    /**
     * Getter method for tableDirectory variable.
     *
     * @return the tableDirectory object
     */
    public Path getTableDirectory() {
        return this.tableDirectory;
    }
    
    // TODO: Parse create statements for table name
    private String getTableName(String sql) { return new String(); }
    
    
    // TODO: Verify create table statements?
    private List<Path> getTableScripts(Path root) throws IOException {
        return getScripts(root.resolve(getTableDirectory()));
    }

    /**
     * Getter method for triggerDirectory variable.
     *
     * @return the triggerDirectory object
     */
    public Path getTriggerDirectory() {
        return this.triggerDirectory;
    }
    
    // TODO: Parse create statements for trigger "name"
    private String getTriggerName(String sql) { return new String(); }
    
    // TODO: Verify create trigger statements?
    private List<Path> getTriggerScripts(Path root) throws IOException {
        return getScripts(root.resolve(getTriggerDirectory()));
    }
    
    /**
     * Getter method for userDirectory variable.
     *
     * @return the userDirectory object
     */
    public Path getUserDirectory() {
        return this.userDirectory;
    }
    
    // TODO: Parse create statements for user "name"
    private String getUserName(String sql) { return new String(); }
    
    // TODO: Verify create user statements?
    private List<Path> getUserScripts(Path root) throws IOException {
        return getScripts(root.resolve(getUserDirectory()));
    }
    
    // Prepend 'USE Schema' to script
    // Prepend 'SET foreign_key_checks=0'
    // Append 'SET foreign_key_checks=1'
    private String toSql(Path script, String domain) throws IOException {
    
        String sql = String.format("USE %s;", domain);
        sql += new String(Files.readAllBytes(script), StandardCharsets.UTF_8);
        
        return sql;
    }
}
