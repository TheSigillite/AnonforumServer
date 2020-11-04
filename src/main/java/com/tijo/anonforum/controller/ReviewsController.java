package com.tijo.anonforum.controller;

import com.tijo.anonforum.domain.dto.ResponseDTO;
import com.tijo.anonforum.domain.dto.review.DeleteReviewDTO;
import com.tijo.anonforum.domain.dto.review.NewReviewDTO;
import com.tijo.anonforum.domain.dto.review.ReviewDTO;
import com.tijo.anonforum.service.ReviewsService;
import com.tijo.anonforum.service.impl.ReviewsServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/reviews")
public class ReviewsController {
    Logger logger = LoggerFactory.getLogger(ReviewsServiceImpl.class);

    private final ReviewsService reviewsService;

    @Autowired
    public ReviewsController(ReviewsService reviewsService){
        this.reviewsService = reviewsService;
    }


    @ApiOperation(value = "Zwraca Liste recenzji danego filmu")
    @CrossOrigin
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<ReviewDTO>> getMovieReviews(
            @ApiParam(value = "Id filmu dla którego mają być zwrócone recenzje", required = true)
            @RequestParam(name = "movieid") Long movieid){
        List<ReviewDTO> sevs = reviewsService.findReviewsForMovie(movieid);
        logger.info(sevs.toString());
        return new ResponseEntity<>(sevs, HttpStatus.OK);
    }

    @ApiOperation(value = "Dodaje nową recenzje", notes = "Metoda weryfikuje czy konto dodające recenzje istnieje",response = ResponseDTO.class)
    @CrossOrigin
    @PostMapping(value = "/new", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseDTO> newReview(
            @ApiParam(value = "Treść recenzji, id filmu i detale konta dodającego ocene", required = true)
            @RequestBody NewReviewDTO newReviewDto){
        ResponseDTO created = reviewsService.addReview(newReviewDto);
        if(created.getWasSuccesful()){
            return new ResponseEntity<>(created,HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(created,HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @ApiOperation(value = "Usuwa recenzje", notes = "Usuwa pojedynczą recenzje danego filmu",response = ResponseDTO.class)
    @CrossOrigin
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseDTO> deleteReview(
            @ApiParam(value = "Informacje o recenzji i kącie usuwającym recenzje")
            @RequestBody DeleteReviewDTO deleteReviewDto){
        ResponseDTO deleted = reviewsService.deleteReview(deleteReviewDto);
        if(deleted.getWasSuccesful()){
            return new ResponseEntity<>(deleted,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(deleted,HttpStatus.FORBIDDEN);
        }
    }
}
