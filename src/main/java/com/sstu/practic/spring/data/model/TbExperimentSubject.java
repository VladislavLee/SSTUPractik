package com.sstu.practic.spring.data.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@Table(name = "tb_experiment_subject")
@AllArgsConstructor
@NoArgsConstructor
public class TbExperimentSubject {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idExperimentSubject;

    private String vcFirstName;

    private String vcSecondName;

    private String vcLastName;

    private LocalDate vcBirthday;

    private String vcGender;

    private String vcLeadingHand;

    private String vcLeadingLeg;

    private String vcLeadingEye;

    private String vcComments;

    private Integer idUser;
}
