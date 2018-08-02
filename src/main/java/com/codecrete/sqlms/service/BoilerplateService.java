package com.codecrete.sqlms.service;

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
    
    //
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deleteProcedure(String table) throws IOException, TemplateException, ClassNotFoundException {
        
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
    private List<ClassField> getClassFields(Class klass) {
    
        // TODO: Return a more comprehensive object than string
        List<ClassField> fields = new ArrayList<>();
        
        for (Field field : klass.getDeclaredFields()) {
            field.setAccessible(true);
            
            // Simple
            if (field.isAnnotationPresent(Column.class)) {
                
                Column column = field.getAnnotation(Column.class);
                fields.add(new ClassField(field.getName(), column.name(), field.getType().getSimpleName(), column.unique()));
            }

            // Nested object in column
            else if (field.isAnnotationPresent(JoinColumn.class)) {
                
                JoinColumn column = field.getAnnotation(JoinColumn.class);
                fields.add(new ClassField(field.getName(), column.name(), "INTEGER", column.unique()));
            }
            
            // Ignore field without correct annotation
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
    
    // Retrieve the class Table annotations name attribute value.
    private String getTable(Class klass) {
    
        String name = null;
        
        if (klass.isAnnotationPresent(Table.class)) {
            Table table = (Table) klass.getAnnotation(Table.class);
            name = table.name();
        }
        
        return name;
    }
    
    //
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String insertProcedure(String table) {
        return new String();
    }
    
    //
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String selectProcedure(String table) throws IOException, ClassNotFoundException, TemplateException {
    
        Configuration configuration = new Configuration(new Version("2.3.28"));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(String.class, "/templates/procedures/");
        Template template = configuration.getTemplate("Select.ftl");
    
        Map<String,Object> variables = new HashMap<>();
        variables.put("definer", getDefiner());
        variables.put("host", getHost());
    
        Class klass = Class.forName(table);
        variables.put("table", getTable(klass));
        
        List<String> fields = new ArrayList<>();
        Map<String,String> mode = new HashMap<>();
        Map<String,Object> type = new HashMap<>();
    
        // Default to id
        variables.put("unique", "id");
        
        for (ClassField field : getClassFields(klass)) {
            
            // Populate fields list
            fields.add(field.getName());
            
            // prefer any field over id
            if (field.isUnique()) {
                if (variables.get("unique") == "id") {
                    variables.put("unique", field.getName());
                }
            }
            
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
    
    //
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateProcedure(String table) {
        return new String();
    }
    
    /**
     * Represents a class field with information used to translate from its
     * native java type to its corresponding mysql data type.
     */
    private class ClassField {

        /**
         *
         */
        private final String column;
        
        /**
         * The name of the
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
        public ClassField(String name, String column, String javaType, boolean unique) {
            
            
            this.name = name;
            this.column = column;
            this.javaType = javaType;
            this.sqlType = MysqlUtils.toSqlType(javaType);
            this.unique = unique;
        }
    
        /**
         * Getter method for column variable.
         *
         * @return the column object
         */
        public String getColumn() {
            return this.column;
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
