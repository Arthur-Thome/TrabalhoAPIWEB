package com.dbc.webdois.repository;

import com.dbc.webdois.entity.HoteisEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoteisRepository extends JpaRepository<HoteisEntity, Integer> {

}

