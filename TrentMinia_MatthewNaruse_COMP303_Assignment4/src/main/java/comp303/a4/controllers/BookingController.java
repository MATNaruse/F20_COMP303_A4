/**
 * COMP303-001 Assignment 4
 * Due Date: Dec 06, 2020
 * Submitted: ??? ##, 2020
 * 301 041 132 : Trent Minia
 * 300 549 638 : Matthew Naruse
 */

package comp303.a4.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import comp303.a4.entities.Booking;
import comp303.a4.entities.Customer;
import comp303.a4.repositories.BookingRepo;
import comp303.a4.repositories.MovieRepo;

@Controller
public class BookingController {

	@Autowired
	BookingRepo bookRepo;
	
	@Autowired
	MovieRepo movieRepo;
	
	private static HttpSession session;
	private static Customer loginCust;
	
	@GetMapping("/new-booking")
	public ModelAndView get_newBooking(HttpServletRequest request){
		ModelAndView MVgetNewBooking;
		updateLoginCust(request);
		MVgetNewBooking = CustomerController.EnsureLoggedIn("new-booking", request);
		List<String> movieList = movieRepo.getAllMovieNames();
		MVgetNewBooking.addObject("movieList", movieList);
		return MVgetNewBooking;
	}
	
	@PostMapping("/new-booking")
	public ModelAndView post_newBooking(@RequestParam("movieName") String movieName, @RequestParam("ticketAdult") String tickAdult,
										@RequestParam("ticketSenStu") String tickSenStu, @RequestParam("ticketChild") String tickChild,
										@RequestParam("viewingDate") String viewingDateStr, @RequestParam("venue") String venue,
										HttpServletRequest request) {
		ModelAndView MVpostNewBooking;
		// Ensure Current Login Customer
		updateLoginCust(request);
		
		// Calc amount "paid" - Adult $12, Senior/Student $8, Child $6.50
		double amtPaid = (Integer.parseInt(tickAdult) * 12) + (Integer.parseInt(tickSenStu) * 8) + (Integer.parseInt(tickChild) * 6.50);
		
		// Parse viewingDate
		String dateMask = "yyyy-MM-dd";
		Date viewDate = new Date();
		try {
			viewDate = new SimpleDateFormat(dateMask).parse(viewingDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Creating New Booking Object
		Booking newBooking = new Booking(movieName, loginCust.getCustId(), amtPaid, new Date(), viewDate, venue);
		
		// Saving to 
		bookRepo.save(newBooking);
		
		MVpostNewBooking = new ModelAndView("index");
		
		return MVpostNewBooking;
	}
	
	
	private void updateLoginCust(HttpServletRequest request) {
		session=request.getSession();
		loginCust= (Customer) session.getAttribute("loginCust");
		if(loginCust==null){System.out.println("NO Login Customer Found");}
		else {System.out.println(loginCust.getUsername());}
	}
}
