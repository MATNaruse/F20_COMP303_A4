/**
 * COMP303-001 Assignment 4
 * Due Date: Dec 06, 2020
 * Submitted: ??? ##, 2020
 * 301 041 132 : Trent Minia
 * 300 549 638 : Matthew Naruse
 */

package comp303.a4.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MovieController {
	
	@Autowired
	MovieRepo movieRepo;

	/* 
		Mapping for CRUD Operations:
	    ----------------------------

	   	Create: /admin/new-movie
	   	Read: /view-movies
	   	Update: /admin/update-movie/id
	   	Delete: /admin/delete-movie/id

	*/

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

	@PostMapping("/admin/add")
	public String addMovie(@RequestParam("imgPoster") MultipartFile poster ,Movie movie, BindingResult result, Model model) {
		if (result.hasErrors()) return "/admin/new-movie";
		
		processPosterUpload(poster, movie);
		movieRepo.save(movie);
		model.addAttribute("movies", movieRepo.findAll());
		return ("redirect:/view-movies");
	}

	// Updating A Movie
	// ----------------
	@GetMapping("/admin/update-movie/{id}")
	public String get_updateMovie(@PathVariable("id") int movieId, Model model) {
		Movie mov = movieRepo.findById(movieId)
		.orElseThrow(() -> new IllegalArgumentException("Invalid movie number: " + movieId));

		model.addAttribute("upMovie", mov);
	
		return ("update-movie");
	}
	
	@PostMapping("/update-movie/{id}")
	public String post_updateMovie(@PathVariable("id") int movieId, @ModelAttribute Movie movie, BindingResult result, Model model) {
		if(result.hasErrors()) {
			return get_updateMovie(movie.getMovieId(), model);
		}
		
		movieRepo.save(movie);
		return ("redirect:/view-movies");
	}

	// Deleting A Movie
	// ----------------
	
	@GetMapping("admin/delete-movie/{id}")
	public String get_deleteMovie(@PathVariable("id") int movieId, Model model) {
		Movie mov = movieRepo.findById(movieId)
		.orElseThrow(() -> new IllegalArgumentException("Invalid movie number: " + movieId));

		model.addAttribute("delMovie", mov);
		return "delete-movie";
	}

	@PostMapping("/delete-movie/{id}")
	public String post_deleteMovie(@PathVariable("id") int movieId) {
		movieRepo.deleteById(movieId);
		return "redirect:/view-movies";
	}
	
	
	
	// =========================================
	
	private void processPosterUpload(MultipartFile poster, Movie movie) {
		if(!poster.isEmpty()) {
			try {
				System.out.println(poster.getContentType());
				String cwd = new File(".").getCanonicalPath();
				String imgName = movie.getMovieName().toLowerCase().replace(" ", "").replace(":", "-").replace("*", "") + ".jpg";
				Path toImages = Paths.get(cwd + "\\src\\main\\resources\\static\\images\\" + imgName);
				InputStream posterInStr = poster.getInputStream();
				Files.copy(posterInStr, toImages, StandardCopyOption.REPLACE_EXISTING);
				
				movie.setImgSrc("images/" + imgName);
			
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
