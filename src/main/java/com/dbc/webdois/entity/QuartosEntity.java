package com.dbc.webdois.entity;


import lombok.*;


@Data
@AllArgsConstructor
@ToString
public class QuartosEntity {
    private Integer idQuarto;
    private Integer idHotel;
    private Integer numeroQuarto;
    private Number valorDiaria;
    private String descricao;

}