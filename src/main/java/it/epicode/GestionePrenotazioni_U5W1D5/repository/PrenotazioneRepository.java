package it.epicode.GestionePrenotazioni_U5W1D5.repository;

import it.epicode.GestionePrenotazioni_U5W1D5.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
}