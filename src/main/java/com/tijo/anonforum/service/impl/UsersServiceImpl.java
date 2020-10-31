package com.tijo.anonforum.service.impl;

import com.tijo.anonforum.domain.dto.ResponseDTO;
import com.tijo.anonforum.domain.dto.user.LoginUserDTO;
import com.tijo.anonforum.domain.dto.user.LoginUserResponseDTO;
import com.tijo.anonforum.domain.dto.user.ModUserDTO;
import com.tijo.anonforum.domain.entity.User;
import com.tijo.anonforum.domain.mapper.OneToOneMapper;
import com.tijo.anonforum.domain.repository.UserRepository;
import com.tijo.anonforum.service.UsersService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    public final UserRepository userRepository;
    public final OneToOneMapper<LoginUserResponseDTO, User> loginUserResponseMapper;
    public final OneToOneMapper<User, User> modUserMapper;

    public UsersServiceImpl(UserRepository userRepository
            , OneToOneMapper<LoginUserResponseDTO, User> loginUserResponseMapper
            , OneToOneMapper<User, User> modUserMapper) {
        this.userRepository = userRepository;
        this.loginUserResponseMapper = loginUserResponseMapper;
        this.modUserMapper = modUserMapper;
    }

    @Override
    public LoginUserResponseDTO loginUser(LoginUserDTO loginUser) {
        Optional<User> logingin = Optional.ofNullable(userRepository.findUserByLoginAndPasswd(loginUser.getLogin(),loginUser.getPasswd()));
        if(logingin.isEmpty()){
            return null;
        }
        return loginUserResponseMapper.convert(logingin.get());
    }

    @Override
    public ResponseDTO registerUser(LoginUserDTO registerUser) {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByLogin(registerUser.getLogin()));
        if(user.isPresent()){
            return new ResponseDTO(false,"This username is already taken");
        }
        userRepository.insertUser(registerUser.getLogin(),registerUser.getPasswd());
        return new ResponseDTO(true,"You were registered");
    }

    @Override
    public ResponseDTO modUser(ModUserDTO modUser) {
        Optional<User> granter = Optional.ofNullable(userRepository.findUserByLoginAndPasswd(modUser.getLogin(), modUser.getPasswd()));
        if(granter.isEmpty()){
            return new ResponseDTO(false,"User granting permission does not exist");
        } else {
            if(granter.get().getIs_adm()){
                Optional<User> newmod = Optional.ofNullable(userRepository.findUserByLogin(modUser.getUsertomod()));
                if(newmod.isEmpty()){
                    return new ResponseDTO(false,"User to grant Moderator status does not exist");
                }
                if(newmod.get().getIs_adm()){
                    return new ResponseDTO(false,"User is already a Moderator");
                }
                userRepository.save(modUserMapper.convert(newmod.get()));
                return new ResponseDTO(true,"User has been successfully made Moderator");
            } else {
                return new ResponseDTO(false,"You do not have Moderator permission");
            }
        }
    }
}
