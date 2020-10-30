package com.tijo.anonforum.domain.mapper.movie

import com.tijo.anonforum.domain.dto.movie.MovieDTO
import com.tijo.anonforum.domain.entity.Movie
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification


@SpringBootTest
class MovieDTOMapperTest extends Specification{
    def "Should convert Movie to MovieDTO"(){
        given:
            def movie = new Movie(1,"Test Movie","Some Cover URL","Mr. Bruh",2020)
            def moviedto = new MovieDTO(1,"Test Movie","Some Cover URL","Mr. Bruh",2020)
            def converter = new MovieMapper()
        when:
            def converted = converter.convert(movie)
        then:
            moviedto == converted
    }
}
