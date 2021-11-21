package com.dbc.webdois.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HoteisComQuartosDTO extends HoteisDTO {

    private List<QuartosDTO> quartosDTOList;
}
