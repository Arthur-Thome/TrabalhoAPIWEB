package com.dbc.webdois.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuartosCreateDTO {

    @NotNull(message = "Não pode ser null")
    @ApiModelProperty("Numero do quarto")
    private Integer numeroQuarto;

    @NotNull(message = "Não pode ser null")
    @ApiModelProperty("Valor da diaria")
    private Number valorDiaria;

    @NotNull(message = "Não pode ser null")
    @NotBlank(message = "Não pode estar em branco")
    @Size(max = 110)
    @ApiModelProperty("descrição")
    private String descricao;
}