package com.tijo.anonforum.domain.repository;

import com.tijo.anonforum.domain.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional()
public interface MovieRepository extends JpaRepository<Movie,Long>, CrudRepository<Movie,Long> {
    @Modifying
    void deleteByMovie_id(Long movie_id);

    Movie findMovieByMovie_id(Long movie_id);


}
