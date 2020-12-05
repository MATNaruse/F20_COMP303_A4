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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import comp303.a4.entities.Movie;
import comp303.a4.SeedData;
import comp303.a4.repositories.MovieRepo;

@Controller
public class MovieController {
	
	@Autowired
	MovieRepo movieRepo;

	@GetMapping("/view-movies")
	public ModelAndView allMovies() {
		ModelAndView MVAllMovies = new ModelAndView("view-movies");
		List<Movie> mList = new ArrayList<Movie>();
		SeedData.loadMovieData(mList);
		MVAllMovies.addObject("movieList", mList);
		return MVAllMovies;
	}
	
	@GetMapping("/admin/new-movie")
	public String getNewMovie() {
		return "new-movie";
	}
	
	
	private List<Movie> dummyMovie(){
		List<Movie> mList = new ArrayList<Movie>();
		if(movieRepo.count() == 0) {		
			mList.add(new Movie("MovieA", "ENG", "Action", 120));
			mList.add(new Movie("MovieB", "ENG", "Bio", 120));
			mList.add(new Movie("MovieC", "ENG", "Comedy", 120));
			mList.add(new Movie("MovieD", "ENG", "Disaster", 120));
			mList.add(new Movie("MovieE", "ENG", "Eclectic", 120));
			
			for(Movie m : mList) {
				movieRepo.save(m);
			}
		}

		else {
			mList = movieRepo.findAll();
		}
		return mList;
	}
}
