//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.web.controller;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.RespuestaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.AutorDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
* @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 28/01/2026.
* Declaración del controlador.
*/
@RestController//DECLARACIÓN DEL CONTROLADOR PARA LOS CRUDS.
public class AutorController {
    
    @Autowired//INYECTAMOS EL SERVICIO.
    private AutorService autorService;
    
    //CONTROLADORES DE CRUDS (CREACIÓN, LECTURA (LISTAR Y CONSULTAR), EDICIÓN Y ELIMINACIÓN DE UN REGISTRO).
    
    //ENDPOINT ÚNICO PARA LISTAR/FILTRAR/ORDENAR/PAGINAR AUTORES CON QUERY PARAMS:
    @GetMapping("/autores")
    public ResponseEntity<Slice<AutorDTO>> listarAutores(
           @RequestParam(required = false) String keyword,
           @RequestParam(required = false) String orderBy,
           @RequestParam(required = false, defaultValue = "asc") String orderMode,
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<AutorDTO> autoresSlice = autorService.listarAutores(keyword, orderBy, orderMode, pageable);
        return new ResponseEntity<>(autoresSlice, HttpStatus.OK);
    }
    
    //CREAR REGISTRO:
    @PostMapping("/autores")//DECLARACIÓN DEL MAPEO DEL CRUD CREAR REGISTRO.
    //@PutMapping("/createAutor")//DECLARACIÓN DEL MAPEO DEL CRUD CREAR REGISTRO.
    public ResponseEntity<RespuestaDTO> crearAutor(@RequestBody AutorDTO autorDTO){
        System.out.println(autorDTO);
        RespuestaDTO respuesta = autorService.crearAutor(autorDTO);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //LEER CONSULTA DE REGISTRO POR ID:
    @GetMapping("/autoresbyId/{idAutor}")//DECLARACIÓN DEL MAPEO DEL CRUD CONSULTAR REGISTRO.
    public ResponseEntity<RespuestaDTO> consultarAutorbyId(@PathVariable Long idAutor){
        RespuestaDTO respuesta = autorService.consultarAutorporId(idAutor);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //MODIFICAR REGISTRO:
    @PutMapping("/autores/{idAutor}")//DECLARACIÓN DEL MAPEO DEL CRUD MODIFICAR REGISTRO.
    public ResponseEntity<RespuestaDTO> actualizarAutor(@PathVariable Long idAutor, @RequestBody AutorDTO autorDTO){
        autorDTO.setIdAutor(idAutor);
        RespuestaDTO respuesta = autorService.actualizarAutor(autorDTO);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //ELIMINAR REGISTRO:
    @DeleteMapping("/autores/{idAutor}")//DECLARACIÓN DEL MAPEO DEL CRUD ELIMINAR REGISTRO.
    public ResponseEntity<RespuestaDTO> eliminarAutor(@PathVariable Long idAutor){
        RespuestaDTO respuesta = autorService.eliminarAutor(idAutor);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //ENDPOINT SUGERENCIAS DE AUTORES (CRITERIO 1 - SEARCH-AS-YOU-TYPE / FULL-TEXT CON OPENSEARCH):
    //Usa MultiMatchQuery con tipo bool_prefix sobre los campos search_as_you_type de la entidad Autor.
    //Ejemplo: GET /autores/sugerencias?q=Gar  →  sugiere autores cuyo nombre/apellido empieza con "Gar"
    @GetMapping("/autores/sugerencias")
    public ResponseEntity<List<AutorDTO>> sugerenciasAutores(@RequestParam(defaultValue = "") String q) {
        List<AutorDTO> sugerencias = autorService.sugerenciasAutores(q);
        return new ResponseEntity<>(sugerencias, HttpStatus.OK);
    }
}
