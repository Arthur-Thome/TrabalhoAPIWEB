package com.dbc.webdois.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final TokenService tokenService;
    private final AuthenticationService authenticationService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(authenticationService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and().cors().and()
                .csrf().disable()
                .authorizeRequests()

                .antMatchers("/").permitAll()
//                .antMatchers("/auth").permitAll()
//                .antMatchers(HttpMethod.GET, "/usuario/{idUsuario}/**").hasRole("USUARIO")
//                .antMatchers(HttpMethod.POST, "/usuario/**", "/reserva/**").hasRole("USUARIO")
//                .antMatchers(HttpMethod.PUT, "/usuario/**", "/reserva/**").hasRole("USUARIO")
//                .antMatchers(HttpMethod.DELETE, "/usuario/**", "/reserva/**").hasRole("USUARIO")
//                .antMatchers(HttpMethod.GET, "/usuario/{idUsuario}/*", "/quartos/*").hasRole("USUARIO")
//                .antMatchers("/**").hasRole("ADMIN") //ROLE_ADMIN
//                .anyRequest().authenticated()

                //filtro de autenticação...
                .and().addFilterBefore(new TokenAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-ui/**",
                "/v2/api-docs",
                "/swagger-ui.html",
                "/swagger-resources/**"
                );
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
