package com.tijo.anonforum.service

import com.tijo.anonforum.domain.dto.ResponseDTO
import com.tijo.anonforum.domain.dto.movie.DeleteMovieDTO
import com.tijo.anonforum.domain.dto.movie.MovieActionDTO
import com.tijo.anonforum.domain.dto.movie.MovieDTO
import com.tijo.anonforum.domain.entity.Movie
import com.tijo.anonforum.domain.entity.User
import com.tijo.anonforum.domain.mapper.OneToOneMapper
import com.tijo.anonforum.domain.repository.MovieRepository
import com.tijo.anonforum.domain.repository.ReviewRepository
import com.tijo.anonforum.domain.repository.UserRepository
import com.tijo.anonforum.service.impl.MovieServiceImpl
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.Mockito.when;

@SpringBootTest
class MovieServiceTest extends Specification{
    @Mock
    UserRepository userRepository
    @Mock
    ReviewRepository reviewRepository
    @Mock
    MovieRepository movieRepository
    @Autowired
    OneToOneMapper<List<MovieDTO>,List<Movie>> movieListMapper;
    @Autowired
    OneToOneMapper<Movie, MovieDTO> movieDTOMapper;

    private MovieServiceImpl movieService

    private Movie testMovie
    def setup() {
        //MockitoAnnotations.initMocks(this);
        testMovie = new Movie((long)2,"Test Movie 2","https://fossbytes.com/wp-content/uploads/2020/05/78-billion-line-text-file.jpg","Tester",2019)
        movieService = new MovieServiceImpl(movieRepository,userRepository,reviewRepository,movieListMapper,movieDTOMapper)
        when(userRepository.findUserByLoginAndPasswd(anyString(),anyString())).thenReturn(null)
        when(userRepository.findUserByLoginAndPasswd("TestUser1","TestPass1")).thenReturn(new User(1,"TestUser1","TestPass1",false))
        when(userRepository.findUserByLoginAndPasswd("TestUser2","TestPass2")).thenReturn(new User(2,"TestUser2","TestPass2",true))
        //when(userRepository.findUserByLoginAndPasswd(anyString(),anyString())).thenReturn(null)
        when(movieRepository.findMovieByTitleAndDirectorAndPremiere("Test Movie 2","Tester",2019)).thenReturn(testMovie)
        when(movieRepository.findMovieByTitleAndDirectorAndPremiere("Test Movie 3","Tester 2",1998)).thenReturn(null)
        when(movieRepository.findMovieByMovie_id(2)).thenReturn(testMovie)
    }

    def "Should not add movie when movie exists"(){
        given:
            def movieAction = new MovieActionDTO()
            movieAction.setLogin("TestUser1")
            movieAction.setPasswd("TestPass1")
            movieAction.setMovieDTO(new MovieDTO(null,"Test Movie 2","https://fossbytes.com/wp-content/uploads/2020/05/78-billion-line-text-file.jpg","Tester",2019))
        when:
            def response = movieService.addMovie(movieAction)
        then:
            response == new ResponseDTO(false,"That movie already exists")
    }

    def "Should not add movie when user is not moderator"(){
        given:
            def movieAction = new MovieActionDTO()
            movieAction.setLogin("TestUser1")
            movieAction.setPasswd("TestPass1")
            movieAction.setMovieDTO(new MovieDTO(null,"Test Movie 3","https://fossbytes.com/wp-content/uploads/2020/05/78-billion-line-text-file.jpg","Tester 2",1998))
        when:
            def response = movieService.addMovie(movieAction)
        then:
            response == new ResponseDTO(false,"You do not have Moderator permissions")
    }

    def "Should not add movie when user does not exist"(){
        given:
            def movieAction = new MovieActionDTO()
            movieAction.setLogin("TestUser3")
            movieAction.setPasswd("TestPass3")
            movieAction.setMovieDTO(new MovieDTO(null,"Test Movie 3","https://fossbytes.com/wp-content/uploads/2020/05/78-billion-line-text-file.jpg","Tester 2",1998))
        when:
            def response = movieService.addMovie(movieAction)
        then:
            response == new ResponseDTO(false,"User does not exist")
    }

    def "Should not add movie when URL is not valid"(){
        given:
            def movieAction = new MovieActionDTO()
            movieAction.setLogin("TestUser2")
            movieAction.setPasswd("TestPass2")
            movieAction.setMovieDTO(new MovieDTO(null,"Test Movie 3","http://becoebca;wob;","Tester 2",1998))
        when:
            def response = movieService.addMovie(movieAction)
        then:
            response == new ResponseDTO(false,"Not a valid image link")
    }

