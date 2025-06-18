package br.com.outsera.films.service;

import br.com.outsera.films.model.Movie;
import br.com.outsera.films.model.MovieInterval;
import br.com.outsera.films.model.MovieResponse;
import br.com.outsera.films.repository.MovieRepository;
import br.com.outsera.films.util.CsvImporter;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
@AllArgsConstructor
public class MovieService {

	private final MovieRepository movieRepository;
	private final CsvImporter csvImporter;

	private final String csvFileName = "./src/main/resources/movielist.csv";

	@Transactional
	public void importMoviesFromCsv(String filePath) {
		List<Movie> movies = csvImporter.importMovies(filePath);
		movies.forEach(movieRepository::save);
	}

	@PostConstruct
	public void loadCsv() {
		if (Files.exists(Path.of(csvFileName))) {
			importMoviesFromCsv(csvFileName);
			System.out.println("Load file ... successfully!");
		} else {
			System.out.println("File Path not found!");
		}
	}

	public Map<String, List<MovieResponse>> getAwardIntervals() {
	    List<Movie> winners = movieRepository.findWinnersGroupedByProducer();
	    Map<String, List<Integer>> producerWins = new HashMap<>();

	    for (Movie row : winners) {
	        for (String producer : row.getProducers().split(",\\s*")) {
	            producerWins.computeIfAbsent(producer, k -> new ArrayList<>()).add(row.getReleaseYear());
	        }
	    }

	    List<MovieInterval> minIntervals = new ArrayList<>();
	    List<MovieInterval> maxIntervals = new ArrayList<>();

	    for (Map.Entry<String, List<Integer>> entry : producerWins.entrySet()) {
	        List<Integer> years = entry.getValue();
	        Collections.sort(years);

	        for (int i = 1; i < years.size(); i++) {
	            int interval = years.get(i) - years.get(i - 1);
	            MovieInterval intervalData = new MovieInterval();
	            intervalData.setProducers(entry.getKey());
	            intervalData.setInterval(interval);
	            intervalData.setPreviousWin(years.get(i - 1));
	            intervalData.setFollowingWin(years.get(i));

	            if (minIntervals.isEmpty() || interval < minIntervals.get(0).getInterval()) {
	                minIntervals.clear();
	                minIntervals.add(intervalData);
	            } else if (interval == minIntervals.get(0).getInterval()) {
	                minIntervals.add(intervalData);
	            }

	            if (maxIntervals.isEmpty() || interval > maxIntervals.get(0).getInterval()) {
	                maxIntervals.clear();
	                maxIntervals.add(intervalData);
	            } else if (interval == maxIntervals.get(0).getInterval()) {
	                maxIntervals.add(intervalData);
	            }
	        }
	    }

		List<MovieResponse> max = maxIntervals.stream()
				.map(x ->
						new MovieResponse(
								x.getProducers(),
								x.getInterval(),
								x.getPreviousWin(),
								x.getFollowingWin()
						)
				).toList();
		List<MovieResponse> min = minIntervals.stream()
				.map(x ->
						new MovieResponse(
								x.getProducers(),
								x.getInterval(),
								x.getPreviousWin(),
								x.getFollowingWin()
						)
				).toList();

		return Map.of("min", min, "max", max);
	}
}