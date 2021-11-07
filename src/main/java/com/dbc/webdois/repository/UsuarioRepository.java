package com.dbc.webdois.repository;


import com.dbc.webdois.dto.TipoUsuario;
import com.dbc.webdois.entity.UsuarioEntity;
import com.dbc.webdois.exceptions.RegraDeNegocioException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class UsuarioRepository {
    private static List<UsuarioEntity> listaUsuario = new ArrayList<>();
    private AtomicInteger COUNTER = new AtomicInteger();

    public UsuarioRepository() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        listaUsuario.add(new UsuarioEntity(COUNTER.incrementAndGet(), "Matheus", "41286811805",
                LocalDate.parse("20/06/1995", formatter), "matheus.camilo1617@gmail.com", TipoUsuario.COMUM));
        listaUsuario.add(new UsuarioEntity(COUNTER.incrementAndGet(), "Camilo", "12345678910", LocalDate.parse("20/06/1995", formatter), "matheus.camilo1617@gmail.com", TipoUsuario.COMUM));
        listaUsuario.add(new UsuarioEntity(COUNTER.incrementAndGet(), "Guilherme", "12345678911", LocalDate.parse("20/06/1995", formatter), "matheus.camilo1617@gmail.com", TipoUsuario.COMUM));
        listaUsuario.add(new UsuarioEntity(COUNTER.incrementAndGet(), "Tiago", "12345678912", LocalDate.parse("20/06/1995", formatter), "matheus.camilo1617@gmail.com", TipoUsuario.COMUM));
    }

    public UsuarioEntity create(UsuarioEntity usuarioEntity){
        usuarioEntity.setIdUsuario(COUNTER.incrementAndGet());
        listaUsuario.add(usuarioEntity);
        return usuarioEntity;
    }

    public List<UsuarioEntity> list() {
        return listaUsuario;
    }

    public UsuarioEntity update(Integer id, UsuarioEntity usuarioEntity) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntityRecuperar = listaUsuario.stream()
                .filter(x -> x.getIdUsuario().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado"));
        usuarioEntityRecuperar.setNome(usuarioEntity.getNome());
        usuarioEntityRecuperar.setCpf(usuarioEntity.getCpf());
        usuarioEntityRecuperar.setDataNascimento(usuarioEntity.getDataNascimento());
        usuarioEntityRecuperar.setEmail(usuarioEntity.getEmail());
        usuarioEntityRecuperar.setTipoUsuario(usuarioEntity.getTipoUsuario());
        return usuarioEntityRecuperar;
    }

    public void delete(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = listaUsuario.stream()
                .filter(x -> x.getIdUsuario().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado"));
        listaUsuario.remove(usuarioEntity);
    }

    public UsuarioEntity listByIdUsuario(Integer id) throws RegraDeNegocioException {
        UsuarioEntity usuarioEntity = listaUsuario.stream()
                .filter(x -> x.getIdUsuario().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado"));
        return usuarioEntity;
    }

}


