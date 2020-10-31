package com.tijo.anonforum.service;

import com.tijo.anonforum.domain.dto.ResponseDTO;
import com.tijo.anonforum.domain.dto.user.LoginUserDTO;
import com.tijo.anonforum.domain.dto.user.LoginUserResponseDTO;
import com.tijo.anonforum.domain.dto.user.ModUserDTO;

public interface UsersService {
    LoginUserResponseDTO loginUser(LoginUserDTO loginUserDto);

    ResponseDTO registerUser(LoginUserDTO loginUserDto);

    ResponseDTO modUser(ModUserDTO modUserDto);
}
