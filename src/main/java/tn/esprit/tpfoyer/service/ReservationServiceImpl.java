package tn.esprit.tpfoyer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;

    public List<Reservation> retrieveAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation retrieveReservation(String reservationId) {
        Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
        return reservationOpt.orElse(null);
    }

    public Reservation addReservation(Reservation r) {
        return reservationRepository.save(r);
    }

    public Reservation modifyReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> trouverResSelonDateEtStatus(Date d, boolean b) {
        return reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(d, b);
    }

    public List<Reservation> findReservationsByDateRange(Date startDate, Date endDate) {
        return (List<Reservation>) reservationRepository.findReservationsByDateBetween(startDate, endDate);
    }

    public void removeReservation(String reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}
