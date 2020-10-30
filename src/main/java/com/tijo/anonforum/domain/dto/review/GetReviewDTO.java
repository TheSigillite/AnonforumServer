package com.tijo.anonforum.domain.dto.review;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "Typ używany do pobrania recenzji")
public class GetReviewDTO implements Serializable {
    @ApiModelProperty(notes = "Id filmu")
    private Long movie_id;

    public GetReviewDTO() {
    }
}
