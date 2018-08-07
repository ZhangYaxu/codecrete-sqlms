package com.codecrete.sqlms.service;

import com.codecrete.utils.ClassUtils;
import com.codecrete.utils.MysqlUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eliot Morris
 */
@Service
public class BoilerplateService {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(BoilerplateService.class);
    
    @Autowired
    private ApplicationContext context;

    @Autowired
    private String definer;
    
    @Autowired
    private String host;
    
    // TODO: Configure?
    public BoilerplateService() {}
    
    @PreAuthorize("hasRole('ROLE_MULE')")
    public String afterInsertTrigger(String table) {
    
    
//    Map<String, Object> variables = new HashMap<>();
//    variables.put("FIELDS", fields);
//    variables.put("PACKAGE_NAME", modelClass.getPackage().getName().replace(".model", ".dto"));
//    variables.put("DTO", String.format("%sDto", modelClass.getSimpleName()));
//    variables.put("MODEL", modelClass.getSimpleName());
//    variables.put("HASHCODE", hashcode.stream().collect(Collectors.joining(", ")));
//    variables.put("EQUALS", equals.stream().collect(Collectors.joining(" && ")));
//
//
//    return getSql(template, variables);

        return new String();
    }
    
    //
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String auditTable(String table) {
        return new String();
    }
    
    //
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String beforeDeleteTrigger(String table) {
        return new String();
    }
    
    //
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String beforeUpdateTrigger(String table) {
        return new String();
    }
    
    /**
     * Generate the stored procedure for performing soft deletes of records
     * used to perform all delete actions.
     *
     * @param table The table that the generated delete procedure will delete
     *              records from when executed
     * @return The sql string for generating the delete procedure on the specified
     *         table
     * @throws IOException If an error occurs while loading the freemarker template
     * @throws TemplateException If something goes wrong during the population of
     *                           the freemarker template with variables
     * @throws ClassNotFoundException When the specified table name cannot be
     *                                translated into a java Class
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteProcedure(String table) throws IOException, ClassNotFoundException, TemplateException {
        
        Configuration configuration = new Configuration(new Version("2.3.28"));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(String.class, "/templates/procedures/");
        Template template = configuration.getTemplate("Delete.ftl");
    
        Map<String,Object> variables = new HashMap<>();
        variables.put("definer", getDefiner());
        variables.put("host", getHost());
        variables.put("table", getTable(Class.forName(table)));
        
        return getSql(template, variables);
    }
    
    /**
     * Get the list of all the Column annotations name attribute found in the
     * specified class.
     *
     * @param klass The Class to reflect for column annotations
     * @return The list of each Column annotations name
     */
    private List<EntityField> getEntityFields(Class<?> klass) {
    
        List<EntityField> fields = new ArrayList<>();
        
        for (Field field : ClassUtils.getFields(klass)) {
            field.setAccessible(true);
            
            // Specified column maps directly
            // FIX: Only tracking one entity name field. prefer column.name and fallback to field.name
            if (field.isAnnotationPresent(Column.class)) {
                
                Column column = field.getAnnotation(Column.class);
                
                fields.add(new EntityField(
                        column.name() == null ? field.getName() : column.name(),
                        field.getType().getSimpleName(),
                        column.unique())
                );
            }

            // Nested object in column, requires recursion later
            else if (field.isAnnotationPresent(JoinColumn.class)) {
                
                JoinColumn column = field.getAnnotation(JoinColumn.class);
                
                fields.add(new EntityField(
                        column.name() == null ? field.getName() : column.name(),
                        "INTEGER",
                        column.unique())
                );
            }
            
            // Ignore field without a required annotation
            else {
                LOG.trace("Field {} does not have a @Column annotation and is being ignored", field.getName());
            }
        }
    
        return fields;
    }
    
    /**
     * Getter method for definer variable.
     *
     * @return the definer object
     */
    private String getDefiner() {
        return this.definer;
    }
    
    /**
     * Getter method for host variable.
     *
     * @return the host object
     */
    private String getHost() {
        return this.host;
    }
    
    /**
     * Given a freemarker template and a corresponding List of variables, substitute
     * the templates variables for the specified values.
     *
     * @param template A valid freemarker template
     * @param variables A Map of variables for the template to substitute
     * @return The output of the populated template
     * @throws IOException
     * @throws TemplateException
     */
    private String getSql(Template template, Map<String,Object> variables) throws IOException, TemplateException {
        
        String sql = new String();
        
        try (StringWriter out = new StringWriter()) {
        
            template.process(variables, out);
            out.flush();
            sql = out.getBuffer().toString();
        }
        
        return sql;
    }
    
    // Retrieve the classes Table annotation name attribute value.
    private String getTable(Class<?> klass) {
    
        String name = null;
        
        if (klass.isAnnotationPresent(Table.class)) {
            Table table = (Table) klass.getAnnotation(Table.class);
            name = table.name();
        }
        
        return name;
    }
    
