package com.rosariodev.medik.model.dto;

import com.rosariodev.medik.enums.EUrgenza;

import lombok.Data;

@Data
public class NuovaRicettaDTO {
    
    private String descrizione;
    private EUrgenza urgenza;

}
