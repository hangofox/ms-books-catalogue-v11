//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.web.controller;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.RespuestaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.CategoriaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service.CategoriaService;
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
public class CategoriaController {
    
    @Autowired//INYECTAMOS EL SERVICIO.
    private CategoriaService categoriaService;
    
    //CONTROLADORES DE CRUDS (CREACIÓN, LECTURA (LISTAR Y CONSULTAR), EDICIÓN Y ELIMINACIÓN DE UN REGISTRO).
    
    //ENDPOINT PARA LISTAR CATEGORIAS SIN PAGINACIÓN (PARA SELECTS DEL FRONTEND):
    @GetMapping("/categorias/lista")
    public ResponseEntity<List<CategoriaDTO>> listarCategoriasNoPaginacion(
           @RequestParam(required = false) String keyword,
           @RequestParam(required = false) String orderBy,
           @RequestParam(required = false, defaultValue = "asc") String orderMode
    ) {
        List<CategoriaDTO> categoriasList = categoriaService.listarCategoriasNoPaginacion(keyword, orderBy, orderMode);
        return new ResponseEntity<>(categoriasList, HttpStatus.OK);
    }
    
    //ENDPOINT ÚNICO PARA LISTAR/FILTRAR/ORDENAR/PAGINAR CATEGORIAS CON QUERY PARAMS:
    @GetMapping("/categorias")
    public ResponseEntity<Slice<CategoriaDTO>> listarCategorias(
           @RequestParam(required = false) String keyword,
           @RequestParam(required = false) String orderBy,
           @RequestParam(required = false, defaultValue = "asc") String orderMode,
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<CategoriaDTO> categoriasSlice = categoriaService.listarCategorias(keyword, orderBy, orderMode, pageable);
        return new ResponseEntity<>(categoriasSlice, HttpStatus.OK);
    }
    
    //CREAR REGISTRO:
    @PostMapping("/categorias")//DECLARACIÓN DEL MAPEO DEL CRUD CREAR REGISTRO.
    //@PutMapping("/categorias")//DECLARACIÓN DEL MAPEO DEL CRUD CREAR REGISTRO.
    public ResponseEntity<RespuestaDTO> crearCategoria(@RequestBody CategoriaDTO categoriaDTO){
        System.out.println(categoriaDTO);
        RespuestaDTO respuesta = categoriaService.crearCategoria(categoriaDTO);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //LEER CONSULTA DE REGISTRO POR ID:
    @GetMapping("/categoriasbyId/{idCategoria}")//DECLARACIÓN DEL MAPEO DEL CRUD CONSULTAR REGISTRO.
    public ResponseEntity<RespuestaDTO> consultarCategoriabyId(@PathVariable Long idCategoria){
        RespuestaDTO respuesta = categoriaService.consultarCategoriaporId(idCategoria);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //LEER CONSULTA DE REGISTRO POR NOMBRE:
    @GetMapping("/categoriasbyNombre/{nombreCategoria}")//DECLARACIÓN DEL MAPEO DEL CRUD CONSULTAR REGISTRO.
    public ResponseEntity<RespuestaDTO> consultarCategoriabyNombre(@PathVariable String nombreCategoria){
        RespuestaDTO respuesta = categoriaService.consultarCategoriaporNombre(nombreCategoria);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //MODIFICAR REGISTRO:
    @PutMapping("/categorias/{idCategoria}")//DECLARACIÓN DEL MAPEO DEL CRUD MODIFICAR REGISTRO.
    public ResponseEntity<RespuestaDTO> actualizarCategoria(@PathVariable Long idCategoria, @RequestBody CategoriaDTO categoriaDTO){
        categoriaDTO.setIdCategoria(idCategoria);
        RespuestaDTO respuesta = categoriaService.actualizarCategoria(categoriaDTO);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //ELIMINAR REGISTRO:
    @DeleteMapping("/categorias/{idCategoria}")//DECLARACIÓN DEL MAPEO DEL CRUD ELIMINAR REGISTRO.
    public ResponseEntity<RespuestaDTO> eliminarCategoria(@PathVariable Long idCategoria){
        RespuestaDTO respuesta = categoriaService.eliminarCategoria(idCategoria);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
}
