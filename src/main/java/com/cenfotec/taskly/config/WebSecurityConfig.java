package com.cenfotec.taskly.config;

import com.cenfotec.taskly.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    //Necesario para evitar que la seguridad se aplique a los resources
    //Como los css, imagenes y javascripts
    String[] resources = new String[]{
            "/include/**","/css/**","/icons/**","/img/**","/js/**","/layer/**"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                //El metodo authorizeRequests() permite restringir y/o dar acceso request HTTP
                .authorizeRequests()
                //antMatchers(): Lista de URL que corresponden a un RequestMapping como lo hacemos en los controladores.
                .antMatchers(resources).permitAll() // permitAll(): Especifica que estas URLs son accesibles por cualquiera.
                .antMatchers("/","/index", "/tasks*", "signin*").permitAll();
                //access(): permite el acceso cumpliendo la expresión, en este caso tenemos la expresion “hasRole()”.
                // Donde verifica si el usuario tiene ese especifico Role.
                //.antMatchers("/tasks*").access("hasRole('USER')")
                //.anyRequest().authenticated()
                //.and()
                //.formLogin()
                //.loginPage("/login")
                //.permitAll()
                //.defaultSuccessUrl("/tasks")
                //.failureUrl("/login?error=true")
                //.usernameParameter("username")
                //.passwordParameter("password")
                //.and()
                //.logout()
                //.permitAll()
                //.logoutSuccessUrl("/login?logout");
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
        //auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
