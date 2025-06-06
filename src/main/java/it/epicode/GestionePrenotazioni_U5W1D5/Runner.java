package it.epicode.GestionePrenotazioni_U5W1D5;

import it.epicode.GestionePrenotazioni_U5W1D5.repository.EdificioRepository;
import it.epicode.GestionePrenotazioni_U5W1D5.repository.PostazioneRepository;
import it.epicode.GestionePrenotazioni_U5W1D5.repository.PrenotazioneRepository;
import it.epicode.GestionePrenotazioni_U5W1D5.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Runner implements CommandLineRunner {

    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private EdificioRepository edificioRepository;
    @Autowired
    private PostazioneRepository postazioneRepository;
    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        //Utente
        System.out.println("=== INSERIMENTO UTENTE ===");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Nome completo: ");
        String nomeCompleto = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        Utente utente = new Utente(username, nomeCompleto, email);
        utenteRepository.save(utente);

        //Edificio
        System.out.println("\n=== INSERIMENTO EDIFICIO ===");
        System.out.print("Nome edificio: ");
        String nomeEdificio = scanner.nextLine();
        System.out.print("Indirizzo: ");
        String indirizzo = scanner.nextLine();
        System.out.print("Città: ");
        String citta = scanner.nextLine();
        Edificio edificio = new Edificio(null, nomeEdificio, indirizzo, citta);
        edificioRepository.save(edificio);

        //Postazione
        System.out.println("\n=== INSERIMENTO POSTAZIONE ===");
        System.out.print("Codice postazione: ");
        String codice = scanner.nextLine();
        System.out.print("Descrizione: ");
        String descrizione = scanner.nextLine();
        System.out.print("Tipo (PRIVATO, OPENSPACE, SALA_RIUNIONI): ");
        TipoPostazione tipo = TipoPostazione.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Numero massimo occupanti: ");
        int maxOccupanti = Integer.parseInt(scanner.nextLine());
        // Associa edificio (mostra elenco)
        System.out.println("Edifici disponibili:");
        List<Edificio> edifici = edificioRepository.findAll();
        edifici.forEach(e -> System.out.println(e.getId() + " - " + e.getNome()));
        System.out.print("ID edificio per la postazione: ");
        Long idEdificio = Long.parseLong(scanner.nextLine());
        Optional<Edificio> edificioSel = edificioRepository.findById(idEdificio);
        if (edificioSel.isEmpty()) {
            System.out.println("Edificio non trovato");
            return;
        }
        Postazione postazione = new Postazione(null, codice, descrizione, tipo, maxOccupanti, edificioSel.get());
        postazioneRepository.save(postazione);

        //Prenotazione
        System.out.println("\n=== INSERIMENTO PRENOTAZIONE ===");
        // Seleziona utente
        System.out.println("Utenti disponibili:");
        utenteRepository.findAll().forEach(u -> System.out.println(u.getUsername() + " - " + u.getNomeCompleto()));
        System.out.print("Username utente: ");
        String userPren = scanner.nextLine();
        Optional<Utente> utenteSel = utenteRepository.findById(userPren);
        if (utenteSel.isEmpty()) {
            System.out.println("Utente non trovato");
            return;
        }
        // Seleziona postazione
        System.out.println("Postazioni disponibili:");
        postazioneRepository.findAll().forEach(p -> System.out.println(p.getId() + " - " + p.getCodice()));
        System.out.print("ID postazione: ");
        Long idPost = Long.parseLong(scanner.nextLine());
        Optional<Postazione> postSel = postazioneRepository.findById(idPost);
        if (postSel.isEmpty()) {
            System.out.println("Postazione non trovata");
            return;
        }
        System.out.print("Data prenotazione (YYYY-MM-DD): ");
        LocalDate data = LocalDate.parse(scanner.nextLine());
        Prenotazione prenotazione = new Prenotazione(null, utenteSel.get(), postSel.get(), data);

        boolean postazioneOccupata = prenotazioneRepository.existsByPostazioneIdAndData(
                prenotazione.getPostazione().getId(), prenotazione.getData());
        boolean utenteHaPrenotazione = prenotazioneRepository.existsByUtenteUsernameAndData(
                prenotazione.getUtente().getUsername(), prenotazione.getData());

        if (postazioneOccupata || utenteHaPrenotazione) {
            System.out.println("Postazione occupata o utente già prenotato per questa data");
        } else {
            prenotazioneRepository.save(prenotazione);
            System.out.println("Prenotazione effettuata");
        }
    }
}