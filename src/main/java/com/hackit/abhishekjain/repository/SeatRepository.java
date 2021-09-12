package com.hackit.abhishekjain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.hackit.abhishekjain.entity.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
	
	@Query(value="SELECT s.id,s.seatNo as number FROM Seat s where s.screenId=:screenId and s.row=:row")
	public List<String> findByScreenIdandRow(@Param("screenId") Long screenId,@Param("row") Integer row);
	
	@Query(value="SELECT Count(Distinct(row)) FROM Seat where screen_id=:screenId",nativeQuery=true)
	public int findDistinctRowCount(@Param("screenId") Long screenId);
	
	@Query(value="SELECT s.id FROM Seat s where s.screenId=:screenId and s.seatNo IN (:seatsNo)")
	public List<Long> findByScreenIdandSeatNo(@Param("screenId") Long screenId,@Param("seatsNo") String[] seatsNo);

}
