package com.tijo.anonforum.service;

import com.tijo.anonforum.domain.dto.ResponseDTO;
import com.tijo.anonforum.domain.dto.review.NewReviewDTO;
import com.tijo.anonforum.domain.dto.review.ReviewDTO;

import java.util.List;

public interface ReviewsService {
    List<ReviewDTO> findReviewsForMovie(Long movieid);

    ResponseDTO addReview(NewReviewDTO newReviewDto);

    ResponseDTO deleteReview(String login, String passwd, Long rev_id);
}
