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
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FilmsApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void testGetProducersAwardIntervals() throws JsonProcessingException {
		ResponseEntity<String> response = restTemplate.getForEntity("/producers/intervals", String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).contains("min", "max");
		String jsonExpected = "{\"min\":[{\"producer\":\"Joel Silver\",\"interval\":1,\"previousWin\":1990,\"followingWin\":1991}],\"max\":[{\"producer\":\"Matthew Vaughn\",\"interval\":13,\"previousWin\":2002,\"followingWin\":2015}]}";

		Map<String, Object> expectedMap = new TreeMap<>(objectMapper.readValue(jsonExpected, Map.class));
		Map<String, Object> responseMap = new TreeMap<>(objectMapper.readValue(response.getBody(), Map.class));

		assertThat(responseMap).isEqualTo(expectedMap);
		assertThat(objectMapper.readTree(response.getBody())).isEqualTo(objectMapper.readTree(jsonExpected));
	}

	@Test
	public void testMovieResponse() throws JsonProcessingException {
		MovieResponse minResponse = new MovieResponse("Joel Silver", 1, 1990, 1991);
		MovieResponse maxResponse = new MovieResponse("Matthew Vaughn", 13, 2002, 2015);

		Map<String, List<MovieResponse>> expectedObject = Map.of(
				"min", List.of(minResponse),
				"max", List.of(maxResponse)
		);

		ResponseEntity<String> response = restTemplate.getForEntity("/producers/intervals", String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);

		String jsonExpected = "{\"min\":[{\"producer\":\"Joel Silver\",\"interval\":1,\"previousWin\":1990,\"followingWin\":1991}],\"max\":[{\"producer\":\"Matthew Vaughn\",\"interval\":13,\"previousWin\":2002,\"followingWin\":2015}]}";

		Map<String, Object> expectedMap = new TreeMap<>(objectMapper.readValue(jsonExpected, Map.class));
		Map<String, Object> responseMap = new TreeMap<>(objectMapper.readValue(response.getBody(), Map.class));
		assertThat(responseMap).isEqualTo(expectedMap);
		assertThat(objectMapper.readTree(response.getBody())).isEqualTo(objectMapper.readTree(jsonExpected));
	}

	@Test
	public void testGetProducersAwardIntervalsJson() throws JsonProcessingException {
		ResponseEntity<String> response = restTemplate.getForEntity("/producers/intervals", String.class);
		assertThat(response.getStatusCodeValue()).isEqualTo(200);
		assertThat(response.getBody()).contains("min", "max");
		String jsonExpected = "{\"min\":[{\"producer\":\"Joel Silver\",\"interval\":1,\"previousWin\":1990,\"followingWin\":1991}],\"max\":[{\"producer\":\"Matthew Vaughn\",\"interval\":13,\"previousWin\":2002,\"followingWin\":2015}]}";

		Map<String, Object> expectedMap = new TreeMap<>(objectMapper.readValue(jsonExpected, Map.class));
		Map<String, Object> responseMap = new TreeMap<>(objectMapper.readValue(response.getBody(), Map.class));
		assertThat(responseMap).isEqualTo(expectedMap);
		assertThat(objectMapper.readTree(response.getBody())).isEqualTo(objectMapper.readTree(jsonExpected));
	}
}
