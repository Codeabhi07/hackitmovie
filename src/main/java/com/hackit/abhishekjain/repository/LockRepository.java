package com.hackit.abhishekjain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hackit.abhishekjain.entity.Lock;

@Repository
public interface LockRepository extends JpaRepository<Lock,Long> {

	@Query(value="SELECT Count(*) FROM seatlock where seat_id=:seatId and show_id=:showId and (lock_date_time + Interval '300') > now()",nativeQuery=true)
	public int checkSeatLocked(@Param("showId") Long showId,@Param("seatId") Long seatId);
	
	@Modifying
	@Query(value="UPDATE seatlock SET booking_id=:bookingId where id IN (:lockIds)",nativeQuery=true)
	public void updateBookingId(@Param("lockIds") List<Long> lockIds, @Param("bookingId") Long bookingId);
	
	@Modifying
	@Query(value="DELETE FROM seatlock where booking_id=:bookingId",nativeQuery=true)
	public void deleteByBookingId(@Param("bookingId") Long bookingId);

}
