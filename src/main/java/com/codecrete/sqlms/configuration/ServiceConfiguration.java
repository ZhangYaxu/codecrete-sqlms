package com.codecrete.sqlms.configuration;

import com.codecrete.sqlms.service.AuthenticateService;
import com.codecrete.sqlms.service.BoilerplateService;
import com.codecrete.sqlms.service.BuildService;
import com.codecrete.sqlms.service.DashboardService;
import com.codecrete.sqlms.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.nio.file.Path;

/**
 * @author Eliot Morris
 */
@Configuration
@PropertySource({
        "classpath:boilerplate.properties",
        "classpath:build.properties"
})
public class ServiceConfiguration {
    
    /**
     * Log4j logger.
     */
    private static final Logger LOG = LogManager.getLogger(ServiceConfiguration.class);

    @Bean
    @Scope("prototype")
    public AuthenticateService authenticateService() {
        return new AuthenticateService();
    }
    
    @Bean
    @Scope("prototype")
    public BoilerplateService boilerplateService() {
        return new BoilerplateService();
    }
    
    @Bean
    @Scope("prototype")
    public BuildService buildService() {
        return new BuildService();
    }
    
    @Bean
    @Scope("prototype")
    public DashboardService dashboardService() {
        return new DashboardService();
    }
    
    @Value("${procedure.delete.definer}")
    private String definer;

    @Bean
    public String definer() {
        return this.definer;
    }
    
    @Deprecated
    @Value("${build.directory.grants}")
    private Path grantDirectory;
    
    @Deprecated
    @Bean
    public Path grantDirectory() {
        return this.grantDirectory;
    }
    
    @Value("${procedure.delete.host}")
    private String host;
    
    @Bean
    public String host() {
        return this.host;
    }
    
    @Deprecated
    @Value("${build.directory.patches}")
    private Path patchDirectory;
    
    @Deprecated
    @Bean
    public Path patchDirectory() {
        return this.patchDirectory;
    }
    
    @Deprecated
    @Value("${build.directory.records}")
    private Path recordDirectory;
    
    @Deprecated
    @Bean
    public Path recordDirectory() {
        return this.recordDirectory;
    }
    
    @Value("${build.directory.root}")
    private Path rootDirectory;
    
    @Bean
    public Path rootDirectory() {
        return this.rootDirectory;
    }
    
    @Deprecated
    @Value("${build.directory.routines}")
    private Path routineDirectory;
    
    @Deprecated
    @Bean
    public Path routineDirectory() {
        return this.routineDirectory;
    }
    
    @Deprecated
    @Value("${build.directory.schemas}")
    private Path schemaDirectory;
    
    @Deprecated
    @Bean
    public Path schemaDirectory() {
        return this.schemaDirectory;
    }
    
    @Value("${build.script.extension}")
    private String scriptExtension;
    
    @Bean
    public String scriptExtension() {
        return this.scriptExtension;
    }
    
    @Value("${build.search.depth}")
    private Integer searchDepth;
    
    @Bean
    public Integer searchDepth() {
        return this.searchDepth;
    }
    
    @Deprecated
    @Value("${build.directory.tables}")
    private Path tableDirectory;
    
    @Deprecated
    @Bean
    public Path tableDirectory() {
        return this.tableDirectory;
    }
    
    @Deprecated
    @Value("${build.directory.triggers}")
    private Path triggerDirectory;
    
    @Deprecated
    @Bean
    public Path triggerDirectory() {
        return this.triggerDirectory;
    }
    
    @Bean
    @Scope("prototype")
    public UserDetailsService userDetailsService() {
        return new AuthenticateService();
    }
    
    @Deprecated
    @Value("${build.directory.users}")
    private Path userDirectory;
    
    @Deprecated
    @Bean
    public Path userDirectory() {
        return this.userDirectory;
    }
    
    @Bean
    @Scope("prototype")
    public UserService userService() {
        return new UserService();
    }
}
