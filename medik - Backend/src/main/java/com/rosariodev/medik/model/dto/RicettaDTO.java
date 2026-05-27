package com.rosariodev.medik.model.dto;

import java.time.LocalDate;

import com.rosariodev.medik.enums.EUrgenza;

import lombok.Data;

@Data
public class RicettaDTO {
    private Long id;
    private String descrizione;
    private LocalDate data;
    private EUrgenza urgenza;
    private String nomeMedico;
    private String cognomeMedico;

}
