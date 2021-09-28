package com.backend.controller;

import com.backend.models.Client;
import com.backend.models.Organisme;
import com.backend.models.Reservation;
import com.backend.models.User;
import com.backend.repository.ClientRepository;
import com.backend.repository.OrganismeRepository;
import com.backend.repository.ReservationRepository;
import com.backend.repository.UserRepository;
import com.backend.service.BackendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:9600")
public class BackendController {

    @Autowired
    BackendService service;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    OrganismeRepository organismeRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @GetMapping(value = "/backend/{courriel}/{mdp}")
    public User login(@PathVariable("courriel") String courriel, @PathVariable("mdp") String motDePasse){
        return service.login(courriel, motDePasse);
    }

    @GetMapping(value = "/backend/type")
    public String findType(@RequestBody User user){
        return service.findType(user);
    }

    @PostMapping("/backend/client")
    public Client signupClient(@RequestBody Client client) {
        return service.signupClient(client);
    }

    @PostMapping("/backend/organisme")
    public Organisme signupOrganisme(@RequestBody Organisme organisme) {
        return service.signupOrganisme(organisme);
    }

    @PostMapping("/backend/reservation/{id}")
    public Reservation signupReservation(@RequestBody Reservation reservation, @PathVariable int id) { return service.createReservation(id, reservation);}

    @GetMapping(value = "/backend/client/{id}")
    public Client findByIdClient(@PathVariable("id") int id) {
        if (clientRepository.findById(id) != null){
            return clientRepository.findById(id);
        }
        return null;
    }

    @GetMapping(value = "/backend/organisme/{id}")
    public Organisme findByIdOrganisme(@PathVariable("id") int id) {
        if (organismeRepository.findById(id) != null){
            return organismeRepository.findById(id);
        }
        return null;
    }

    @GetMapping(value = "/backend/reservation/{id}")
    public List<Reservation> findByOrganisme(@PathVariable("id") int id) {
        if (organismeRepository.findById(id) != null){
            Organisme organisme = organismeRepository.findById(id);
            return reservationRepository.findByOrganisme(organisme);
        }
        return null;
    }

}
