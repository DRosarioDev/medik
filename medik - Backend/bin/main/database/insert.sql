-- ── 1. UTENTI ───────────────────────────────────────────────
INSERT INTO users (nome, cognome, email, password, cf, data_nascita, role) VALUES
-- Medici
('Mario',    'Rossi',   'a@medico.com',   '$2b$10$btrzQicSLB0kcm3Rkz/yxOjCTGfctTIn.auEsvvUXfRmm.OjU0FKm', 'MRORSS80A01F839X', '1980-01-01', 'MEDICO'),
('Luigi',    'Verdi',   'b@medico.com',   '$2a$10$3CmeIJlLG3SkCZGH34oD8.1MisnO0KH3mjFH7USDfxZ0iNh72w3aW', 'VRDLGU80A01F839Y', '1980-01-01', 'MEDICO'),
('Giovanni', 'Bianchi', 'c@medico.com',   '$2a$10$TnknLW6NVNYo3MrJJyNLZ.arBDKt/2sVJxR7ZLn6pJFCeDmFr6WgC', 'BNCGNN85L12F839Z', '1985-07-12', 'MEDICO'),
-- Pazienti
('Anna',     'Neri',    'a@paziente.com', '$2b$10$lIyYVWr4pAE7tfSuBG/Hk.N.3sCpazWt/2lSBD4cG6uT66CjNnGB2', 'NRANNA90E60F839A', '1990-05-20', 'PAZIENTE'),
('Marco',    'Bianchi', 'b@paziente.com', '$2a$10$3CmeIJlLG3SkCZGH34oD8.1MisnO0KH3mjFH7USDfxZ0iNh72w3aW', 'BNCMRC85R15F839B', '1985-10-15', 'PAZIENTE'),
('Giulia',   'Rossi',   'c@paziente.com', '$2a$10$TnknLW6NVNYo3MrJJyNLZ.arBDKt/2sVJxR7ZLn6pJFCeDmFr6WgC', 'RSSGLL90E60F839C', '1990-05-20', 'PAZIENTE');
 
-- IDs attesi dopo l'insert (serial):
--   1 = Mario Rossi    (MEDICO)
--   2 = Luigi Verdi    (MEDICO)
--   3 = Giovanni Bianchi (MEDICO)
--   4 = Anna Neri      (PAZIENTE)  → medico: Mario Rossi   (1)
--   5 = Marco Bianchi  (PAZIENTE)  → medico: Mario Rossi   (1)
--   6 = Giulia Rossi   (PAZIENTE)  → medico: Luigi Verdi   (2)
 
 
-- ── 2. RICETTE ──────────────────────────────────────────────
INSERT INTO ricette (descrizione, data, urgenza, medico_id, paziente_id) VALUES
-- Anna Neri (paziente 4) – medico Mario Rossi (1)
('Amoxicillina 875 mg – ciclo 7 giorni', "2026-06-02 09:00:00.000"     'BASSA', 1, 4),
('Ibuprofene 600 mg al bisogno',         "2026-06-02 11:00:00.000"      'MEDIA', 1, 4),
-- Marco Bianchi (paziente 5) – medico Mario Rossi (1)
('Metformina 500 mg – 1 cp a colazione', "2026-06-02 12:00:00.000"    'ALTA',  1, 5),
-- Giulia Rossi (paziente 6) – medico Luigi Verdi (2)
('Loratadina 10 mg – 1 cp/die per 30 giorni', "2026-06-02 14:00:00.000" 'BASSA', 2, 6);
 
 
-- ── 3. APPUNTAMENTI ─────────────────────────────────────────
INSERT INTO appuntamenti (titolo, data_inizio, data_fine, paziente_id, medico_id) VALUES
-- Anna Neri (4) con Mario Rossi (1)
('Anna Neri',    '2026-06-02 09:00:00', '2026-06-02 10:00:00', 4, 1),
('Anna Neri',    '2026-06-16 11:00:00', '2026-06-16 12:00:00', 4, 1),
-- Marco Bianchi (5) con Mario Rossi (1)
('Marco Bianchi','2026-06-05 10:00:00', '2026-06-05 11:00:00', 5, 1),
-- Giulia Rossi (6) con Luigi Verdi (2)
('Giulia Rossi', '2026-06-10 14:00:00', '2026-06-10 15:00:00', 6, 2),
-- Anna Neri (4) con Giovanni Bianchi (3) – visita specialistica
('Anna Neri',    '2026-06-20 09:00:00', '2026-06-20 10:00:00', 4, 3);
 
