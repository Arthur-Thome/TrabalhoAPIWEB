package com.dbc.webdois.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReservaSemHotelDTO {
    private Integer idReserva;

    @NotNull(message = "Não pode estar null")
    @ApiModelProperty(value = "Quartos")
    private QuartosDTO quartosDTO;

    @NotNull(message = "Não pode estar null" )
    @ApiModelProperty(value = "Usuarios")
    private UsuarioDTO usuarioDTO;
}
