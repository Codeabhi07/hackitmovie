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
@Entity(name = "Lock")
@Table(name = "seatlock")
public class Lock {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	@Column(name="seat_id")
	private Long seatId;
	@Column(name="show_id")
	private Long showId;
	@Column(name="booking_id")
	private Long bookingId;
	@Column(name="timeout")
	private Long timeoutInSeconds;
	@Column(name="lock_date_time")
	private Date lockDateTime;

}
