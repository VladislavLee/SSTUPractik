package com.sstu.practic.spring.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;


@Entity
@Data
@Builder
@Table(name = "tb_users")
@AllArgsConstructor
@NoArgsConstructor
public class TbUser {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer idUser;

    private String vcLogin;

    private String vcPassword;

    private String vcFirstName;

    private String vcSecondName;

    private String vcLastName;

    private String vcRole;

    private Integer iPhoto;

    private String vcComments;



}
