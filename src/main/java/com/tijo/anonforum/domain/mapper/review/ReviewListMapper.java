package com.tijo.anonforum.domain.mapper.review;

import com.tijo.anonforum.domain.dto.review.ReviewDTO;
import com.tijo.anonforum.domain.entity.Review;
import com.tijo.anonforum.domain.entity.User;
import com.tijo.anonforum.domain.mapper.TwoToOneMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewListMapper implements TwoToOneMapper<List<ReviewDTO>,List<Review>,List<User>> {
    @Override
    public List<ReviewDTO> convert(List<Review> reviews, List<User> users) {
        return reviews.stream().map(review -> {
            String login = "";
            for (User user: users) {
                if (review.getAcc_id().equals(user.getAcc_id())) {
                    login = user.getLogin();
                }
            }
            return ReviewDTO.builder()
                    .rev_id(review.getRev_id())
                    .movie_id(review.getMovie_id())
                    .login(login)
                    .rev(review.getRev())
                    .build();
        }).collect(Collectors.toList());
    }
}
