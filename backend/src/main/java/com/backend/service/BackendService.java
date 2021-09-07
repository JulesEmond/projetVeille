package com.backend.service;

import com.backend.models.Client;
import com.backend.models.Organisme;
import com.backend.models.User;
import com.backend.repository.ClientRepository;
import com.backend.repository.OrganismeRepository;
import com.backend.repository.ReservationRepository;
import com.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
