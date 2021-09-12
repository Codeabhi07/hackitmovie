package com.hackit.abhishekjain.entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.ToString
@Entity(name = "Show")
@Table(name = "show")
public class Show {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "show_id")
    private Long id;
	
	@Column(name = "movieName")
    private String movieName;
    
	@Column(name = "theaterName")
    private String theaterName;
	
	@Column(name = "screen_id")
	private Long screenId;
	
	@Column(name = "start_time")
    private Date startTime;
	
	@Column(name = "duration")
    private Integer durationInSeconds;
    
  //private final List<Seat> seats;
}
