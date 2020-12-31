package com.tijo.anonforum.service;

import com.tijo.anonforum.domain.dto.ResponseDTO;
import com.tijo.anonforum.domain.dto.movie.DeleteMovieDTO;
import com.tijo.anonforum.domain.dto.movie.MovieActionDTO;
import com.tijo.anonforum.domain.dto.movie.MovieDTO;

import java.util.List;

public interface MoviesService {
    List<MovieDTO> findAll();

    MovieDTO getOne(Long movie_id);

    ResponseDTO addMovie(MovieActionDTO newMovieDto);

    ResponseDTO updateMovie(MovieActionDTO updateMovieDto);

    ResponseDTO deleteMovie(String login, String passwd , Long movie_id);
}
