package com.sstu.practic.spring.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Transactional
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

    private LocalDate vcDate;

    private String vcRecordDuration;

    private Double vcMotivationLevel;

    private Double vcRestLevel;

    private String vcExperimentType;

    private String vcExperimentSubject;

    private String vcExperimentDesign;

    private String vcMood;

    private String vcNameAgreement;

    private String vcNameProtocol1;

    private String vcNameProtocol2;

    private String vcNameProtocol3;

    @ManyToMany
    private List<TbGroup> groupList;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TbExperiment that = (TbExperiment) o;
        return Objects.equals(idExperiment, that.idExperiment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idExperiment);
    }
}
