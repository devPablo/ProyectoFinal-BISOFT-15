package com.cenfotec.taskly.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.cenfotec.taskly.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.cenfotec.taskly.domain.User appUser;
        //Buscar el usuario con el repositorio y si no existe lanzar una exepcion
        //esta excepcion es atrapada por el webSecurityConfig por lo tanto ejecutara el .failureUrl("/login?error=true")
        try{
            appUser = userRepository.findByUsername(username).get(0);
        }catch(UsernameNotFoundException ex){
            throw new UsernameNotFoundException("No existe usuario");
        }


        //Mapear nuestra lista de Authority con la de spring security
        List grantList = new ArrayList();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        grantList.add(grantedAuthority);

        //Crear El objeto UserDetails que va a ir en sesion y retornarlo.
        UserDetails user = new User(appUser.getUsername(), appUser.getPassword(), grantList);
        return user;
    }
}
