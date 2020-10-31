package com.tijo.anonforum.service.impl;

import com.tijo.anonforum.domain.dto.ResponseDTO;
import com.tijo.anonforum.domain.dto.review.DeleteReviewDTO;
import com.tijo.anonforum.domain.dto.review.NewReviewDTO;
import com.tijo.anonforum.domain.dto.review.ReviewDTO;
import com.tijo.anonforum.domain.entity.Review;
import com.tijo.anonforum.domain.entity.User;
import com.tijo.anonforum.domain.mapper.TwoToOneMapper;
import com.tijo.anonforum.domain.repository.ReviewRepository;
import com.tijo.anonforum.domain.repository.UserRepository;
import com.tijo.anonforum.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewsServiceImpl implements ReviewsService {

    public final TwoToOneMapper<List<ReviewDTO>,List<Review>,List<User>> reviewListMapper;
    public final TwoToOneMapper<ReviewDTO, Review, User> reviewMapper;
    public final TwoToOneMapper<Review, User, NewReviewDTO> newReviewMapper;
    public final ReviewRepository reviewRepository;
    public final UserRepository userRepository;

    @Autowired
    public ReviewsServiceImpl(TwoToOneMapper<List<ReviewDTO>, List<Review>, List<User>> reviewListMapper
            , TwoToOneMapper<ReviewDTO, Review, User> reviewMapper
            , TwoToOneMapper<Review, User, NewReviewDTO> newReviewMapper
            , ReviewRepository reviewRepository
            , UserRepository userRepository) {
        this.reviewListMapper = reviewListMapper;
        this.reviewMapper = reviewMapper;
        this.newReviewMapper = newReviewMapper;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ReviewDTO> findReviewsForMovie(Long movieid) {
        List<Review> reviews = reviewRepository.findReviewsByMovie_id(movieid);
        return reviews.stream()
                .map(review -> reviewMapper.convert(review,userRepository.findUserByAcc_id(review.getAcc_id())))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseDTO addReview(NewReviewDTO newReviewDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByLoginAndPasswd(newReviewDto.getLogin(), newReviewDto.getPasswd()));
        if(user.isEmpty()){
            return new ResponseDTO(false,"User does not exist");
        }
        Review review = newReviewMapper.convert(user.get(),newReviewDto);
        reviewRepository.insertReview(review.getMovie_id(),review.getAcc_id(),review.getRev());
        return new ResponseDTO(true,"Review was posted, refresh page to see it");
    }

    @Override
    public ResponseDTO deleteReview(DeleteReviewDTO deleteReviewDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByLoginAndPasswd(deleteReviewDto.getLogin(), deleteReviewDto.getPasswd()));
        Review review = reviewRepository.findByRev_id(deleteReviewDto.getRev_id());
        if(user.isEmpty()){
            return new ResponseDTO(false,"User does not exist");
        }
        if(review.getAcc_id().equals(user.get().getAcc_id()) || user.get().getIs_adm()){
            reviewRepository.deleteByRev_id(review.getRev_id());
            return new ResponseDTO(true,"Review Deleted");
        }
        return new ResponseDTO(false,"You do not have permissions to delete this review");
    }
}
