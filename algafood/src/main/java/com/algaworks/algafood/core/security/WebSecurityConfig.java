package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                // apenas as requisicoes informadas nao precisa de autorizacao
                .antMatchers("/v1/cozinhas/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                // removendo cookies na sessao
                .sessionCreationPolicy(STATELESS)
                .and()
                // CSRF eh um tipo de ataque onde quem esta atacando falsifica a requisicao, isso
                // acontece quando se eh utilizado cookie.
                .csrf().disable();
    }

}
