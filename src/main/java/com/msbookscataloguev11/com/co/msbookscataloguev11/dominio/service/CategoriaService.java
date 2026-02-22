//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.CategoriaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.RespuestaDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import java.util.List;

/**
* @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 28/01/2026.
* Declaración de los métodos de respuesta en la interface para los cruds (creación, lectura (listar y consultar),
* edición y eliminación de un registro).
*/
//DECLARACIÓN DE LA INTERFACE DE LA CLASE PRINCIPAL DEL SERVICIO:
public interface CategoriaService {
    //DECLARACIÓN DE LOS METODOS DE RESPUESTA EN LA INTERFACE PARA LOS CRUDS QUE SON LOS METODOS PARA LA
    //CREACIÓN, LECTURA (LISTAR Y CONSULTAR), EDICIÓN Y ELIMINACIÓN DE UN REGISTRO:
    //MÉTODO PARA LISTAR/FILTRAR/ORDENAR CATEGORIAS SIN PAGINACIÓN (PARA SELECTS):
    List<CategoriaDTO> listarCategoriasNoPaginacion(String keyword, String orderBy, String orderMode);
    //MÉTODO ÚNICO PARA LISTAR/FILTRAR/ORDENAR/PAGINAR CATEGORIAS:
    Slice<CategoriaDTO> listarCategorias(String keyword, String orderBy, String orderMode, Pageable pageable);
    RespuestaDTO crearCategoria(CategoriaDTO categoriaDTO);
    RespuestaDTO consultarCategoriaporId(Long idCategoria);
    RespuestaDTO consultarCategoriaporNombre(String nombreCategoria);
    RespuestaDTO actualizarCategoria(CategoriaDTO CategoriaDTO);
    RespuestaDTO eliminarCategoria(Long idCategoria);
}
