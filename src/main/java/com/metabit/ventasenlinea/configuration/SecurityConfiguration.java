package com.metabit.ventasenlinea.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	@Qualifier("userServiceSecurityImpl")
	private UserDetailsService userServiceSecurityImpl;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userServiceSecurityImpl).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	
	/*DOCUMENTACION
	 * .antMatchers: Permite establecer urls que son posibles de acceder sin estar autorizados
	 * .anyRequest().authenticated(): El resto de urls van a requerir autenticación
	 * 
	 * */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/* Agregar urls que pueden ser accedidas sin estar autenticado en antMatchers
		 * además si agregan carpetas en static tambien deberan agregarlas*/
		http.authorizeRequests()
		.antMatchers(
				"/cliente/crear-cliente",
				"/cliente/verificar-codigo/{id}",
				"/cliente/verificar-codigo",
				"/producto/index",
				//INICIO RICARDO PRUEBAS
				"/kardex/**",
				"/kardex-one/**",
				//"/pedido/**",
				//FIN RICARDO PRUEBAS
				"/producto/agregar-producto",
				//"/producto/asignar-descuento",
				//"/producto/hab-deshab",
				//"/producto/ver-detalle/{id}",
				"/producto/agregar-producto/{cantidad}/{id}",
				"/producto/remover-producto/{id}",
				//"/usuario/deshabilitar",
				//"/pedido/cambio-estado",
				"/api/productos-agregados",
				"/api/cantidad-disponible/{id}",
				//Recuperacion de contraseña
				"/forgot-password**",
				"/reset-password**",
				//Prueba de que no me funciona :( la busqueda por el security
				"/producto/subcategoria/**",
				"/producto/categoria/**",
				"/producto/searchProduct/**",
				"/producto/departamento/**"
				).permitAll()
		.antMatchers("/css/**","/img/**","/js/**","/scss/**","/vendor/**","/img_products/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/ventas-online/login").loginProcessingUrl("/ventas-online/login-check")
		.usernameParameter("exampleInputEmail").passwordParameter("exampleInputPassword")
		.defaultSuccessUrl("/ventas-online/loginsuccess").permitAll()
		.and()
		.logout().logoutUrl("/ventas-online/logout").logoutSuccessUrl("/ventas-online/login?logout")
		.permitAll();
	}

}
