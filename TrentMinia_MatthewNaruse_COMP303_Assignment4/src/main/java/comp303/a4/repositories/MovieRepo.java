package comp303.a4.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import comp303.a4.entities.Movie;

public interface MovieRepo extends JpaRepository<Movie, Integer>{

	@Query(value="SELECT m.movieName FROM Movie m")
	List<String> getAllMovieNames();
}
