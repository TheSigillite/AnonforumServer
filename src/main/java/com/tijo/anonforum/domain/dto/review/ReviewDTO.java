package com.tijo.anonforum.domain.dto.review;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;

import java.io.Serializable;

@Builder
@ApiModel(description = "Model recenzji która będzie wyświetlana pod filmem")
public class ReviewDTO implements Serializable {
    @ApiModelProperty(notes = "Id recenzji")
    private Long rev_id;
    @ApiModelProperty(notes = "Id filmu pod którym znajduje się recenzja")
    private Long movie_id;
    @ApiModelProperty(notes = "Login osoby która zostawiła recenzje")
    private String login;
    @ApiModelProperty(notes = "Treść recenzji")
    private String rev;

}
