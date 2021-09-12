package com.backend.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@MappedSuperclass
@SequenceGenerator(name = "userSeq", initialValue = 1, allocationSize = 10000)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    private int id;

    private String courriel;
    private String motDePasse;
}
