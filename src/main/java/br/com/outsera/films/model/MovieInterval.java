package br.com.outsera.films.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MovieInterval {
    private String producers;
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;
}