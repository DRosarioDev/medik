package com.rosariodev.medik.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rosariodev.medik.model.dto.MedicoDTO;
import com.rosariodev.medik.model.dto.PazienteDTO;
import com.rosariodev.medik.model.dto.RicettaDTO;
import com.rosariodev.medik.model.dto.UserDTO;
import com.rosariodev.medik.service.PazienteService;

@RestController
@RequestMapping("/api")
public class PazienteController {
    
    @Autowired
    private PazienteService pazienteService;

    @GetMapping("/pazienti/{idPaziente}")
    @PreAuthorize("hasRole('MEDICO')")
    public PazienteDTO getPazienteById(@PathVariable Long idPaziente) {
        return pazienteService.getPazienteById(idPaziente);
    }

    @GetMapping("/paziente/ricette")
    @PreAuthorize("hasRole('PAZIENTE')")
    public List<RicettaDTO> getAllRicetteByPaziente(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        return pazienteService.getAllRicetteByPaziente(email);
    }

    @GetMapping("/paziente/medico")
    @PreAuthorize("hasRole('PAZIENTE')")
    public MedicoDTO getMedicoPaziente(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        return pazienteService.getMedicoPaziente(email);
    }

    @PutMapping("/paziente/cambio-medico")
    @PreAuthorize("hasRole('PAZIENTE')")
    public void cambiaMedico(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Long idNuovoMedico) {
        String email = userDetails.getUsername();
        pazienteService.cambioMedico(email, idNuovoMedico);
    }

    @GetMapping("/paziente/medici-disponibili")
    @PreAuthorize("hasRole('PAZIENTE')")
    public List<UserDTO> getMediciDisponibiliAlCambio(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        return pazienteService.getMediciDisponibiliAlCambio(email);
    }

}