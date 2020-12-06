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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import comp303.a4.entities.Customer;
import comp303.a4.repositories.CustomerRepo;


@Controller
public class CustomerController {

	@Autowired
	private CustomerRepo custRepo;
	
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
			return "profile";
		}
	}
	
//	@GetMapping("/profile")
//	public ModelAndView get_profile(HttpServletRequest request) {
//		ModelAndView MVgetProfile;
//		// checking login customer
//		updateLoginCust(request);
//		
//		if(loginCust==null) {
//			// if no customer logged in, send to Login Page
//			MVgetProfile = new ModelAndView("login");
//			MVgetProfile.addObject("err_msg", "Please Log In First");
//		}
//		
//		else {
//			// Pass logged in customer to Profile
//			MVgetProfile = new ModelAndView("profile");
//			MVgetProfile.addObject("loginCust", loginCust);
//		}
//		
//		return MVgetProfile;
//	}
	
	
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
