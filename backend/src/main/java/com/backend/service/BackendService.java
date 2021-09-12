package com.backend.service;

import com.backend.models.Client;
import com.backend.models.Organisme;
import com.backend.models.Reservation;
import com.backend.models.User;
import com.backend.repository.ClientRepository;
import com.backend.repository.OrganismeRepository;
import com.backend.repository.ReservationRepository;
import com.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BackendService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrganismeRepository organismeRepository;

    @Autowired
    private ReservationRepository reservationRepository;


    public Client signupClient (Client client){
        clientRepository.save(client);
        return client;
    }

    public Organisme signupOrganisme (Organisme organisme){
        organismeRepository.save(organisme);
        return organisme;
    }


    public User login (String courriel, String motDePasse){
        Client client = clientRepository.findByCourrielIgnoreCaseAndMotDePasse(courriel, motDePasse);
        if(client != null) {
            return client;
        } else {
            Organisme organisme = organismeRepository.findByCourrielIgnoreCaseAndMotDePasse(courriel, motDePasse);
            return organisme;
        }
    }

    public String findType(User user){
        if(user != null){
            if(clientRepository.existsById(user.getId())){
                return "client";
            } else if (organismeRepository.existsById(user.getId())){
                return "organisme";
            } else{
                return "erreur";
            }
        }
        return "invalide";
    }

    public Reservation createReservation(int idOrg, Reservation reservation){
        Organisme organisme = organismeRepository.findById(idOrg);
        if(organisme != null){
            reservation.setOrganisme(organisme);
            reservationRepository.save(reservation);
            return reservation;
        } else {
            return null;
        }
    }
    
    public Client clientReservation(int idClient, int idReservation){
        Client client = clientRepository.findById(idClient);
        Reservation reservation = reservationRepository.findById(idReservation);
        if (client != null && reservation != null){
            if(client.getReservations().contains(reservation)){
                return null;
            }
            else if(reservation.getDateLimite().isAfter(LocalDate.now())) {
                client.getReservations().add(reservation);
                reservation.getClients().add(client);
                reservation.setNbPlaces(reservation.getNbPlaces() - 1);
                clientRepository.save(client);
                reservationRepository.save(reservation);
                return client;
            }
        }
        return null;
    }

    public Client clientAnnulation(int idClient, int idReservation){
        Client client = clientRepository.findById(idClient);
        Reservation reservation = reservationRepository.findById(idReservation);
        if (client != null && reservation != null){
            if(!client.getReservations().contains(reservation)){
                return null;
            }
            else if(reservation.getDateLimite().isAfter(LocalDate.now())) {
                client.getReservations().remove(reservation);
                reservation.getClients().remove(client);
                reservation.setNbPlaces(reservation.getNbPlaces() + 1);
                clientRepository.save(client);
                reservationRepository.save(reservation);
                return client;
            }
        }
        return null;
    }

    public List<Reservation> listeReservationDispo(int idClient){
        Client client = clientRepository.findById(idClient);
        if(client == null){
            return null;
        }
        List<Reservation> reservations = reservationRepository.findAllByDateLimiteAfter(LocalDate.now());
        for (Reservation reservation : reservations){
            if(reservation.getClients().contains(client)){
                reservations.remove(reservation);
            }
        }
        return reservations;
    }

}
