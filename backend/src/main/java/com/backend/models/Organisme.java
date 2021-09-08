package com.backend.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
public class Organisme extends User implements Serializable {

    @Column(unique = true)
    @NotNull
    private String nomOrganisme;

    private String numTelephone;

}
