package com.sstu.practic.spring.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;


@Entity
@Data
@Builder
@Table(name = "tb_group")
@AllArgsConstructor
@NoArgsConstructor
public class TbGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idGroup;

    private String vcName;

    private Integer idCreator;

    @Override
    public String toString() {
        return vcName;
    }


    @ManyToMany
    private List<TbUser> userList;
}
