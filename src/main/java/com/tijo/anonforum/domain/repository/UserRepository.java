package com.tijo.anonforum.domain.repository;

import com.tijo.anonforum.domain.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional()
@Profile("!test")
public interface UserRepository extends JpaRepository<User,Long>, CrudRepository<User,Long> {
    @Query(value = "SELECT * FROM useraccounts u WHERE u.login = ?1 AND u.passwd = ?2", nativeQuery = true)
    User findUserByLoginAndPasswd(String login, String passwd);
    @Query(value = "SELECT * FROM useraccounts u WHERE u.acc_id = :acc_id", nativeQuery = true)
    User findUserByAcc_id(@Param("acc_id") Long acc_id);
    @Query(value = "SELECT * FROM useraccounts u WHERE u.login = :login", nativeQuery = true)
    User findUserByLogin(@Param("login") String login);

    @Modifying
    @Query( value = "INSERT INTO useraccounts(login,passwd,is_adm) VALUES (:login, :passwd, false)", nativeQuery = true)
    void insertUser(@Param("login") String login,@Param("passwd") String passwd);
}
