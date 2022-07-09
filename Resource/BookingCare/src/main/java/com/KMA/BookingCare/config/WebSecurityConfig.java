package com.KMA.BookingCare.config;

import com.KMA.BookingCare.ServiceImpl.UserDetailsServiceImpl;
import com.KMA.BookingCare.common.JwtUtils;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        // securedEnabled = true,
        // jsr250Enabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
                .authorizeRequests().antMatchers("/api/**").permitAll()
                .antMatchers("/signin").permitAll()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers("/api/handbook/deletes").hasAnyRole("ADMIN","USER","DOCTER")
                .antMatchers("/api/handbook/deletes").hasAnyRole("ADMIN","USER","DOCTER")
                .antMatchers(HttpMethod.POST,"/api/handbook").hasAnyRole("ADMIN","DOCTER")
                .antMatchers(HttpMethod.GET,"/api/current-login").authenticated()
                .antMatchers(HttpMethod.GET,"/api/media/check/**").authenticated()
                .antMatchers(HttpMethod.GET,"/api/media/get-by-current-login").authenticated()
                .antMatchers("/**").permitAll()
                .antMatchers("/book/**").authenticated()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(loginSuccessHandler)
                .failureUrl("/register")
                .permitAll().and().logout().logoutSuccessUrl("/login")
                .logoutUrl("/logout").permitAll().and().exceptionHandling().accessDeniedPage("/access-deny")
        ;
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Autowired
    private LoginSuccessHandler loginSuccessHandler;
}