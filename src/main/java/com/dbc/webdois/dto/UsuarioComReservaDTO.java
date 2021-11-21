package com.dbc.webdois.dto;

import lombok.Data;

import java.util.List;

@Data
public class UsuarioComReservaDTO extends UsuarioDTO {
    List<ReservaSemUsuarioDTO> reservas;
}
