package com.giaynhap.securechat.config;

import com.giaynhap.securechat.securefilter.AccessDeniedHandler;
import com.giaynhap.securechat.securefilter.JwtAuthenticationEntryPoint;
import com.giaynhap.securechat.securefilter.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecureConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        System.out.println("configureGlobal");
      //  auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().
                authorizeRequests()

                .antMatchers("/authenticate","/refreshToken","/call").permitAll()
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
                .antMatchers("/user").permitAll()
                .anyRequest().authenticated().and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler )
                .authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
