package com.rosariodev.medik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rosariodev.medik.model.User;
import com.rosariodev.medik.persistence.IDAOUser;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private IDAOUser daoUser;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = daoUser.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Utente non trovato: " + email);
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}