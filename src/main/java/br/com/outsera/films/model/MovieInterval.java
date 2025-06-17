package br.com.outsera.films.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieInterval extends Movie {
    private Integer interval;
    private Integer previousWin;
    private Integer followingWin;
}