package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager (DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }


    @Bean
    //kofiguje permisie
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception
    {
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET,"/test").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET,"/test").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET,"/Manager").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET,"/findAll").hasRole("MANAGER")
                        .anyRequest().authenticated()
        )
                .formLogin(Customizer.withDefaults());


        return http.build();

    }


//    @Bean
//    //tworzy w pamięci aplikacji testowe chużytkowników podejscie bez baz danych
//    public InMemoryUserDetailsManager userDetailsManager()
//    {
//        UserDetails john = User.builder().username("john").password("{noop}test123").roles("EMPLOYEE").build();
//        UserDetails mary = User.builder().username("mary").password("{noop}test123").roles("EMPLOYEE", "MANAGER").build();
//
//       return new InMemoryUserDetailsManager(john,mary);
//    }


}
