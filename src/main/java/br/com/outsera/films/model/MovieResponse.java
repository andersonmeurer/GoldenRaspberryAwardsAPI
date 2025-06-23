package br.com.outsera.films.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class MovieResponse {

	private String producer;

	private Integer interval;

	private Integer previousWin;

	private Integer followingWin;

}
