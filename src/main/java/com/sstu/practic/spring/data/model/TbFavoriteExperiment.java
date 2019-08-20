package com.sstu.practic.spring.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@Table(name = "tb_favorite_experiment")
@AllArgsConstructor
@NoArgsConstructor
public class TbFavoriteExperiment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idFavoriteExperiment;

    @OneToOne
    private TbExperiment tbExperiment;
}
