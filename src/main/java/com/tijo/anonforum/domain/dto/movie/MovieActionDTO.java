package com.tijo.anonforum.domain.dto.movie;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.io.Serializable;

@Data
@ApiModel(description = "Klasa używana do dodawania i modyfikacji filmu")
public class MovieActionDTO implements Serializable {
    @ApiModelProperty(notes = "Login dodającego")
    private String login;
    @ApiModelProperty(notes = "Hasło dodającego")
    private String passwd;
    @ApiModelProperty(notes = "Dane dodawanego/modyfikowanego filmu")
    private MovieDTO movieDTO;

    public MovieActionDTO(){
    }
}
