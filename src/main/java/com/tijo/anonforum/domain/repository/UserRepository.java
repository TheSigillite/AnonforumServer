package com.tijo.anonforum.domain.repository;

import com.tijo.anonforum.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User,Long>, CrudRepository<User,Long> {
    User findUserByLoginAndPasswd(String login, String passwd);
    User findUserByAcc_id(Long acc_id);
    User findUserByLogin(String login);
}
