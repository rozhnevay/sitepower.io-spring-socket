package ru.otus.spring.config.security;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
    http.cors().disable();
    http.csrf().disable();
    http.authorizeRequests()
        .antMatchers("/api-widget/**").permitAll()
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

  @Bean
  public FilterRegistrationBean filterRegistrationBean() {
    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(corsConfigurationSource()));
    bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
    return bean;
  }

  /* TODO: убрать */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(ImmutableList.of("*"));
    configuration.setAllowedMethods(ImmutableList.of(
        "OPTIONS", "HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
    configuration.setAllowCredentials(true);
    configuration.setAllowedHeaders(ImmutableList.of("*"));
    configuration.setExposedHeaders(ImmutableList.of(
        "X-Auth-Token", "Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
