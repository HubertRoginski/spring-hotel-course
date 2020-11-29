package org.springproject.springproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    IN MEMORY AUTHENTICATION

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("stachu")
//                .password("{noop}japycz")
//                .authorities("ROLE_USER")
//                .and()
//                .withUser("patryk")
//                .password("{noop}pietrek")
//                .authorities("ROLE_USER");
//    }

//    JDBC AUTHENTICATION

    private final DataSource dataSource;


    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("select username, password, enabled from users where username = ?")
        .authoritiesByUsernameQuery("select username, authority from authorities where username = ?");
    }

}
