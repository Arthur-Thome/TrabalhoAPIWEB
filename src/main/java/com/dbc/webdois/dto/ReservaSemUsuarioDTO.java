package com.dbc.webdois.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReservaSemUsuarioDTO {
    private Integer idReserva;
    @NotNull(message = "Não pode estar null")
    @ApiModelProperty(value = "Hoteis")
    private HoteisDTO hoteisDTO;

    @NotNull(message = "Não pode estar null")
    @ApiModelProperty(value = "Quartos")
    private QuartosDTO quartosDTO;
}
