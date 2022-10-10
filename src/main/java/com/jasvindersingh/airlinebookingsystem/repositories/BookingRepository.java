package com.jasvindersingh.airlinebookingsystem.repositories;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jasvindersingh.airlinebookingsystem.models.Booking;

public interface BookingRepository extends JpaRepository<Booking,Long> {
	@Query("SELECT b FROM Booking b WHERE b.user.id = ?1")
	public List<Booking> findByUserId(Long userId);
}
