package com.hackit.abhishekjain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hackit.abhishekjain.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	
	
	@Query(value = "SELECT b FROM Booking b where b.showId=:showId")
	public List<Booking> findByShowId(@Param("showId") Long showId);

	@Query(value = "SELECT UNNEST(seatsBooked) FROM Booking where show_id=:showId and status!=:status",nativeQuery = true)
	public List<String> findByBookingConfirmed(@Param("showId") Long showId, @Param("status") String status);

}
