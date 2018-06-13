package ru.tuxoft.secure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    protected TokenAuthenticationManager tokenAuthenticationManager;

    protected void configure(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry conf) {
        conf.antMatchers("/profile").hasRole("User");
        conf.antMatchers("/admin/**").hasRole("Admin");
        conf.antMatchers("/**").permitAll();
    }

    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry conf = http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().sameOrigin()
                .and()
                .addFilterBefore(new TokenAuthenticationFilter(tokenAuthenticationManager), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests();
        configure(conf);
    }

}