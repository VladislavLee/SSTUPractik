package com.sstu.practic.spring.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "tb_experiment_type")
@AllArgsConstructor
@NoArgsConstructor
public class TbExperimentType {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idExperimentType;

    private String vcName;

    private String vcDescription;

    private Integer idUser;
}
