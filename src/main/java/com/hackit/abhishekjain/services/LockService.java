package com.hackit.abhishekjain.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hackit.abhishekjain.constants.Constants;
import com.hackit.abhishekjain.entity.Lock;
import com.hackit.abhishekjain.exception.SeatOnHoldException;
import com.hackit.abhishekjain.repository.LockRepository;
import com.hackit.abhishekjain.repository.SeatRepository;
import com.hackit.abhishekjain.repository.ShowRepository;
import com.hackit.abhishekjain.services.LockService;

@Service("Locking")
public class LockService {

	@Autowired
	LockRepository lockRepository;
	
	@Autowired
	ShowRepository showRepository;
	
	@Autowired
	SeatRepository seatRepository;
	
	@Autowired
	SeatService seatService;
	

	synchronized public List<Long> lockSeats(Long showId,String[] seatsBooked) throws SeatOnHoldException {
		System.out.println("Inside lockSeats");
		List<Long> lockIds=new ArrayList<>();
		
		Long screenId=showRepository.findByShowId(showId);
		List<Long> seatIds = seatService.getSeatIds(seatsBooked,screenId);
		
		for (Long seatId : seatIds) {
			if (lockRepository.checkSeatLocked(showId, seatId)==1) {
				throw new SeatOnHoldException();
			}
		}

		for (Long seatId : seatIds) {
			Long lockId=lockSeat(showId, seatId);
			lockIds.add(lockId);
		}
		return lockIds;
	}

	private Long lockSeat(Long showId, Long seatId) {
		System.out.println("Inside lockSeat: seatId=" +seatId );
		Lock newLock = new Lock();
		newLock.setSeatId(seatId);
		newLock.setShowId(showId);
		newLock.setBookingId(null);
		newLock.setTimeoutInSeconds(Constants.TIMEOUT);
		newLock.setLockDateTime(new Date());
		return lockRepository.save(newLock).getId();
	}
	
	@Transactional
	public void updateBookingId(List<Long> lockIds, Long bookingId) {
		lockRepository.updateBookingId(lockIds,bookingId);	
	}
	
	@Transactional
	public void unlockSeats(Long bookingId) {
		lockRepository.deleteByBookingId(bookingId);
	}
	

}
