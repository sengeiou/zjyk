package com.lys.app.manager;

import com.lys.utils.LOG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class AppSecurityConfigurer extends WebSecurityConfigurerAdapter
{
	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_USER = "ROLE_USER";

	@Value("${system.user.password.secret}")
	private String secret = null;

	private PasswordEncoder passwordEncoder = null;

	public String encodePsw(String psw)
	{
		return passwordEncoder.encode(psw);
	}

	@Autowired
	private UserDetailsService userServiceImpl = null;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		LOG.v("----------- configure ---------------- auth ---------------------");
//		passwordEncoder = new BCryptPasswordEncoder();
		passwordEncoder = new Pbkdf2PasswordEncoder(this.secret);
		auth.userDetailsService(userServiceImpl).passwordEncoder(passwordEncoder);
//		InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> userConfig = auth.inMemoryAuthentication().passwordEncoder(passwordEncoder);
//		userConfig.withUser("admin").password(passwordEncoder.encode("abc")).authorities(ROLE_ADMIN, ROLE_USER);
//		userConfig.withUser("user").password(passwordEncoder.encode("123")).authorities(ROLE_USER);
	}

	@Override
	public void configure(WebSecurity web) throws Exception
	{
		super.configure(web);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
//		super.configure(http);
		http.csrf().disable() //
				.authorizeRequests() //
				.antMatchers("/info", "/config", "/upload/**", "/file/**", "/run/**", "/logic/**").permitAll() //
				.anyRequest().permitAll() //
//				.anyRequest().authenticated() //
				.and().headers().frameOptions().disable() // 解决页面不能嵌入 iframe 的问题
				.and().formLogin() //
				.and().httpBasic();
	}
}
