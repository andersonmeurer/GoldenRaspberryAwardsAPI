package br.com.outsera.films;

import br.com.outsera.films.model.MovieResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilmsApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetProducersAwardIntervals() throws JsonProcessingException {
		ResponseEntity<String> response = restTemplate.getForEntity("/producers/intervals", String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).contains("min", "max");
		String jsonExpected = "{\"min\":[{\"producers\":\"Joel Silver\",\"interval\":1,\"previousWin\":1990,\"followingWin\":1991}],\"max\":[{\"producers\":\"Matthew Vaughn\",\"interval\":13,\"previousWin\":2002,\"followingWin\":2015}]}";

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> expectedMap = objectMapper.readValue(jsonExpected, Map.class);
		Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), Map.class);
		assertThat(response.getBody()).isEqualTo(jsonExpected);
	}

	@Test
	public void testMovieResponse() {
		MovieResponse minResponse = new MovieResponse("Joel Silver", 1, 1990, 1991);
		MovieResponse maxResponse = new MovieResponse("Matthew Vaughn", 13, 2002, 2015);

		Map<String, List<MovieResponse>> expectedObject = Map.of(
				"min", List.of(minResponse),
				"max", List.of(maxResponse)
		);

		ResponseEntity<String> response = restTemplate.getForEntity("/producers/intervals", String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);

		String jsonExpected = "{\"min\":[{\"producers\":\"Joel Silver\",\"interval\":1,\"previousWin\":1990,\"followingWin\":1991}],\"max\":[{\"producers\":\"Matthew Vaughn\",\"interval\":13,\"previousWin\":2002,\"followingWin\":2015}]}";
		assertThat(response.getBody()).isEqualTo(jsonExpected);
	}

	@Test
	public void testGetProducersAwardIntervalsJson() {
		ResponseEntity<String> response = restTemplate.getForEntity("/producers/intervals", String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).contains("min", "max");
		String jsonExpected = "{\"min\":[{\"producers\":\"Joel Silver\",\"interval\":1,\"previousWin\":1990,\"followingWin\":1991}],\"max\":[{\"producers\":\"Matthew Vaughn\",\"interval\":13,\"previousWin\":2002,\"followingWin\":2015}]}";
		assertThat(response.getBody()).isEqualTo(jsonExpected);
	}
}
