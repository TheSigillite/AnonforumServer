package com.tijo.anonforum.domain.mapper.movie;

import com.tijo.anonforum.domain.dto.movie.MovieDTO;
import com.tijo.anonforum.domain.entity.Movie;
import com.tijo.anonforum.domain.mapper.OneToOneMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieListMapper implements OneToOneMapper<List<MovieDTO>,List<Movie>> {
    @Override
    public List<MovieDTO> convert(List<Movie> movies) {
        return movies.stream().map(movie ->  MovieDTO.builder()
                .movie_id(movie.getMovie_id())
                .title(movie.getTitle())
                .cover(movie.getCover())
                .director(movie.getDirector())
                .premiere(movie.getPremiere())
                .build()).collect(Collectors.toList());
    }
}
