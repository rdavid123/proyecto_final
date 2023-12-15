package com.aquaclean.aquacleanapp.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.aquaclean.aquacleanapp.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserService userService;
	

	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
        
            .antMatchers("/aquaclean/clientes/**").hasRole("CLIENTE")
            .antMatchers("/aquaclean/empleados/**").hasRole("EMPLEADO")
            .antMatchers("/aquaclean/admin/**").hasRole("ADMIN")
            .antMatchers("/aquaclean/repartidor/**").hasRole("REPARTIDOR")
            .antMatchers("/","/js/**", "/css/**", "/img/**").permitAll()
            
        	.antMatchers("/aquaclean/**").permitAll()
            .antMatchers("/create-payment-intent").permitAll()
            .antMatchers("/register","/login").permitAll()
            .anyRequest().permitAll()
            .and()
            
            .formLogin()
            .loginPage("/login")
            .successHandler(loginSuccessHandler())
            .and()
            .logout()
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessUrl("/login")
            .permitAll()
            .and().addFilterBefore(new RedirectFilter(), UsernamePasswordAuthenticationFilter.class)
            
            .csrf().disable();
    }
	
	@Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }
	
	private String obtainUserRole(Authentication authentication) { //funcion para obtener el rol del usuario
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role;
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
            return role;
        }
        return null;
	}
	
	public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            String role = obtainUserRole(authentication);
            if (role.equals("ROLE_CLIENTE")) {
                response.sendRedirect("/aquaclean/clientes");
            } else if (role.equals("ROLE_EMPLEADO")) {
                response.sendRedirect("/aquaclean/empleados");
            }else if (role.equals("ROLE_ADMIN")) {
                response.sendRedirect("/aquaclean/admin");
            }else if (role.equals("ROLE_REPARTIDOR")) {
                response.sendRedirect("/aquaclean/repartidor");
            } else {
                response.sendRedirect("/aquaclean/login?rol_unknown");
            }
        } 
    }
	
	// prohibir acceso a login y register si el usuario inicio sesion
	public class RedirectFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null && authentication.isAuthenticated()
                    && (request.getRequestURI().equals("/login") || request.getRequestURI().equals("/register"))) {
                String role = obtainUserRole(authentication);

                if (role.equals("ROLE_CLIENTE")) {
                    response.sendRedirect("/aquaclean/clientes");
                } else if (role.equals("ROLE_EMPLEADO")) {
                    response.sendRedirect("/aquaclean/empleados");
                }else if (role.equals("ROLE_ADMIN")) {
                    response.sendRedirect("/aquaclean/admin");
                }else if (role.equals("ROLE_REPARTIDOR")) {
                    response.sendRedirect("/aquaclean/repartidor");
                }else {
                    response.sendRedirect("/"); // Redirigir a la página principal por defecto
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}