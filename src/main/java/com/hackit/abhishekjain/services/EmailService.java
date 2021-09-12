package com.hackit.abhishekjain.services;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.hackit.abhishekjain.entity.Booking;
import com.hackit.abhishekjain.entity.Show;

@Service("emailService")
@Configurable
public class EmailService {
	
	@Autowired
    private JavaMailSender mailSender;
	
	public void sendMail(String name,String email,Booking bookingDetails, Show show) throws MessagingException {
		   
	
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("hackitmoviebooking@gmail.com");
        helper.setTo(email);
        message.setSubject("Booking Confirmation for "+show.getMovieName());
        message.setContent("<h3>Dear "+name+",</h3>"
        		+ "<h2><b>Your Booking is Confirmed!</b></h2>"
        		+ "<h3>"+show.getMovieName()+"</h3>"
        		+ "<h4>"+show.getStartTime().toString().split(" ")[0]+" | "+show.getStartTime().toString().split(" ")[1]+" | "+show.getTheaterName()+"</h4>"
        		+ "<h4> SCREEN "+show.getScreenId()+"</h4>"
        		+ "<h3> EXECUTIVE - "+String.join(" ",bookingDetails.getSeatsBooked())+"</h3>"+
        "<img src='https://image.tmdb.org/t/p/w780/1BIoJGKbXjdFDAqUEiA2VHqkK1Z.jpg'>","text/html");
        mailSender.send(message);
        
	}

}
