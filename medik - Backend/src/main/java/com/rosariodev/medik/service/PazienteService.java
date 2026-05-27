package com.rosariodev.medik.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rosariodev.medik.enums.ERuolo;
import com.rosariodev.medik.model.Ricetta;
import com.rosariodev.medik.model.User;
import com.rosariodev.medik.model.dto.MedicoDTO;
import com.rosariodev.medik.model.dto.PazienteDTO;
import com.rosariodev.medik.model.dto.RicettaDTO;
import com.rosariodev.medik.model.dto.UserDTO;
import com.rosariodev.medik.persistence.IDAORicetta;
import com.rosariodev.medik.persistence.IDAOUser;
import com.rosariodev.medik.util.Mapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PazienteService {
    
    @Autowired
    private IDAOUser daoUser;

    @Autowired
    private IDAORicetta daoRicetta;

    @Transactional
    public PazienteDTO getPazienteById(Long id) {
        log.info("Recupero paziente con id: {}", id);
        User paziente = daoUser.findById(id);
        if (paziente == null) {
            throw new IllegalArgumentException("Paziente non trovato");
        }
        log.info("Paziente trovato: {} {}", paziente.getNome(), paziente.getCognome());
        return  Mapper.map(paziente, PazienteDTO.class);
    }

    @Transactional
    public List<RicettaDTO> getAllRicetteByPaziente(String email) {
        User user = daoUser.findByEmail(email);
        if (!user.getRole().equals(ERuolo.PAZIENTE)) {
            throw new IllegalArgumentException("L'utente non è un paziente");
        }
        List<Ricetta> ricette = daoRicetta.findByIdPaziente(user.getId());
        List<RicettaDTO> ricetteDTO = new ArrayList<>();
        if (ricette.isEmpty()) {
            log.info("Nessuna ricetta trovata per paziente id: {}", user.getId());
        }

        for (Ricetta ricetta : ricette) {
            RicettaDTO ricettaDTO = Mapper.map(ricetta, RicettaDTO.class);
            if (ricetta.getMedico() != null){
                ricettaDTO.setNomeMedico(ricetta.getMedico().getNome());
                ricettaDTO.setCognomeMedico(ricetta.getMedico().getCognome());
            }
            ricetteDTO.add(ricettaDTO);
        }
        
        log.info("Ricette trovate: {}", ricetteDTO);
        return ricetteDTO;
    }

    @Transactional
    public MedicoDTO getMedicoPaziente(String email) {
        User paziente = daoUser.findByEmail(email);
        log.info("Recupero medico per paziente: {} {}", paziente.getNome(), paziente.getCognome());
        if (!paziente.getRole().equals(ERuolo.PAZIENTE)) {
            throw new IllegalArgumentException("L'utente non è un paziente");
        }
        User medico = paziente.getMedico();
        log.info("Medico trovato: {} {}", medico.getNome(), medico.getCognome());
        return Mapper.map(medico, MedicoDTO.class);
    }

    @Transactional
    public void cambioMedico(String email, Long nuovoIdMedico){
        User paziente = daoUser.findByEmail(email);
        if (!paziente.getRole().equals(ERuolo.PAZIENTE)) {
            throw new IllegalArgumentException("L'utente non è un paziente");
        }
        User nuovoMedico = daoUser.findById(nuovoIdMedico);
        if (!nuovoMedico.getRole().equals(ERuolo.MEDICO)) {
            throw new IllegalArgumentException("L'utente non è un medico");
        }
        if (!nuovoMedico.haMenoDiNPaziienti()) {
            throw new IllegalArgumentException("Il medico ha già il numero massimo di pazienti");
        }

        User medico = paziente.getMedico();
        if (medico != null) {
            medico.getPazienti().remove(paziente);
            daoUser.makePersistent(medico);
        }
        paziente.setMedico(nuovoMedico);
        nuovoMedico.getPazienti().add(paziente);
        daoUser.makePersistent(paziente);
        daoUser.makePersistent(nuovoMedico);
    }

    @Transactional
    public List<UserDTO> getMediciDisponibiliAlCambio(String email) {
        log.info("Recupero medici disponibili al cambio per paziente con email: {}", email);
        User paziente = daoUser.findByEmail(email);
        log.info("IdType: {}", paziente.getId().getClass().getName());
        if (!paziente.getRole().equals(ERuolo.PAZIENTE)) {
            throw new IllegalArgumentException("L'utente non è un paziente");
        }
        List<User> mediciDisponibili = daoUser.findMediciDisponibiliAlCambio(paziente.getId());
        return Mapper.map(mediciDisponibili, UserDTO.class);
    }

}
