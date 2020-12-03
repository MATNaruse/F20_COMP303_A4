package comp303.a4.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import comp303.a4.entities.Booking;

public interface BookingRepo extends JpaRepository<Booking, Integer>{

}
