package com.dbc.webdois.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioCreateDTO {

    @NotNull(message = "Não pode ser null")
    @NotBlank(message = "Não pode estar em branco")
    @ApiModelProperty(value= "Nome do usuario")
    private String nome;

    @NotNull(message = "Não pode ser null")
    @NotBlank(message = "Não pode estar em branco")
    @Size(max=11, min=11)
    @ApiModelProperty(value= "CPF")
    private String cpf;

    @NotNull(message = "Não pode ser null")
    @ApiModelProperty(value= "Data de nascimento")
    private LocalDate dataNascimento;

    @NotNull(message = "Não pode ser null")
    @NotBlank(message = "Não pode estar em branco")
    @ApiModelProperty(value= "E-mail")
    private String email;

    @NotNull(message = "Não pode ser null")
    @NotBlank(message = "Não pode estar em branco")
    @ApiModelProperty(value= "Login")
    private String login;

    @NotNull(message = "Não pode ser null")
    @NotBlank(message = "Não pode estar em branco")
    @ApiModelProperty(value= "Senha")
    private String senha;

    private List<Integer> grupos;
}
