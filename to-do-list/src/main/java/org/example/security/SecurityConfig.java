package org.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationConfiguration authConfig) throws Exception {

        http.csrf().disable();

        http.cors();

        http.authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/create_account").permitAll()
                .antMatchers("/refresh_token").authenticated()
//TASK
                .antMatchers(HttpMethod.GET,"/api/task").permitAll()
                .antMatchers(HttpMethod.GET,"/api/task/*/").permitAll()
                .antMatchers(HttpMethod.GET,"/api/task/*").permitAll()

                .antMatchers(HttpMethod.POST,"/api/task").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/task/*").permitAll()
                .antMatchers(HttpMethod.DELETE,"/api/task/*").permitAll()
                //hasAnyAuthority("USER","ADMIN")
//APPUSER
                .antMatchers(HttpMethod.GET, "/api/dashboard").permitAll()
                .antMatchers(HttpMethod.GET, "/api/dashboard/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/dashboard").permitAll()
                .antMatchers(HttpMethod.GET, "/api/dashboard/username/*").permitAll()



                .antMatchers("/**").denyAll()
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(authConfig), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}