package com.tijo.anonforum.domain.repository;

import com.tijo.anonforum.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ReviewRepository extends JpaRepository<Review, Long>, CrudRepository<Review, Long> {
    @Modifying
    void deleteByRev_id(Long rev_id);

    List<Review> findReviewByMovie_id(Long movie_id);
}
