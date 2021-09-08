package com.backend.repository;

import com.backend.models.Organisme;
import com.backend.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository <Reservation, Integer> {

    Reservation findById(int id);

    List<Reservation> findByOrganisme(Organisme organisme);
}
