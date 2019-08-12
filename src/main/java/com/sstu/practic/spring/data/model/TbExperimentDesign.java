package com.sstu.practic.spring.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "tb_experiment_design")
@AllArgsConstructor
@NoArgsConstructor
public class TbExperimentDesign {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idExperimentDesign;

    private String vcName;

    private String vcDescription;

    private String vcSampling;

    private String vsArrangement;

    private Integer idUser;
}
