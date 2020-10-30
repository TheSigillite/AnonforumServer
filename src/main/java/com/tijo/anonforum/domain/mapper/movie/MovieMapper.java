package com.tijo.anonforum.domain.mapper.movie;

import com.tijo.anonforum.domain.dto.movie.MovieDTO;
import com.tijo.anonforum.domain.entity.Movie;
import com.tijo.anonforum.domain.mapper.OneToOneMapper;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper implements OneToOneMapper<MovieDTO, Movie> {
    @Override
    public MovieDTO convert(Movie movie) {
        return MovieDTO.builder()
                .movie_id(movie.getMovie_id())
                .title(movie.getTitle())
                .cover(movie.getCover())
                .director(movie.getDirector())
                .premiere(movie.getPremiere())
                .build();
    }
}
