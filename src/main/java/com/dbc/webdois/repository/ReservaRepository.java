package com.dbc.webdois.repository;


import com.dbc.webdois.entity.ReservaEntity;
import com.dbc.webdois.exceptions.RegraDeNegocioException;
import org.springframework.stereotype.Repository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ReservaRepository {
    private static List<ReservaEntity> listaReservasEntity = new ArrayList<>();
    private final AtomicInteger COUNTER = new AtomicInteger();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public ReservaRepository(){
          listaReservasEntity.add(new ReservaEntity(COUNTER.incrementAndGet(),1,1,1));
          listaReservasEntity.add(new ReservaEntity(COUNTER.incrementAndGet(),1,1,1));
    }

    //Lista
    public List<ReservaEntity> list(){
        return listaReservasEntity;
    }

    //Create
    public ReservaEntity create(ReservaEntity reservaEntity){
        reservaEntity.setIdReserva(COUNTER.incrementAndGet());
        listaReservasEntity.add(reservaEntity);
        return reservaEntity;
    }
    //Update
    public ReservaEntity update(Integer id, ReservaEntity reservaEntity) throws Exception {
        ReservaEntity reservaRecuperada = listaReservasEntity.stream()
                .filter(reserva -> reserva.getIdReserva().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Reserva não encontrada"));
        reservaRecuperada.setIdQuarto(reservaEntity.getIdQuarto());
        reservaRecuperada.setIdHotel(reservaEntity.getIdHotel());
        reservaRecuperada.setIdUsuario(reservaEntity.getIdUsuario());

        return reservaRecuperada;
    }

    //Deleta
    public void delete(Integer id) throws RegraDeNegocioException{
        ReservaEntity reservaEntity = listaReservasEntity.stream()
                .filter(x -> x.getIdReserva().equals(id))
                .findFirst()
                .orElseThrow(() -> new RegraDeNegocioException("Reserva não encontrada"));
        listaReservasEntity.remove(reservaEntity);
    }


}