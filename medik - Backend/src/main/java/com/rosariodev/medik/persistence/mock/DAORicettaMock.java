package com.rosariodev.medik.persistence.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.rosariodev.medik.model.Ricetta;
import com.rosariodev.medik.persistence.IDAORicetta;

@Repository
@Profile("mock")
public class DAORicettaMock extends DAOGenericMock<Ricetta> implements IDAORicetta {
    
    @Override
    public List<Ricetta> findByIdPaziente(Long idPaziente) {
        List<Ricetta> ricettePaziente = new ArrayList<>();
        for (Ricetta ricetta : findAll()) {
            if (ricetta.getPaziente().getId().equals(idPaziente)) {
                ricettePaziente.add(ricetta);
            }
        }
        return ricettePaziente;

    }
    
    
}
