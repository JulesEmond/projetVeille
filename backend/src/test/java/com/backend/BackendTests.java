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

    private int idClient;
    private int idOrganisme;
    private int idReservation1;
    private int idReservation2;


    @BeforeAll
    public void insertData(){
        //Arrange initial (valeurs de base utilisées dans tous les tests)
        Client c = new Client();
        Organisme o = new Organisme();
        Reservation r1 = new Reservation();
        Reservation r2 = new Reservation();

        c.setCourriel("exemple1@exemple.com");
        c.setMotDePasse("mdp123");
        c.setNomUtilisateur("exemple1");
        c.setAdresse("123 rue test, Montreal");


        o.setCourriel("exemple2@exemple.com");
        o.setMotDePasse("mdp456");
        o.setNomOrganisme("comp1");
        o.setNumTelephone("555-555-5555");


        r1.setDateLimite(LocalDate.now().plusDays(14));
        r1.setDescription("Spectacle dans les rues");
        r1.setLieu("Centre-ville");
        r1.setNbPlaces(2500);
        r1.setOrganisme(o);

        r2.setDateLimite(LocalDate.of(2021, 5, 25));
        r2.setDescription("Collecte de déchets");
        r2.setLieu("Bord de l'eau");
        r2.setNbPlaces(150);
        r2.setOrganisme(o);

        userRepository.saveAll(Arrays.asList(c, o));
        reservationRepository.saveAll(Arrays.asList(r1, r2));

        idClient = c.getId();
        idOrganisme = o.getId();
        idReservation1 = r1.getId();
        idReservation2 = r2.getId();
    }

    @Test
    public void loginTest() {
        //Act et Assert
        assertNull(service.login("bla@bla.com", "mdp789"));
        assertNotNull(service.login("Exemple1@exemple.com", "mdp123"));
        assertNull(service.login("exemple2@exemple.com", "Mdp456"));
    }

    @Test
    public void findTypeTest(){
        //Arrange
        Client client = clientRepository.findById(idClient);
        Organisme organisme = organismeRepository.findById(idOrganisme);
        Client empty = clientRepository.findById(400);

        //Act et Assert
        assertEquals("client", service.findType(client));
        assertEquals("organisme", service.findType(organisme));
        assertEquals("invalide", service.findType(empty));

    }

    @Test
    public void signupClientTest(){
        //Arrange
        Client c = new Client();
        c.setCourriel("exemple3@exemple.com");
        c.setMotDePasse("exe321");
        c.setNomUtilisateur("exemple3");
        c.setAdresse("345 avenue exemple, Laval");

        //Act et Assert
        assertEquals(c, service.signupClient(c));
        assertNotNull(clientRepository.findByCourrielIgnoreCaseAndMotDePasse(c.getCourriel(), c.getMotDePasse()));

    }

    @Test
    public void signupOrganismeTest(){
        //Arrange
        Organisme o = new Organisme();
        o.setCourriel("exemple4@exemple.com");
        o.setMotDePasse("exe654");
        o.setNomOrganisme("comp2");
        o.setNumTelephone("777-777-7777");

        //Act et Assert
        assertEquals(o, service.signupOrganisme(o));
        assertNotNull(organismeRepository.findByCourrielIgnoreCaseAndMotDePasse(o.getCourriel(), o.getMotDePasse()));
    }


    @Test
    public void createReservationTest(){
        //Arrange
        Organisme o = organismeRepository.findById(idOrganisme);
        assertEquals(2, reservationRepository.findByOrganisme(o).size());

        Reservation r = new Reservation();
        r.setDateLimite(LocalDate.now().plusDays(7));
        r.setDescription("Match des Allouettes");
        r.setLieu("McGill");
        r.setNbPlaces(15000);

        //Act
        assertEquals(r, service.createReservation(idOrganisme, r));

        //Assert
        assertEquals(3, reservationRepository.findByOrganisme(o).size());
        assertEquals(3, reservationRepository.findAll().size());
    }

    @Test
    public void clientReservationTest(){
        //Act et Assert
        assertNull(service.clientReservation(140, 101));
        assertNull(service.clientReservation(idClient,idReservation2));
        assertTrue(service.clientReservation(idClient,idReservation1).getReservations().contains(reservationRepository.findById(idReservation1)));
        assertTrue(reservationRepository.findById(idReservation1).getClients().contains(clientRepository.findById(idClient)));
        assertNull(service.clientReservation(idClient,idReservation1));
        assertEquals(2499, reservationRepository.findById(idReservation1).getNbPlaces());
    }

    @Test
    public void clientAnnulationTest(){
        //Act
        service.clientReservation(idClient,idReservation1);

        //Assert
        assertEquals(2499, reservationRepository.findById(idReservation1).getNbPlaces());
        assertNull(service.clientAnnulation(140, 101));
        assertNull(service.clientAnnulation(idClient,idReservation2));
        assertFalse(service.clientAnnulation(idClient,idReservation1).getReservations().contains(reservationRepository.findById(idReservation1)));
        assertFalse(reservationRepository.findById(idReservation1).getClients().contains(clientRepository.findById(idClient)));
        assertNull(service.clientAnnulation(idClient,idReservation1));
        assertEquals(2500, reservationRepository.findById(idReservation1).getNbPlaces());
    }

    @Test
    public void listeReservationDispoTest(){
        //Arrange
        Reservation r = new Reservation();
        r.setDateLimite(LocalDate.now().plusDays(7));
        r.setDescription("Match des Allouettes");
        r.setLieu("McGill");
        r.setNbPlaces(15000);

        //Act
        service.createReservation(idOrganisme, r);

        //Assert
        assertEquals(3, reservationRepository.findAll().size());
        assertNull(service.listeReservationDispo(140));
        assertEquals(2, service.listeReservationDispo(idClient).size());

        //Act
        service.clientReservation(idClient,idReservation1);

        //Assert
        assertEquals(1, service.listeReservationDispo(idClient).size());

        //Act
        service.clientAnnulation(idClient,idReservation1);

        //Assert
        assertEquals(2, service.listeReservationDispo(idClient).size());
    }

    @Test
    public void listeReservationActuelleTest() {
        //Arrange
        Reservation r = new Reservation();
        r.setDateLimite(LocalDate.now().plusDays(7));
        r.setDescription("Match des Allouettes");
        r.setLieu("McGill");
        r.setNbPlaces(15000);

        //Act
        service.createReservation(idOrganisme, r);
        int idRes = r.getId();

        //Assert
        assertEquals(3, reservationRepository.findAll().size());
        assertNull(service.listeReservationActuelle(140));
        assertEquals(0, service.listeReservationActuelle(idClient).size());

        //Act
        service.clientReservation(idClient, idReservation1);

        //Assert
        assertEquals(1, service.listeReservationActuelle(idClient).size());

        //Act
        service.clientReservation(idClient, idRes);

        //Assert
        assertEquals(2, service.listeReservationActuelle(idClient).size());

        //Act
        service.clientAnnulation(idClient,idReservation1);

        //Assert
        assertEquals(1, service.listeReservationActuelle(idClient).size());
    }

    @Test
    public void listeReservationOrganismeTest(){
        //Arrange
        Reservation r = new Reservation();
        r.setDateLimite(LocalDate.now().plusDays(7));
        r.setDescription("Match des Allouettes");
        r.setLieu("McGill");
        r.setNbPlaces(15000);

        //Assert
        assertEquals(2, reservationRepository.findAll().size());
        assertNull(service.listeReservationOrganisme(140));
        assertEquals(2, service.listeReservationOrganisme(idOrganisme).size());

        //Act
        service.createReservation(idOrganisme, r);

        //Assert
        assertEquals(3, service.listeReservationOrganisme(idOrganisme).size());
    }
}
