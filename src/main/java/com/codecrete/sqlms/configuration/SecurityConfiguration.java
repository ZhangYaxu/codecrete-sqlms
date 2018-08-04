package com.codecrete.sqlms.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

import static com.codecrete.utils.FileUtils.getString;

/**
 *
 */
@Configuration
@EnableWebSecurity
@EnableJpaRepositories(basePackages="com.codecrete.sqlms.repository")
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Import(ServiceConfiguration.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    private static final Logger LOG = LogManager.getLogger(SecurityConfiguration.class);
    
    @Autowired
    DataSource dataSource;
    
    @Autowired
    private UserDetailsService userService;
    
    @Bean
    public DaoAuthenticationProvider daoAuthentication() {

        DaoAuthenticationProvider daoAuthentication = new DaoAuthenticationProvider();
        daoAuthentication.setUserDetailsService(this.userService);
        daoAuthentication.setPasswordEncoder(passwordEncoder());

        return daoAuthentication;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/dashboard").permitAll()
                .antMatchers("/boilerplate/**").hasRole("ADMIN")
                .antMatchers("/build").hasRole("MULE")
                .antMatchers("/drop").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/dashboard")
                .successHandler(successHandler())
                .loginPage("/login").permitAll()
                .and()
                .logout().permitAll();
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder authentication) throws Exception {
        
        // Load sql from file
        String usersByUsernameSql = getString(getClass().getResourceAsStream("/sql/usersByUsername.sql"));
        String authoritiesByUsernameSql = getString(getClass().getResourceAsStream("/sql/authoritiesByUsername.sql"));
        
        authentication.jdbcAuthentication().dataSource(this.dataSource)
                .usersByUsernameQuery(usersByUsernameSql)
                .authoritiesByUsernameQuery(authoritiesByUsernameSql)
                .passwordEncoder(passwordEncoder());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginSuccessHandler successHandler() {
        return new LoginSuccessHandler();
    }

    /**
     *
     */
    public class LoginSuccessHandler implements AuthenticationSuccessHandler {

        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
            
            LOG.trace("AuthenticationSuccess");
            redirectStrategy.sendRedirect(request, response, "/dashboard");
        }
    }
}
