package com.dbc.webdois.service;


import com.dbc.webdois.dto.UsuarioCreateDTO;
import com.dbc.webdois.dto.UsuarioDTO;
import com.dbc.webdois.entity.UsuarioEntity;
import com.dbc.webdois.exceptions.RegraDeNegocioException;
import com.dbc.webdois.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO) {
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
        UsuarioEntity usuarioCriar = usuarioRepository.create(usuarioEntity);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioCriar, UsuarioDTO.class);
//      emailService.enviarCadastroUsuario(usuarioDTO);
        return usuarioDTO;
    }

    public List<UsuarioDTO> list () {
        return usuarioRepository.list().stream()
                .map(x-> objectMapper.convertValue(x, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    public UsuarioDTO update (Integer id, UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
        UsuarioEntity usuarioAtt = usuarioRepository.update(id, usuarioEntity);
        UsuarioDTO dto = objectMapper.convertValue(usuarioAtt, UsuarioDTO.class);
        return dto;

    }

    public void delete (Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = usuarioRepository.listByIdUsuario(id);
        usuarioRepository.delete(id);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(usuarioEntity,UsuarioDTO.class);
//      emailService.enviarDeleteUsuario(pessoaDTO);
    }
    public UsuarioDTO getPorId(Integer idUsuario) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity= usuarioRepository.listByIdUsuario(idUsuario);
        return  objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
    }
}
