package org.springproject.springproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springproject.springproject.service.UserDetailServiceImpl;

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

//    private final DataSource dataSource;
//
//
//    public SecurityConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
    //    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource)
//                .usersByUsernameQuery("select username, password, enabled from users where username = ?")
//                .authoritiesByUsernameQuery("select username, authority from authorities where username = ?")
//                .passwordEncoder(new StandardPasswordEncoder("secret"));
//    }
    // OWN AUTHENTICATION

    private final UserDetailServiceImpl userDetailService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(UserDetailServiceImpl userDetailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailService = userDetailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/users/{id}")
                .hasRole("ADMIN")
                .antMatchers("/users/**","/personnel/**","/customers/**")
                .hasAnyRole("ADMIN","MANAGER")
                .antMatchers("/user/**")
                .hasAnyRole("ADMIN","MANAGER","USER")
                .antMatchers("/", "/**","/login","/register").permitAll().anyRequest().authenticated();
        http.formLogin()
                .loginPage("/login")
        .permitAll();

        http.httpBasic();
    }
}
