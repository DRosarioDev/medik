package com.rosariodev.medik.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rosariodev.medik.model.Ricetta;
import com.rosariodev.medik.model.User;
import com.rosariodev.medik.model.dto.NuovaRicettaDTO;
import com.rosariodev.medik.model.dto.PazienteDTO;
import com.rosariodev.medik.model.dto.RicettaDTO;
import com.rosariodev.medik.persistence.IDAORicetta;
import com.rosariodev.medik.persistence.IDAOUser;
import com.rosariodev.medik.util.Mapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MedicoService {

    @Autowired
    private IDAOUser daoUser;

    @Autowired
    private IDAORicetta daoRicette;

    @Transactional
    public List<PazienteDTO> getPazientiByMedico(String email) {
        User medico = daoUser.findByEmail(email);
        return Mapper.map(medico.getPazienti(), PazienteDTO.class);

    }

    @Transactional
    public List<PazienteDTO> getPazientiByCognome(String email, String cognomePaziente) {
        User medico = daoUser.findByEmail(email);
        List<User> pazienti = daoUser.findPazientiDalCognomeMedico(medico.getId(), cognomePaziente);
        return Mapper.map(pazienti, PazienteDTO.class);
        
    }

    @Transactional
    public List<RicettaDTO> getRicetteByPaziente(Long idPaziente, String emailMedico) {
        User medico = daoUser.findByEmail(emailMedico);
        User paziente = daoUser.findById(idPaziente);

        if (!medico.getPazienti().contains(paziente)) {
            throw new IllegalArgumentException("Paziente non associato al medico");
        }
        log.info("Recupero ricette per paziente id: {} associato al medico: {}", idPaziente, emailMedico);

        List<Ricetta> ricette = daoRicette.findByIdPaziente(idPaziente);

        if (ricette.isEmpty()) {
            log.info("Nessuna ricetta trovata per paziente id: {}", idPaziente);
        }
        log.info("Ricette trovate: {}", ricette.size());
        return Mapper.map(ricette, RicettaDTO.class);
    }

    @Transactional
    public void creaRicetta(Long idPaziente, String emailMedico, NuovaRicettaDTO ricettaDTO) {
        User medico = daoUser.findByEmail(emailMedico);
        User paziente = daoUser.findById(idPaziente);
        if (!medico.getPazienti().contains(paziente)) {
            throw new IllegalArgumentException("Paziente non associato al medico");
        }
        log.info("Creazione ricetta per paziente id: {} associato al medico: {}", idPaziente, emailMedico);
        Ricetta ricetta = new Ricetta(ricettaDTO.getDescrizione(), LocalDate.now(), ricettaDTO.getUrgenza(), medico,
                paziente);

        medico.getRicetteEmesse().add(ricetta);
        paziente.getRicettePaziente().add(ricetta);

        daoRicette.makePersistent(ricetta);
        log.info("Ricetta creata con id: {}", ricetta.getId());
    }

}
