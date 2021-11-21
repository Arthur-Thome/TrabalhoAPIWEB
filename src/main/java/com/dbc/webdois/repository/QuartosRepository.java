package com.dbc.webdois.repository;


import com.dbc.webdois.entity.QuartosEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuartosRepository extends JpaRepository<QuartosEntity, Integer> {

    @Query(value = "SELECT * from quartos WHERE id_hoteis= :id ", nativeQuery = true)
    List<QuartosEntity> findByidHotel(Integer id);
}
