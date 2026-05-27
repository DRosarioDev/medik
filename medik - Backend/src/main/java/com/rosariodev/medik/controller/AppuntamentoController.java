package com.rosariodev.medik.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rosariodev.medik.model.dto.AppuntamentoDTO;
import com.rosariodev.medik.service.AppuntamentoService;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@Slf4j
public class AppuntamentoController {

    @Autowired
    private AppuntamentoService appuntamentoService;

    @GetMapping("/appuntamenti")
    public List<AppuntamentoDTO> getAppuntamenti(@AuthenticationPrincipal UserDetails userDetails) {
        return appuntamentoService.findAll(userDetails.getUsername());
    }

    @PostMapping("/appuntamenti/nuovo")
    public void creaAppuntamento(@RequestBody AppuntamentoDTO dto, @AuthenticationPrincipal UserDetails userDetails) {
        log.info("DTO classe: {}", dto.getClass().getName());
        log.info("Titolo: '{}'", dto.getTitolo());
        log.info("DTO completo: {}", dto);
        appuntamentoService.creaAppuntamento(dto, userDetails.getUsername());
    }

    @GetMapping("/appuntamenti/disponibilita")
    public List<LocalDateTime> getDisponibilita(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        return appuntamentoService.getDisponibilitaMedico(email, data);
    }

    @GetMapping("/appuntamenti/{id}")
    public AppuntamentoDTO getAppuntamentoByID(@PathVariable Long id){
        return this.appuntamentoService.getAppuntamentoById(id);
    }

    @PutMapping("appuntamenti/modifica/{id}")
    public void mofificaAppuntamento(@PathVariable Long id, @RequestBody String titolo, @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        this.appuntamentoService.modificaAppuntamento(id, email, titolo);;
    }

    @DeleteMapping("/appuntamenti/elimina/{id}")
    public void eliminaAppuntamento(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        this.appuntamentoService.eliminaAppuntamento(id, email);
    }

    @GetMapping("/appuntamenti/prenotazione-vicina")
    public List<LocalDateTime> prenotaAppuntamentoVicino(@AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        return this.appuntamentoService.getPrenotazioneVicina(email);
    }


}