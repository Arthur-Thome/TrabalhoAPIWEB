package com.dbc.webdois.service;

import com.dbc.webdois.dto.*;
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

    //Listar
    public List<ReservaDTO> list() {

        return reservaRepository.findAll().stream().map(reserva -> {
                    ReservaDTO reservaDTO = objectMapper.convertValue(reserva, ReservaDTO.class);
                    reservaDTO.setUsuarioDTO(objectMapper.convertValue(reserva.getUsuarioEntity(), UsuarioDTO.class));
                    reservaDTO.getUsuarioDTO()
                            .setGrupos(reserva.getUsuarioEntity().getGrupos().stream().map(grupoEntity ->
                            objectMapper.convertValue(grupoEntity, GrupoDTO.class)).collect(Collectors.toList()));
                    reservaDTO.setHoteisDTO(objectMapper.convertValue(reserva.getHoteisEntity(), HoteisDTO.class));
                    reservaDTO.setQuartosDTO(objectMapper.convertValue(reserva.getQuartosEntity(), QuartosDTO.class));
                    return reservaDTO;
                })
                .collect(Collectors.toList());
    }

    //Criar
    public ReservaDTO create(ReservaCreateDTO reservaCreateDTO) throws RegraDeNegocioException {
        ReservaEntity reservaEntity = objectMapper.convertValue(reservaCreateDTO, ReservaEntity.class);
        QuartosEntity quartosEntity = quartosRepository.findById(reservaCreateDTO.getIdQuarto())
                .orElseThrow(() -> new RegraDeNegocioException("Quarto não encontrado"));

        HoteisEntity hoteisEntity = hoteisRepository.findById(reservaCreateDTO.getIdHotel())
                .orElseThrow(() -> new RegraDeNegocioException("Hotel não encontrado"));

        UsuarioEntity usuarioEntity = usuarioRepository.findById(reservaCreateDTO.getIdUsuario())
                .orElseThrow(() -> new RegraDeNegocioException("Usuario não encontrado"));

        if(quartosEntity.getHoteisEntity().getIdHotel() != hoteisEntity.getIdHotel()){
            throw  new RegraDeNegocioException("o quarto não pertence ao mesmo hotel");
        }

        reservaEntity.setUsuarioEntity(usuarioEntity);
        reservaEntity.setHoteisEntity(hoteisEntity);
        reservaEntity.setQuartosEntity(quartosEntity);
        ReservaEntity reservaCriar = reservaRepository.save(reservaEntity);
        ReservaDTO reservaDTO = objectMapper.convertValue(reservaEntity, ReservaDTO.class);
        reservaDTO.setUsuarioDTO(objectMapper.convertValue(reservaCriar.getUsuarioEntity(), UsuarioDTO.class));
        reservaDTO.getUsuarioDTO().setGrupos(reservaCriar.getUsuarioEntity().getGrupos().stream().map(grupoEntity ->
                objectMapper.convertValue(grupoEntity, GrupoDTO.class)).collect(Collectors.toList()));
        reservaDTO.setHoteisDTO(objectMapper.convertValue(reservaCriar.getHoteisEntity(), HoteisDTO.class));
        reservaDTO.setQuartosDTO(objectMapper.convertValue(reservaCriar.getQuartosEntity(), QuartosDTO.class));
        return reservaDTO;

    }
    //Deletar
    public void delete(Integer id) throws RegraDeNegocioException {
        ReservaEntity reservaEntity = findById(id);
        reservaRepository.delete(reservaEntity);
    }

    //Update
    public ReservaDTO update(Integer id, ReservaCreateDTO reservaCreateDTO) throws RegraDeNegocioException {
        findById(id);
        ReservaEntity reservaEntity = objectMapper.convertValue(reservaCreateDTO, ReservaEntity.class);
        reservaEntity.setUsuarioEntity(usuarioRepository.getById(reservaCreateDTO.getIdUsuario()));
        reservaEntity.setHoteisEntity(hoteisRepository.getById(reservaCreateDTO.getIdHotel()));
        reservaEntity.setQuartosEntity(quartosRepository.getById(reservaCreateDTO.getIdQuarto()));
        reservaEntity.setIdReserva(id);

        ReservaEntity reservaCriar = reservaRepository.save(reservaEntity);

        ReservaDTO reservaDTO = objectMapper.convertValue(reservaCriar, ReservaDTO.class);
        reservaDTO.setUsuarioDTO(objectMapper.convertValue(reservaCriar.getUsuarioEntity(), UsuarioDTO.class));
        reservaDTO.getUsuarioDTO().setGrupos(reservaCriar.getUsuarioEntity().getGrupos().stream().map(grupoEntity ->
                objectMapper.convertValue(grupoEntity, GrupoDTO.class)).collect(Collectors.toList()));
        reservaDTO.setHoteisDTO(objectMapper.convertValue(reservaCriar.getHoteisEntity(), HoteisDTO.class));
        reservaDTO.setQuartosDTO(objectMapper.convertValue(reservaCriar.getQuartosEntity(), QuartosDTO.class));


        return reservaDTO;

    }

    //FindById
    public ReservaEntity findById(Integer id) throws RegraDeNegocioException {
        ReservaEntity entity = reservaRepository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException(""));
        return entity;
    }

    //GetById
    public ReservaDTO getById(Integer id) throws RegraDeNegocioException {
        ReservaEntity entity = findById(id);
        ReservaDTO reservaDTO = objectMapper.convertValue(entity, ReservaDTO.class);
        reservaDTO.setUsuarioDTO(objectMapper.convertValue(entity.getUsuarioEntity(), UsuarioDTO.class));
        reservaDTO.getUsuarioDTO().setGrupos(entity.getUsuarioEntity().getGrupos().stream().map(grupoEntity ->
                objectMapper.convertValue(grupoEntity, GrupoDTO.class)).collect(Collectors.toList()));
        reservaDTO.setHoteisDTO(objectMapper.convertValue(entity.getHoteisEntity(), HoteisDTO.class));
        reservaDTO.setQuartosDTO(objectMapper.convertValue(entity.getQuartosEntity(), QuartosDTO.class));
        return reservaDTO;
    }

    //UsuarioComReserva
    public UsuarioComReservaDTO reservasPorUsuario(Integer id) throws RegraDeNegocioException{
        UsuarioEntity usuario = usuarioRepository.getById(id);
        UsuarioComReservaDTO usuarioComReservaDTO= objectMapper.convertValue(usuario, UsuarioComReservaDTO.class);
        usuarioComReservaDTO.setGrupos(usuario.getGrupos().stream().map(grupoEntity ->
                objectMapper.convertValue(grupoEntity, GrupoDTO.class)).collect(Collectors.toList()));
        usuarioComReservaDTO.setReservas(
                usuario.getReservas()
                        .stream()
                        .map(reserva -> {
                            ReservaSemUsuarioDTO reservaDto = objectMapper.convertValue(reserva, ReservaSemUsuarioDTO.class);

                            reservaDto.setHoteisDTO(objectMapper.convertValue(reserva.getHoteisEntity(), HoteisDTO.class));
                            reservaDto.setQuartosDTO(objectMapper.convertValue(reserva.getQuartosEntity(), QuartosDTO.class));

                            return reservaDto;
                        })
                        .collect(Collectors.toList())
        );

        return usuarioComReservaDTO;
    }

    //HoteisComReserva
    public List<HotelComReservaDTO> listReservasPorHotel(Integer id) throws RegraDeNegocioException{
        return hoteisRepository.findById(id).stream()
                .map(hotel -> {
                    HotelComReservaDTO hotelComReservaDTO = objectMapper.convertValue(hotel, HotelComReservaDTO.class);
                    hotelComReservaDTO.setReservas(
                            hotel.getReservas()
                                    .stream()
                                    .map(reserva -> {
                                        ReservaSemHotelDTO reservaSemHotelDTO = objectMapper.convertValue(reserva, ReservaSemHotelDTO.class);
                                        reservaSemHotelDTO.setUsuarioDTO(objectMapper.convertValue(reserva.getUsuarioEntity(), UsuarioDTO.class));
                                        reservaSemHotelDTO.getUsuarioDTO().setGrupos(reserva.getUsuarioEntity().getGrupos().stream().map(
                                                grupoEntity -> objectMapper.convertValue(grupoEntity, GrupoDTO.class)
                                        ).collect(Collectors.toList()));
                                        reservaSemHotelDTO.setQuartosDTO(objectMapper.convertValue(reserva.getQuartosEntity(), QuartosDTO.class));

                                        return reservaSemHotelDTO;
                                    })
                                    .collect(Collectors.toList())
                    );
                    return hotelComReservaDTO;
                })
                .collect(Collectors.toList());
    }


}

