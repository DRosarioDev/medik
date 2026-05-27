package com.rosariodev.medik.model.dto;


import java.time.LocalDate;

import com.rosariodev.medik.enums.ERuolo;

import lombok.Data;

@Data
public class UserDTO{

    private Long id;
    private String nome;
    private String cognome;
    private String email;
    private ERuolo ruolo;
    private String cf;
    private LocalDate dataNascita;
    private String authToken;

}
