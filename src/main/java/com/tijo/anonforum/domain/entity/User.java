package com.tijo.anonforum.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "useraccounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {

    @Id
    @Column(name = "acc_id")
    private Long acc_id;

    @Column(name = "login")
    private String login;

    @Column(name = "passwd")
    private String passwd;

    @Column(name = "is_adm")
    private Boolean is_adm;

}
