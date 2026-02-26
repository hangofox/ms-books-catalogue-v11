//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.AutorDTO;
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
public interface AutorService {
    //DECLARACIÓN DE LOS METODOS DE RESPUESTA EN LA INTERFACE PARA LOS CRUDS QUE SON LOS METODOS PARA LA
    //CREACIÓN, LECTURA (LISTAR Y CONSULTAR), EDICIÓN Y ELIMINACIÓN DE UN REGISTRO:
    //MÉTODO ÚNICO PARA LISTAR/FILTRAR/ORDENAR/PAGINAR AUTORES:
    Slice<AutorDTO> listarAutores(String keyword, String orderBy, String orderMode, Pageable pageable);
    //MÉTODO PARA SUGERENCIAS DE AUTORES (SEARCH-AS-YOU-TYPE + FULL-TEXT CON OPENSEARCH):
    List<AutorDTO> sugerenciasAutores(String q);
    RespuestaDTO crearAutor(AutorDTO autorDTO);
    RespuestaDTO consultarAutorporId(Long idAutor);
    RespuestaDTO actualizarAutor(AutorDTO autorDTO);
    RespuestaDTO eliminarAutor(Long idAutor);
}
