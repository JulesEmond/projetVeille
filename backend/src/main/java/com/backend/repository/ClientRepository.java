package com.backend.repository;

import com.backend.models.Client;
import com.backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository <Client, Integer> {

    Client findById(int id);

    Client findByCourrielIgnoreCaseAndMotDePasse(String courriel, String pwd);

    boolean existsById(int id);
}
