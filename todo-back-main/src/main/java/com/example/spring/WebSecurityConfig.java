package com.example.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CurrentUserDetailsService userDetailsService;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers(HttpMethod.OPTIONS, "/**")
				.antMatchers("/app/**/*.{js,html}")
				.antMatchers("/bower_components/**")
				.antMatchers("/partials/**/*")
				.antMatchers(HttpMethod.POST, "/users"); // insc
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests().antMatchers("/").permitAll().anyRequest().authenticated().and()
				.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class).csrf()
				.csrfTokenRepository(csrfTokenRepository());

		// http.csrf().disable();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;

	}

	/**
	 * 
	 * To take advantage of this, your server needs to set a token in a
	 * JavaScript readable session cookie called XSRF-TOKEN on first HTTP GET
	 * request. On subsequent non-GET requests the server can verify that the
	 * cookie matches X-XSRF-TOKEN HTTP header, and therefore be sure that only
	 * JavaScript running on your domain could have read the token. The token
	 * must be unique for each user and must be verifiable by the server (to
	 * prevent the JavaScript making up its own tokens). We recommend that the
	 * token is a digest of your site's authentication cookie with salt for
	 * added security.
	 * 
	 */
}
