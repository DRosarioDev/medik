package com.rosariodev.medik.model.dto;


import java.time.LocalDate;


import lombok.Data;

@Data
public class MedicoDTO{
    
    private Long id;
    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String cf;
    private String email;
    private String specializzazione;


}
