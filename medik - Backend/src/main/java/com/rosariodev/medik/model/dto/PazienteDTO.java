package com.rosariodev.medik.model.dto;


import java.time.LocalDate;


import lombok.Data;

@Data
public class PazienteDTO{
    
    private Long id;
    private String nome;
    private String cognome;
    private LocalDate dataNascita;
    private String cf;
    private String email;

}
