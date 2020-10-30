package com.tijo.anonforum.domain.dto.movie;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "Typ używany przy usuwaniu filmu")
public class DeleteMovieDTO implements Serializable {
    @ApiModelProperty(notes = "Login usuwającego")
    private String login;
    @ApiModelProperty(notes = "Hasło usuwającego")
    private String passwd;
    @ApiModelProperty(notes = "Id filmu do usunięcia")
    private Long movie_id;

    public DeleteMovieDTO(){

    }
}
