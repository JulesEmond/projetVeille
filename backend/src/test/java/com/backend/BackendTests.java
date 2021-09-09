package com.backend;

import com.backend.models.Client;
import com.backend.models.Organisme;
import com.backend.models.Reservation;
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

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

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
        Reservation r1 = new Reservation();
        Reservation r2 = new Reservation();

        //id = 1
        c.setCourriel("exemple1@exemple.com");
        c.setMotDePasse("mdp123");
        c.setNomUtilisateur("exemple1");
        c.setAdresse("123 rue test, Montreal");

        //id = 2
        o.setCourriel("exemple2@exemple.com");
        o.setMotDePasse("mdp456");
        o.setNomOrganisme("comp1");
        o.setNumTelephone("555-555-5555");

        //id = 3
        r1.setDateLimite(LocalDate.now().plusDays(14));
        r1.setDescription("Spectacle dans les rues");
        r1.setLieu("Centre-ville");
        r1.setNbPlaces(2500);
        r1.setOrganisme(o);

        //id = 4
        r2.setDateLimite(LocalDate.of(2021, 5, 25));
        r2.setDescription("Collecte de déchets");
        r2.setLieu("Bord de l'eau");
        r2.setNbPlaces(150);
        r2.setOrganisme(o);

        userRepository.saveAll(Arrays.asList(c, o));
        reservationRepository.saveAll(Arrays.asList(r1, r2));
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


    @Test
    public void createReservationTest(){
        Organisme o = organismeRepository.findById(2);
        assertEquals(2, reservationRepository.findByOrganisme(o).size());

        Reservation r = new Reservation();
        r.setDateLimite(LocalDate.now().plusDays(7));
        r.setDescription("Match des Allouettes");
        r.setLieu("McGill");
        r.setNbPlaces(15000);

        assertEquals(r, service.createReservation(2, r));
        assertEquals(3, reservationRepository.findByOrganisme(o).size());
        assertEquals(3, reservationRepository.findAll().size());
    }

    @Test
    public void clientReservationTest(){
        assertNull(service.clientReservation(14, 10));
        assertNull(service.clientReservation(1,4));
        assertTrue(service.clientReservation(1,3).getReservations().contains(reservationRepository.findById(3)));
        assertTrue(reservationRepository.findById(3).getClients().contains(clientRepository.findById(1)));
    }
}
