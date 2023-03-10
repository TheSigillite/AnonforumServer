package com.tijo.anonforum.domain.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Builder
@ToString
@EqualsAndHashCode
@Data
@ApiModel(description = "Odpowiedź na zalogowanie użytkownika")
public class LoginUserResponseDTO implements Serializable {
    @ApiModelProperty(notes = "Czy użytkownik jest moderatorem")
    private Boolean isAdmin;
}
