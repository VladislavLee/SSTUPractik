package com.sstu.practic.spring.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "tb_arrangements")
@AllArgsConstructor
@NoArgsConstructor
public class TbArrangements {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idArrangement;

    private String vcName;

    private String vcDescription;

    private Integer idUser;
}
