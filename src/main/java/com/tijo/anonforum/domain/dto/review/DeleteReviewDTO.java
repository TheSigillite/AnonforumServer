package com.tijo.anonforum.domain.dto.review;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "Typ używany do usunięcia recenzji")
public class DeleteReviewDTO implements Serializable {
    @ApiModelProperty(notes = "Login usuwającego")
    private String login;
    @ApiModelProperty(notes = "Hasło usuwającego")
    private String passwd;
    @ApiModelProperty(notes = "Id recenzji do usunięcia")
    private Long rev_id;

    public DeleteReviewDTO() {
    }
}
