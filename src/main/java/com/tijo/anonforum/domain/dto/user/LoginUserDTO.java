package com.tijo.anonforum.domain.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "Objekt używany przy logowaniu i rejestracj")
public class LoginUserDTO implements Serializable {
    @ApiModelProperty(notes = "Login użytkownika")
    private String login;
    @ApiModelProperty(notes = "Hasło użytkownika")
    private String passwd;

    public LoginUserDTO(){
    }

}
