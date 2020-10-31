package com.tijo.anonforum.service.impl;

import com.tijo.anonforum.domain.dto.ResponseDTO;
import com.tijo.anonforum.domain.dto.movie.DeleteMovieDTO;
import com.tijo.anonforum.domain.dto.movie.MovieActionDTO;
import com.tijo.anonforum.domain.dto.movie.MovieDTO;
import com.tijo.anonforum.domain.entity.Movie;
import com.tijo.anonforum.domain.entity.User;
import com.tijo.anonforum.domain.mapper.OneToOneMapper;
import com.tijo.anonforum.domain.repository.MovieRepository;
import com.tijo.anonforum.domain.repository.ReviewRepository;
import com.tijo.anonforum.domain.repository.UserRepository;
import com.tijo.anonforum.domain.utilities.URLregexp;
import com.tijo.anonforum.service.MoviesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MoviesService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final OneToOneMapper<List<MovieDTO>,List<Movie>> movieListMapper;
    private final OneToOneMapper<Movie, MovieDTO> movieDTOMapper;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository
            , UserRepository userRepository
            , ReviewRepository reviewRepository
            , OneToOneMapper<List<MovieDTO>, List<Movie>> movieListMapper
            , OneToOneMapper<Movie, MovieDTO> movieDTOMapper) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
        this.movieListMapper = movieListMapper;
        this.movieDTOMapper = movieDTOMapper;
    }

    @Override
    public List<MovieDTO> findAll() {
        List<Movie> movies = movieRepository.findAll(Sort.by(Sort.Direction.DESC, "premiere"));
        return movieListMapper.convert(movies);
    }

    @Override
    public ResponseDTO addMovie(MovieActionDTO newMovieDto) {
        MovieDTO movieDTO = newMovieDto.getMovieDTO();
        if(!URLregexp.VerifyURL(movieDTO)){
            return new ResponseDTO(false,"Not a valid image link");
        }
        Optional<Movie> movie = Optional.ofNullable(movieRepository.findMovieByTitleAndDirectorAndPremiere(movieDTO.getTitle(), movieDTO.getDirector(), movieDTO.getPremiere()));
        if (movie.isPresent()){
            return new ResponseDTO(false,"That movie already exists");
        }
        User user = userRepository.findUserByLoginAndPasswd(newMovieDto.getLogin(),newMovieDto.getPasswd());
        //Check if user has admin privileges on site
        if(user == null){
            return new ResponseDTO(false,"User does not exist");
        }
        if(user.getIs_adm()){
            movieRepository.insertMovie(movieDTO.getTitle()
                    , movieDTO.getCover()
                    , movieDTO.getDirector()
                    , movieDTO.getPremiere());
            return new ResponseDTO(true,"Movie added successfully");
        }
        return new ResponseDTO(false,"You do not have Moderator permissions");
    }


    @Override
    public ResponseDTO updateMovie(MovieActionDTO updateMovieDto) {
        MovieDTO updateMovie = updateMovieDto.getMovieDTO();
        if(!URLregexp.VerifyURL(updateMovie)){
            return new ResponseDTO(false,"Not a valid image link");
        }
        Optional<User> user = Optional.ofNullable(userRepository.findUserByLoginAndPasswd(updateMovieDto.getLogin(), updateMovieDto.getPasswd()));
        Optional<Movie> mov = Optional.ofNullable(movieRepository.findMovieByMovie_id(updateMovie.getMovie_id()));
        if(mov.isEmpty()){
            return new ResponseDTO(false, "This movie does not exist");
        }
        if(user.isEmpty()){
            return new ResponseDTO(false,"User does not exist");
        }
        if (user.get().getIs_adm()){
            Movie movie = movieDTOMapper.convert(updateMovie);
            movieRepository.save(movie);
            return new ResponseDTO(true,"Movie has been successfully updated");
        }
        return new ResponseDTO(false,"You do not have Moderator permissions");
    }

    @Override
    public ResponseDTO deleteMovie(DeleteMovieDTO deleteMovieDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByLoginAndPasswd(deleteMovieDto.getLogin(), deleteMovieDto.getPasswd()));
        if(user.isEmpty()){
            return new ResponseDTO(false,"User does not exist");
        }
        if(user.get().getIs_adm()){
            movieRepository.deleteByMovie_id(deleteMovieDto.getMovie_id());
            reviewRepository.deleteByMovie_id(deleteMovieDto.getMovie_id());
            return new ResponseDTO(true,"Movie and reviews of that movie have been deleted");
        }
        return new ResponseDTO(false,"You do not have Moderator permissions");
    }
}
