/**
 * COMP303-001 Assignment 4
 * Due Date: Dec 06, 2020
 * Submitted: ??? ##, 2020
 * 301 041 132 : Trent Minia
 * 300 549 638 : Matthew Naruse
 */

package comp303.a4.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import comp303.a4.entities.Customer;
import comp303.a4.repositories.BookingRepo;

@Controller
public class BookingController {

	@Autowired
	BookingRepo bookRepo;
	
	private static HttpSession session;
	private static Customer loginCust;
	
	@GetMapping("/new-booking")
	public ModelAndView get_newBooking(HttpServletRequest request){
		ModelAndView MVgetNewBooking;
		updateLoginCust(request);
		MVgetNewBooking = CustomerController.EnsureLoggedIn("new-booking", request);
		return MVgetNewBooking;
	}
	
	
	
	
	private void updateLoginCust(HttpServletRequest request) {
		session=request.getSession();
		loginCust= (Customer) session.getAttribute("loginCust");
		if(loginCust==null){System.out.println("NO Login Customer Found");}
		else {System.out.println(loginCust.getUsername());}
	}
}
