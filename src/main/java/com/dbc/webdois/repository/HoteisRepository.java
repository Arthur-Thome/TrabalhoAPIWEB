package com.dbc.webdois.repository;


import com.dbc.webdois.entity.HoteisEntity;
import com.dbc.webdois.exceptions.RegraDeNegocioException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class HoteisRepository {
    private static List<HoteisEntity> listaHoteisEntity = new ArrayList<>();
    private AtomicInteger COUNTER = new AtomicInteger();

    public HoteisRepository(){
        listaHoteisEntity.add(new HoteisEntity(COUNTER.incrementAndGet(), "Hotel Teste", "RS",
                "POA", "92332662",105, "logradouro"));

        listaHoteisEntity.add(new HoteisEntity(COUNTER.incrementAndGet(), "Hotel Teste2", "RS",
                "POA", "92332662",105, "logradouro"));

        listaHoteisEntity.add(new HoteisEntity(COUNTER.incrementAndGet(), "Hotel Teste3", "RS",
                "POA", "92332662",105, "logradouro"));


    }
    public HoteisEntity getPorId(Integer id) throws RegraDeNegocioException {
        HoteisEntity hoteisEntity = listaHoteisEntity.stream()
                .filter(hotel -> hotel.getIdHotel().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Hotel não encontrado"));

        return hoteisEntity;
    }

    public List<HoteisEntity> list(){return listaHoteisEntity;}

    public List<HoteisEntity> getById(Integer id){
        return listaHoteisEntity.stream()
                .filter(x -> x.getIdHotel().equals(id))
                .collect(Collectors.toList());
    }

    public HoteisEntity create(HoteisEntity hoteisEntity) throws RegraDeNegocioException{
        hoteisEntity.setIdHotel(COUNTER.incrementAndGet());
        listaHoteisEntity.add(hoteisEntity);
        return hoteisEntity;
    }

    public HoteisEntity update(Integer id, HoteisEntity hoteisEntityAtualizado) throws Exception {
        HoteisEntity hoteisEntity = listaHoteisEntity.stream()
                .filter(hotel -> hotel.getIdHotel().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Hotel não encontrado"));
        hoteisEntity.setIdHotel(id);
        hoteisEntity.setNomeHotel(hoteisEntityAtualizado.getNomeHotel());
        hoteisEntity.setNomeEstado(hoteisEntityAtualizado.getNomeEstado());
        hoteisEntity.setNomecidade(hoteisEntityAtualizado.getNomecidade());
        hoteisEntity.setCep(hoteisEntityAtualizado.getCep());
        hoteisEntity.setNumero(hoteisEntityAtualizado.getNumero());
        hoteisEntity.setLogradouro(hoteisEntityAtualizado.getLogradouro());

        return hoteisEntity;
    }

    public void delete(Integer id) throws Exception {
        HoteisEntity hoteisEntityRecuperado = listaHoteisEntity.stream()
                .filter(hotel -> hotel.getIdHotel().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Contato não encontrado"));
        listaHoteisEntity.remove(hoteisEntityRecuperado);
    }

}

