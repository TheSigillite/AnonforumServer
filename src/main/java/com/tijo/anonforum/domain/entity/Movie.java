package com.tijo.anonforum.domain.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "movies")
@Data
@Builder
public class Movie implements Serializable {

    @Id
    @Column(name = "movie_id")
    @GeneratedValue
    private Long movie_id;

    @Column(name = "title")
    private String title;

    @Column(name = "cover")
    private String cover;

    @Column(name = "director")
    private String director;

    @Column(name = "premiere")
    private Integer premiere;

    public Movie() {
    }

}
