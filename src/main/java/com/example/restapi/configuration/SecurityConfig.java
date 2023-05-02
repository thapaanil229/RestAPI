package com.example.restapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    //add support for JDBC ... no more hard coded users

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return  new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws  Exception{
        httpSecurity.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/employees").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/employees/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN"));

        httpSecurity.httpBasic();
        httpSecurity.csrf().disable();
        return httpSecurity.build();
    }
}


//   @Bean
////    public InMemoryUserDetailsManager userDetailsManager(){
////        UserDetails kushal = User.builder()
////                .username("kushal")
////                .password("{noop}thapa")
////                .roles("ADMIN", "MANAGER","EMPLOYEE")
////                .build();
////
////        UserDetails sandesh = User.builder()
////                .username("sandesh")
////                .password("{noop}thapa")
////                .roles("EMPLOYEE","MANAGER")
////                .build();
////
////        UserDetails anil = User.builder()
////                .username("anil")
////                .password("{noop}thapa")
////                .roles("EMPLOYEE")
////                .build();
////
////        return new InMemoryUserDetailsManager(kushal, sandesh, anil);
////    }

