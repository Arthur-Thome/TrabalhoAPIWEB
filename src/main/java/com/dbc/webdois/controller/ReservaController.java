package com.dbc.webdois.controller;

import com.dbc.webdois.dto.ReservaCreateDTO;
import com.dbc.webdois.dto.ReservaDTO;

import com.dbc.webdois.exceptions.RegraDeNegocioException;
import com.dbc.webdois.service.ReservaService;
import freemarker.template.TemplateException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Validated
@RestController
@RequestMapping("/reserva")
@RequiredArgsConstructor
@Slf4j
public class ReservaController {
    private final ReservaService reservaService;


    @ApiOperation(value = "Lista reservas")
    @ApiResponses(value ={
            @ApiResponse(code = 400, message = "Algum dado inconsistente"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @GetMapping
    public List<ReservaDTO> list(){
        return reservaService.list();
    }


    @ApiOperation(value = "Cria uma reserva")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Reserva criada com sucesso"),
            @ApiResponse(code = 400, message = "Algum dado inconsistente"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PostMapping
    public ReservaDTO create(@Valid @RequestBody ReservaCreateDTO reservaCreateDTO) throws Exception {
        log.info("Criando reserva...");
        ReservaDTO reservaDTO = reservaService.create(reservaCreateDTO);
        log.info("Reserva criada");
        return reservaDTO;
    }


    @ApiOperation(value = "Atualiza reservas")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Reserva atualizada com sucesso"),
            @ApiResponse(code = 400, message = "Algum dado inconsistente"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @PutMapping("/{idReserva}")
    public ReservaDTO update(@Valid @PathVariable("idReserva") Integer id,
                             @Valid @RequestBody ReservaCreateDTO reservaCreateDTO) throws Exception {

        log.info("Atualizando reserva");
        ReservaDTO reservaDTO = reservaService.update(id, reservaCreateDTO);
        log.info("Atualizado com sucesso");

        return reservaDTO;
    }

    @ApiOperation(value = "Deleta reservas")
    @ApiResponses(value ={
            @ApiResponse(code = 200, message = "Reserva deletada com sucesso"),
            @ApiResponse(code = 400, message = "Algum dado inconsistente"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
    })
    @DeleteMapping("/{idReserva}")
    public void deletar(@Valid @PathVariable("idReserva") Integer id) throws RegraDeNegocioException, MessagingException, TemplateException, IOException {
        log.info("Deletando reserva...");
        reservaService.delete(id);

        log.info("Deletado...");
    }

}
