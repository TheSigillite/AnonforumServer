package com.tijo.anonforum.domain.mapper.movie

import com.tijo.anonforum.domain.dto.review.NewReviewDTO
import com.tijo.anonforum.domain.dto.review.ReviewDTO
import com.tijo.anonforum.domain.entity.Review
import com.tijo.anonforum.domain.entity.User
import com.tijo.anonforum.domain.mapper.TwoToOneMapper
import com.tijo.anonforum.domain.mapper.review.NewReviewMapper
import com.tijo.anonforum.domain.mapper.review.ReviewMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ReviewMappersTest extends Specification{

    @Autowired
    TwoToOneMapper<Review, User, NewReviewDTO> newReviewMapper

    @Autowired
    TwoToOneMapper<ReviewDTO, Review, User> reviewDTOMapper

    def "Should load correct beans"(){
        expect:
            newReviewMapper instanceof NewReviewMapper
            reviewDTOMapper instanceof ReviewMapper
    }

    def "Should convert NewReviewDTO and User into Review"(){
        given:
            NewReviewDTO newReviewDTO = new NewReviewDTO()
            newReviewDTO.setLogin("Test1")
            newReviewDTO.setPasswd("Test1")
            newReviewDTO.setRev("This is a review")
            newReviewDTO.setMovie_id(1)
            User user = new User(1,"Test1","Test1",false)
        when:
            def out = newReviewMapper.convert(user,newReviewDTO)
        then:
            out == new Review(null,1,1,"This is a review")
    }

    def "Should convert Review and User into ReviewDTO"(){
        given:
            Review review = new Review(1,1,1,"This is a review")
            User user = new User(1,"Test1","Test1",false)
            ReviewDTO reviewDTO = ReviewDTO.builder().login("Test1").movie_id(1).rev("This is a review").rev_id(1).build()
        when:
            def out = reviewDTOMapper.convert(review,user)
        then:
            out == reviewDTO
    }

}
