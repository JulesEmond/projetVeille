package com.backend.models;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Data
@MappedSuperclass
public class User implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    private String courriel;
    private String motDePasse;
}
