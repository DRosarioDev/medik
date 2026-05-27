package com.rosariodev.medik.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.rosariodev.medik.enums.ERuolo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "users")
public class User {
    
    private static final int MAX_PAZIENTI = 100;

    @EqualsAndHashCode.Include
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String nome;
    private String cognome;
    @Column(unique = true)
    private String email;
    private String password;
    private String cf;
    @Column(name = "data_nascita")
    private LocalDate dataNascita;

    @Enumerated(EnumType.STRING)
    private ERuolo role;

    // Medici //
    @OneToMany(mappedBy = "medico")
    private List<User> pazienti = new ArrayList<>();
    @OneToMany(mappedBy = "medico")
    private List<Ricetta> ricetteEmesse = new ArrayList<>();
    @OneToMany(mappedBy = "medico")
    private List<Appuntamento> appuntamentiMedico = new ArrayList<>();

    // Pazienti //
    @ManyToOne
    @JoinColumn(name = "medico_id")
    private User medico;
    @OneToMany(mappedBy = "paziente")
    private List<Ricetta> ricettePaziente = new ArrayList<>();
    @OneToMany(mappedBy = "paziente")
    private List<Appuntamento> appuntamentiPaziente = new ArrayList<>();

    
    

    public User(String nome, String cognome, String email, String password, String cf, LocalDate dataNascita, ERuolo role) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.cf = cf;
        this.dataNascita = dataNascita;
        this.role = role;
    }

    public boolean haMenoDiNPaziienti(){
        if(this.getPazienti().size() < MAX_PAZIENTI && this.getRole() == ERuolo.MEDICO){
            return true;
        }
        return false;
    }




}
