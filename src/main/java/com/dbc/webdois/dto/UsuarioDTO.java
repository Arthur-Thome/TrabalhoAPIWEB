package com.dbc.webdois.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioDTO {
    private Integer idUsuario;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String login;
    private List<GrupoDTO> grupos;

}

