package com.tijo.anonforum.controller;

import com.tijo.anonforum.domain.dto.ResponseDTO;
import com.tijo.anonforum.domain.dto.movie.DeleteMovieDTO;
import com.tijo.anonforum.domain.dto.movie.MovieActionDTO;
import com.tijo.anonforum.domain.dto.movie.MovieDTO;
import com.tijo.anonforum.service.MoviesService;
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

    @Autowired
    public MoviesController(MoviesService moviesService) {
        this.moviesService = moviesService;
    }

    @ApiOperation(value = "Zwraca liste wszystkich filmów na stronie")
    @CrossOrigin
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<MovieDTO>> getMovies(){
        return new ResponseEntity<>(moviesService.findAll(), HttpStatus.OK);
    }

    @ApiOperation(value = "Dodaj nowy film",notes = "Metoda weryfikuje czy dodający ma uprawnienia",response = ResponseDTO.class)
    @CrossOrigin
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

    @ApiOperation(value = "Edytuje istniejący film",notes = "Metoda weryfikuje czy edytujący ma uprawnienia", response = ResponseDTO.class)
    @CrossOrigin
    @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseDTO> updateMovie(
            @ApiParam(value = "Detale edytowanego filmu i dane edytującego", required = true)
            @RequestBody MovieActionDTO updateMovieDto){
        ResponseDTO updated = moviesService.updateMovie(updateMovieDto);
        if(updated.getWasSuccesful()){
            return new ResponseEntity<>(updated,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(updated,HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Usuwa istniejący film", notes = "Usuwa również recenzje tego filmu", response = ResponseDTO.class)
    @CrossOrigin
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseDTO> deleteMovie(
            @ApiParam(value = "Id usuwanego pliku i dane usuwającego", required = true)
            @RequestBody DeleteMovieDTO deleteMovieDto){
        ResponseDTO deleted = moviesService.deleteMovie(deleteMovieDto);
        if(deleted.getWasSuccesful()){
            return new ResponseEntity<>(deleted,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(deleted,HttpStatus.NOT_FOUND);
        }
    }
}
