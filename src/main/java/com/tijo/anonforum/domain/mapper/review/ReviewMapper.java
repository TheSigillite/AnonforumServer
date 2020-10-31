package com.tijo.anonforum.domain.mapper.review;

import com.tijo.anonforum.domain.dto.review.ReviewDTO;
import com.tijo.anonforum.domain.entity.Review;
import com.tijo.anonforum.domain.entity.User;
import com.tijo.anonforum.domain.mapper.TwoToOneMapper;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper implements TwoToOneMapper<ReviewDTO, Review, User> {

    @Override
    public ReviewDTO convert(Review review, User user) {
        return ReviewDTO.builder()
                .rev_id(review.getRev_id())
                .movie_id(review.getMovie_id())
                .login(user.getLogin())
                .rev(review.getRev())
                .build();
    }
}
