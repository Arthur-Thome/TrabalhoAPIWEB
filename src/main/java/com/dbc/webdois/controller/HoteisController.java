package com.dbc.webdois.controller;

import com.dbc.webdois.dto.HoteisCreateDTO;
import com.dbc.webdois.dto.HoteisDTO;
import com.dbc.webdois.exceptions.RegraDeNegocioException;
import com.dbc.webdois.service.HoteisService;
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
@RequestMapping("/hoteis")
@Slf4j
@RequiredArgsConstructor
public class HoteisController{
    private final HoteisService hoteisService;

    @ApiOperation(value = "Lista de Hoteis")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada um execeção")})
    @GetMapping
    public List<HoteisDTO> list(){
        return hoteisService.list();
    }


    @ApiOperation(value = "Lista de Hoteis por id")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada um execeção")})
    @GetMapping("/{idHotel}")
    public List<HoteisDTO> getById(@Valid @PathVariable("idHotel") Integer id){
        return hoteisService.getById(id);
    }

    @ApiOperation(value = "Cria um Hotel")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Hotel criado"),
            @ApiResponse(code = 400, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada um execeção")
    })
    @PostMapping
    public HoteisDTO create(@Valid @RequestBody HoteisCreateDTO hoteisCreateDTO) throws RegraDeNegocioException {
        log.info("Criando hotel");
        HoteisDTO hoteisDTO = hoteisService.create(hoteisCreateDTO);
        log.info("Hotel criado");

        return hoteisDTO;
    }

    @ApiOperation(value = "Update um hotel")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Hotel atualizado"),
            @ApiResponse(code = 400, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada um execeção")
    })
    @PutMapping("/{idHotel}")
    public HoteisDTO update(@Valid @PathVariable("idHotel") Integer id,
                             @Valid @RequestBody HoteisCreateDTO hoteisCreateDTO) throws Exception {
        log.info("Atualizando hotel");
        HoteisDTO hotel = hoteisService.update(id, hoteisCreateDTO);
        log.info("Hotel atualizado com sucesso");

        return hotel;
    }

    @ApiOperation(value = "Delata um hotel")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Hotel deletado"),
            @ApiResponse(code = 400, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada um execeção")
    })
    @DeleteMapping("/{idHotel}")
    public void delete(@PathVariable("idHotel") Integer id) throws Exception {
        hoteisService.delete(id);
    }


}
