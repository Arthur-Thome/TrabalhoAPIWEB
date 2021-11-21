package com.dbc.webdois.service;



import com.dbc.webdois.dto.HoteisComQuartosDTO;
import com.dbc.webdois.dto.HoteisCreateDTO;
import com.dbc.webdois.dto.HoteisDTO;
import com.dbc.webdois.dto.QuartosDTO;
import com.dbc.webdois.entity.HoteisEntity;
import com.dbc.webdois.exceptions.RegraDeNegocioException;
import com.dbc.webdois.repository.HoteisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HoteisService {
    private final HoteisRepository hoteisRepository;
    private final ObjectMapper objectMapper;


    public HoteisDTO create (HoteisCreateDTO hoteisCreateDTO) throws Exception {

        HoteisEntity entity = objectMapper.convertValue(hoteisCreateDTO, HoteisEntity.class);
        HoteisEntity hotelCriado = hoteisRepository.save(entity);
        HoteisDTO dto = objectMapper.convertValue(hotelCriado, HoteisDTO.class);
        return dto;

    }

    public List<HoteisDTO> list(){

        return hoteisRepository.findAll().stream()
        .map(hoteis ->{
            HoteisDTO hoteisDTO = objectMapper.convertValue(hoteis, HoteisDTO.class);
            return hoteisDTO;
        }).collect(Collectors.toList());
   }

    public HoteisDTO getById(Integer id)throws RegraDeNegocioException {
         return hoteisRepository.findById(id).map(hoteis ->{
            HoteisComQuartosDTO hoteisComQuartosDTO = objectMapper.convertValue(hoteis, HoteisComQuartosDTO.class);
            hoteisComQuartosDTO.setQuartosDTOList(hoteis.getQuartosEntity().stream()
                    .map(quartosEntity -> objectMapper.convertValue(quartosEntity, QuartosDTO.class)).collect(Collectors.toList()));
            return hoteisComQuartosDTO;
    }).orElseThrow(() -> new RegraDeNegocioException("hotel não encontrado"));
        }

    public HoteisDTO update(Integer id,
                            HoteisCreateDTO hoteisCreateDTO) throws Exception {
       HoteisEntity hoteisentite = objectMapper.convertValue(hoteisCreateDTO, HoteisEntity.class);
        HoteisEntity hoteisEntity = hoteisRepository.findById(id).orElseThrow(() -> new Exception("Hotel não Encontrado"));
        hoteisEntity.setCep(hoteisentite.getCep());
        hoteisEntity.setLogradouro(hoteisentite.getLogradouro());
        hoteisEntity.setNomeEstado(hoteisentite.getNomeEstado());
        hoteisEntity.setNomeHotel(hoteisentite.getNomeHotel());
        hoteisEntity.setNomeCidade(hoteisentite.getNomeCidade());
        hoteisEntity.setNumero(hoteisentite.getNumero());

        HoteisEntity hoteisEntity1 = hoteisRepository.save(hoteisEntity);

        HoteisDTO hoteisDTO1 = objectMapper.convertValue(hoteisEntity1, HoteisDTO.class);
        return hoteisDTO1;
    }


    public void delete(Integer id) throws Exception {
        HoteisEntity enderecoDeletar = hoteisRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Hotel não encontrado"));
        hoteisRepository.delete(enderecoDeletar);
    }


    //FindById
    public HoteisEntity findById(Integer id) throws RegraDeNegocioException {
        HoteisEntity entity = hoteisRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException(""));
        return entity;
    }
}

