package com.hackit.abhishekjain.entity;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.vladmihalcea.hibernate.type.array.StringArrayType;


@lombok.Setter
@lombok.Getter
@lombok.NoArgsConstructor
@lombok.ToString
@Entity(name = "Booking")
@Table(name = "booking")
@TypeDef(name = "string-array", typeClass = StringArrayType.class)
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private Long id;

	@Column(name = "show_id")
	private Long showId;

	@Type(type = "string-array")
	@Column(name = "seatsbooked", columnDefinition = "text[]")
	private String[] seatsBooked;

	@Column(name = "status", columnDefinition = "varchar")
	private String status;

	@Column(name = "created_on_dt")
	private Date createdOnDt;
	
	@Column(name = "username", columnDefinition = "varchar")
	private String userName;
	
	@Column(name = "useremail", columnDefinition = "varchar")
	private String userEmail;

}
