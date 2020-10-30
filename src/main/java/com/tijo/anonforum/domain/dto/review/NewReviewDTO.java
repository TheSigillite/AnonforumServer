package com.tijo.anonforum.domain.dto.review;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "Typ używany do dodawania recenzji")
public class NewReviewDTO implements Serializable {
    @ApiModelProperty(notes = "Login dodającego")
    private String login;
    @ApiModelProperty(notes = "Hasło dodającego")
    private String passwd;
    @ApiModelProperty(notes = "Id filmu pod którym ma sie znaleźć recenzja")
    private Long movie_id;
    @ApiModelProperty(notes = "Treść recenzji")
    private String rev;

    public NewReviewDTO() {
    }
}
