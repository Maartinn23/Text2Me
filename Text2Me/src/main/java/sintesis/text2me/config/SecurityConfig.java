package sintesis.text2me.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import sintesis.text2me.services.AppUserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	private final AppUserService appUserService = new AppUserService();
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return appUserService;
	}
	
	@Bean
	public AuthenticationProvider authenticatorProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(appUserService);
		provider.setPasswordEncoder(passwordEncoder());
		
		return provider;
	}
	
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
        		.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( auth -> auth
                		.requestMatchers("/").permitAll()
                		.requestMatchers("/contact").permitAll()
	                    .requestMatchers("/register").permitAll()
	                    .requestMatchers("/login").permitAll()
	                    .requestMatchers("/logout").permitAll()
	                    .requestMatchers("css/**").permitAll()
	                    .requestMatchers("/js/**").permitAll()
	                    .anyRequest().authenticated()
                )
                .formLogin(form -> form
                		.loginPage("/login")
                		.usernameParameter("email")
                		.passwordParameter("password")
                		.defaultSuccessUrl("/", true)
                )
                .logout(config -> config.logoutSuccessUrl("/"))
        		.build();
    }


	
}































