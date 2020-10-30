package com.tijo.anonforum.domain.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reviews")
@Data
@Builder
public class Review implements Serializable {
    @Id
    @Column(name = "rev_id")
    @GeneratedValue
    private Long rev_id;

    @Column(name = "movie_id")
    private Long movie_id;

    @Column(name = "acc_id")
    private Long acc_id;

    @Column(name = "rev")
    private String rev;

    public Review(){
    }
}
