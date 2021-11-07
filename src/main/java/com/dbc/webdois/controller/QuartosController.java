package com.dbc.webdois.controller;


import com.dbc.webdois.dto.QuartosCreateDTO;
import com.dbc.webdois.dto.QuartosCreateDTODOIS;
import com.dbc.webdois.dto.QuartosDTO;
import com.dbc.webdois.exceptions.RegraDeNegocioException;
import com.dbc.webdois.service.QuartosService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequestMapping("/quartos")
@Slf4j
@RequiredArgsConstructor
public class QuartosController {
    private final QuartosService quartosService;

    @ApiOperation(value = "Lista Quartos")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Lista gerada com sucesso"),
            @ApiResponse(code = 400, message = "Algum dado inconsistente"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public List<QuartosDTO> list() {
        return quartosService.listarQuartos();
    }


    @ApiOperation(value = "Lista de Quartos por ID Hotel")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Lista gerada com sucesso"),
            @ApiResponse(code = 400, message = "Algum dado inconsistente"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/idhotel")
    public List<QuartosDTO> list(@Valid @RequestParam("idHotel") Integer idHotel) {
        return quartosService.listarQuartosPorHotel(idHotel);
    }

    @ApiOperation(value = "Criando Quartos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quarto Criado com sucesso!"),
            @ApiResponse(code = 400, message = "voçe não tem permição para usar esse recurso"),
            @ApiResponse(code = 500, message = "Exceção no sistema!")
    })

    @PostMapping("/{idHotel}")
    public QuartosDTO create(@PathVariable("idHotel") Integer id,
                             @Valid @RequestBody QuartosCreateDTODOIS quartosCreateDTODOIS) throws RegraDeNegocioException {
        QuartosDTO quartosDTO = quartosService.create(id ,quartosCreateDTODOIS);
        return quartosDTO;
    }

    @ApiOperation(value = "Atualizando Quartos Por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quarto  atualizada com sucesso!"),
            @ApiResponse(code = 400, message = "voçe não tem permição para usar esse recurso"),
            @ApiResponse(code = 500, message = "Exceção no sistema!")
    })
    @PutMapping("/{id}")
    public QuartosDTO update(@PathVariable("id")@Valid Integer id,
                              @RequestBody @Valid QuartosCreateDTO quartosCreateDTO) throws Exception {
        log.info("atualizando Quarto");
        QuartosDTO quartosDTO1 = quartosService.update(id,quartosCreateDTO);
        log.info("endereço atualizado");
        return quartosDTO1;
    }

    @ApiOperation(value = "Deletando Quarto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quarto Deletado com sucesso!"),
            @ApiResponse(code = 400, message = "voçe não tem permição para usar esse recurso"),
            @ApiResponse(code = 500, message = "Exceção no sistema!")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable ("id") @Valid Integer id) throws Exception {
        log.info("deletando Quarto");
        quartosService.delete(id);
        log.info("Quarto deletado");

    }

}
