package com.tijo.anonforum.domain.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "Dane do dodawania moderatora")
public class ModUserDTO implements Serializable {
    @ApiModelProperty(notes = "Login dodającego")
    private String login;
    @ApiModelProperty(notes = "Hasło dodającego")
    private String passwd;
    @ApiModelProperty(notes = "Login użytkownika który ma zostać nowym moderatorem")
    private String usertomod;

    public ModUserDTO(){
    }
}
