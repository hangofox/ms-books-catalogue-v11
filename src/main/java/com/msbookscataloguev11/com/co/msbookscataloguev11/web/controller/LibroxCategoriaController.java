//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.web.controller;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.LibroxCategoriaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.RespuestaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service.LibroxCategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
* @Autor DAVID GIOVANNI PAEZ OVALLE.
* @Since 21/02/2026.
* Declaración del controlador.
*/
@RestController//DECLARACIÓN DEL CONTROLADOR PARA LOS CRUDS.
public class LibroxCategoriaController {
    
    @Autowired//INYECTAMOS EL SERVICIO.
    private LibroxCategoriaService libroxCategoriaService;
    
    //CONTROLADORES DE CRUDS (CREACIÓN, LECTURA (LISTAR Y CONSULTAR), EDICIÓN Y ELIMINACIÓN DE UN REGISTRO).
    
    //ENDPOINT PARA LISTAR LIBROS X CATEGORIAS SIN PAGINACIÓN (PARA SELECTS DEL FRONTEND):
    @GetMapping("/librosxcategorias/lista")
    public ResponseEntity<List<LibroxCategoriaDTO>> listarLibroxCategoriasNoPaginacion() {
        return new ResponseEntity<>(libroxCategoriaService.listaTodo(), HttpStatus.OK);
    }
    
    //ENDPOINT ÚNICO PARA LISTAR/FILTRAR/ORDENAR/PAGINAR LIBROS X CATEGORIAS CON QUERY PARAMS:
    @GetMapping("/librosxcategorias")
    public ResponseEntity<Slice<LibroxCategoriaDTO>> listarLibroxCategorias(
           @RequestParam(required = false) String keyword,
           @RequestParam(required = false) String orderBy,
           @RequestParam(required = false, defaultValue = "asc") String orderMode,
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<LibroxCategoriaDTO> slice = libroxCategoriaService.listarLibroxCategorias(keyword, orderBy, orderMode, pageable);
        return new ResponseEntity<>(slice, HttpStatus.OK);
    }
    
    //CREAR REGISTRO:
    @PostMapping("/librosxcategorias")//DECLARACIÓN DEL MAPEO DEL CRUD CREAR REGISTRO.
    public ResponseEntity<RespuestaDTO> crearLibroxCategoria(@RequestBody LibroxCategoriaDTO libroxCategoriaDTO){
        System.out.println(libroxCategoriaDTO);
        RespuestaDTO respuesta = libroxCategoriaService.crearLibroxCategoria(libroxCategoriaDTO);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //LEER CONSULTA DE REGISTRO POR ID:
    @GetMapping("/librosxcategoriasbyId/{idLibroxCategoria}")//DECLARACIÓN DEL MAPEO DEL CRUD CONSULTAR REGISTRO.
    public ResponseEntity<RespuestaDTO> consultarLibroxCategoriabyId(@PathVariable Long idLibroxCategoria){
        RespuestaDTO respuesta = libroxCategoriaService.consultarLibroxCategoriaById(idLibroxCategoria);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //LEER CONSULTA DE REGISTROS POR ID DEL LIBRO:
    @GetMapping("/librosxcategorias/libro/{idLibro}")//DECLARACIÓN DEL MAPEO DEL CRUD CONSULTAR REGISTROS POR LIBRO.
    public ResponseEntity<RespuestaDTO> consultarLibroxCategoriasByLibro(@PathVariable Long idLibro){
        RespuestaDTO respuesta = libroxCategoriaService.consultarLibroxCategoriasByLibro(idLibro);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //LEER CONSULTA DE REGISTROS POR ID DE LA CATEGORÍA:
    @GetMapping("/librosxcategorias/categoria/{idCategoria}")//DECLARACIÓN DEL MAPEO DEL CRUD CONSULTAR REGISTROS POR CATEGORÍA.
    public ResponseEntity<RespuestaDTO> consultarLibroxCategoriasByCategoria(@PathVariable Long idCategoria){
        RespuestaDTO respuesta = libroxCategoriaService.consultarLibroxCategoriasByCategoria(idCategoria);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //MODIFICAR REGISTRO:
    @PutMapping("/librosxcategorias/{idLibroxCategoria}")//DECLARACIÓN DEL MAPEO DEL CRUD MODIFICAR REGISTRO.
    public ResponseEntity<RespuestaDTO> actualizarLibroxCategoria(@PathVariable Long idLibroxCategoria, @RequestBody LibroxCategoriaDTO libroxCategoriaDTO){
        libroxCategoriaDTO.setIdLibroxCategoria(idLibroxCategoria);
        RespuestaDTO respuesta = libroxCategoriaService.actualizarLibroxCategoria(libroxCategoriaDTO);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //ELIMINAR REGISTRO:
    @DeleteMapping("/librosxcategorias/{idLibroxCategoria}")//DECLARACIÓN DEL MAPEO DEL CRUD ELIMINAR REGISTRO.
    public ResponseEntity<RespuestaDTO> eliminarLibroxCategoria(@PathVariable Long idLibroxCategoria){
        RespuestaDTO respuesta = libroxCategoriaService.eliminarLibroxCategoria(idLibroxCategoria);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
}
