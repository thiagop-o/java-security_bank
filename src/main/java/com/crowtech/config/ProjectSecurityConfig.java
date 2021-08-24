package com.crowtech.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * myAcccount = secured
     * myBalance = secured
     * myLoans = secured
     * myCards = secured
     * notices = not secured
     * contact = not secured
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Default Configuration
        //http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic();

        //Custom Configurations
        /*
        http.authorizeRequests()
                .antMatchers("/myAccount").authenticated()
                .antMatchers("/myBalance").authenticated()
                .antMatchers("/myLoans").authenticated()
                .antMatchers("/myCards").authenticated()
                .antMatchers("/notices").permitAll()
                .antMatchers("/contact").permitAll()
                .and()
                .formLogin().and().httpBasic();

         */

        //Deny Configuration
        /*
        http.authorizeRequests()
                .anyRequest().denyAll()
                .and()
                .formLogin().and()
                .httpBasic();
         */

        //Permit all configuration
        http
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .formLogin().and()
                .httpBasic();

    }
}
