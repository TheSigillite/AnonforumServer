package com.tijo.anonforum.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie implements Serializable {

    @Id
    @Column(name = "movie_id")
    private Long movie_id;

    @Column(name = "title")
    private String title;

    @Column(name = "cover")
    private String cover;

    @Column(name = "director")
    private String director;

    @Column(name = "premiere")
    private Integer premiere;

}
