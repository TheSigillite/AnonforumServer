package com.tijo.anonforum.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "Detale o przebiegu operacji")
public class ResponseDTO implements Serializable {
    @ApiModelProperty(notes = "Informacja czy operacja przebiegła pomyślnie")
    private Boolean wasSuccesful;
    @ApiModelProperty(notes = "Komunikat z którym zakończyło się wykonanie operacji")
    private String details;

    public ResponseDTO(Boolean wasSuccesful, String details) {
        this.wasSuccesful = wasSuccesful;
        this.details = details;
    }
}
