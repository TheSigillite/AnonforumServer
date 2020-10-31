package com.tijo.anonforum.domain.mapper.review;

import com.tijo.anonforum.domain.dto.review.NewReviewDTO;
import com.tijo.anonforum.domain.entity.Review;
import com.tijo.anonforum.domain.entity.User;
import com.tijo.anonforum.domain.mapper.TwoToOneMapper;
import org.springframework.stereotype.Component;

@Component
public class NewReviewMapper implements TwoToOneMapper<Review, User, NewReviewDTO> {

    @Override
    public Review convert(User user, NewReviewDTO newReviewDto) {
        return  Review.builder()
                .acc_id(user.getAcc_id())
                .movie_id(newReviewDto.getMovie_id())
                .rev(newReviewDto.getRev())
                .build();
    }
}
