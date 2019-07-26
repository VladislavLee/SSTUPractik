package com.sstu.practic.spring.data.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class tbFiles {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idFile;
    private String vcPath;
}
