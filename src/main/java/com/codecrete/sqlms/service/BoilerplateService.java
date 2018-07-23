package com.codecrete.sqlms.service;

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
    
    // Configure?
    public BoilerplateService() {}
    
    @PreAuthorize("hasRole('ROLE_MULE')")
    public String afterInsertTrigger(String className) {
    
    
//    Map<String, Object> variables = new HashMap<>();
//    variables.put("FIELDS", fields);
//    variables.put("PACKAGE_NAME", modelClass.getPackage().getName().replace(".model", ".dto"));
//    variables.put("DTO", String.format("%sDto", modelClass.getSimpleName()));
//    variables.put("MODEL", modelClass.getSimpleName());
//    variables.put("HASHCODE", hashcode.stream().collect(Collectors.joining(", ")));
//    variables.put("EQUALS", equals.stream().collect(Collectors.joining(" && ")));
//
//    try (StringWriter out = new StringWriter()) {
//
//      dtoTemplate.process(variables, out);
//      out.flush();
//      string = out.getBuffer().toString();
//    }
//
//    return string;

        return new String();
    }
    
    @PreAuthorize("hasRole('ROLE_MULE')")
    public String deleteProcedure(String className) throws IOException, TemplateException, ClassNotFoundException {
    
        //
        Class<?> klass = Class.forName(className);
    
        Map<String,Object> variables = new HashMap<>();
        
        //
//        variables.put("user", "");
    
        //
//        variables.put("host", "");
    
        //
//        variables.put("fields", getFields(klass));
        
        //
        variables.put("table", getTable(klass));
    
    
    
        Configuration configuration = new Configuration(new Version("2.3.28"));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(String.class, "/templates/procedures/");
        Template template = configuration.getTemplate("Delete.ftl");
        
        return getSql(template, variables);
    }
    
    //
    private List<String> getFields(Class klass) {
    
        List<String> fields = new ArrayList<>();
        
        for (Field field : klass.getFields()) {
            field.setAccessible(true);
            
            //
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                fields.add(column.name());
            }
            
            //
            else if (field.isAnnotationPresent(JoinColumn.class)) {
                JoinColumn column = field.getAnnotation(JoinColumn.class);
                fields.add(column.name());
            }
        }
        
        return fields;
    }
    
    //
    private String getSql(Template template, Map<String,Object> variables) throws IOException, TemplateException {
        
        String sql = new String();
        
        try (StringWriter out = new StringWriter()) {
        
            template.process(variables, out);
            out.flush();
            sql = out.getBuffer().toString();
        }
        
        return sql;
    }
    
    //
    private String getTable(Class klass) {
    
        String name = null;
        
        if (klass.isAnnotationPresent(klass)) {
            Table table = (Table) klass.getAnnotation(Table.class);
            name = table.name();
        }
        
        return name;
    }
}
