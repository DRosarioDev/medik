package com.rosariodev.medik.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "appuntamenti")
public class Appuntamento {
    
    @EqualsAndHashCode.Include
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titolo;
    private LocalDateTime dataInizio;
    private LocalDateTime dataFine;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "paziente_id")
    private User paziente;
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "medico_id")
    private User medico;
    
    public Appuntamento(String titolo, LocalDateTime dataInizio, LocalDateTime dataFine, User paziente, User medico) {
        this.titolo = titolo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.paziente = paziente;
        this.medico = medico;
    }

    

}