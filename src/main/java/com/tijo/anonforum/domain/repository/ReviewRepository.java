package com.tijo.anonforum.domain.repository;

import com.tijo.anonforum.domain.entity.Review;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional()
@Profile("!test")
public interface ReviewRepository extends JpaRepository<Review, Long>, CrudRepository<Review, Long> {

    @Query(value = "SELECT * FROM reviews r WHERE r.rev_id = :rev_id", nativeQuery = true)
    Review findByRev_id(@Param("rev_id") Long rev_id);

    @Modifying
    @Query(value = "DELETE FROM reviews r WHERE r.movie_id = :movie_id", nativeQuery = true)
    void deleteByMovie_id(@Param("movie_id") Long movie_id);

    @Modifying
    @Query(value = "DELETE FROM reviews r WHERE r.rev_id = :rev_id", nativeQuery = true)
    void deleteByRev_id(@Param("rev_id") Long rev_id);

    @Query(value = "SELECT * FROM reviews r WHERE r.movie_id = :movie_id", nativeQuery = true)
    List<Review> findReviewsByMovie_id(@Param("movie_id") Long movie_id);

    @Modifying
    @Query(value = "INSERT INTO reviews(movie_id, acc_id, rev) VALUES (:movie_id, :acc_id, :rev)", nativeQuery = true)
    void insertReview(@Param("movie_id") Long movie_id, @Param("acc_id") Long acc_id, @Param("rev") String rev);
}
