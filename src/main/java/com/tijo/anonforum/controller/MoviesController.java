package com.tijo.anonforum.controller;

import com.tijo.anonforum.domain.dto.ResponseDTO;
import com.tijo.anonforum.domain.dto.movie.DeleteMovieDTO;
import com.tijo.anonforum.domain.dto.movie.MovieActionDTO;
import com.tijo.anonforum.domain.dto.movie.MovieDTO;
import com.tijo.anonforum.domain.dto.review.ReviewDTO;
import com.tijo.anonforum.service.MoviesService;
import com.tijo.anonforum.service.ReviewsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/movies")
public class MoviesController {

    private final MoviesService moviesService;
    private final ReviewsService reviewsService;

    @Autowired
    public MoviesController(MoviesService moviesService, ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
        this.moviesService = moviesService;
    }

    @ApiOperation(value = "Zwraca liste wszystkich filmów na stronie")
    @CrossOrigin
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<MovieDTO>> getMovies(){
        return new ResponseEntity<>(moviesService.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Dodaj nowy film",notes = "Metoda weryfikuje czy dodający ma uprawnienia",response = ResponseDTO.class)
    @CrossOrigin
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseDTO> addMovie(
            @ApiParam(value = "Detale nowego filmu i dane dodającego", required = true)
            @RequestBody MovieActionDTO newMovieDto){
        ResponseDTO wascreated = moviesService.addMovie(newMovieDto);
        if(wascreated.getWasSuccesful()){
            return new ResponseEntity<>(wascreated,HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(wascreated,HttpStatus.FORBIDDEN);
        }
    }

    @ApiOperation(value = "Zwraca pojedynczy film")
    @CrossOrigin
    @GetMapping(value = "/{movie_id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<MovieDTO> getMovies(
            @ApiParam(value = "Id żądanego filmu", required = true)
            @PathVariable Long movie_id){
        return new ResponseEntity<>(moviesService.getOne(movie_id), HttpStatus.OK);
    }

    @ApiOperation(value = "Zwraca Liste recenzji danego filmu")
    @CrossOrigin
    @GetMapping(value = "/{movie_id}/reviews",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ReviewDTO>> getMovieReviews(
            @ApiParam(value = "Id filmu dla którego mają być zwrócone recenzje", required = true)
            @PathVariable Long movie_id){
        List<ReviewDTO> revs = reviewsService.findReviewsForMovie(movie_id);
        return new ResponseEntity<>(revs, HttpStatus.OK);
    }

    @ApiOperation(value = "Edytuje istniejący film",notes = "Metoda weryfikuje czy edytujący ma uprawnienia", response = ResponseDTO.class)
    @CrossOrigin
    @PutMapping(value = "/{movie_id}",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseDTO> updateMovie(
            @ApiParam(value = "Id edytowanego filmu", required = true)
            @PathVariable Long movie_id,
            @ApiParam(value = "Detale edytowanego filmu i dane edytującego", required = true)
            @RequestBody MovieActionDTO updateMovieDto){
        updateMovieDto.getMovieDTO().setMovie_id(movie_id);
        ResponseDTO updated = moviesService.updateMovie(updateMovieDto);
        if(updated.getWasSuccesful()){
            return new ResponseEntity<>(updated,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(updated,HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Usuwa istniejący film", notes = "Usuwa również recenzje tego filmu", response = ResponseDTO.class)
    @CrossOrigin
    @DeleteMapping(value = "/{movie_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseDTO> deleteMovie(
            @ApiParam(value = "Id filmu który ma zostać usunięty", required = true)
            @PathVariable Long movie_id,
            @ApiParam(value = "Login usuwającego")
            @RequestHeader(value="Usr-Login") String login,
            @ApiParam(value = "Hasło usuwającego")
            @RequestHeader(value="Usr-Pass") String passwd){
        ResponseDTO deleted = moviesService.deleteMovie(login,passwd,movie_id);
        if(deleted.getWasSuccesful()){
            return new ResponseEntity<>(deleted,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(deleted,HttpStatus.NOT_FOUND);
        }
    }
}
