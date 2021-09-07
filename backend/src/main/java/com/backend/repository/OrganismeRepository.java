package com.backend.repository;

import com.backend.models.Organisme;
import com.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganismeRepository extends JpaRepository <Organisme, Integer> {

    Organisme findById(int id);

    Organisme findByCourrielIgnoreCaseAndMotDePasse(String courriel, String pwd);

    boolean existsById(int id);
}
