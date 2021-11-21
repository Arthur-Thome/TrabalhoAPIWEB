package com.dbc.webdois.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity(name="RESERVA")
public class ReservaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESERVA_SEQ")
    @SequenceGenerator(name = "RESERVA_SEQ", sequenceName = "SEQ_RESERVAS", allocationSize = 1)
    @Column(name = "id_reserva")
    private Integer idReserva;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hoteis")
    private HoteisEntity hoteisEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_quartos")
    private QuartosEntity quartosEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuarioEntity;










}
