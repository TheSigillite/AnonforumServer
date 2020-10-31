package com.tijo.anonforum.domain.repository;

import com.tijo.anonforum.domain.entity.Movie;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional()
@Profile("!test")
public interface MovieRepository extends JpaRepository<Movie,Long>, CrudRepository<Movie,Long> {
    @Modifying
    @Query(value = "insert into movies(title,cover,director,premiere) values (:title, :cover, :director, :premiere)", nativeQuery = true)
    void insertMovie(@Param("title") String title, @Param("cover") String cover, @Param("director") String director, @Param("premiere") Integer premiere);
    @Modifying
    @Query(value = "DELETE FROM movies m WHERE m.movie_id = :movie_id", nativeQuery = true)
    void deleteByMovie_id(@Param("movie_id") Long movie_id);

    @Query(value = "SELECT * FROM movies m WHERE m.title = :title AND m.director = :director AND m.premiere = :premiere", nativeQuery = true)
    Movie findMovieByTitleAndDirectorAndPremiere(@Param("title") String title,@Param("director") String director,@Param("premiere") Integer premiere);

    @Query(value = "SELECT * FROM movies m WHERE m.movie_id = :movie_id", nativeQuery = true)
    Movie findMovieByMovie_id(@Param("movie_id") Long movie_id);


}
