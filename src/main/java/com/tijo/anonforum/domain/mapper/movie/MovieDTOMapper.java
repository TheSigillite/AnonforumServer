package com.tijo.anonforum.domain.mapper.movie;

import com.tijo.anonforum.domain.dto.movie.MovieDTO;
import com.tijo.anonforum.domain.entity.Movie;
import com.tijo.anonforum.domain.mapper.OneToOneMapper;
import org.springframework.stereotype.Component;

@Component
public class MovieDTOMapper implements OneToOneMapper<Movie, MovieDTO> {
    @Override
    public Movie convert(MovieDTO movieDTO) {
        return Movie.builder()
                .movie_id(movieDTO.getMovie_id())
                .title(movieDTO.getTitle())
                .cover(movieDTO.getCover())
                .director(movieDTO.getDirector())
                .premiere(movieDTO.getPremiere())
                .build();
    }
}
