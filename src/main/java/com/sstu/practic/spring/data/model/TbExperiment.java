package com.sstu.practic.spring.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "tb_experiment")
@AllArgsConstructor
@NoArgsConstructor
public class TbExperiment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idExperiment;

    private String vcDescription;

    private String vcSubjectWeight;

    private String vcDate;

    private String vcRecordDuration;

    private String vcMotivationLevel;

    private String vcExperimentType;

    private String vcExperimentSubject;

    private String vcExperimentDesign;

    private String vcMood;


    @Lob
    @Column(columnDefinition="BLOB")
    private byte[] vcAgreement;

    @Lob
    @Column(columnDefinition="BLOB")
    private byte[] vcProtocol1;

    @Lob
    @Column(columnDefinition="BLOB")
    private byte[] vcProtocol2;

    @Lob
    @Column(columnDefinition="BLOB")
    private byte[] vcProtocol3;

    private Integer idUser;
}
