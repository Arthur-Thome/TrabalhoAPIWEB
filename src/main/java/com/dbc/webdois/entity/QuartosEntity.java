package com.dbc.webdois.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Getter
@Setter
@Entity(name="QUARTOS")
public class QuartosEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "QUARTOS_SEQ")
    @SequenceGenerator(name = "QUARTOS_SEQ", sequenceName = "SEQ_QUARTOS", allocationSize = 1)
    @Column(name = "id_quartos")
    private Integer idQuarto;

    @Column(name = "numero")
    private Integer numeroQuarto;
    @Column(name = "valor_diaria")
    private Double valorDiaria;
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(mappedBy = "quartosEntity",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ReservaEntity> reservas;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="id_hoteis",referencedColumnName = "id_hoteis")
    private HoteisEntity hoteisEntity;

}