    def "Should return success when all conditions are met (user exists and is moderator, movie does not exist, url is valid)"(){
        given:
            def movieAction = new MovieActionDTO()
            movieAction.setLogin("TestUser2")
            movieAction.setPasswd("TestPass2")
            movieAction.setMovieDTO(new MovieDTO(null,"Test Movie 3","https://fossbytes.com/wp-content/uploads/2020/05/78-billion-line-text-file.jpg","Tester 2",1998))
        when:
            def response = movieService.addMovie(movieAction)
        then:
            response == new ResponseDTO(true,"Movie added successfully")
    }

    def "Should modify movie when movie exists"(){
        given:
        def movieAction = new MovieActionDTO()
        movieAction.setLogin("TestUser2")
        movieAction.setPasswd("TestPass2")
        movieAction.setMovieDTO(new MovieDTO((long)2,"Test Movie 2","https://fossbytes.com/wp-content/uploads/2020/05/78-billion-line-text-file.jpg","Tester",2019))
        when:
        def response = movieService.updateMovie(movieAction)
        then:
        response == new ResponseDTO(true,"Movie has been successfully updated")
    }

    def "Should not modify movie when user is not moderator"(){
        given:
        def movieAction = new MovieActionDTO()
        movieAction.setLogin("TestUser1")
        movieAction.setPasswd("TestPass1")
        movieAction.setMovieDTO(new MovieDTO((long)2,"Test Movie 2","https://fossbytes.com/wp-content/uploads/2020/05/78-billion-line-text-file.jpg","Tester",2019))
        when:
        def response = movieService.updateMovie(movieAction)
        then:
        response == new ResponseDTO(false,"You do not have Moderator permissions")
    }

    def "Should not modify movie when user does not exist"(){
        given:
        def movieAction = new MovieActionDTO()
        movieAction.setLogin("TestUser3")
        movieAction.setPasswd("TestPass3")
        movieAction.setMovieDTO(new MovieDTO((long)2,"Test Movie 2","https://fossbytes.com/wp-content/uploads/2020/05/78-billion-line-text-file.jpg","Tester",2019))
        when:
        def response = movieService.updateMovie(movieAction)
        then:
        response == new ResponseDTO(false,"User does not exist")
    }

    def "Should not modify movie when URL is not valid"(){
        given:
        def movieAction = new MovieActionDTO()
        movieAction.setLogin("TestUser2")
        movieAction.setPasswd("TestPass2")
        movieAction.setMovieDTO(new MovieDTO(null,"Test Movie 3","http://becoebca;wob;","Tester 2",1998))
        when:
        def response = movieService.updateMovie(movieAction)
        then:
        response == new ResponseDTO(false,"Not a valid image link")
    }

    def "Should not modify movie when movie does not exist"(){
        given:
        def movieAction = new MovieActionDTO()
        movieAction.setLogin("TestUser2")
        movieAction.setPasswd("TestPass2")
        movieAction.setMovieDTO(new MovieDTO(null,"Test Movie 4","https://fossbytes.com/wp-content/uploads/2020/05/78-billion-line-text-file.jpg","Tester 5",6969))
        when:
        def response = movieService.updateMovie(movieAction)
        then:
        response == new ResponseDTO(false,"This movie does not exist")
    }

    def "Should not delete movie when user does not exist"(){
        given:
        String login = "TestUser3"
        String passwd = "TestPass3"
        Long movie_id = 2
        when:
        def response = movieService.deleteMovie(login,passwd,movie_id)
        then:
        response == new ResponseDTO(false,"User does not exist")
    }

    def "Should not delete movie when user does not have moderator privileges"(){
        given:
        String login = "TestUser1"
        String passwd = "TestPass1"
        Long movie_id = 2
        when:
        def response = movieService.deleteMovie(login,passwd,movie_id)
        then:
        response == new ResponseDTO(false,"You do not have Moderator permissions")
    }

    def "Should delete movie when user is moderator"(){
        given:
        String login = "TestUser2"
        String passwd = "TestPass2"
        Long movie_id = 2
        when:
        def response = movieService.deleteMovie(login,passwd,movie_id)
        then:
        response == new ResponseDTO(true,"Movie and reviews of that movie have been deleted")
    }
}
