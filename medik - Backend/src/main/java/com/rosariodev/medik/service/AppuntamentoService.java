package com.rosariodev.medik.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosariodev.medik.enums.ERuolo;
import com.rosariodev.medik.model.Appuntamento;
import com.rosariodev.medik.model.User;
import com.rosariodev.medik.model.dto.AppuntamentoDTO;
import com.rosariodev.medik.persistence.IDAOAppuntamento;
import com.rosariodev.medik.persistence.IDAOUser;
import com.rosariodev.medik.util.AppuntamentoComparator;
import com.rosariodev.medik.util.Mapper;


import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AppuntamentoService {

    @Autowired
    private IDAOAppuntamento daoAppuntamento;

    @Autowired
    private IDAOUser daoUser;


    @Transactional
    public List<AppuntamentoDTO> findAll(String email) {

        List<Appuntamento> listaAppuntamenti = new ArrayList<>();
        User user = daoUser.findByEmail(email);
        if (user.getRole().equals(ERuolo.MEDICO)){
            listaAppuntamenti = daoAppuntamento.findByMedicoId(user.getId());
            log.info("Appuntamenti medico: {}", listaAppuntamenti);
            Collections.sort(listaAppuntamenti, new AppuntamentoComparator());
            return Mapper.map(listaAppuntamenti, AppuntamentoDTO.class);
        }
        if (user.getRole().equals(ERuolo.PAZIENTE)) {
            listaAppuntamenti = daoAppuntamento.findByPazienteId(user.getId());
            log.info("Appuntamenti paziente: {}", listaAppuntamenti);
            Collections.sort(listaAppuntamenti, new AppuntamentoComparator());
            return Mapper.map(listaAppuntamenti, AppuntamentoDTO.class);
        }
        throw new IllegalArgumentException("Utente non trovato");
    }

    @Transactional
    public void creaAppuntamento(AppuntamentoDTO dto, String email) {
        User user = daoUser.findByEmail(email);
        if (user != null && user.getRole().equals(ERuolo.MEDICO)) {
            Appuntamento appuntamento = new Appuntamento(dto.getTitolo(), dto.getDataInizio(), dto.getDataFine(), null,
                    user);
            user.getAppuntamentiMedico().add(appuntamento); 
            daoAppuntamento.makePersistent(appuntamento);
            log.info("Appuntamento medico creato: {}", appuntamento);
            return;
        }
        if (user != null && user.getRole().equals(ERuolo.PAZIENTE)) {
            User medicoScelto = daoUser.findById(user.getMedico().getId());
            Appuntamento appuntamento = new Appuntamento(user.getCognome() + " " + user.getNome(),
                    dto.getDataInizio(), dto.getDataFine(), user, medicoScelto);
            daoAppuntamento.makePersistent(appuntamento);
            medicoScelto.getAppuntamentiMedico().add(appuntamento);
            log.info("Appuntamento paziente creato: {}", appuntamento);
            return;
        }
        throw new IllegalArgumentException("Utente non trovato");
    }

    @Transactional
    public List<LocalDateTime> getDisponibilitaMedico(String email, LocalDate dataSelezionata) {
        log.info("Data selezionata ricevuta: {}", dataSelezionata);
        List<LocalTime> orariPossibili = new ArrayList<>();
        orariPossibili.add(LocalTime.of(9, 0));
        orariPossibili.add(LocalTime.of(10, 0));
        orariPossibili.add(LocalTime.of(11, 0));
        orariPossibili.add(LocalTime.of(12, 0));
        orariPossibili.add(LocalTime.of(14, 0));
        orariPossibili.add(LocalTime.of(15, 0));
        orariPossibili.add(LocalTime.of(16, 0));

        log.info("Dara appuntamento raw: {}", orariPossibili);

        User user = daoUser.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Utente non trovato");
        }
        User medico = null;
        if (user.getRole().equals(ERuolo.MEDICO)) {
            medico = user;
        } else if (user.getRole().equals(ERuolo.PAZIENTE)) {
            medico = daoUser.findById(user.getMedico().getId());
        } else {
            throw new IllegalArgumentException("Ruolo utente non valido");
        }

        List<Appuntamento> listaFiltrata = new ArrayList<>();
        for (Appuntamento appuntamento : medico.getAppuntamentiMedico()) {
            log.info("Data appuntamento raw: {}", appuntamento.getDataInizio());
            if (appuntamento.getDataInizio().toLocalDate().equals(dataSelezionata)) {
                listaFiltrata.add(appuntamento);
            }
        }
        log.info("Lista filtrata raw: {}", listaFiltrata);

        List<LocalDateTime> orariDisponibili = new ArrayList<>();
        for (LocalTime orario : orariPossibili) {
            boolean occupato = false;
            for (Appuntamento appuntamento : listaFiltrata) {
                LocalTime orarioAppuntamento = appuntamento.getDataInizio().toLocalTime();
                if (orarioAppuntamento.equals(orario)) {
                    occupato = true;
                }
            }
            if (!occupato) {
                orariDisponibili.add(LocalDateTime.of(dataSelezionata, orario));
            }
        }

        log.info("Orari disponibili per il medico {}: {}", email, orariDisponibili);
        return orariDisponibili;
    }

    @Transactional
    public AppuntamentoDTO getAppuntamentoById(Long id){
        Appuntamento appuntamento = this.daoAppuntamento.findById(id);
        if (appuntamento == null) {
           throw new IllegalArgumentException("Appuntamento non trovato"); 
        }
        log.info(appuntamento.toString());
        return Mapper.map(appuntamento, AppuntamentoDTO.class);
    }

    @Transactional
    public void eliminaAppuntamento(Long id, String email){
        User medico = this.daoUser.findByEmail(email);
        List<Appuntamento> listaAppuntamenti = medico.getAppuntamentiMedico();
        Appuntamento appuntamento = this.daoAppuntamento.findById(id);
        if (!listaAppuntamenti.contains(appuntamento)) {
            throw new IllegalArgumentException("Appuntamento non trovato!");
        }
        medico.getAppuntamentiMedico().remove(appuntamento);    
        this.daoAppuntamento.makeTransient(appuntamento);
    }

    @Transactional
    public void modificaAppuntamento(Long id, String email, String titolo){
        log.info(titolo);
        User medico = this.daoUser.findByEmail(email);
        Appuntamento appuntamento = this.daoAppuntamento.findById(id);
        if (!medico.getAppuntamentiMedico().contains(appuntamento)) {
            throw new IllegalArgumentException("Appuntamento non trovato!");
        }
        appuntamento.setTitolo(titolo);
        this.daoAppuntamento.makePersistent(appuntamento);
    }

    @Transactional
    public List<LocalDateTime> getPrenotazioneVicina(String email){

        User user = this.daoUser.findByEmail(email);
        User medico = null;

        if(user.getRole().equals(ERuolo.MEDICO)){
            medico = user;
        } else if (user.getRole().equals(ERuolo.PAZIENTE)) {
            if (user.getMedico() == null) {
                throw new IllegalArgumentException("Paziente senza medico!");
            }
            medico = this.daoUser.findById(user.getMedico().getId());
        }

        int maxGiorni = 5;
        LocalDate data = LocalDate.now();
        data = data.plusDays(1); 
        for(int i = 0; i < maxGiorni; i++){
            List<LocalDateTime> disponibilita = this.getDisponibilitaMedico(medico.getEmail(), data);
            if (!disponibilita.isEmpty()) {
                return disponibilita;
            }
            data = data.plusDays(1);
        }

        throw new IllegalArgumentException("Nessuna disponibilità trovata nei prossimi " + maxGiorni + " giorni!");
    }


}
