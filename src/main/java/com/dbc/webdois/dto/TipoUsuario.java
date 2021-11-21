package com.dbc.webdois.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public enum TipoUsuario {
    COMUM(0),
    ADMIN(1);

    private Integer tipo;
}
