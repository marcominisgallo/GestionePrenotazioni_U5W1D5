package it.epicode.GestionePrenotazioni_U5W1D5;

import it.epicode.GestionePrenotazioni_U5W1D5.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrenotazioneService {
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    public boolean prenota(Prenotazione prenotazione) {
        boolean postazioneOccupata = prenotazioneRepository.existsByPostazioneIdAndData(
                prenotazione.getPostazione().getId(), prenotazione.getData());
        boolean utenteHaPrenotazione = prenotazioneRepository.existsByUtenteUsernameAndData(
                prenotazione.getUtente().getUsername(), prenotazione.getData());

        if (postazioneOccupata || utenteHaPrenotazione) {
            return false;
        }
        prenotazioneRepository.save(prenotazione);
        return true;
    }
}