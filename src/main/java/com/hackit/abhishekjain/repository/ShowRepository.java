package com.hackit.abhishekjain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.hackit.abhishekjain.entity.Show;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long>{

	@Query(value="SELECT s.screenId FROM Show s where s.id=:showId")
	public Long findByShowId(@Param("showId") Long showId);

}
