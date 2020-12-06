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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import comp303.a4.entities.Booking;
import comp303.a4.entities.Customer;
import comp303.a4.repositories.BookingRepo;
import comp303.a4.repositories.CustomerRepo;
import comp303.a4.repositories.MovieRepo;

@Controller
public class BookingController {

	@Autowired
	BookingRepo bookRepo;
	
	@Autowired
	MovieRepo movieRepo;
	
	@Autowired
	CustomerRepo custRepo;
	
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
		Booking newBooking = new Booking(movieName, loginCust.getCustId(), Integer.parseInt(tickAdult), Integer.parseInt(tickSenStu), Integer.parseInt(tickChild), new Date(), viewDate, venue);
		
		// Saving to Repo
		bookRepo.save(newBooking);
		
		// TO CHANGE -> Returning to Index
		MVpostNewBooking = new ModelAndView("index");
		
		return MVpostNewBooking;
	}
	
	
	@GetMapping("/view-booking/{id}")
	public ModelAndView get_viewBooking(@PathVariable("id") int bookId) {
		ModelAndView MVgetViewBooking = new ModelAndView("view-booking");

		try {
			Booking bking =  bookRepo.getOne(bookId);		
			Customer cust = custRepo.getOne(bking.getCustId());
			MVgetViewBooking.addObject("booking", bking);
			MVgetViewBooking.addObject("custName", cust.getCustName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MVgetViewBooking = new ModelAndView("index");
		}

		
		return MVgetViewBooking;
	}
	
	
	@GetMapping("/update-booking/{id}")
	public ModelAndView get_updateBooking(@PathVariable("id") int bookId) {
		ModelAndView MVgetUpdateBooking = new ModelAndView("update-booking");
	
		try {
			Booking bking =  bookRepo.getOne(bookId);		
			MVgetUpdateBooking.addObject("upBook", bking);
			MVgetUpdateBooking.addObject("movieList", movieRepo.getAllMovieNames());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MVgetUpdateBooking = new ModelAndView("index");
		}
	
		return MVgetUpdateBooking;
	}
	
	@PostMapping("/update-booking")
	public ModelAndView post_updateBooking(@RequestParam("bookId") String bookId,@RequestParam("movieName") String movieName, 
										@RequestParam("tickAdult") String tickAdult, @RequestParam("tickSenStu") String tickSenStu,
										@RequestParam("tickChild") String tickChild, @RequestParam("viewingDate") String viewingDateStr, 
										@RequestParam("venue") String venue) {
		ModelAndView MVpostUpdateBooking = new ModelAndView("index");
	
		try {
			Booking bking =  bookRepo.getOne(Integer.parseInt(bookId));
			bking.setMovieName(movieName);
			bking.setTickAdult(Integer.parseInt(tickAdult));
			bking.setTickSenStu(Integer.parseInt(tickSenStu));
			bking.setTickChild(Integer.parseInt(tickChild));
			
			String dateMask = "yyyy-MM-dd";
			Date viewDate = new Date();
			try {
				viewDate = new SimpleDateFormat(dateMask).parse(viewingDateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			bking.setViewingDate(viewDate);
			bking.setVenue(venue);
			
			bookRepo.save(bking);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MVpostUpdateBooking = new ModelAndView("index");
		}
	
		return MVpostUpdateBooking;
	}
	
	
	private void updateLoginCust(HttpServletRequest request) {
		session=request.getSession();
		loginCust= (Customer) session.getAttribute("loginCust");
		if(loginCust==null){System.out.println("NO Login Customer Found");}
		else {System.out.println(loginCust.getUsername());}
	}
}
