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
        if (!organismeRepository.existsByCourriel(client.getCourriel())) {
            clientRepository.save(client);
            return client;
        }
        return null;
    }

    public Organisme signupOrganisme (Organisme organisme){
        if (!clientRepository.existsByCourriel(organisme.getCourriel())) {
            organismeRepository.save(organisme);
            return organisme;
        }
        return null;
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
        if(organisme != null && reservation.getDateLimite().isAfter(LocalDate.now())){
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
    public List<Reservation> listeReservationActuelle(int idClient){
        Client client = clientRepository.findById(idClient);
        if(client == null){
            return null;
        }
        List<Reservation> reservations = reservationRepository.findAllByDateLimiteAfter(LocalDate.now());
        for (int i = 0; i<reservations.size(); i++){
            if(!client.getReservations().contains(reservations.get(i))){
                reservations.remove(i);
                i--;
            }
        }
        return reservations;
    }

    public List<Reservation> listeReservationOrganisme(int idOrganisme){
        Organisme organisme = organismeRepository.findById(idOrganisme);
        if (organisme != null){
            List<Reservation> reservations = reservationRepository.findByOrganisme(organisme);
            return reservations;
        }
        return null;
    }

}
