package com.tijo.anonforum.controller;

import com.tijo.anonforum.domain.dto.ResponseDTO;
import com.tijo.anonforum.domain.dto.user.LoginUserDTO;
import com.tijo.anonforum.domain.dto.user.LoginUserResponseDTO;
import com.tijo.anonforum.domain.dto.user.ModUserDTO;
import com.tijo.anonforum.service.UsersService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/users")
public class UsersController {

    private final UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService){
        this.usersService = usersService;
    }

    @ApiOperation(value = "Loguje użytkownika"
            , notes = "Zwraca czy użytkownik jets moderatorem, jeśli nie istnieje zwraca błąd i pustą odpowiedź")
    @CrossOrigin
    @PostMapping(value = "/login",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<LoginUserResponseDTO> loginUser(
            @ApiParam(value = "Login i hasło użytkownika",required = true)
            @RequestBody LoginUserDTO loginUserDto){
        LoginUserResponseDTO loginUser = usersService.loginUser(loginUserDto);
        System.out.println(loginUser.toString());
        return new ResponseEntity<>(loginUser, HttpStatus.OK);
    }

    @ApiOperation(value = "Dodaje nowego użytkownika", response = ResponseDTO.class)
    @CrossOrigin
    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseDTO> registerUser(
            @ApiParam(value = "Login i hasło nowego użytkownika", required = true)
            @RequestBody LoginUserDTO loginUserDto){
        ResponseDTO registerresponse = usersService.registerUser(loginUserDto);
        if(registerresponse.getWasSuccesful()){
            return new ResponseEntity<>(registerresponse,HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(registerresponse,HttpStatus.BAD_REQUEST);
        }

    }

    @ApiOperation(value = "Nadaje użytkownikowi uprawinienia moderatora"
            , notes = "Operacja musi zostać przeprowadzona przez istniejącego moderatora", response = ResponseDTO.class)
    @CrossOrigin
    @PutMapping(value = "/moderation",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ResponseDTO> modUser(
            @ApiParam(value = "Login nowego moderatora i dane moderatora nadającego uprawnienia")
            @RequestBody ModUserDTO modUserDto){
        ResponseDTO modResponse = usersService.modUser(modUserDto);
        if(modResponse.getWasSuccesful()){
            return new ResponseEntity<>(modResponse,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(modResponse,HttpStatus.FORBIDDEN);
        }

    }
}
