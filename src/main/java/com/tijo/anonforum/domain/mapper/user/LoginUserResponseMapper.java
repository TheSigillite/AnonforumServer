package com.tijo.anonforum.domain.mapper.user;

import com.tijo.anonforum.domain.dto.user.LoginUserResponseDTO;
import com.tijo.anonforum.domain.entity.User;
import com.tijo.anonforum.domain.mapper.OneToOneMapper;
import org.springframework.stereotype.Component;

@Component
public class LoginUserResponseMapper implements OneToOneMapper<LoginUserResponseDTO, User> {
    @Override
    public LoginUserResponseDTO convert(User user) {
        System.out.println(user.toString());
        return LoginUserResponseDTO.builder().isAdmin(user.getIs_adm()).build();
    }
}
