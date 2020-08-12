package ru.otus.spring.config.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Slf4j
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final EntryPoint authenticationEntryPoint;

  @Override
  public void configure(WebSecurity web) {
    web.ignoring().antMatchers("/swagger-ui.html")
        .antMatchers("/webjars/springfox-swagger-ui/**")
        .antMatchers("/swagger-resources/**")
        .antMatchers("/v2/api-docs")
        .antMatchers("/h2-console/**");
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    try {
      auth.authenticationProvider(daoAuthenticationProvider)
          .userDetailsService(userDetailsService)
          .passwordEncoder(passwordEncoder);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic()
        .authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .logout()
        .logoutUrl("/api/logout")
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
  }

  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

}
