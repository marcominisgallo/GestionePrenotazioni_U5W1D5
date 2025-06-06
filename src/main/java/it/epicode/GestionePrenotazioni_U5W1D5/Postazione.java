package it.epicode.GestionePrenotazioni_U5W1D5;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Postazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String codice;

    private String descrizione;

    @Enumerated(EnumType.STRING)
    private TipoPostazione tipo;

    private int maxOccupanti;

    @ManyToOne
    private Edificio edificio;
}