package com.dbc.webdois.service;

import com.dbc.webdois.dto.ReservaCreateDTO;
import com.dbc.webdois.dto.ReservaDTO;

import com.dbc.webdois.entity.HoteisEntity;
import com.dbc.webdois.entity.QuartosEntity;
import com.dbc.webdois.entity.ReservaEntity;
import com.dbc.webdois.entity.UsuarioEntity;
import com.dbc.webdois.exceptions.RegraDeNegocioException;
import com.dbc.webdois.repository.HoteisRepository;
import com.dbc.webdois.repository.QuartosRepository;
import com.dbc.webdois.repository.ReservaRepository;
import com.dbc.webdois.repository.UsuarioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservaService {
    private final ReservaRepository reservaRepository;
    private final HoteisRepository hoteisRepository;
    private final QuartosRepository quartosRepository;
    private final UsuarioRepository usuarioRepository;

    private final ObjectMapper objectMapper;
    private final QuartosService quartosService;
    private final HoteisService hoteisService;
    private final UsuarioService usuarioService;
    private final EmailService emailService;

    //Lista
    public List<ReservaDTO> list(){
        return reservaRepository.list().stream()
                .map(x -> {
                    ReservaDTO reservaDTO = objectMapper.convertValue(x, ReservaDTO.class);
                    try {
                        reservaDTO.setQuartosDTO(quartosService.getQuartoPorId(x.getIdQuarto()));
                        reservaDTO.setHoteisDTO(hoteisService.getPorId(x.getIdHotel()));
                        reservaDTO.setUsuarioDTO(usuarioService.getPorId(x.getIdUsuario()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return reservaDTO;
                })
                .collect(Collectors.toList());

    }

    //Create
    public ReservaDTO create(ReservaCreateDTO reservaCreateDTO) throws Exception {
        ReservaEntity entity = objectMapper.convertValue(reservaCreateDTO, ReservaEntity.class);

        HoteisEntity hotelEntity = hoteisRepository.list().stream()
                .filter(x -> x.getIdHotel().equals(entity.getIdHotel()))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Hotel não encontrado"));

                                                                                //1
        QuartosEntity quartosEntity = quartosRepository.listarQuartosPorHotel(hotelEntity.getIdHotel())
                .stream()
                .filter(x -> x.getIdQuarto().equals(entity.getIdQuarto()))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Quarto não encontrado neste hotel"));

        UsuarioEntity usuarioEntity = usuarioRepository.list()
                .stream()
                .filter(x -> x.getIdUsuario().equals(entity.getIdUsuario()))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));

        ReservaEntity novaReserva = reservaRepository.create(entity);
        ReservaDTO dto = objectMapper.convertValue(novaReserva, ReservaDTO.class);

        dto.setHoteisDTO(hoteisService.getPorId(novaReserva.getIdHotel()));
        dto.setQuartosDTO(quartosService.getQuartoPorId(novaReserva.getIdQuarto()));
        dto.setUsuarioDTO(usuarioService.getPorId(novaReserva.getIdUsuario()));
        emailService.enviarCadastroReserva(dto);
        return dto;
    }

    //Update
    public ReservaDTO update(Integer id, ReservaCreateDTO reservaCreateDTO) throws Exception {
        ReservaEntity entity = objectMapper.convertValue(reservaCreateDTO, ReservaEntity.class);

        HoteisEntity hotelEntity = hoteisRepository.list().stream()
                .filter(x -> x.getIdHotel().equals(entity.getIdHotel()))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Hotel não encontrado"));

        //1
        QuartosEntity quartosEntity = quartosRepository.listarQuartosPorHotel(hotelEntity.getIdHotel())
                .stream()
                .filter(x -> x.getIdQuarto().equals(entity.getIdQuarto()))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Quarto não encontrado neste hotel"));

        UsuarioEntity usuarioEntity = usuarioRepository.list()
                .stream()
                .filter(x -> x.getIdUsuario().equals(entity.getIdUsuario()))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Usuário não encontrado"));


        ReservaEntity atualizado = reservaRepository.update(id, entity);
        ReservaDTO dto = objectMapper.convertValue(atualizado, ReservaDTO.class);

        dto.setHoteisDTO(hoteisService.getPorId(atualizado.getIdHotel()));
        dto.setQuartosDTO(quartosService.getQuartoPorId(atualizado.getIdQuarto()));
        dto.setUsuarioDTO(usuarioService.getPorId(atualizado.getIdUsuario()));

        return dto;
    }

    //Deleta
    public  void delete(Integer id) throws RegraDeNegocioException {
        reservaRepository.delete(id);
    }

}
