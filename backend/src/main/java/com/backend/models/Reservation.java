package com.backend.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
@Entity
public class Reservation implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    private String description;
    private int nbPlaces;
    private String lieu;
    private LocalDate dateLimite;

    @ManyToOne
    private Organisme organisme;

    @ManyToMany(mappedBy = "reservations")
    private Set<Client> clients;
}
