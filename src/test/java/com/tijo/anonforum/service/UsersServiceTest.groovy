package com.tijo.anonforum.service

import com.tijo.anonforum.domain.dto.ResponseDTO
import com.tijo.anonforum.domain.dto.user.LoginUserDTO
import com.tijo.anonforum.domain.dto.user.LoginUserResponseDTO
import com.tijo.anonforum.domain.dto.user.ModUserDTO
import com.tijo.anonforum.domain.entity.User
import com.tijo.anonforum.domain.mapper.OneToOneMapper
import com.tijo.anonforum.domain.repository.UserRepository
import com.tijo.anonforum.service.impl.UsersServiceImpl
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import static org.mockito.Mockito.when

@SpringBootTest
class UsersServiceTest extends Specification{

    @Mock
    UserRepository userRepository
    @Autowired
    OneToOneMapper<LoginUserResponseDTO, User> loginUserResponseMapper
    @Autowired
    OneToOneMapper<User, User> modUserMapper

    User normalUser = new User(1,"TestUser1","TestPass1",false)
    User moderator1 = new User(2,"TestMod1","TestPass2",true)
    User moderator2 = new User(3,"TestMod2","TestPass3",true)

    UsersService usersService

    def setup(){
        when(userRepository.findUserByLoginAndPasswd("TestUser1","TestPass1")).thenReturn(normalUser)
        when(userRepository.findUserByLoginAndPasswd("TestMod1","TestPass2")).thenReturn(moderator1)
        when(userRepository.findUserByLoginAndPasswd("TestMod2","TestPass3")).thenReturn(moderator2)
        when(userRepository.findUserByLogin("TestUser1")).thenReturn(normalUser)
        when(userRepository.findUserByLogin("TestMod1")).thenReturn(moderator1)
        when(userRepository.findUserByLogin("TestMod2")).thenReturn(moderator2)
        usersService = new UsersServiceImpl(userRepository,loginUserResponseMapper,modUserMapper)
    }

    def "Should be able to log in existing user"(){
        given:
            LoginUserDTO loginUserDTO = new LoginUserDTO()
            loginUserDTO.setLogin("TestUser1")
            loginUserDTO.setPasswd("TestPass1")
        when:
            def login = usersService.loginUser(loginUserDTO)
        then:
            login instanceof LoginUserResponseDTO
    }

    def "Should not be able to log in user that does not exist"(){
        given:
            LoginUserDTO loginUserDTO = new LoginUserDTO()
            loginUserDTO.setLogin("TestUser2")
            loginUserDTO.setPasswd("TestPass2")
        when:
            def login = usersService.loginUser(loginUserDTO)
        then:
            login == null
    }

    def "Should be able to register new user"(){
        given:
            LoginUserDTO registerUserDTO = new LoginUserDTO()
            registerUserDTO.setLogin("TestUser2")
            registerUserDTO.setPasswd("TestPass2")
        when:
            def res = usersService.registerUser(registerUserDTO)
        then:
            res == new ResponseDTO(true,"You were registered")
    }

    def "Should not be able to register existing user"(){
        given:
            LoginUserDTO registerUserDTO = new LoginUserDTO()
            registerUserDTO.setLogin("TestMod1")
            registerUserDTO.setPasswd("TestPass2")
        when:
            def res = usersService.registerUser(registerUserDTO)
        then:
            res == new ResponseDTO(false,"This username is already taken")
    }

    def "Should be able to make existing user moderator"(){
        given:
        ModUserDTO modUserDTO = new ModUserDTO()
            modUserDTO.setLogin("TestMod1")
            modUserDTO.setPasswd("TestPass2")
            modUserDTO.setUsertomod("TestUser1")
        when:
            def res = usersService.modUser(modUserDTO)
        then:
            res == new ResponseDTO(true,"User has been successfully made Moderator")
    }

    def "Should not be able to make a moderator moderator"(){
        given:
            ModUserDTO modUserDTO = new ModUserDTO()
            modUserDTO.setLogin("TestMod1")
            modUserDTO.setPasswd("TestPass2")
            modUserDTO.setUsertomod("TestMod2")
        when:
            def res = usersService.modUser(modUserDTO)
        then:
            res == new ResponseDTO(false,"User is already a Moderator")
    }

    def "Should not be able to make a non existent user moderator"(){
        given:
            ModUserDTO modUserDTO = new ModUserDTO()
            modUserDTO.setLogin("TestMod1")
            modUserDTO.setPasswd("TestPass2")
            modUserDTO.setUsertomod("TestUser3")
        when:
            def res = usersService.modUser(modUserDTO)
        then:
            res == new ResponseDTO(false,"User to grant Moderator status does not exist")
    }

    def "Normal user should not be able to grant moderator permissions"(){
        given:
            ModUserDTO modUserDTO = new ModUserDTO()
            modUserDTO.setLogin("TestUser1")
            modUserDTO.setPasswd("TestPass1")
            modUserDTO.setUsertomod("TestUser1")
        when:
            def res = usersService.modUser(modUserDTO)
        then:
            res == new ResponseDTO(false,"You do not have Moderator permission")
    }
}
