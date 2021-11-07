package com.dbc.webdois.service;


import com.dbc.webdois.dto.QuartosCreateDTO;
import com.dbc.webdois.dto.QuartosCreateDTODOIS;
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

    public List<QuartosDTO> listarQuartos() {
        return quartosRepository.listar().stream()
                .map(quarto -> {
                    QuartosDTO quartosDTO = objectMapper.convertValue(quarto, QuartosDTO.class);
                    try {
                        quartosDTO.setHoteisDTO(hoteisService.getPorId(quarto.getIdHotel()));
                    } catch (RegraDeNegocioException e) {
                        e.printStackTrace();
                    }
                    return quartosDTO;
                })
                .collect(Collectors.toList());
    }

    public List<QuartosDTO> listarQuartosPorHotel(Integer idHotel) {
        return quartosRepository.listarQuartosPorHotel(idHotel).stream()
                .map(quarto -> {
                    QuartosDTO quartosDTO = objectMapper.convertValue(quarto, QuartosDTO.class);
                    try {
                        quartosDTO.setHoteisDTO(hoteisService.getPorId(idHotel));
                    } catch (RegraDeNegocioException e) {
                        e.printStackTrace();
                    }
                    return quartosDTO;
                })
                .collect(Collectors.toList());
    }

    public QuartosDTO create(Integer id, QuartosCreateDTODOIS quartosCreateDTODOIS) throws RegraDeNegocioException {
        QuartosEntity entity = objectMapper.convertValue(quartosCreateDTODOIS, QuartosEntity.class);

        hoteisRepository.getById(id).stream()
                .filter(x-> x.getIdHotel().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Hotel não encontrado"));

        if(listarQuartosPorHotel(id).stream()
                .filter(x-> x.getNumeroQuarto().equals(quartosCreateDTODOIS.getNumeroQuarto())).count() > 0){
            throw  new RegraDeNegocioException("Quarto já cadastrado");
        }


        entity.setIdHotel(id);
        QuartosEntity quartoCriado = quartosRepository.adicionar(entity);
        QuartosDTO dto = objectMapper.convertValue(quartoCriado, QuartosDTO.class);

        dto.setHoteisDTO(hoteisService.getPorId(id));//

        return dto;
    }

    public QuartosDTO getQuartoPorId(Integer id) throws RegraDeNegocioException {
        QuartosEntity quartosEntity= quartosRepository.getQuartoPorId(id);
        QuartosDTO dto = objectMapper.convertValue(quartosEntity, QuartosDTO.class);
        dto.setHoteisDTO(hoteisService.getPorId(quartosEntity.getIdHotel()));
        return dto;
    }

    public void removerQuartoPorHotel(Integer indexHotel) {
             quartosRepository.removerPorHotel(indexHotel);

   }

    public QuartosDTO update(Integer id, QuartosCreateDTO quartosCreateDTO) throws RegraDeNegocioException {
        QuartosEntity quartosEntity = objectMapper.convertValue(quartosCreateDTO,QuartosEntity.class);

        QuartosDTO quartosDTO = getQuartoPorId(id);

        if(listarQuartosPorHotel(quartosCreateDTO.getIdHotel()).stream().filter(x-> x.getNumeroQuarto().equals(quartosCreateDTO.getNumeroQuarto())).count() > 0){
            throw  new RegraDeNegocioException("Quarto já cadastrado");
        }

        QuartosEntity quartosEntity1 = quartosRepository.update(id,quartosEntity);

        QuartosDTO quartosDTO1 = objectMapper.convertValue(quartosEntity1,QuartosDTO.class);

        quartosDTO1.setHoteisDTO(hoteisService.getPorId(quartosCreateDTO.getIdHotel()));
        return quartosDTO1;


    }

    public void delete(Integer id) throws RegraDeNegocioException {
        quartosRepository.delete(id);
    }
}
