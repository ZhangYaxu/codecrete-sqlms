package com.codecrete.sqlms.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
import java.io.IOException;

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
    private UserDetailsService userDetailsService;
    
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//        authenticationProvider.setUserDetailsService(userDetailsService());
//        authenticationProvider.setPasswordEncoder(passwordEncoder());
//
//        return authenticationProvider;
//    }


    // Authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/dashboard").hasRole("MULE")
                .antMatchers("/boilerplate/**").hasRole("MULE")
                .antMatchers("/user/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login").failureUrl("/404");
        
        
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


    // Authentication
    @Override
    public void configure(AuthenticationManagerBuilder authentication) throws Exception {
        authentication.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
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
            LOG.debug("AuthenticationSuccess");
            redirectStrategy.sendRedirect(request, response, "/dashboard");
        }
    }
}
