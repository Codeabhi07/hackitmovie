package com.hackit.abhishekjain.controller;

import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.hackit.abhishekjain.constants.Constants;
import com.hackit.abhishekjain.services.BookingService;
import com.hackit.abhishekjain.services.SeatService;


@Controller
public class BaseController {

	@Autowired
	SeatService seatService;
	@Autowired
	BookingService bookingService;
	

	@GetMapping(value="/getAllSeats/{showId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getAllSeats(@PathVariable("showId") String showId) {
		try {
			JSONArray jsonArray = seatService.getAllSeats(Long.parseLong(showId));
			return ResponseEntity.status(HttpStatus.OK).body(jsonArray.toString()); 
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getBookedSeats/{showId}")
	public ResponseEntity<List<String>> getBookedSeats(@PathVariable("showId") String showId) {
		try {
			return new ResponseEntity<>(bookingService.getBookedSeats(Long.parseLong(showId)), HttpStatus.OK);
		} catch (Exception e) {
			// Log.error("Error occured while fetching booked seat details :",e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/createBooking")
	public ResponseEntity<?> startBooking(
			@RequestHeader(value = Constants.SHOWID, required=true) String showId,
			@RequestBody String[] seatsBooked
			) {
		try {
			return new ResponseEntity<>(bookingService.createBooking(Long.parseLong(showId),seatsBooked), HttpStatus.OK);
		}
		catch(Exception e) {
			String msg="Error Encountered : "+e;
			return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/confirmBooking")
	public ResponseEntity<?> confirmBooking(@RequestParam("name") String name,@RequestParam("email") String email,
			@RequestHeader(value = Constants.BOOKINGID, required=true) String bookingid
			) {
		try {
			return new ResponseEntity<>(bookingService.confirmBooking(Long.parseLong(bookingid), name, email), HttpStatus.OK);
		}
		catch(Exception e) {
			String msg="Error Encountered : "+e;
			return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/cancelBooking")
	public ResponseEntity<?> confirmBooking(@RequestHeader(value = Constants.BOOKINGID, required=true) String bookingid
			) {
		try {
			return new ResponseEntity<>(bookingService.cancelBooking(Long.parseLong(bookingid)), HttpStatus.OK);
		}
		catch(Exception e) {
			String msg="Error Encountered : "+e;
			return new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}