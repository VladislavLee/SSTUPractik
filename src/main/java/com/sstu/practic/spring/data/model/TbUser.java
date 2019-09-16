package com.sstu.practic.spring.data.model;

import com.sstu.practic.spring.services.security.entites.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Objects;


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

    private Role vcRole;

    @Lob
    @Column(columnDefinition="BLOB")
    private byte[] iPhoto;

    private String vcComments;

    @Override
    public String toString() {
        return vcFirstName + " " + vcLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TbUser user = (TbUser) o;
        return idUser.equals(user.idUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser);
    }
}
