package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReservationServiceTest {

    @Autowired
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationServiceMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test JUnit pour la méthode de recherche par date
    @Test
    public void testFindReservationsByDateRange() {
        Date startDate = new Date();
        Date endDate = new Date(System.currentTimeMillis() + 86400000); // 1 jour après

        List<Reservation> reservations = reservationService.findReservationsByDateRange(startDate, endDate);
        assertNotNull(reservations);
        assertTrue(reservations.size() >= 0); // Vérifie qu'il y a 0 ou plus de réservations
    }

    // Test Mockito pour simuler le repository
    @Test
    public void testFindReservationsByDateRange_Mockito() {
        Date startDate = new Date();
        Date endDate = new Date(System.currentTimeMillis() + 86400000); // 1 jour après

        // Simule une liste vide
        when(reservationRepository.findReservationsByDateBetween(startDate, endDate))
                .thenReturn(new ArrayList<>());

        List<Reservation> reservations = reservationServiceMock.findReservationsByDateRange(startDate, endDate);
        assertNotNull(reservations);
        assertEquals(0, reservations.size()); // Vérifie qu'il y a 0 réservations
    }

    // Test JUnit pour l'ajout d'une réservation
    @Test
    public void testAddReservation() {
        Reservation reservation = new Reservation();
        reservation.setReservationId("123");

        Reservation savedReservation = reservationService.addReservation(reservation);
        assertNotNull(savedReservation);
        assertEquals("123", savedReservation.getReservationId());
    }

    // Test Mockito pour simuler l'ajout d'une réservation
    @Test
    public void testAddReservation_Mockito() {
        Reservation reservation = new Reservation();
        reservation.setReservationId("123");

        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation savedReservation = reservationServiceMock.addReservation(reservation);
        assertNotNull(savedReservation);
        assertEquals("123", savedReservation.getReservationId());
    }

    // Test JUnit pour récupérer une réservation par ID
    @Test
    public void testRetrieveReservation() {
        String reservationId = "123";
        Reservation reservation = reservationService.retrieveReservation(reservationId);
        assertNull(reservation); // Si la réservation n'existe pas
    }

    // Test Mockito pour récupérer une réservation par ID
    @Test
    public void testRetrieveReservation_Mockito() {
        String reservationId = "123";
        Reservation reservation = new Reservation();
        reservation.setReservationId(reservationId);

        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        Reservation foundReservation = reservationServiceMock.retrieveReservation(reservationId);
        assertNotNull(foundReservation);
        assertEquals(reservationId, foundReservation.getReservationId());
    }

    // Test JUnit pour modifier une réservation
    @Test
    public void testModifyReservation() {
        Reservation reservation = new Reservation();
        reservation.setReservationId("123");
        reservation.setEstValide(true);

        Reservation updatedReservation = reservationService.modifyReservation(reservation);
        assertNotNull(updatedReservation);
        assertTrue(updatedReservation.isEstValide());
    }

    // Test Mockito pour simuler la modification d'une réservation
    @Test
    public void testModifyReservation_Mockito() {
        Reservation reservation = new Reservation();
        reservation.setReservationId("123");
        reservation.setEstValide(true);

        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation updatedReservation = reservationServiceMock.modifyReservation(reservation);
        assertNotNull(updatedReservation);
        assertTrue(updatedReservation.isEstValide());
    }
}
