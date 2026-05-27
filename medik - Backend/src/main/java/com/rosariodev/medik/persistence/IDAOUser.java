package com.rosariodev.medik.persistence;

import java.util.List;

import com.rosariodev.medik.enums.ERuolo;
import com.rosariodev.medik.model.User;

public interface IDAOUser extends IDAOGeneric<User>{
    
    User findByEmail(String email);
    List<User> findByRole(ERuolo ruolo);
    List<User> findMediciDisponibiliAlCambio(Long idPaziente);
    List<User> findPazientiDalCognomeMedico(Long idMedico, String cognomePaziente);

}
