package com.tijo.anonforum.domain.mapper.movie

import com.tijo.anonforum.domain.dto.movie.MovieDTO
import com.tijo.anonforum.domain.entity.Movie
import com.tijo.anonforum.domain.mapper.OneToOneMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll


@SpringBootTest
class MovieMappersTest extends Specification{


    @Autowired
    private OneToOneMapper<List<MovieDTO>,List<Movie>> movieListMapper
    @Autowired
    private OneToOneMapper<Movie, MovieDTO> movieDTOmapper
    @Autowired
    private OneToOneMapper<MovieDTO, Movie> movieMapper

    def dtos = [new MovieDTO((long) 1,"Test Movie 1","Wrong cover","Tester",2020),
                new MovieDTO((long) 2,"Test Movie 2","https://fossbytes.com/wp-content/uploads/2020/05/78-billion-line-text-file.jpg","Tester",2019),
                new MovieDTO((long) 3,"Test Movie 3","https://fossbytes.com/wp-content/uploads/2020/05/78-billion-line-text-file.jpg","Tester 2",2020)]

    def movies = [new Movie((long) 1,"Test Movie 1","Wrong cover","Tester",2020),
                  new Movie((long) 2,"Test Movie 2","https://fossbytes.com/wp-content/uploads/2020/05/78-billion-line-text-file.jpg","Tester",2019),
                  new Movie((long) 3,"Test Movie 3","https://fossbytes.com/wp-content/uploads/2020/05/78-billion-line-text-file.jpg","Tester 2",2020)]




    def "Should convert Movie to MovieDTO"(){
        given:
            def movie = movies[2]
            def moviedto = dtos[2]
        when:
            def converted = movieMapper.convert(movie)
        then:
            moviedto == converted
    }
    @Unroll
    def "Shold convert List<Movie> to List<MovieDTO>"(){
        given:
            def testMovies = movies
            def convertedMovies = movieListMapper.convert(testMovies)
        expect:
            convertedMovies == dtos
    }
    def "Should convert MovieDTO to Movie"(){
        given:
            def movie = movies[2]
            def moviedto = dtos[2]
        when:
            def converted = movieDTOmapper.convert(moviedto)
        then:
            movie == converted
    }

}
