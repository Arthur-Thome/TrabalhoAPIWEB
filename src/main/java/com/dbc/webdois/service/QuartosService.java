package com.dbc.webdois.service;


import com.dbc.webdois.dto.HoteisDTO;
import com.dbc.webdois.dto.QuartosComHotelDTO;
import com.dbc.webdois.dto.QuartosCreateDTO;
import com.dbc.webdois.dto.QuartosDTO;
import com.dbc.webdois.entity.QuartosEntity;
import com.dbc.webdois.exceptions.RegraDeNegocioException;
import com.dbc.webdois.repository.HoteisRepository;
import com.dbc.webdois.repository.QuartosRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuartosService {
    private final QuartosRepository quartosRepository;
    private final HoteisService hoteisService;
    private final HoteisRepository hoteisRepository;
    private final ObjectMapper objectMapper;


    public List<QuartosDTO> list() {
        return quartosRepository.findAll().stream()
                .map(quartos -> {
                    QuartosComHotelDTO quartosDTO = objectMapper.convertValue(quartos, QuartosComHotelDTO.class);
                    quartosDTO.setHoteisDTO(objectMapper.convertValue(quartos.getHoteisEntity(), HoteisDTO.class));
                    return quartosDTO;
                })
                .collect(Collectors.toList());
    }

    public List<QuartosDTO> getByIdHotel(Integer id) throws RegraDeNegocioException {
        return quartosRepository.findByidHotel(id).stream()
                .map(quartosEntities -> {
                    QuartosComHotelDTO quartosDTO = objectMapper.convertValue(quartosEntities, QuartosComHotelDTO.class);

                    quartosDTO.setHoteisDTO(objectMapper.convertValue(quartosEntities.getHoteisEntity(), HoteisDTO.class));
                    return quartosDTO;

                })
                .collect(Collectors.toList());
    }

    public QuartosDTO update(Integer id, QuartosCreateDTO quartosCreateDTO) throws RegraDeNegocioException {
        QuartosEntity quartosEntity = objectMapper.convertValue(quartosCreateDTO, QuartosEntity.class);
        QuartosEntity quartosEntity1 = quartosRepository.findById(id).orElseThrow(() -> new RegraDeNegocioException("Quarto não Encontrado"));
        quartosEntity1.setDescricao(quartosEntity.getDescricao());
        quartosEntity1.setNumeroQuarto(quartosEntity.getNumeroQuarto());
        quartosEntity1.setValorDiaria(quartosEntity.getValorDiaria());

        QuartosEntity quartosEntity2 = quartosRepository.save(quartosEntity1);

        QuartosComHotelDTO quartosDTO1 = objectMapper.convertValue(quartosEntity2, QuartosComHotelDTO.class);
        quartosDTO1.setHoteisDTO(objectMapper.convertValue(quartosEntity2.getHoteisEntity(), HoteisDTO.class));
        return quartosDTO1;
    }

    public void delete(Integer id) throws Exception {
        QuartosEntity quartoDeletar = quartosRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Quarto não encontrado"));
        quartosRepository.delete(quartoDeletar);
    }

    public QuartosDTO create(Integer id, QuartosCreateDTO quartosCreateDTO) throws RegraDeNegocioException {

        QuartosEntity entity = objectMapper.convertValue(quartosCreateDTO, QuartosEntity.class);
        entity.setHoteisEntity(hoteisRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Hotel não encontrado")));
        QuartosEntity quartoCriado = quartosRepository.save(entity);
        QuartosComHotelDTO dto = objectMapper.convertValue(quartoCriado, QuartosComHotelDTO.class);
        dto.setHoteisDTO(objectMapper.convertValue(quartoCriado.getHoteisEntity(), HoteisDTO.class));
        return dto;

    }

    //FindById
    public QuartosEntity findById(Integer id) throws RegraDeNegocioException {
        QuartosEntity entity = quartosRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException(""));
        return entity;
    }

    //GetById
    public QuartosDTO getById(Integer id) throws RegraDeNegocioException {
        QuartosEntity entity = findById(id);
        QuartosDTO quartosDTO = objectMapper.convertValue(entity, QuartosDTO.class);
        return quartosDTO;
    }




}