    //
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String insertProcedure(String table) throws IOException, ClassNotFoundException, TemplateException {
    
        Configuration configuration = new Configuration(new Version("2.3.28"));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(String.class, "/templates/procedures/");
        Template template = configuration.getTemplate("Insert.ftl");
    
        Map<String,Object> variables = new HashMap<>();
        variables.put("definer", getDefiner());
        variables.put("host", getHost());
    
        Class<?> klass = Class.forName(table);
        variables.put("table", getTable(klass));
    
        List<String> fields = new ArrayList<>();
        Map<String,String> mode = new HashMap<>();
        Map<String,Object> type = new HashMap<>();
    
        for (EntityField field : getEntityFields(klass)) {
        
            // Add field to fields list
            fields.add(field.getName());
        
            // Populate mode map
            mode.put(field.getName(), field.isUnique() ? "INOUT" : "OUT");
        
            // Populate type map
            type.put(field.getName(), field.getSqlType());
        }
    
        variables.put("fields", fields);
        variables.put("mode", mode);
        variables.put("type", type);
        
        return getSql(template, variables);
    }
    
    /**
     * Generate the stored procedure to select records from the specified table.
     *
     * @param table The table the procedure will be selecting from
     * @return The sql string to create the stored procedure when executed
     * @throws IOException If an error occurs while loading the freemarker template
     * @throws ClassNotFoundException When the specified table name cannot be
     *                                translated into a java Class
     * @throws TemplateException If something goes wrong during the population of
     *                           the freemarker template with variables
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String selectProcedure(String table) throws IOException, ClassNotFoundException, TemplateException {
    
        Configuration configuration = new Configuration(new Version("2.3.28"));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(String.class, "/templates/procedures/");
        Template template = configuration.getTemplate("Select.ftl");
    
        Map<String,Object> variables = new HashMap<>();
        variables.put("definer", getDefiner());
        variables.put("host", getHost());
    
        Class<?> klass = Class.forName(table);
        variables.put("table", getTable(klass));
        
        List<String> fields = new ArrayList<>();
        Map<String,String> mode = new HashMap<>();
        Map<String,Object> type = new HashMap<>();
        
        for (EntityField field : getEntityFields(klass)) {
            
            // Add field to fields list
            fields.add(field.getName());
            
            // Populate mode map
            mode.put(field.getName(), field.isUnique() ? "INOUT" : "OUT");
    
            // Populate type map
            type.put(field.getName(), field.getSqlType());
        }
    
        variables.put("fields", fields);
        variables.put("mode", mode);
        variables.put("type", type);
    
        StringBuilder sql = new StringBuilder();
        
        // Create stored procedure sql for each unique field and concat together
        for (EntityField field : getEntityFields(klass)) {
    
            if (field.isUnique()) {
                
                variables.put("unique", field.getName());
    
                sql.append(System.lineSeparator());
                sql.append(getSql(template, variables));
            }
        }
        
        return sql.toString();
    }
    
    // TODO: Rename!!
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String triggersEnabledFunction() {
        return new String();
    }
    
    //
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateProcedure(String table) throws IOException, ClassNotFoundException, TemplateException {
        
        Configuration configuration = new Configuration(new Version("2.3.28"));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(String.class, "/templates/procedures/");
        Template template = configuration.getTemplate("Update.ftl");
    
        Map<String,Object> variables = new HashMap<>();
        variables.put("definer", getDefiner());
        variables.put("host", getHost());
    
        Class<?> klass = Class.forName(table);
        variables.put("table", getTable(klass));
    
        List<String> fields = new ArrayList<>();
        Map<String,Object> type = new HashMap<>();
        List<String> ignore = new ArrayList<>();
        ignore.add("id");
        ignore.add("created");
        ignore.add("modified");
        ignore.add("deleted");
        
        for (EntityField field : getEntityFields(klass)) {
        
            // Ignore id, created, modified and deleted fields since they
            // should not be updated by user
            if (ignore.contains(field.getName()) == false) {
            
                // Add field to fields list
                fields.add(field.getName());
            
                // Populate type map
                type.put(field.getName(), field.getSqlType());
            }
        }
    
        variables.put("fields", fields);
        variables.put("type", type);
        
        return getSql(template, variables);
    }
    
    /**
     * Represents a class field with information used to translate from its
     * native java type to its corresponding mysql data type.
     */
    private class EntityField {
        
        /**
         * FIX: Merge this with column? giving column name priority and defaulting to the fields actual name
         */
        private final String name;
    
        /**
         *
         */
        private final String javaType;
    
        /**
         *
         */
        private final String sqlType;
    
        /**
         *
         */
        private final boolean unique;
    
        /**
         *
         */
        public EntityField(String name, String javaType, boolean unique) {
            
            this.name = name;
            this.javaType = javaType;
            this.sqlType = MysqlUtils.toSqlType(javaType);
            this.unique = unique;
        }
    
        /**
         * Getter method for javaType variable.
         *
         * @return the javaType object
         */
        public String getJavaType() {
            return this.javaType;
        }
    
        /**
         * Getter method for name variable.
         *
         * @return the name object
         */
        public String getName() {
            return this.name;
        }
    
        /**
         * Getter method for sqlType variable.
         *
         * @return the sqlType object
         */
        public String getSqlType() {
            return this.sqlType;
        }
    
        /**
         * Getter method for unique variable.
         *
         * @return the unique object
         */
        public boolean isUnique() {
            return this.unique;
        }
    }
}
