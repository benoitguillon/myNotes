package org.bgi.springboot1;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		.withUser("admin").password("admin").roles(Roles.ADMIN_ROLE)
		.and()
		.withUser("regular").password("regular").roles(Roles.REGULAR_ROLE);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			// api requests authenticated through basic HTTP
//			.authorizeRequests()
//			.antMatchers("/api/**")
//				.authenticated()
//				.and()
//				.httpBasic()
//			.and()
			// web application authenticated through html form
			.authorizeRequests()
			.antMatchers("/**")
				.authenticated()
				.and()
				.formLogin();
	}

}
