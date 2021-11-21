package com.dbc.webdois.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuartosDTO {
    @NotNull(message = "Não pode ser null")
    @ApiModelProperty("Id do hotel")
    private Integer idQuarto;

    @NotNull(message = "Não pode ser null")
    @ApiModelProperty(value= "Numero Quarto")
    private Integer numeroQuarto;

    @NotNull(message = "Não pode ser null")
    @ApiModelProperty(value= "Valor Diaria")
    private Number valorDiaria;

    @NotNull(message = "Não pode ser null")
    @NotBlank(message = "Não pode estar em branco")
    @Size(max = 110)
    @ApiModelProperty(value= "Descrição")
    private String descricao;
}