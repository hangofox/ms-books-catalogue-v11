//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.LibroDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.RespuestaDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import java.util.List;

/**
* @Autor PD04. HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 02/02/2026.
* Declaración de la entidad.
* @Actualizacion David Paez 06/02/2026.
* Declaración de los métodos de respuesta en la interface para los cruds (creación, lectura (listar y consultar),
* edición y eliminación de un registro).
*/
//DECLARACIÓN DE LA INTERFACE DE LA CLASE PRINCIPAL DEL SERVICIO:
public interface LibroService {
    //DECLARACIÓN DE LOS METODOS DE RESPUESTA EN LA INTERFACE PARA LOS CRUDS QUE SON LOS METODOS PARA LA
    //CREACIÓN, LECTURA (LISTAR Y CONSULTAR), EDICIÓN Y ELIMINACIÓN DE UN REGISTRO:
    //MÉTODO PARA LISTAR/FILTRAR/ORDENAR CATEGORIAS SIN PAGINACIÓN (PARA SELECTS):
    List<LibroDTO> listaTodo();
    //MÉTODO ÚNICO PARA LISTAR/FILTRAR/ORDENAR LIBROS SIN PAGINACIÓN (PARA SELECTS):
    List<LibroDTO> listarLibrosNoPaginacion(
            String keyword,
            String titulo,
            String sinopsisLibro,
            String codigoIsbnLibro,
            String formatoLibro,
            String estadoLibro,
            String fechaPublicacionLibro,
            Long idCategoria,
            String nombreCategoria,
            Long idAutor,
            String nombresAutor,
            String primerApellidoAutor,
            String segundoApellidoAutor,
            Double minPrecio,
            Double maxPrecio,
            String orderBy,
            String orderMode
    );
    //MÉTODO ÚNICO PARA LISTAR/FILTRAR/ORDENAR/PAGINAR LIBROS:
    Slice<LibroDTO> listarLibros(
            String keyword,
            String titulo,
            String sinopsisLibro,
            String codigoIsbnLibro,
            String formatoLibro,
            String estadoLibro,
            String fechaPublicacionLibro,
            Long idCategoria,
            String nombreCategoria,
            Long idAutor,
            String nombresAutor,
            String primerApellidoAutor,
            String segundoApellidoAutor,
            Double minPrecio,
            Double maxPrecio,
            String orderBy,
            String orderMode,
            Pageable pageable
    );
    //MÉTODO PARA BUSCAR LIBROS POR CRITERIOS INDIVIDUALES Y COMBINADOS:
    Slice<LibroDTO> buscarLibrosPorCriterios(
            String tituloLibro,
            String fechaPublicacionLibro,
            String sinopsisLibro,
            String codigoIsbnLibro,
            String precioLibro,
            String formatoLibro,
            String estadoLibro,
            String orderBy,
            String orderMode,
            Pageable pageable
    );
    RespuestaDTO crearLibro(LibroDTO libroDTO);
    RespuestaDTO crearLibrosMasivo(List<LibroDTO> librosDTO);
    RespuestaDTO consultarLibroporId(Long idLibro);
    RespuestaDTO actualizarLibro(LibroDTO libroDTO);
    RespuestaDTO eliminarLibro(Long idLibro);
    //MÉTODO PARA ELIMINACIÓN MASIVA DE LIBROS Y SUS RELACIONES (TRUNCATE):
    RespuestaDTO eliminarTodosLosLibros();
    //GESTIÓN DE RELACIÓN LIBRO - CATEGORIA:
    RespuestaDTO agregarCategoriaALibro(Long idLibro, Long idCategoria);
    RespuestaDTO eliminarCategoriaDeLibro(Long idLibro, Long idCategoria);
    RespuestaDTO reemplazarCategoriasDeLibro(Long idLibro, List<Long> categoriasIds);
}
