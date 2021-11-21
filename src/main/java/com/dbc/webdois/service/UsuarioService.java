package com.dbc.webdois.service;

import com.dbc.webdois.dto.GrupoDTO;
import com.dbc.webdois.dto.UsuarioCreateDTO;
import com.dbc.webdois.dto.UsuarioDTO;
import com.dbc.webdois.entity.GrupoEntity;
import com.dbc.webdois.entity.UsuarioEntity;
import com.dbc.webdois.exceptions.RegraDeNegocioException;
import com.dbc.webdois.repository.GrupoRepository;
import com.dbc.webdois.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ObjectMapper objectMapper;
    private final GrupoRepository grupoRepository;

    public UsuarioDTO create(UsuarioCreateDTO usuarioCreateDTO) {
        UsuarioEntity entity = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
        entity.setNome(usuarioCreateDTO.getNome());
        entity.setCpf(usuarioCreateDTO.getCpf());
        entity.setDataNascimento(usuarioCreateDTO.getDataNascimento());
        entity.setEmail(usuarioCreateDTO.getEmail());
        entity.setLogin(usuarioCreateDTO.getLogin());
        entity.setSenha(new BCryptPasswordEncoder().encode(usuarioCreateDTO.getSenha()));
        entity.setGrupos(
                usuarioCreateDTO.getGrupos().stream()
                        .map(grupoId -> grupoRepository.findById(grupoId)
                                .orElse(null))
                        .collect(Collectors.toList())
        );
        UsuarioEntity save = usuarioRepository.save(entity);
        UsuarioDTO usuarioDTO = objectMapper.convertValue(save, UsuarioDTO.class);

      return new UsuarioDTO(save.getIdUsuario(), save.getNome(),save.getCpf(),save.getDataNascimento(), save.getEmail(), save.getLogin(), save.getGrupos().stream().map(grupoEntity ->  objectMapper.convertValue(grupoEntity, GrupoDTO.class)).collect(Collectors.toList()));

    }



    //Listar
    public List<UsuarioDTO> list () {
        return usuarioRepository.findAll().stream()
                .map(x-> {
                   UsuarioDTO usuarioDTO = objectMapper.convertValue(x, UsuarioDTO.class);
                   usuarioDTO.setGrupos(x.getGrupos().stream().map(grupoEntity -> objectMapper.convertValue(grupoEntity, GrupoDTO.class)).collect(Collectors.toList()));
                   return usuarioDTO;
                })
                .collect(Collectors.toList());
    }

    //Atualizar
    public UsuarioDTO update (Integer id, UsuarioCreateDTO usuarioCreateDTO) throws RegraDeNegocioException {
        findById(id);
        List<GrupoEntity> grupoEntityList = grupoRepository.findAllById(usuarioCreateDTO.getGrupos());
        UsuarioEntity usuarioEntity = objectMapper.convertValue(usuarioCreateDTO, UsuarioEntity.class);
        usuarioEntity.setIdUsuario(id);
        usuarioEntity.setSenha(new BCryptPasswordEncoder().encode(usuarioCreateDTO.getSenha()));
        usuarioEntity.setGrupos(grupoEntityList);
        UsuarioEntity usuarioAtt = usuarioRepository.save(usuarioEntity);
        UsuarioDTO dto = objectMapper.convertValue(usuarioAtt, UsuarioDTO.class);
        dto.setGrupos(usuarioAtt.getGrupos().stream().map(grupoEntity -> objectMapper.convertValue(grupoEntity, GrupoDTO.class)).collect(Collectors.toList()));

        return dto;
    }

    //Deletar
    public void delete (Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findById(id);
        usuarioRepository.delete(usuarioEntity);
    }

    //FindById
    public UsuarioEntity findById(Integer id) throws RegraDeNegocioException {
    UsuarioEntity entity = usuarioRepository.findById(id)
            .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado"));
    return entity;
}

    //GetPorId
    public UsuarioDTO getPorId(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = findById(id);

        UsuarioDTO usuarioDTO= objectMapper.convertValue(usuarioEntity, UsuarioDTO.class);
        usuarioDTO.setGrupos(usuarioEntity.getGrupos().stream().map(grupoEntity -> objectMapper.convertValue(grupoEntity, GrupoDTO.class)).collect(Collectors.toList()));

        return usuarioDTO;
    }
    public Optional<UsuarioEntity> findByLoginAndSenha(String login, String senha) {
        return usuarioRepository.findByLoginAndSenha(login, senha);
    }

    public Optional<UsuarioEntity> findByLogin(String login){
        return usuarioRepository.findByLogin(login);
    }
}
