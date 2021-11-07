package com.dbc.webdois.dto;

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
public class QuartosCreateDTODOIS{

    @NotNull(message = "Não pode ser null")
    private Integer numeroQuarto;

    @NotNull(message = "Não pode ser null")
    private Number valorDiaria;

    @NotNull(message = "Não pode ser null")
    @NotBlank(message = "Não pode estar em branco")
    @Size(max = 110)
    private String descricao;
}