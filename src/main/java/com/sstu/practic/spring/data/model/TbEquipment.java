package com.sstu.practic.spring.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "tb_equipment")
@AllArgsConstructor
@NoArgsConstructor
public class TbEquipment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idEquipment;

    private String vcName;

    private String vcDescription;

    private String vcCertificateName;

    private String vcCertificateOutput;
    @Lob
    @Column(columnDefinition="BLOB")
    private byte[] vcCertificate;

    private Integer idUser;
}
