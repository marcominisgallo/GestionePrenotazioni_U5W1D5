package it.epicode.GestionePrenotazioni_U5W1D5.repository;

import it.epicode.GestionePrenotazioni_U5W1D5.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

    boolean existsByPostazioneIdAndData(Long postazioneId, LocalDate data);
    boolean existsByUtenteUsernameAndData(String username, LocalDate data);
}