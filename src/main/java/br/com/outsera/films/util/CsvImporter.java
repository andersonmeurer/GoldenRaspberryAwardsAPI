package br.com.outsera.films.util;

import br.com.outsera.films.model.Movie;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class CsvImporter {

	public List<Movie> importMovies(String filePath) {
		List<Movie> movies = new ArrayList<>();
		try (CSVReader reader = new CSVReaderBuilder(new FileReader(filePath))
				.withCSVParser(new CSVParserBuilder().withSeparator(';').build())
				.build()) {
			List<String[]> rows = reader.readAll();
			rows.stream().skip(1).forEach(row -> {
				String[] producers = Objects.isNull(row[3]) ? new String[]{} : row[3].split(",\\s*|\\sand\\s");
				for (String producer : producers) {
					Movie movie = new Movie();
					movie.setReleaseYear(Integer.parseInt("0" + row[0]));
					movie.setTitle(Objects.isNull(row[1]) ? "" : row[1]);
					movie.setStudios(Objects.isNull(row[2]) ? "" : row[2]);
					movie.setProducers(Objects.isNull(producer.trim()) ? "" : producer.trim());
					movie.setWinner("yes".equalsIgnoreCase(Objects.isNull(row[4]) ? "" : row[4].trim()));

					movies.add(movie);
				}
			});
		} catch (Exception e) {
			throw new RuntimeException("Erro ao processar o arquivo CSV", e);
		}
		return movies;
	}
}