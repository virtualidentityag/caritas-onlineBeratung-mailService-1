package de.caritas.cob.mailservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.csrf.CsrfFilter;
import de.caritas.cob.mailservice.filter.StatelessCsrfFilter;

/**
 * Provides the Security configuration.
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${csrf.cookie.property}")
  private String csrfCookieProperty;

  @Value("${csrf.header.property}")
  private String csrfHeaderProperty;

  /**
   * Configure spring security filter chain
   * 
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable()
        .addFilterBefore(new StatelessCsrfFilter(csrfCookieProperty, csrfHeaderProperty),
            CsrfFilter.class)
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .sessionAuthenticationStrategy(new NullAuthenticatedSessionStrategy()).and()
        .authorizeRequests()
        .anyRequest().permitAll();
  }

}
