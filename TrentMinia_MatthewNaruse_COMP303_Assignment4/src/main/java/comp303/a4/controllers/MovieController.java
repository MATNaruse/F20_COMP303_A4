/**
 * COMP303-001 Assignment 4
 * Due Date: Dec 06, 2020
 * Submitted: ??? ##, 2020
 * 301 041 132 : Trent Minia
 * 300 549 638 : Matthew Naruse
 */

package comp303.a4.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import comp303.a4.entities.Movie;

@Controller
public class MovieController {

	@GetMapping("/Movies")
	public ModelAndView allMovies() {
		ModelAndView MVAllMovies = new ModelAndView("view-movies");
		MVAllMovies.addObject("movieList", this.dummyMovie());
		return MVAllMovies;
	}
	
	
	private List<Movie> dummyMovie(){
		List<Movie> mList = new ArrayList<Movie>();
		
		mList.add(new Movie("MovieA", "ENG", "Action", 120));
		mList.add(new Movie("MovieB", "ENG", "Bio", 120));
		mList.add(new Movie("MovieC", "ENG", "Comedy", 120));
		mList.add(new Movie("MovieD", "ENG", "Disaster", 120));
		mList.add(new Movie("MovieE", "ENG", "Eclectic", 120));
		return mList;
	}
}
