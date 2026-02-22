//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.LibroxCategoriaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.RespuestaDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import java.util.List;

/**
* @Autor DAVID GIOVANNI PAEZ OVALLE.
* @Since 21/02/2026.
* Declaración de los métodos de respuesta en la interface para los cruds (creación, lectura (listar y consultar),
* edición y eliminación de un registro).
*/
//DECLARACIÓN DE LA INTERFACE DE LA CLASE PRINCIPAL DEL SERVICIO:
public interface LibroxCategoriaService {
    //DECLARACIÓN DE LOS METODOS DE RESPUESTA EN LA INTERFACE PARA LOS CRUDS QUE SON LOS METODOS PARA LA
    //CREACIÓN, LECTURA (LISTAR Y CONSULTAR), EDICIÓN Y ELIMINACIÓN DE UN REGISTRO:
    //MÉTODO ÚNICO PARA LISTAR/FILTRAR/ORDENAR/PAGINAR LIBROS X CATEGORIAS:
    List<LibroxCategoriaDTO> listaTodo();
    Slice<LibroxCategoriaDTO> listarLibroxCategorias(String keyword, String orderBy, String orderMode, Pageable pageable);
    RespuestaDTO crearLibroxCategoria(LibroxCategoriaDTO libroxCategoriaDTO);
    RespuestaDTO consultarLibroxCategoriaById(Long idLibroxCategoria);
    RespuestaDTO consultarLibroxCategoriasByLibro(Long idLibro);
    RespuestaDTO consultarLibroxCategoriasByCategoria(Long idCategoria);
    RespuestaDTO actualizarLibroxCategoria(LibroxCategoriaDTO libroxCategoriaDTO);
    RespuestaDTO eliminarLibroxCategoria(Long idLibroxCategoria);
}
