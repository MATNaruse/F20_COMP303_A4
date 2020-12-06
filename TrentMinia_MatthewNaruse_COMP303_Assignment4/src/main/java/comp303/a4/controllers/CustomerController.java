/**
 * COMP303-001 Assignment 4
 * Due Date: Dec 06, 2020
 * Submitted: ??? ##, 2020
 * 301 041 132 : Trent Minia
 * 300 549 638 : Matthew Naruse
 */

package comp303.a4.controllers;

import java.util.List;

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


@Controller
public class CustomerController {

	@Autowired
	private CustomerRepo custRepo;
	
	@Autowired
	private BookingRepo bookRepo;
	
	private static HttpSession session;
	public static Customer loginCust;
	
	@GetMapping("/register")
	public String get_register(Model model) {
		model.addAttribute("Cust", new Customer());
		return "register";
	}
	
	@PostMapping("/register")
	public String post_register(@Valid @ModelAttribute Customer customer, BindingResult result, Model model, HttpServletRequest request) {
		if(result.hasErrors()) {return get_register(model);}
		else {
			custRepo.save(customer);
			session = request.getSession();
			session.setAttribute("loginCust", customer);
			return "index";
		}
	}
	
	
	@GetMapping("/login")
	public String get_login(Model model) {
		return "login";
	}
	
	@PostMapping("/login")
	public ModelAndView post_login(@RequestParam("Username") String user, @RequestParam("Password") String pass, HttpServletRequest request) {
		ModelAndView MVpostLogin = null;
		
		// Check if Username and Passwords match
		Customer loginCust = custRepo.loginValidation(user, pass);
		
		// If it doesn't match
		if(loginCust==null) {
			// Begin process to prompt user to log in again
			MVpostLogin = new ModelAndView("login");
			
			// Check if Username even exists
			List<Customer> userCheck = custRepo.usernameExistCheck(user);
			
			// Error message based on if does exist. If yes, password wrong. If no, user not exist.
			String err_msg = userCheck.size() == 0 ? "Username does NOT exist!" : "Password does not match!";
			
			// Add err_msg to ModelAndView
			MVpostLogin.addObject("err_msg", err_msg);
		}
		else {
			// If login was successful
			
			// Set customer as the "logged in customer"
			session = request.getSession();
			session.setAttribute("loginCust", loginCust);
			session.setAttribute("loginCustId", loginCust.getCustId());
			updateLoginCust(request);
			// Return them to index
			MVpostLogin = new ModelAndView("index");
		}
		return MVpostLogin;
	}
	
	@GetMapping("/logout")
	public String get_logout(HttpServletRequest request) {
		session = request.getSession();
		// Removing loginCust from session, and returning to index
		session.setAttribute("loginCust", null);
		loginCust = null;
		return "index";
	}

	@GetMapping("/profile")
	public String get_profile(Model model, HttpServletRequest request) {
		// checking login customer
		updateLoginCust(request);
		
		if(loginCust==null) {
			// if no customer logged in, send to Login Page
			model.addAttribute("err_msg", "Please log in first");
			return get_login(model);
		}
		
		else {
			// Pass logged in customer to Profile
			model.addAttribute("loginCust", loginCust);
			List<Booking> bookings = bookRepo.findAllBookingsByCustId(loginCust.getCustId());
			model.addAttribute("loginBookings", bookings);
			return "profile";
		}
	}

	
	@GetMapping("/update-profile/{id}")
	public String get_updateProfile(@PathVariable("id") int custId, Model model, HttpServletRequest request) {
		// checking login customer
		updateLoginCust(request);
		
		if(loginCust==null) {
			// if no customer logged in, send to Login Page
			model.addAttribute("err_msg", "Please log in first");
			return get_login(model);
		}
		
		else if (loginCust.getCustId() != custId) {
			// If trying to edit another profile 
			model.addAttribute("err_msg", "You do not have permission to edit that profile!");
			return "redirect:/index";
		}
		
		else {
			// If trying to edit own Profile
			Customer cust = custRepo.getOne(custId);
			model.addAttribute("upCust", cust);
			return "update-profile";
		}
	}
	
	
	@PostMapping("/update-profile/{id}")
	public String post_updateProfile(@PathVariable("id") int custId, @Valid @ModelAttribute Customer customer, BindingResult result, Model model, HttpServletRequest request) {
		if(result.hasErrors()) {
			model.addAttribute("upCust", customer);
			return get_updateProfile(custId, model, request);
		}
		
		else {
			custRepo.save(customer);
			updateLoginCust(request);
			session.setAttribute("loginCust", customer);
			model.addAttribute("loginCust", customer);
			return "redirect:/" + get_profile(model, request);
		}
	}
	
	@GetMapping("/delete-profile/{id}")
	public String get_deleteProfile(@PathVariable("id") int custId, Model model, HttpServletRequest request) {
		updateLoginCust(request);
		
		if(loginCust==null) {
			// if no customer logged in, send to Login Page
			model.addAttribute("err_msg", "Please log in first");
			return get_login(model);
		}
		
		else if (loginCust.getCustId() != custId) {
			// If trying to delete another profile 
			model.addAttribute("err_msg", "You do not have permission to edit that profile!");
			return "redirect:/index";
		}
		else {
			// If trying to delete own profile
			Customer cust = custRepo.getOne(loginCust.getCustId());
			model.addAttribute("delCust", cust);
			return "delete-profile";
		}
	}
	
	@PostMapping("/delete-profile/{id}")
	public String post_deleteProfile(@PathVariable("id") int custId, HttpServletRequest request, Model model) {
		custRepo.deleteById(custId);
		session = request.getSession();
		session.setAttribute("loginCust", null);
		model.addAttribute("err_msg", "Profile Deleted");
		return "redirect:/index";
		
	}
	//
	// ==================================================================
	//
		
	public static ModelAndView EnsureLoggedIn(String successPage, HttpServletRequest request) {
		ModelAndView MVensureLogin;
		HttpSession sess = request.getSession();
		Customer lInCust = (Customer) sess.getAttribute("loginCust");
		
		if(lInCust==null) {
			// if no customer logged in, send to Login Page
			MVensureLogin = new ModelAndView("login");
			MVensureLogin.addObject("err_msg", "Please Log In First");
		}
		
		else {
			// Pass logged in customer to Profile
			MVensureLogin = new ModelAndView(successPage);
		}
		return MVensureLogin;
	}
	
	
	private void updateLoginCust(HttpServletRequest request) {
		session=request.getSession();
		loginCust= (Customer) session.getAttribute("loginCust");
		if(loginCust==null){System.out.println("NO Login Customer Found");}
		else {System.out.println(loginCust.getUsername());}
	}
	
	public static Customer getLoginCust(HttpServletRequest request) {
		session=request.getSession();
		loginCust= (Customer) session.getAttribute("loginCust");
		if(loginCust==null){System.out.println("NO Login Customer Found");}
		else {System.out.println(loginCust.getUsername());}
		return loginCust;
	}
}
