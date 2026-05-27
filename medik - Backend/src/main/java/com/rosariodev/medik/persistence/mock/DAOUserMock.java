package com.rosariodev.medik.persistence.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rosariodev.medik.enums.ERuolo;
import com.rosariodev.medik.model.User;
import com.rosariodev.medik.persistence.IDAOUser;

import lombok.extern.slf4j.Slf4j;

@Repository
@Profile("mock")
@Slf4j
public class DAOUserMock extends DAOGenericMock<User> implements IDAOUser {

    @Override
    public User findByEmail(String email) {
        for (User user : findAll()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> findByRole(ERuolo ruolo) {
        List<User> listaFiltrata = new ArrayList<>();
        for (User user : findAll()) {
            if (user.getRole() == ruolo) {
                listaFiltrata.add(user);
            }
        }
        return listaFiltrata;
    }


    @Override
    public List<User> findMediciDisponibiliAlCambio(Long idPaziente) {
        List<User> listaFiltrata = new ArrayList<>();
        for (User medico : findAll()) {
            log.info("Medico scansionato: {} {}", medico.getNome(), medico.getCognome());
            boolean pazienteTrovato = false;
            if (medico.getRole() == ERuolo.MEDICO) {
                for (User paziente : medico.getPazienti()) {
                    if (paziente.getId().equals(idPaziente)) {
                        log.info("Paziente scansionato: {} {}", paziente.getNome(), paziente.getCognome());
                        pazienteTrovato = true;
                        break;
                    }
                }
                if (!pazienteTrovato) {
                    listaFiltrata.add(medico);
                }
            }
        }
        return listaFiltrata;
    }

    @Override
    public List<User> findPazientiDalCognomeMedico(Long idMedico, String cognomePaziente) {
        List<User> listaFiltrata = new ArrayList<>();
        for (User medico : findAll()) {
            if (medico.getRole() == ERuolo.MEDICO && medico.getId().equals(idMedico)) {
                for (User paziente : medico.getPazienti()) {
                    if (paziente.getCognome().equalsIgnoreCase(cognomePaziente)) {
                        listaFiltrata.add(paziente);
                    }
                }
                
            }
        }
        return listaFiltrata;
    }
    
}
