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

import comp303.a4.SeedData;
import comp303.a4.repositories.MovieRepo;

@Controller
public class GeneralController {

	@Autowired
	MovieRepo movieRepo;
	
	@GetMapping("/")
	public String root() {
		System.out.println("RUNNING MAPPING GeneralController:'/' !");
		if(movieRepo.count() == 0) {SeedData.loadMovieData(movieRepo);}
		return "index";
	}
	@GetMapping("/index")
	public String index() {
		return "index";
	}
}
