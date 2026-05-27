package com.rosariodev.medik.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rosariodev.medik.model.User;
import com.rosariodev.medik.model.dto.UserDTO;
import com.rosariodev.medik.model.dto.UserLoginDTO;
import com.rosariodev.medik.persistence.IDAOUser;
import com.rosariodev.medik.util.JWTUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    
    @Autowired
    private IDAOUser daoUser;    
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Transactional
    public UserDTO login(UserLoginDTO medicoLoginDTO){
        log.info("Tentativo di login per email: {}", medicoLoginDTO.getEmail());
        User user = daoUser.findByEmail(medicoLoginDTO.getEmail());
        if (user == null) {
            throw new IllegalArgumentException("Utente non trovato");
        }

        if (!passwordEncoder.matches(medicoLoginDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Password errata");
        }
        log.info("Utente loggato");
        
        UserDTO userDTO = new UserDTO();
        userDTO.setAuthToken(JWTUtil.generateToken(medicoLoginDTO.getEmail()));
        userDTO.setNome(user.getNome());
        userDTO.setCognome(user.getCognome());
        userDTO.setEmail(user.getEmail());
        userDTO.setId(user.getId());
        userDTO.setRuolo(user.getRole());
        userDTO.setCf(user.getCf());
        userDTO.setDataNascita(user.getDataNascita());
        userDTO.setAuthToken(JWTUtil.generateToken(user.getEmail()));

        return userDTO;

    }


}
