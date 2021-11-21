package com.dbc.webdois.controller;

import com.dbc.webdois.dto.QuartosCreateDTO;
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

    @ApiOperation(value = "Listar Quartos")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Lista gerada com sucesso"),
            @ApiResponse(code = 400, message = "Algum dado inconsistente"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public List<QuartosDTO> list() {
        return quartosService.list();
    }

    @ApiOperation(value = "Lista de Quartos por ID Hotel")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Lista gerada com sucesso"),
            @ApiResponse(code = 400, message = "Algum dado inconsistente"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping("/idhotel")
    public List<QuartosDTO> listById(@Valid @RequestParam("idHotel") Integer idHotel) throws RegraDeNegocioException {
        return quartosService.getByIdHotel(idHotel);
    }

    @ApiOperation(value = "Criando Quartos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quarto Criado com sucesso!"),
            @ApiResponse(code = 400, message = "Algum dado inconsistente"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção")
    })

    @PostMapping("/{idHotel}")
    public QuartosDTO create(@PathVariable("idHotel") Integer id, @Valid @RequestBody QuartosCreateDTO quartosCreateDTO) throws RegraDeNegocioException {
        log.info("Criando Quarto");
        QuartosDTO quartosDTO = quartosService.create(id ,quartosCreateDTO);
        log.info("Quarto criado" );
        return quartosDTO;
    }

    @ApiOperation(value = "Atualizar Quartos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quarto  atualizada com sucesso!"),
            @ApiResponse(code = 400, message = "Algum dado inconsistente"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção")
    })
    @PutMapping("/{id}")
    public QuartosDTO update(@PathVariable("id")@Valid Integer id, @RequestBody @Valid QuartosCreateDTO quartosCreateDTO) throws RegraDeNegocioException {
        log.info("Atualizando Quarto");
        QuartosDTO quartosDTO1 = quartosService.update(id,quartosCreateDTO);
        log.info("Quarto atualizado");
        return quartosDTO1;
    }

    @ApiOperation(value = "Deletar Quarto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Quarto deletado com sucesso!"),
            @ApiResponse(code = 400, message = "Algum dado inconsistente"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção")
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable ("id") @Valid Integer id) throws Exception {
        log.info("Deletando quarto");
        quartosService.delete(id);
        log.info("Quarto deletado");

    }

}
