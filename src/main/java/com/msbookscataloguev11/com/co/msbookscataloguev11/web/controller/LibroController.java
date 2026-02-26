//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.web.controller;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.FacetDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.RespuestaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.LibroDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Autor PD04. HERNAN ADOLFO NUÑEZ GONZALEZ.
 * @Since 02/02/2026.
 * Declaración de la entidad.
 * @Actualizacion David Paez 06/02/2026.
*/
@RestController//DECLARACIÓN DEL CONTROLADOR PARA LOS CRUDS.
public class LibroController {
    
    @Autowired//INYECTAMOS EL SERVICIO.
    private LibroService libroService;
    
    //CONTROLADORES DE CRUDS (CREACIÓN, LECTURA (LISTAR Y CONSULTAR), EDICIÓN Y ELIMINACIÓN DE UN REGISTRO).
    
    //ENDPOINT LISTAR TODOS LOS LIBROS VISIBLES:
    @GetMapping("/libros/todos")
    public ResponseEntity<List<LibroDTO>> listarUsuarios() {
        List<LibroDTO> listaLibros = libroService.listaTodo();
        return new ResponseEntity<>(listaLibros, HttpStatus.OK);
    }
    
    //ENDPOINT LISTAR TODOS LOS LIBROS SIN PAGINACION (PARA SELECTS DEL FRONTEND):
    @GetMapping("/libros/lista")
    public ResponseEntity<List<LibroDTO>> listarLibrosNoPaginacion(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String  titulo,
            @RequestParam(required = false) String  sinopsisLibro,
            @RequestParam(required = false) String  codigoIsbnLibro,
            @RequestParam(required = false) String  formatoLibro,
            @RequestParam(required = false) String  estadoLibro,
            @RequestParam(required = false) String  fechaPublicacionLibro,
            @RequestParam(required = false) Long    idCategoria,
            @RequestParam(required = false) String  nombreCategoria,
            @RequestParam(required = false) Long    idAutor,
            @RequestParam(required = false) String  nombresAutor,
            @RequestParam(required = false) String  primerApellidoAutor,
            @RequestParam(required = false) String  segundoApellidoAutor,
            @RequestParam(required = false) Double  minPrecio,
            @RequestParam(required = false) Double  maxPrecio,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false, defaultValue = "asc") String orderMode
    ) {
        List<LibroDTO> listaLibros = libroService.listarLibrosNoPaginacion(
                keyword, titulo,
                sinopsisLibro, codigoIsbnLibro, formatoLibro, estadoLibro, fechaPublicacionLibro,
                idCategoria, nombreCategoria, idAutor,
                nombresAutor, primerApellidoAutor, segundoApellidoAutor,
                minPrecio, maxPrecio, orderBy, orderMode
        );
        return new ResponseEntity<>(listaLibros, HttpStatus.OK);
    }
    
    //ENDPOINT ÚNICO PARA LISTAR/FILTRAR/ORDENAR/PAGINAR LIBROS CON QUERY PARAMS:
    @GetMapping("/libros")
    public ResponseEntity<Slice<LibroDTO>> listarLibros(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String  titulo,
            @RequestParam(required = false) String  sinopsisLibro,
            @RequestParam(required = false) String  codigoIsbnLibro,
            @RequestParam(required = false) String  formatoLibro,
            @RequestParam(required = false) String  estadoLibro,
            @RequestParam(required = false) String  fechaPublicacionLibro,
            @RequestParam(required = false) Long    idCategoria,
            @RequestParam(required = false) String  nombreCategoria,          // <-- NUEVO
            @RequestParam(required = false) Long    idAutor,
            @RequestParam(required = false) String  nombresAutor,             // <-- NUEVO
            @RequestParam(required = false) String  primerApellidoAutor,      // <-- NUEVO
            @RequestParam(required = false) String  segundoApellidoAutor,     // <-- NUEVO
            @RequestParam(required = false) Double  minPrecio,
            @RequestParam(required = false) Double  maxPrecio,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false, defaultValue = "asc") String orderMode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Slice<LibroDTO> librosSlice = libroService.listarLibros(
                keyword, titulo,
                sinopsisLibro, codigoIsbnLibro, formatoLibro, estadoLibro, fechaPublicacionLibro,
                idCategoria, nombreCategoria, idAutor,
                nombresAutor, primerApellidoAutor, segundoApellidoAutor,
                minPrecio, maxPrecio, orderBy, orderMode, pageable
        );
        if (librosSlice == null || librosSlice.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>(librosSlice, HttpStatus.OK);
    }
    
    //ENDPOINT PARA LISTAR LIBROS POR CRITERIOS INDIVIDUALES Y COMBINADOS:
    @GetMapping("/librosporCriteriosIndividualesyCombinados")
    public ResponseEntity<Slice<LibroDTO>> listarLibrosPorCriterios(
            @RequestParam(required = false) String tituloLibro,
            @RequestParam(required = false) String fechaPublicacionLibro,
            @RequestParam(required = false) String sinopsisLibro,
            @RequestParam(required = false) String codigoIsbnLibro,
            @RequestParam(required = false) String precioLibro,
            @RequestParam(required = false) String formatoLibro,
            @RequestParam(required = false) String estadoLibro,
            @RequestParam(required = false) String orderBy,
            @RequestParam(required = false, defaultValue = "asc") String orderMode,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Slice<LibroDTO> librosSlice = libroService.buscarLibrosPorCriterios(
                tituloLibro,
                fechaPublicacionLibro,
                sinopsisLibro,
                codigoIsbnLibro,
                precioLibro,
                formatoLibro,
                estadoLibro,
                orderBy,
                orderMode,
                pageable
        );
        return new ResponseEntity<>(librosSlice, HttpStatus.OK);
    }
    
    //CREAR REGISTRO:
    @PostMapping("/libros")//DECLARACIÓN DEL MAPEO DEL CRUD CREAR REGISTRO.
    //@PutMapping("/createLibro")//DECLARACIÓN DEL MAPEO DEL CRUD CREAR REGISTRO.
    public ResponseEntity<RespuestaDTO> crearLibro(@RequestBody LibroDTO libroDTO){
        System.out.println(libroDTO);
        RespuestaDTO respuesta = libroService.crearLibro(libroDTO);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //CARGUE MASIVO DE LIBROS:
    @PostMapping("/libros/cargue-masivo")//DECLARACIÓN DEL MAPEO DEL CRUD CARGUE MASIVO.
    public ResponseEntity<RespuestaDTO> crearLibrosMasivo(@RequestBody List<LibroDTO> librosDTO){
        RespuestaDTO respuesta = libroService.crearLibrosMasivo(librosDTO);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //LEER CONSULTA DE REGISTRO POR ID:
    @GetMapping("/libros/{idLibro}")//DECLARACIÓN DEL MAPEO DEL CRUD CONSULTAR REGISTRO.
    public ResponseEntity<RespuestaDTO> consultarLibrobyId(@PathVariable Long idLibro){
        RespuestaDTO respuesta = libroService.consultarLibroporId(idLibro);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //MODIFICAR REGISTRO:
    @PutMapping("/libros/{idLibro}")//DECLARACIÓN DEL MAPEO DEL CRUD MODIFICAR REGISTRO.
    public ResponseEntity<RespuestaDTO> actualizarLibro(@PathVariable Long idLibro, @RequestBody LibroDTO libroDTO){
        libroDTO.setIdLibro(idLibro);
        RespuestaDTO respuesta = libroService.actualizarLibro(libroDTO);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //ELIMINAR REGISTRO:
    @DeleteMapping("/libros/{idLibro}")//DECLARACIÓN DEL MAPEO DEL CRUD ELIMINAR REGISTRO.
    public ResponseEntity<RespuestaDTO> eliminarLibro(@PathVariable Long idLibro){
        RespuestaDTO respuesta = libroService.eliminarLibro(idLibro);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //ELIMINACIÓN MASIVA DE LIBROS (TRUNCATE):
    @DeleteMapping("/libros/truncado")//DECLARACIÓN DEL MAPEO DEL CRUD ELIMINACIÓN MASIVA.
    public ResponseEntity<RespuestaDTO> eliminarTodosLosLibros() {
        RespuestaDTO respuesta = libroService.eliminarTodosLosLibros();
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //ENDPOINTS RELACIÓN LIBRO-CATEGORIA:
    @PostMapping("/libros/{idLibro}/categorias/{idCategoria}")
    public ResponseEntity<RespuestaDTO> agregarCategoriaALibro(@PathVariable Long idLibro, @PathVariable Long idCategoria) {
        RespuestaDTO respuesta = libroService.agregarCategoriaALibro(idLibro, idCategoria);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //ELIMINAR CATEGORIA DE LIBRO:
    @DeleteMapping("/libros/{idLibro}/categorias/{idCategoria}")
    public ResponseEntity<RespuestaDTO> eliminarCategoriaDeLibro(@PathVariable Long idLibro, @PathVariable Long idCategoria) {
        RespuestaDTO respuesta = libroService.eliminarCategoriaDeLibro(idLibro, idCategoria);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(respuesta, httpStatus);
    }
    
    //REEMPLAZAR CATEGORIAS DE UN LIBRO:
    @PutMapping("/libros/{idLibro}/categorias")
    public ResponseEntity<RespuestaDTO> reemplazarCategoriasDeLibro(@PathVariable Long idLibro, @RequestBody List<Long> categoriasIds) {
        RespuestaDTO respuesta = libroService.reemplazarCategoriasDeLibro(idLibro, categoriasIds);
        HttpStatus httpStatus = respuesta.isBanderaexito() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(respuesta, httpStatus);
    }

    //ENDPOINT SUGERENCIAS DE LIBROS (CRITERIO 1 - SEARCH-AS-YOU-TYPE / FULL-TEXT CON OPENSEARCH):
    //Usa MultiMatchQuery con tipo bool_prefix sobre el campo tituloLibro (search_as_you_type)
    //y sus subcampos _2gram y _3gram generados automáticamente por OpenSearch.
    //Ejemplo: GET /libros/sugerencias?q=don  →  sugiere libros con títulos que empiezan con "don"
    @GetMapping("/libros/sugerencias")
    public ResponseEntity<List<LibroDTO>> sugerenciasLibros(
            @RequestParam(defaultValue = "") String q) {
        List<LibroDTO> sugerencias = libroService.sugerenciasLibros(q);
        return new ResponseEntity<>(sugerencias, HttpStatus.OK);
    }
    
    //ENDPOINT FACETS DE LIBROS (CRITERIO 2 - FACETED SEARCH CON OPENSEARCH AGGREGATIONS):
    //Ejecuta TermsAggregation sobre formatoLibro, estadoLibro y
    //NestedAggregation + TermsAggregation sobre categorias.nombreCategoria.
    //Retorna los valores únicos de cada campo con el conteo de documentos.
    //Ejemplo: GET /libros/facets  →  { campo: "por_formato", buckets: [{valor:"DIGITAL",cantidad:5},...] }
    @GetMapping("/libros/facets")
    public ResponseEntity<List<FacetDTO>> facetsLibros() {
        List<FacetDTO> facets = libroService.facetsLibros();
        return new ResponseEntity<>(facets, HttpStatus.OK);
    }
}
