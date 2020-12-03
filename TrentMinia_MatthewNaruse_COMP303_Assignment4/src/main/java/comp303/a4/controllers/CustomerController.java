/**
 * COMP303-001 Assignment 4
 * Due Date: Dec 06, 2020
 * Submitted: ??? ##, 2020
 * 301 041 132 : Trent Minia
 * 300 549 638 : Matthew Naruse
 */

package comp303.a4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import comp303.a4.entities.Customer;
import comp303.a4.repositories.CustomerRepo;


@Controller
public class CustomerController {

	@Autowired
	private CustomerRepo custRepo;
	
	@GetMapping("/register")
	public String newRegisterForm() {
		return "register";
	}
	
	@PostMapping("/new_customer")
	public ModelAndView registerNewCustomer(@RequestParam("Username") String user, 
											@RequestParam("Password") String pass,
											@RequestParam("custName") String custName,
											@RequestParam("address") String address,
											@RequestParam("city") String city,
											@RequestParam("email") String email,
											@RequestParam("phoneNumber") String phone) {
		
		Customer customer = new Customer(user, pass, custName, address, city, email, phone);
		custRepo.save(customer);
		return new ModelAndView("index");
	}
}
