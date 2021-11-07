package com.dbc.webdois.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public enum TipoUsuario {
    COMUM(0),
    ADMIN(1);

    private Integer tipo;
}
