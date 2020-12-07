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
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	
	/**
	 * GET: Get New Booking Page
	 */
	@GetMapping("/new-booking")
	public String get_newBooking(Model model, HttpServletRequest request){
		Customer loginCust = CustomerController.getLoginCust(request);
		if(loginCust == null) {
			// If tried to access without logging in first
			model.addAttribute("err_msg", "Please log in first");
			return "login";
		}
		else {
			// If logged in
			System.out.println("GET_NEWBOOKING");
			Booking newbook = new Booking(CustomerController.loginCust.getCustId());
			newbook.setPurchaseDate(new Date());
			System.out.println("CUSTID" + newbook.getCustId());
			model.addAttribute("Booking", newbook);
			model.addAttribute("movieList", movieRepo.getAllMovieNames());
			return "new-booking";
		}

	}
	
	/**
	 * POST: Validate and Save New Booking
	 */
	@PostMapping("/new-booking")
	public String post_newBooking(@Valid @ModelAttribute Booking Booking, BindingResult result, Model model, HttpServletRequest request) {
		System.out.println(result.getAllErrors());
		System.out.println("POST_NEWBOOKING");
		if(result.hasErrors()) {return get_newBooking(model, request);} // If failed/errors, send back to New Booking Pahe
		else { 
			// If Successful
			Booking.setAmountPaid(Booking.getAmountPaid());
			bookRepo.save(Booking); // Save to Repo
			return "index";	
		}
	}

	/**
	 * GET: Viewing a Booking by Id
	 */
	@GetMapping("/view-booking/{id}")
	public String get_viewBooking(@PathVariable("id") int bookId, Model model, HttpServletRequest request) {
		Customer loginCust = CustomerController.getLoginCust(request);
		if(loginCust == null) {
			// If user not logged in, send to Login Screen
			model.addAttribute("err_msg", "Please log in first");
			return "login";
		}
		else {
			// If logged in
			try {
				// Get the booking and customer from Booking.custId
				Booking bking =  bookRepo.getOne(bookId);		
				Customer cust = custRepo.getOne(bking.getCustId());
				
				// Pass Both to model to display
				model.addAttribute("booking", bking);
				model.addAttribute("custName", cust.getCustName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "index";
			}
			return "view-booking";
		}

	}
		
	/**
	 * GET: Get Update Booking page by Id
	 */
	@GetMapping("/update-booking/{id}")
	public String get_updateBooking(@PathVariable("id") int bookId, Model model, HttpServletRequest request) {
		Customer loginCust = CustomerController.getLoginCust(request);
		if(loginCust == null) {
			// If user not logged in, send to Login Screen
			model.addAttribute("err_msg", "Please log in first");
			return "login";
		}
		else {
			// If logged in
			try {
				// Get the Booking by Id
				Booking bking =  bookRepo.getOne(bookId);
				
				// Load Booking entity and MovieList into model
				model.addAttribute("Booking", bking);
				model.addAttribute("movieList", movieRepo.getAllMovieNames());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "index";
			}
			return "update-booking";
		}
		
	}

	/**
	 * POST: Validate and Save Updated Booking
	 */
	@PostMapping("/update-booking/{id}")
	public String post_updateBooking(@PathVariable("id") int bookId, @Valid @ModelAttribute Booking booking, BindingResult result, Model model, HttpServletRequest request) {
		if(result.hasErrors()) {
			// If Errors, send them back to UpdateBooking
			return get_updateBooking(booking.getBookingId(), model, request);
		}
		booking.setAmountPaid(booking.getAmountPaid());
		bookRepo.save(booking); // Save updated Booking to Repo
		
		return "redirect:/" + get_viewBooking(booking.getBookingId(), model, request) + "/" + bookId;
	}
	
	/**
	 * GET: Get Delete Booking Page by Id
	 */
	@GetMapping("/delete-booking/{id}")
	public String get_deleteBooking(@PathVariable("id") int bookId, Model model, HttpServletRequest request) {
		Customer loginCust = CustomerController.getLoginCust(request);
		if(loginCust == null) {
			// If user not logged in, send to Login Screen
			model.addAttribute("err_msg", "Please log in first");
			return "login";
		}
		else {
			// If logged in
			try {
				// Get Booking and Customer by ID
				Booking bking =  bookRepo.getOne(bookId);		
				Customer cust = custRepo.getOne(bking.getCustId());
				
				// Load Booking and Customer data into Model
				model.addAttribute("booking", bking);
				model.addAttribute("custName", cust.getCustName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "index";
			}
			return "delete-booking";			
		}

	}

	/**
	 * POST: Delete Selected Booking by ID
	 */
	@PostMapping("/delete-booking/{id}")
	public String post_deleteBooking(@PathVariable("id") int bookId) {
		bookRepo.deleteById(bookId);
		return "redirect:/index";
	}
}
