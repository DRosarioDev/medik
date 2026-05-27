package com.rosariodev.medik.persistence;

import java.util.List;

import com.rosariodev.medik.model.Ricetta;

public interface IDAORicetta extends IDAOGeneric<Ricetta> {
    
    List<Ricetta> findByIdPaziente(Long idPaziente);

}
