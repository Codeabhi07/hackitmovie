package com.hackit.abhishekjain.entity;

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
@Entity(name = "Seat")
@Table(name = "seat")
public class Seat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="seat_id")
	private Long id;
	
	@Column(name="row")
	private Integer row;
	
	@Column(name="seat_no")
	private String seatNo;
	
	@Column(name="category")
	private String seatCategory;
	
	@Column(name="screen_id")
	private Long screenId;
	
}
