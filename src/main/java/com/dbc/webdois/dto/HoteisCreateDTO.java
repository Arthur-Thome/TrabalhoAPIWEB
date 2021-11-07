package com.dbc.webdois.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoteisCreateDTO {
    @NotNull(message = "Nome não pode ser null")
    @NotBlank(message = "Não pode estar em branco")
    @ApiModelProperty("Nome hotel")
    private String nomeHotel;

    @NotNull(message = "Nome não pode ser null")
    @NotBlank(message = "Não pode estar em branco")
    @ApiModelProperty("Nome Estado")
    private String nomeEstado;

    @NotNull(message = "Nome não pode ser null")
    @NotBlank(message = "Não pode estar em branco")
    @ApiModelProperty("Nome Cidade")
    private String nomecidade;

    @NotNull(message = "Nome não pode ser null")
    @NotBlank(message = "Não pode estar em branco")
    @ApiModelProperty("Numero cep")
    @Size(max = 8, min = 8)
    private String cep;

    @NotNull(message = "Nome não pode ser null")
    @ApiModelProperty("Numero do hotel")
    private Integer numero;

    @NotNull(message = "Nome não pode ser null")
    @NotBlank(message = "Não pode estar em branco")
    @ApiModelProperty("Nome logradouro")
    private String logradouro;
}
