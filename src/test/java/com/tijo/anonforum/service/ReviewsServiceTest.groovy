package com.tijo.anonforum.service

import com.tijo.anonforum.domain.dto.ResponseDTO
import com.tijo.anonforum.domain.dto.review.DeleteReviewDTO
import com.tijo.anonforum.domain.dto.review.NewReviewDTO
import com.tijo.anonforum.domain.dto.review.ReviewDTO
import com.tijo.anonforum.domain.entity.Review
import com.tijo.anonforum.domain.entity.User
import com.tijo.anonforum.domain.mapper.TwoToOneMapper
import com.tijo.anonforum.domain.repository.ReviewRepository
import com.tijo.anonforum.domain.repository.UserRepository
import com.tijo.anonforum.service.impl.ReviewsServiceImpl
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

import static org.mockito.Mockito.when

@SpringBootTest
class ReviewsServiceTest extends Specification{
    @Autowired
    private TwoToOneMapper<ReviewDTO, Review, User> reviewMapper
    @Autowired
    private TwoToOneMapper<Review, User, NewReviewDTO> newReviewMapper
    @Mock
    UserRepository userRepository
    @Mock
    ReviewRepository reviewRepository

    private ReviewsService reviewsService

    private testReview = new Review(1,1,1,"Test Review")
    private secondtestReview = new Review(2,1,2,"Test 2")
    private testUser = new User(1,"TestUser1","TestPass1",false)
    private testModerator = new User(2,"TestUser2","TestPass2",true)
    def setup(){
        when(userRepository.findUserByLoginAndPasswd("TestUser1","TestPass1")).thenReturn(testUser)
        when(userRepository.findUserByLoginAndPasswd("TestUser2","TestPass2")).thenReturn(testModerator)
        when(reviewRepository.findByRev_id(1)).thenReturn(testReview)
        when(reviewRepository.findByRev_id(2)).thenReturn(secondtestReview)
        reviewsService = new ReviewsServiceImpl(reviewMapper,newReviewMapper,reviewRepository,userRepository)
    }

    def "Should not add review if user does not exist"(){
        given:
            NewReviewDTO newReviewDTO = new NewReviewDTO()
            newReviewDTO.setLogin("TestUser3")
            newReviewDTO.setPasswd("TestPass3")
            newReviewDTO.setMovie_id(1)
            newReviewDTO.setRev("Lorem Ipsum")
        when:
            ResponseDTO out = reviewsService.addReview(newReviewDTO)
        then:
            out == new ResponseDTO(false,"User does not exist")
    }

    def "Should add review when user exists"(){
        given:
            NewReviewDTO newReviewDTO = new NewReviewDTO()
            newReviewDTO.setLogin("TestUser1")
            newReviewDTO.setPasswd("TestPass1")
            newReviewDTO.setMovie_id(1)
            newReviewDTO.setRev("Lorem Ipsum")
        when:
            ResponseDTO out = reviewsService.addReview(newReviewDTO)
        then:
            out == new ResponseDTO(true,"Review was posted, refresh page to see it")
    }

    def "Should not delete review if user does not exist"(){
        given:
            DeleteReviewDTO deleteReviewDTO = new DeleteReviewDTO()
            deleteReviewDTO.setLogin("TestUser3")
            deleteReviewDTO.setPasswd("TestPass3")
            deleteReviewDTO.setRev_id(1)
        when:
            ResponseDTO out = reviewsService.deleteReview(deleteReviewDTO)
        then:
            out == new ResponseDTO(false,"User does not exist")
    }

    def "Should not delete review when user is not the author and moderator"(){
        given:
            DeleteReviewDTO deleteReviewDTO = new DeleteReviewDTO()
            deleteReviewDTO.setLogin("TestUser1")
            deleteReviewDTO.setPasswd("TestPass1")
            deleteReviewDTO.setRev_id(2)
        when:
        ResponseDTO out = reviewsService.deleteReview(deleteReviewDTO)
        then:
        out == new ResponseDTO(false,"You do not have permissions to delete this review")
    }

    def "Should delete review when user has moderator priviliges"(){
        given:
            DeleteReviewDTO deleteReviewDTO = new DeleteReviewDTO()
            deleteReviewDTO.setLogin("TestUser2")
            deleteReviewDTO.setPasswd("TestPass2")
            deleteReviewDTO.setRev_id(1)
        when:
            ResponseDTO out = reviewsService.deleteReview(deleteReviewDTO)
        then:
            out == new ResponseDTO(true,"Review Deleted")
    }

    def "Should delete review when user is the author"(){
        given:
            DeleteReviewDTO deleteReviewDTO = new DeleteReviewDTO()
            deleteReviewDTO.setLogin("TestUser1")
            deleteReviewDTO.setPasswd("TestPass1")
            deleteReviewDTO.setRev_id(1)
        when:
            ResponseDTO out = reviewsService.deleteReview(deleteReviewDTO)
        then:
            out == new ResponseDTO(true,"Review Deleted")
    }
}
