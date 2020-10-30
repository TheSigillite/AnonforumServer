package com.tijo.anonforum.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review implements Serializable {
    @Id
    @Column(name = "rev_id")
    private Long rev_id;

    @Column(name = "movie_id")
    private Long movie_id;

    @Column(name = "acc_id")
    private Long acc_id;

    @Column(name = "rev")
    private String rev;

}
