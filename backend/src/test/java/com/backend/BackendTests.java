package com.backend;

import com.backend.models.Client;
import com.backend.models.Organisme;
import com.backend.models.User;
import com.backend.repository.ClientRepository;
import com.backend.repository.OrganismeRepository;
import com.backend.repository.ReservationRepository;
import com.backend.repository.UserRepository;
import com.backend.service.BackendService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ComponentScan(basePackages = {"com.backend.service"})
public class BackendTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private OrganismeRepository organismeRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BackendService service;


    @BeforeAll
    public void insertData(){

        Client c = new Client();
        Organisme o = new Organisme();

        c.setCourriel("exemple1@exemple.com");
        c.setMotDePasse("mdp123");
        c.setNomUtilisateur("exemple1");
        c.setAdresse("123 rue test, Montreal");

        o.setCourriel("exemple2@exemple.com");
        o.setMotDePasse("mdp456");
        o.setNomOrganisme("comp1");
        o.setNumTelephone("555-555-5555");

        userRepository.saveAll(Arrays.asList(c, o));

    }

    @Test
    public void loginTest() {
        assertNull(service.login("bla@bla.com", "mdp789"));
        assertNotNull(service.login("Exemple1@exemple.com", "mdp123"));
        assertNull(service.login("exemple2@exemple.com", "Mdp456"));
    }

    @Test
    public void findTypeTest(){
        Client client = clientRepository.findById(1);
        Organisme organisme = organismeRepository.findById(2);
        Client empty = clientRepository.findById(3);

        assertEquals("client", service.findType(client));
        assertEquals("organisme", service.findType(organisme));
        assertEquals("invalide", service.findType(empty));

    }

    @Test
    public void signupClientTest(){
        Client c = new Client();

        c.setCourriel("exemple3@exemple.com");
        c.setMotDePasse("exe321");
        c.setNomUtilisateur("exemple3");
        c.setAdresse("345 avenue exemple, Laval");

        assertEquals(c, service.signupClient(c));
        assertNotNull(clientRepository.findByCourrielIgnoreCaseAndMotDePasse(c.getCourriel(), c.getMotDePasse()));

    }

    @Test
    public void signupOrganismeTest(){
        Organisme o = new Organisme();
        o.setCourriel("exemple4@exemple.com");
        o.setMotDePasse("exe654");
        o.setNomOrganisme("comp2");
        o.setNumTelephone("777-777-7777");

        assertEquals(o, service.signupOrganisme(o));
        assertNotNull(organismeRepository.findByCourrielIgnoreCaseAndMotDePasse(o.getCourriel(), o.getMotDePasse()));
    }

}
