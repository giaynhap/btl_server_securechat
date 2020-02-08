package com.giaynhap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private UserDetailsService jwtUserDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        System.out.println("configureGlobal");
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // We don't need CSRF for this example
        System.out.println("configure");
        httpSecurity.csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests().antMatchers("/authenticate","/refreshToken","/call").permitAll()
                .antMatchers(HttpMethod.GET, "/users/avatar/{uuid}").permitAll()
                .antMatchers(HttpMethod.GET, "/users/exist/{uuid}").permitAll()
                .antMatchers(HttpMethod.GET, "/conversation/thumb/{uuid}/{useruuid}").permitAll()
                .antMatchers("/ws", "/ws/socket", "/topic/**", "/queue/**", "/ws/success", "/ws/**").permitAll()
                .antMatchers("/keyprovider/public/{uuid}").permitAll()
                .antMatchers("/users/prelogin/{device}/{username}").permitAll()
                .antMatchers("/users/verifyRegister").permitAll()
                .antMatchers("/users/add").permitAll()
                .antMatchers("/file/image/{uuid}/{imguuid}").permitAll()
                .antMatchers("/file/audio/{uuid}/{imguuid}").permitAll()
                .antMatchers("/sticker/{sticker}/{index}").permitAll()
                .antMatchers("/sticker/list").permitAll()
                .antMatchers("/makepass/{password}").permitAll()
                .antMatchers("/avatar_letter/{name}").permitAll()


                // all other requests need to be authenticated
                .anyRequest().authenticated().and().


                // make sure we use stateless session; session won't be used to
                // store user's state.
                        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}