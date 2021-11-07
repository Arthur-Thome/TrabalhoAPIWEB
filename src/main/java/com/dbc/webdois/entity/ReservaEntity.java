package com.dbc.webdois.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaEntity {
    private Integer idReserva;
    private Integer idHotel;
    private Integer idQuarto;
    private Integer idUsuario;
//    private LocalDate dataReserva;
}
