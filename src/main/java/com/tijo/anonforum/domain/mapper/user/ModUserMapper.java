package com.tijo.anonforum.domain.mapper.user;

import com.tijo.anonforum.domain.entity.User;
import com.tijo.anonforum.domain.mapper.OneToOneMapper;
import org.springframework.stereotype.Component;

@Component
public class ModUserMapper implements OneToOneMapper<User, User> {
    @Override
    public User convert(User olduser) {
        return User.builder()
                .acc_id(olduser.getAcc_id())
                .login(olduser.getLogin())
                .passwd(olduser.getPasswd())
                .is_adm(true).build();
    }
}
