package com.crowtech.config;

import com.crowtech.filters.AuthoritiesLoggingAfterFilter;
import com.crowtech.filters.JWTTokenGeneratorFilter;
import com.crowtech.filters.JWTTokenValidatorFilter;
import com.crowtech.filters.RequestValidationBeforeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ProjectSecurityConfig extends WebSecurityConfigurerAdapter {
    //Configurando User com InMemoryAuthentication
    /*@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("12345").authorities("admin").and()
                .withUser("user").password("12345").authorities("read").and()
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }
     */
    /*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws  Exception{
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        UserDetails user = User.withUsername("admin").password("12345").authorities("admin").build();
        UserDetails user1 = User.withUsername("user").password("12345").authorities("read").build();
        userDetailsService.createUser(user);
        userDetailsService.createUser(user1);
        auth.userDetailsService(userDetailsService);
    }

     */

    /*@Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }*/

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
        //http.authorizeRequests().anyRequest().authenticated().and().formLogin().and().httpBasic()

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(List.of("Authorization")); //(Arrays.asList("Authorization")
                        config.setMaxAge(3600L);
                        return config;
                    }
        }).and().csrf().disable() //.csrf().ignoringAntMatchers("/contact").csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new JWTTokenValidatorFilter(),BasicAuthenticationFilter.class)
                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/myAccount").hasRole("USER")
                .antMatchers("/myBalance").hasAnyRole("ADMIN", "USER")
                .antMatchers("/myLoans").hasAnyRole("ADMIN", "USER")
                .antMatchers("/myCards").hasAnyRole("ADMIN", "USER")
                .antMatchers("/user").authenticated()
                .antMatchers("/notices").permitAll()
                .antMatchers("/contact").permitAll()
                .and().httpBasic();
        //.formLogin().and().httpBasic();



        //Deny Configuration
        /*
        http.authorizeRequests()
                .anyRequest().denyAll()
                .and()
                .formLogin().and()
                .httpBasic();
         */

        //Permit all configuration
        /*http
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .formLogin().and()
                .httpBasic();

         */

    }

    /*@Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }
}
