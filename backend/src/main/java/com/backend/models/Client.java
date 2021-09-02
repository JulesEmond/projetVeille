package com.backend.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
public class Client extends User implements Serializable {

    @Column(unique = true)
    private String nomUtilisateur;

    private String adresse;

    @ManyToMany
    private Set<Reservation> reservations;

}
