//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.web.controller;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.ParametroDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.RespuestaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service.ParametroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
* @Autor MARIA MAGDALENA PARRAGA CASTRO.
* @Since 28/01/2026.
* Declaración del controlador.
*/
@RestController//DECLARACIÓN DEL CONTROLADOR PARA LOS CRUDS.
public class ParametroController {
    
    @Autowired//INYECTAMOS EL SERVICIO.
    private ParametroService parametroService;
    
    //CONTROLADORES DE CRUDS (CONSULTAR Y EDICIÓN DE UN REGISTRO).
    
    //LEER CONSULTA DE REGISTRO POR ID:
    @GetMapping("/parametrosbyId/{idParametro}")//DECLARACIÓN DEL MAPEO DEL CRUD CONSULTAR REGISTRO.
    public ResponseEntity<RespuestaDTO> consultarParametrobyId(@PathVariable Long idParametro){
        RespuestaDTO respuesta = parametroService.consultarParametroporId(idParametro);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //MODIFICAR REGISTRO:
    @PutMapping("/parametros/{idParametro}")//DECLARACIÓN DEL MAPEO DEL CRUD MODIFICAR REGISTRO.
    public ResponseEntity<RespuestaDTO> actualizarParametro(@PathVariable Long idParametro, @RequestBody ParametroDTO parametroDTO){
        parametroDTO.setIdParametro(idParametro);
        RespuestaDTO respuesta = parametroService.actualizarParametro(parametroDTO);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
}
