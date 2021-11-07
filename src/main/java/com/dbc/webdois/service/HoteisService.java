package com.dbc.webdois.service;


import com.dbc.webdois.dto.HoteisCreateDTO;
import com.dbc.webdois.dto.HoteisDTO;
import com.dbc.webdois.entity.HoteisEntity;
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
public class HoteisService {
    private final HoteisRepository hoteisRepository;
    private final QuartosRepository quartosRepository;
    private final ObjectMapper objectMapper;


    public List<HoteisDTO> list(){
        return hoteisRepository.list().stream()
                .map(x -> objectMapper.convertValue(x, HoteisDTO.class))
                .collect(Collectors.toList());
    }

    public List<HoteisDTO> getById(Integer id){
        return hoteisRepository.getById(id).stream()
                .map(x -> objectMapper.convertValue(x, HoteisDTO.class))
                .collect(Collectors.toList());
    }

    public HoteisDTO create(HoteisCreateDTO hoteisCreateDTO) throws RegraDeNegocioException {
        HoteisEntity entity = objectMapper.convertValue(hoteisCreateDTO, HoteisEntity.class);
        HoteisEntity hotelCriado = hoteisRepository.create(entity);
        HoteisDTO dto = objectMapper.convertValue(hotelCriado, HoteisDTO.class);
        return dto;
    }

    public HoteisDTO update(Integer id, HoteisCreateDTO hoteisCreateDTO) throws Exception {
        HoteisEntity entity = objectMapper.convertValue(hoteisCreateDTO, HoteisEntity.class);
        HoteisEntity atualizado = hoteisRepository.update(id, entity);
        HoteisDTO dto = objectMapper.convertValue(atualizado, HoteisDTO.class);
        return dto;
    }

    public void delete(Integer id) throws Exception {
        hoteisRepository.delete(id);
        quartosRepository.listarQuartosPorHotel(id).stream()
                .forEach(x-> {
                    try {
                        quartosRepository.delete(x.getIdQuarto());
                    } catch (RegraDeNegocioException e) {
                        e.printStackTrace();
                    }
                });
    }

    public HoteisDTO getPorId(Integer idHotel) throws RegraDeNegocioException {
        HoteisEntity hoteisEntity= hoteisRepository.getPorId(idHotel);
        return  objectMapper.convertValue(hoteisEntity, HoteisDTO.class);
    }
}

