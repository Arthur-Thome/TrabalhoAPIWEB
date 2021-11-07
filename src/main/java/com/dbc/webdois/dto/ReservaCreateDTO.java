package com.dbc.webdois.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaCreateDTO {

    @ApiModelProperty("Id do hotel")
    @NotNull(message = "Não pode ser null")
    private Integer idHotel;

    @ApiModelProperty("Id do quarto")
    @NotNull(message = "Não pode ser null")
    private Integer idQuarto;

    @ApiModelProperty("Id do usuário")
    @NotNull(message = "Não pode ser null")
    private Integer idUsuario;
}
