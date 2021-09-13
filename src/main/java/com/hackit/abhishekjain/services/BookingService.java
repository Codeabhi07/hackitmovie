package com.hackit.abhishekjain.services;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hackit.abhishekjain.constants.Constants;
import com.hackit.abhishekjain.entity.Booking;
import com.hackit.abhishekjain.entity.Show;
import com.hackit.abhishekjain.exception.SeatUnavailableException;
import com.hackit.abhishekjain.repository.BookingRepository;
import com.hackit.abhishekjain.repository.ShowRepository;


@Service("Booking")
public class BookingService {
	
	@Autowired
	BookingRepository bookingRepository;
	
	@Autowired
	LockService lockService;
	
	@Autowired
	EmailService eMailService;
	
	@Autowired
	ShowRepository showRepository;

	public Long createBooking(Long showId, String[] seatsBooked) throws SeatUnavailableException {
		System.out.println("Inside creatBooking");
		if (checkSeatAlreadyBooked(showId, seatsBooked)) {
            throw new SeatUnavailableException();
        }
		List<Long> lockIds=lockService.lockSeats(showId,seatsBooked);
		Booking newBooking=new Booking();
		newBooking.setShowId(showId);
		newBooking.setSeatsBooked(seatsBooked);
		newBooking.setStatus(Constants.CREATED);
		newBooking.setCreatedOnDt(new Date());
		Long bookingId = bookingRepository.save(newBooking).getId();
		
		System.out.println("BookingId Generated: "+ bookingId);
		
		lockService.updateBookingId(lockIds,bookingId);
		return bookingId;
	}
	
	public List<Booking> getAllShowBookings(Long showId) {
        List<Booking> allBookings = bookingRepository.findByShowId(showId);
        return allBookings;
    }
	
	public List<String> getBookedSeats(Long showId) {
        return bookingRepository.findByBookingConfirmed(showId,Constants.CANCELED);
    }
	
	private boolean checkSeatAlreadyBooked(Long showId, String[] seatsBooked) {
		System.out.println("Inside checkSeatAlreadyBooked");
		List<String> bookedSeats = getBookedSeats(showId);
	    for (String seatNo : seatsBooked) {
	        if (bookedSeats.contains(seatNo)) {
	            return true;
	        }
	    }
	    return false;
    }
	
	public String confirmBooking(Long bookingId, String userDetails) throws MessagingException {
		JSONObject jsonObject= new JSONObject(userDetails);
		String name = jsonObject.getString("name");
		String email= jsonObject.getString("email");
		Booking booking=bookingRepository.getById(bookingId);
		booking.setStatus(Constants.CONFIRMED);
		booking.setUserName(name);
		booking.setUserEmail(email);
		bookingRepository.save(booking);
		Show show = showRepository.getById(booking.getShowId());
		eMailService.sendMail(name, email,booking,show);
		return "Booking Confirmed! Please Check Your eMail";
	}
	
	public String cancelBooking(Long bookingId){
		lockService.unlockSeats(bookingId);
		Booking booking=bookingRepository.getById(bookingId);
		booking.setStatus(Constants.CANCELED);
		bookingRepository.save(booking);
		return "Booking Cancelled!";
	}
	
	
	
	

}
