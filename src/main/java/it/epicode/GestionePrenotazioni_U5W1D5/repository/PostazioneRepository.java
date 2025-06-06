package it.epicode.GestionePrenotazioni_U5W1D5.repository;

import it.epicode.GestionePrenotazioni_U5W1D5.Postazione;
import it.epicode.GestionePrenotazioni_U5W1D5.TipoPostazione;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostazioneRepository extends JpaRepository<Postazione, Long> {
    List<Postazione> findByTipoAndEdificio_Citta(TipoPostazione tipo, String citta);
}