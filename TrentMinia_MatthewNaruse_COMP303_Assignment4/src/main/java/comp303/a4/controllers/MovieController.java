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

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MovieController {
	
	@Autowired
	MovieRepo movieRepo;

	@GetMapping("/view-movies")
	public ModelAndView allMovies() {
		ModelAndView MVAllMovies = new ModelAndView("view-movies");
		List<Movie> mList = new ArrayList<Movie>();
		
		if(movieRepo.count() == 0) {SeedData.loadMovieData(movieRepo);}
		mList = movieRepo.findAll();
		MVAllMovies.addObject("movieList", mList);
		return MVAllMovies;
	}
	
	// Adding New Movie
	// ----------------

	@GetMapping("/admin/new-movie")
	public String getNewMovie(Movie movie) {
		return "new-movie";
	}

	@PostMapping("/add")
	public String addMovie(Movie movie, BindingResult result, Model model) {
		movieRepo.save(movie);
		model.addAttribute("movies", movieRepo.findAll());
		return ("index");
	}

	// Updating A Movie
	// ----------------
	@GetMapping("/admin/update-movie/{id}")
	public String get_updatMovie(@PathVariable("id") int movieId, Model model) {
		Movie mov = movieRepo.findById(movieId)
		.orElseThrow(() -> new IllegalArgumentException("Invalid movie number: " + movieId));

		model.addAttribute("upMovie", mov);
	
		return ("update-movie");
	}
	
	@PostMapping("/update-movie/{id}")
	public String post_updateMovie(@PathVariable("id") int movieId, Movie movie, BindingResult result, Model model) {
		movieRepo.save(movie);
		model.addAttribute("movies", movieRepo.findAll());
		return ("update-success");
	}
	
}
