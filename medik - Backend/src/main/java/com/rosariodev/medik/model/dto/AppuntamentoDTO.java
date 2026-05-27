package com.rosariodev.medik.model.dto;

import java.time.LocalDateTime;


import lombok.Data;

@Data
public class AppuntamentoDTO {
    
    private Long id;
    private String titolo;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;

}