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
import comp303.a4.SeedData;

@Controller
public class MovieController {

	@GetMapping("/Movies")
	public ModelAndView allMovies() {
		ModelAndView MVAllMovies = new ModelAndView("view-movies");
		List<Movie> mList = new ArrayList<Movie>();
		SeedData.loadMovieData(mList);
		MVAllMovies.addObject("movieList", mList);
		return MVAllMovies;
	}
}
