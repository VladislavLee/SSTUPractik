package com.sstu.practic.spring.data.model;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;


@Entity
@Data
@Builder
@Table(name = "tb_channels")
@AllArgsConstructor
@NoArgsConstructor
public class TbChannels {
        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Integer idChannels;

        private String vcName;

        private String vcDescription;

        private Integer idUser;
}
