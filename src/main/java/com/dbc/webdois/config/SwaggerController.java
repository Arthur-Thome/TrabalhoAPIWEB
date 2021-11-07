package com.dbc.webdois.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@ApiIgnore
public class SwaggerController {

    @GetMapping(value = "/") //localhost:8080 //localhost:8080/swagger-ui/
    public void index(HttpServletResponse response) throws IOException {
        response.sendRedirect("/swagger-ui/");
    }
}
