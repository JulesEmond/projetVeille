package com.backend.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false, exclude = "reservations")
@Entity
public class Client extends User implements Serializable {

    @Column(unique = true)
    private String nomUtilisateur;

    private String adresse;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Set<Reservation> reservations = new HashSet<>();

}
