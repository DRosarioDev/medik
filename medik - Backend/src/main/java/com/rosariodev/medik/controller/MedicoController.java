package com.rosariodev.medik.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rosariodev.medik.model.dto.NuovaRicettaDTO;
import com.rosariodev.medik.model.dto.PazienteDTO;
import com.rosariodev.medik.model.dto.RicettaDTO;
import com.rosariodev.medik.service.MedicoService;

@RestController
@RequestMapping("/api")
public class MedicoController {
    
    @Autowired
    private MedicoService medicoService;

    @GetMapping("/medici/pazienti")
    @PreAuthorize("hasRole('MEDICO')")
    public List<PazienteDTO> getPazientiByMedico(@AuthenticationPrincipal UserDetails userDetails) {
        return medicoService.getPazientiByMedico(userDetails.getUsername());
    }

    @GetMapping("/pazienti")
    @PreAuthorize("hasRole('MEDICO')")
    public List<PazienteDTO> getPazientiByMedicoAndCognome(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String cognome) {
        return medicoService.getPazientiByCognome(userDetails.getUsername(), cognome);
    }

    @GetMapping("/medici/{idPaziente}/ricette")
    @PreAuthorize("hasRole('MEDICO')")
    public List<RicettaDTO> getRicetteByPaziente(@PathVariable Long idPaziente, @AuthenticationPrincipal UserDetails userDetails) {
        return medicoService.getRicetteByPaziente(idPaziente, userDetails.getUsername());
    }

    @PostMapping("/medici/pazienti/{idPaziente}/nuova-ricetta")
    @PreAuthorize("hasRole('MEDICO')")
    public void creaRicetta(@PathVariable Long idPaziente, @RequestBody NuovaRicettaDTO ricettaDTO, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        medicoService.creaRicetta(idPaziente, email, ricettaDTO);
    }

}