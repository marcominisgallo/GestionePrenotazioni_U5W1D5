package it.epicode.GestionePrenotazioni_U5W1D5;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utente {
    @Id
    private String username;

    private String nomeCompleto;
    private String email;
}