package br.com.outsera.films.controller;

import br.com.outsera.films.model.MovieResponse;
import br.com.outsera.films.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class MovieController {

	private final MovieService movieService;

	@PostMapping("/import")
	public String importMovies(@RequestParam String filePath) {
		movieService.importMoviesFromCsv(filePath);
		return "Importação concluída com sucesso!";
	}

	@GetMapping("/producers/intervals")
	public Map<String, List<MovieResponse>> getProducersAwardIntervals() {
		return movieService.getAwardIntervals();
	}
}