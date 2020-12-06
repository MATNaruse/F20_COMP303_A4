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
	
//	@GetMapping("/new-booking")
//	public ModelAndView get_newBooking(HttpServletRequest request){
//		ModelAndView MVgetNewBooking;
//		updateLoginCust(request);
//		MVgetNewBooking = CustomerController.EnsureLoggedIn("new-booking", request);
//		List<String> movieList = movieRepo.getAllMovieNames();
//		MVgetNewBooking.addObject("movieList", movieList);
//		return MVgetNewBooking;
//	}

	@GetMapping("/new-booking")
	public String get_newBooking(Model model, HttpServletRequest request){
		Customer loginCust = CustomerController.getLoginCust(request);
		if(loginCust == null) {
			model.addAttribute("err_msg", "Please log in first");
			return "login";
		}
		else {
			System.out.println("GET_NEWBOOKING");
			Booking newbook = new Booking(CustomerController.loginCust.getCustId());
			newbook.setPurchaseDate(new Date());
			System.out.println("CUSTID" + newbook.getCustId());
			model.addAttribute("Booking", newbook);
			model.addAttribute("movieList", movieRepo.getAllMovieNames());
			return "new-booking";
		}

	}
	
	@PostMapping("/new-booking")
	public String post_newBooking(@Valid @ModelAttribute Booking Booking, BindingResult result, Model model, HttpServletRequest request) {
		System.out.println(result.getAllErrors());
		System.out.println("POST_NEWBOOKING");
		if(result.hasErrors()) {return get_newBooking(model, request);}
		else { 
			Booking.setAmountPaid(Booking.getAmountPaid());
			bookRepo.save(Booking); 
			return "index";	
		}
	}

	@GetMapping("/view-booking/{id}")
	public String get_viewBooking(@PathVariable("id") int bookId, Model model, HttpServletRequest request) {
		Customer loginCust = CustomerController.getLoginCust(request);
		if(loginCust == null) {
			model.addAttribute("err_msg", "Please log in first");
			return "login";
		}
		else {
			try {
				Booking bking =  bookRepo.getOne(bookId);		
				Customer cust = custRepo.getOne(bking.getCustId());
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
		
	@GetMapping("/update-booking/{id}")
	public String get_updateBooking(@PathVariable("id") int bookId, Model model, HttpServletRequest request) {
		Customer loginCust = CustomerController.getLoginCust(request);
		if(loginCust == null) {
			model.addAttribute("err_msg", "Please log in first");
			return "login";
		}
		else {
			try {
				Booking bking =  bookRepo.getOne(bookId);
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

	@PostMapping("/update-booking/{id}")
	public String post_updateBooking(@PathVariable("id") int bookId, @Valid @ModelAttribute Booking booking, BindingResult result, Model model, HttpServletRequest request) {
		if(result.hasErrors()) {
			return get_updateBooking(booking.getBookingId(), model, request);
		}
		booking.setAmountPaid(booking.getAmountPaid());
		bookRepo.save(booking);
		
		return "redirect:/" + get_viewBooking(booking.getBookingId(), model, request) + "/" + bookId;
	}
	
	@GetMapping("/delete-booking/{id}")
	public String get_deleteBooking(@PathVariable("id") int bookId, Model model, HttpServletRequest request) {
		Customer loginCust = CustomerController.getLoginCust(request);
		if(loginCust == null) {
			model.addAttribute("err_msg", "Please log in first");
			return "login";
		}
		else {
			try {
				Booking bking =  bookRepo.getOne(bookId);		
				Customer cust = custRepo.getOne(bking.getCustId());
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

	@PostMapping("/delete-booking/{id}")
	public String post_deleteBooking(@PathVariable("id") int bookId) {
		bookRepo.deleteById(bookId);
		return "redirect:/index";
	}
}
