package com.dbc.webdois.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioDTO {
    private Integer idUsuario;

    @NotNull(message = "Não pode estar null")
    @NotBlank(message = "Não pode estar em branco")
    @ApiModelProperty(value= "Nome do usuario")
    private String nome;

    @NotNull(message = "Não pode estar null")
    @NotBlank(message = "Não pode estar em branco")
    @Size(max=11, min=11)
    @ApiModelProperty(value= "CPF")
    private String cpf;

    @NotNull(message = "Não pode estar null")
    @ApiModelProperty(value= "Data de nascimento")
    private LocalDate dataNascimento;


    @NotBlank(message = "Não pode estar em branco")
    @ApiModelProperty(value= "E-mail")
    private String email;

    @NotBlank(message = "Não pode estar em branco")
    @ApiModelProperty(value= "Tipo de usuario")
    private TipoUsuario tipoUsuario; // 0 - Comum, 1 - Admin.
}
