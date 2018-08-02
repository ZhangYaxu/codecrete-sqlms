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
    private UserDetailsService userDetailsService;
    
    @Bean
    public DaoAuthenticationProvider daoAuthentication() {

        DaoAuthenticationProvider daoAuthentication = new DaoAuthenticationProvider();
        daoAuthentication.setUserDetailsService(userDetailsService);
        daoAuthentication.setPasswordEncoder(passwordEncoder());

        return daoAuthentication;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    
        http.csrf().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers( "/**").permitAll()
                .anyRequest().authenticated();
        
//        http
//                .authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/boilerplate/**").hasRole("ADMIN")
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                    .defaultSuccessUrl("/dashboard")
//                    .successHandler(successHandler())
//                    .failureUrl("/404");
        
        
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/dashboard").hasRole("ADMIN")
//                .antMatchers("/boilerplate/**").hasRole("ADMIN")
//                .antMatchers("/user/**").hasRole("ADMIN")
//// TODO: FIX!   .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                    .loginPage("/login").permitAll()
//                    .defaultSuccessUrl("/dashboard")
//                    .successHandler(successHandler())
//                    .failureUrl("/404")
//                .and()
//                .exceptionHandling().accessDeniedPage("/403")
//                .and()
//                .logout().permitAll().deleteCookies("remember-me")
//                .and()
//                .rememberMe().alwaysRemember(true);
    }
    
    @Override
    public void configure(AuthenticationManagerBuilder authentication) throws Exception {
    
//        authentication.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    
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
