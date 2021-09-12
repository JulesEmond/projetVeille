package com.backend.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "clients")
@Entity
@SequenceGenerator(name = "reservationSeq", initialValue = 1, allocationSize = 10000)
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservationSeq")
    private int id;

    private String description;
    private int nbPlaces;
    private String lieu;
    private LocalDate dateLimite;

    @ManyToOne
    private Organisme organisme;

    @ManyToMany(mappedBy = "reservations")
    @ToString.Exclude
    private Set<Client> clients = new HashSet<>();
}
