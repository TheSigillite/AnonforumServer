package com.tijo.anonforum.domain.dto.movie;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;

@Data
@Builder
@ApiModel(description = "Typ w jakim zwracane są filmy")
public class MovieDTO implements Serializable {
    @ApiModelProperty(notes = "Id filmu")
    private Long movie_id;
    @ApiModelProperty(notes = "Tytuł filmu")
    private String title;
    @ApiModelProperty(notes = "Adres URL zdjęcia okładkowego")
    private String cover;
    @ApiModelProperty(notes = "Reżyser filmu")
    private String director;
    @ApiModelProperty(notes = "Data premiery")
    private Integer premiere;

    public MovieDTO(){

    }
}
