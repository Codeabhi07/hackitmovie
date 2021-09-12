package com.hackit.abhishekjain.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hackit.abhishekjain.repository.SeatRepository;
import com.hackit.abhishekjain.repository.ShowRepository;

@Service("Seating")
public class SeatService {
	

	@Autowired
	SeatRepository seatRepository;

	@Autowired
	ShowRepository showRepository;
	
	@Autowired
	BookingService bookingService;
	
	public JSONArray getAllSeats(Long showId) {
		
		Long screenId =showRepository.findByShowId(showId);
		List<String> bookedSeats = bookingService.getBookedSeats(showId);
		int rowCount = seatRepository.findDistinctRowCount(screenId);
		JSONArray response =new JSONArray();
		for(int row=1; row<=rowCount;row++) {
			List<String> seats = seatRepository.findByScreenIdandRow(screenId,Integer.valueOf(row));
			List<JSONObject> rowFormat = new ArrayList<>();
			for(String seat: seats) {
				JSONObject seatFormat = new JSONObject();
				String[] data=seat.split(","); 
				seatFormat.put("id",Long.parseLong(data[0]));
				seatFormat.put("number",data[1]);
				if(bookedSeats.contains(data[1])) {
					seatFormat.put("isReserved",true);
				}
				rowFormat.add(seatFormat);
			}
			rowFormat.add(3,null);
			rowFormat.add(10,null);
			if(row < 4) {
				rowFormat.addAll(Arrays.asList(null,null,null));
			}
			response.put(rowFormat);
		}
		return response;
	}
		
	public List<Long> getSeatIds(String[] seatsNo,Long screenId){
		return seatRepository.findByScreenIdandSeatNo(screenId,seatsNo);
	}

}
