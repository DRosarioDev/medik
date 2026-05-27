package com.rosariodev.medik.persistence;

import java.util.List;

import com.rosariodev.medik.model.Appuntamento;

public interface IDAOAppuntamento extends IDAOGeneric<Appuntamento> {
    
    List<Appuntamento> findByMedicoId(Long medicoId);
    List<Appuntamento> findByPazienteId(Long pazienteId);

}
