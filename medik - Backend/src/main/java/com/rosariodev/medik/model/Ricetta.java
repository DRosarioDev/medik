package com.rosariodev.medik.model;

import java.time.LocalDate;

import com.rosariodev.medik.enums.EUrgenza;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "ricette")
public class Ricetta {

    @EqualsAndHashCode.Include
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descrizione;
    private LocalDate data;
    @Enumerated(EnumType.STRING)
    private EUrgenza urgenza;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    private User medico;
    @ManyToOne
    @JoinColumn(name = "paziente_id")
    private User paziente;

    
    public Ricetta(String descrizione, LocalDate data, EUrgenza urgenza, User medico, User paziente) {
        this.descrizione = descrizione;
        this.data = data;
        this.urgenza = urgenza;
        this.medico = medico;
        this.paziente = paziente;
    }

    

}
