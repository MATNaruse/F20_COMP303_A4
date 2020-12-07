/**
 * COMP303-001 Assignment 4
 * Due Date: Dec 06, 2020
 * Submitted: ??? ##, 2020
 * 301 041 132 : Trent Minia
 * 300 549 638 : Matthew Naruse
 */

package comp303.a4.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="mov_gen")
	@SequenceGenerator(name="mov_gen", sequenceName="mov_seq")
	private int movieId;
	
	@NotEmpty(message="Movie must have a Name")
	private String movieName;
	@NotEmpty(message="Movie must have a Director")
	private String movieDirector;
	@NotEmpty(message="Movie must have a Language")
	private String movieLanguage;
	@NotEmpty(message="Movie must have a Genre")
	private String movieGenre;
	@NotEmpty(message="Movie must have a Blurb")
	private String movieBlurb;
	@NotEmpty(message="Movie must have a Rating")
	private String movieRating; // movie rating for appropriate audiences
	@Min(value=0)
	private int duration;
	
	private String imgSrc;
	
	public int getMovieId() {
		return movieId;
	}
	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getMovieDirector() {
		return movieDirector;
	}
	public void setMovieDirector(String movieDirector) {
		this.movieDirector = movieDirector;
	}
	public String getMovieLanguage() {
		return movieLanguage;
	}
	public void setMovieLanguage(String movieLanguage) {
		this.movieLanguage = movieLanguage;
	}
	public String getMovieGenre() {
		return movieGenre;
	}
	public void setMovieGenre(String movieGenre) {
		this.movieGenre = movieGenre;
	}
	public String getMovieBlurb() {
		return movieBlurb;
	}
	public void setMovieBlurb(String movieBlurb) {
		this.movieBlurb = movieBlurb;
	}
	public String getMovieRating() {
		return movieRating;
	}
	public void setMovieRating(String movieRating) {
		this.movieRating = movieRating;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	
	public Movie() {};
	
	public Movie(String name, 
				 String dir,
				 String lang, 
				 String genr,
				 String blur,
				 String rat,
				 int dur,
				 String img) {
		this.movieName = name;
		this.movieDirector = dir;
		this.movieLanguage = lang;
		this.movieGenre = genr;
		this.movieBlurb = blur;
		this.movieRating = rat;
		this.duration = dur;
		this.imgSrc = img;
	}
}
