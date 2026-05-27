package com.rosariodev.medik.persistence.mock;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rosariodev.medik.enums.ERuolo;
import com.rosariodev.medik.enums.EUrgenza;
import com.rosariodev.medik.model.Appuntamento;
import com.rosariodev.medik.model.Ricetta;
import com.rosariodev.medik.model.User;

public class RepositoryMock extends RepositoryGenericMock {

        private static final RepositoryMock singleton = new RepositoryMock();
        
    private BCryptPasswordEncoder passwordEncoder;

        private RepositoryMock() {
            passwordEncoder = new BCryptPasswordEncoder();
            User medico1 = new User("Mario", "Rossi", "a@medico.com", passwordEncoder.encode("1234"), "MARROS",
                            LocalDate.of(1980, 1, 1), ERuolo.MEDICO);
                super.saveOrUpdate(medico1);
                User medico2 = new User("Luigi", "Verdi", "b@medico.com", passwordEncoder.encode("5678"), "LUIVER",
                                LocalDate.of(1980, 1, 1),
                                ERuolo.MEDICO);
                super.saveOrUpdate(medico2);
                User medico3 = new User("Giovanni", "Bianchi", "c@medico.com", passwordEncoder.encode("9012"), "GIOBIA",
                                LocalDate.of(1985, 7, 12),
                                ERuolo.MEDICO);
                super.saveOrUpdate(medico3);

                User paziente1 = new User("Anna", "Neri", "a@paziente.com", passwordEncoder.encode("1234"), "ANNNER",
                                LocalDate.of(1990, 5, 20), ERuolo.PAZIENTE);
                super.saveOrUpdate(paziente1);
                User paziente2 = new User("Marco", "Bianchi", "b@paziente.com", passwordEncoder.encode("5678")  , "MARBIA",
                                LocalDate.of(1985, 10, 15), ERuolo.PAZIENTE);
                super.saveOrUpdate(paziente2);
                User paziente3 = new User("Giulia", "Rossi", "c@paziente.com", passwordEncoder.encode("9012"), "GIOROS",
                                LocalDate.of(1990, 5, 20), ERuolo.PAZIENTE);
                super.saveOrUpdate(paziente3);

                medico1.getPazienti().add(paziente1);
                medico1.getPazienti().add(paziente2);
                medico2.getPazienti().add(paziente3);

                paziente1.setMedico(medico1);
                paziente2.setMedico(medico1);
                paziente3.setMedico(medico2);

                Ricetta a11 = new Ricetta("Ricetta 1", LocalDate.of(2023, 1, 1), EUrgenza.BASSA, medico1, paziente1);
                super.saveOrUpdate(a11);
                paziente1.getRicettePaziente().add(a11);
                medico1.getRicetteEmesse().add(a11);

                Ricetta a12 = new Ricetta("Ricetta 2", LocalDate.of(2023, 4, 2), EUrgenza.MEDIA, medico1, paziente1);
                super.saveOrUpdate(a12);
                paziente1.getRicettePaziente().add(a12);
                medico1.getRicetteEmesse().add(a12);

                Ricetta a21 = new Ricetta("Ricetta 3", LocalDate.of(2023, 5, 1), EUrgenza.ALTA, medico1, paziente2);
                super.saveOrUpdate(a21);
                paziente2.getRicettePaziente().add(a21);
                medico1.getRicetteEmesse().add(a21);

                Ricetta a31 = new Ricetta("Ricetta 4", LocalDate.of(2024, 5, 1), EUrgenza.BASSA, medico2, paziente3);
                super.saveOrUpdate(a31);
                paziente3.getRicettePaziente().add(a31);
                medico2.getRicetteEmesse().add(a31);

                Appuntamento app1 = new Appuntamento("Anna Neri", LocalDateTime.of(2026, 4, 30, 10, 0),
                                LocalDateTime.of(2026, 4, 30, 11, 0), paziente1, medico1);
                super.saveOrUpdate(app1);
                medico1.getAppuntamentiMedico().add(app1);
                paziente1.getAppuntamentiPaziente().add(app1);

                Appuntamento app12 = new Appuntamento("Anna Neri", LocalDateTime.of(2026, 4, 15, 11, 0),
                                LocalDateTime.of(2026, 4, 15, 12, 0), paziente1, medico1);
                super.saveOrUpdate(app12);
                medico1.getAppuntamentiMedico().add(app12);
                paziente1.getAppuntamentiPaziente().add(app12);

                Appuntamento app2 = new Appuntamento("Giulia Rossi", LocalDateTime.of(2026, 4, 20, 14, 30),
                                LocalDateTime.of(2026, 4, 20, 15, 30), paziente3, medico2);
                super.saveOrUpdate(app2);
                medico2.getAppuntamentiMedico().add(app2);
                paziente3.getAppuntamentiPaziente().add(app2);

                Appuntamento app3 = new Appuntamento("Marco Bianchi", LocalDateTime.of(2026, 4, 10, 9, 0),
                                LocalDateTime.of(2026, 4, 10, 10, 0), paziente2, medico3);
                super.saveOrUpdate(app3);
                medico3.getAppuntamentiMedico().add(app3);
                paziente2.getAppuntamentiPaziente().add(app3);
                Appuntamento app4 = new Appuntamento("Anna Neri", LocalDateTime.of(2026, 5, 5, 11, 0),
                                LocalDateTime.of(2026, 5, 5, 12, 0), paziente1, medico1);
                super.saveOrUpdate(app4);
                medico1.getAppuntamentiMedico().add(app4);
                paziente1.getAppuntamentiPaziente().add(app4);

        }

        public static RepositoryMock getInstance() {
                return singleton;
        }

}
