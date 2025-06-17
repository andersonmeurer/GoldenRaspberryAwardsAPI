package br.com.outsera.films.repository;

import br.com.outsera.films.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query("SELECT m FROM Movie m WHERE m.winner = true ORDER BY m.producers, m.releaseYear")
	List<Movie> findWinnersGroupedByProducer();

}