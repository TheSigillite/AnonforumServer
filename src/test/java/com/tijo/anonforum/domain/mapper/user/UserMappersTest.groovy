package com.tijo.anonforum.domain.mapper.user

import com.tijo.anonforum.domain.dto.user.LoginUserResponseDTO
import com.tijo.anonforum.domain.entity.User
import com.tijo.anonforum.domain.mapper.OneToOneMapper
import com.tijo.anonforum.domain.mapper.user.LoginUserResponseMapper
import com.tijo.anonforum.domain.mapper.user.ModUserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class UserMappersTest extends Specification{
    @Autowired
    OneToOneMapper<LoginUserResponseDTO, User> loginUserResponseMapper
    @Autowired
    OneToOneMapper<User, User> modUserMapper

    def "Should wire correct beans"(){
        expect:
            loginUserResponseMapper instanceof LoginUserResponseMapper && modUserMapper instanceof ModUserMapper
    }

    def "Should convert User to LoginUserResponseDTO"(){
        given:
            User user = new User(1,"Test","Test",false)
            User user2 = new User(2,"Test2","Test2",true)
        when:
            def out1 = loginUserResponseMapper.convert(user)
            def out2 = loginUserResponseMapper.convert(user2)
        then:
            out1 == new LoginUserResponseDTO(false) && out2 == new LoginUserResponseDTO(true)
    }

    def "Should convert user to moderator user"(){
        given:
            User user3 = new User(3,"Test3","Test3",false)
        when:
            def out3 = modUserMapper.convert(user3)
        then:
            out3 == new User(3,"Test3","Test3",true)
    }
}
