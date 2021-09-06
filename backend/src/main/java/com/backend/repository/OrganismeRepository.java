package com.backend.repository;

import com.backend.models.Organisme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganismeRepository extends JpaRepository <Organisme, Integer> {

    Organisme findById(int id);
}
