package com.rosariodev.medik.persistence.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rosariodev.medik.model.Appuntamento;
import com.rosariodev.medik.persistence.IDAOAppuntamento;

@Repository
@Profile("mock")
public class DAOAppuntamentoMock extends DAOGenericMock<Appuntamento> implements IDAOAppuntamento {

    @Override
    public List<Appuntamento> findByMedicoId(Long medicoId) {
        List<Appuntamento> appuntamenti = new ArrayList<>();
        for (Appuntamento appuntamento : findAll()) {
            if (appuntamento.getMedico().getId().equals(medicoId)) {
                appuntamenti.add(appuntamento);
            }
        }
        return appuntamenti;
    }

    @Override
    public List<Appuntamento> findByPazienteId(Long pazienteId) {
        List<Appuntamento> appuntamenti = new ArrayList<>();
        for (Appuntamento appuntamento : findAll()) {
            if (appuntamento.getPaziente().getId().equals(pazienteId)) {
                appuntamenti.add(appuntamento);
            }
        }
        return appuntamenti;
    }

}
