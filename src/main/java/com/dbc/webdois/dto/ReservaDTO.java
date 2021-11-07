package com.dbc.webdois.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReservaDTO {
    private Integer idReserva;

    @NotNull(message = "Não pode estar null")
    @ApiModelProperty(value = "Hoteis")
    private HoteisDTO hoteisDTO;

    @NotNull(message = "Não pode estar null")
    @ApiModelProperty(value = "Quartos")
    private QuartosDTO quartosDTO;

    @NotNull(message = "Não pode estar null")
    @ApiModelProperty(value = "Usuario")
    private UsuarioDTO usuarioDTO;

}
