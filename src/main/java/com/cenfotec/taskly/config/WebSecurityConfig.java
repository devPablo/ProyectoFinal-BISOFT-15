package com.cenfotec.taskly.config;

import com.cenfotec.taskly.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    //Necesario para evitar que la seguridad se aplique a los resources
    //Como los css, imagenes y javascripts
    String[] resources = new String[]{
            "/include/*","/css/*","/icons/","/img/","/js/*","/layer/*"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                //El metodo authorizeRequests() permite restringir y/o dar acceso request HTTP
                .authorizeRequests()
                //antMatchers(): Lista de URL que corresponden a un RequestMapping como lo hacemos en los controladores.
                .antMatchers(resources).permitAll() // permitAll(): Especifica que estas URLs son accesibles por cualquiera.
                .antMatchers("/login*","/", "/signin*", "/api*", "/resources/**").permitAll()
                //access(): permite el acceso cumpliendo la expresión, en este caso tenemos la expresion “hasRole()”.
                // Donde verifica si el usuario tiene ese especifico Role.
                .antMatchers("/tasks*").access("hasRole('USER')")
                //anyRequest(): Ya que la configuración es lineal poniendo este metodo al final interpreta los request
                //a las URLs que no fueron descritos, y en conjunto con el metodo authenticated() permite y da acceso
                // a cualquier usuario que este autenticado.
                .anyRequest().authenticated()
                .and()
                //El metodo fromLogin(). Permite personalizar el proceso de inicio de sesión
                .formLogin()
                //loginPage(): indica la url de la pagina de inicio de sesión
                .loginPage("/login")
                .permitAll()
                //defaultSuccessUrl(): Indica a cual URL sera redirigido cuando el usuario inicie sesión.
                .defaultSuccessUrl("/tasks")
                //failureUrl(): Indica a cual URL sera redirigido cuando el inicio de sesión falla.
                .failureUrl("/login?error=true")
                //usernameParameter() y passwordParameter(): Indica el nombre de los parámetros respectivamente.
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                //El metodo logout(): Personaliza el proceso de cierre de sesión.
                .logout()
                .permitAll()
                //logoutSuccessUrl(): Indica la URL donde sera redirigido cuando el usuario cierre sesión.
                .logoutSuccessUrl("/login?logout");
    }

    @Override
    public void configure(WebSecurity web) throws  Exception{
        web.ignoring().antMatchers("/resources/**", "/api/**");
    }

    BCryptPasswordEncoder bCryptPasswordEncoder;
    //Crea el encriptador de contraseñas
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
        //El numero 4 representa que tan fuerte quieres la encriptacion.
        //Se puede en un rango entre 4 y 31.
        //Si no pones un numero el programa utilizara uno aleatoriamente cada vez
        //que inicies la aplicacion, por lo cual tus contrasenas encriptadas no funcionaran bien
        return bCryptPasswordEncoder;
    }

    @Autowired
    UserServiceImpl userDetailsService;

    //Registra el service para usuarios y el encriptador de contrasena
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        // Setting Service to find User in the database.
        // And Setting PassswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}