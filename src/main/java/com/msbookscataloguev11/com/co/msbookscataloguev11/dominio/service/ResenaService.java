//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.ResenaDTO;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @Autor LAURA VANESSA NARANJO RODRIGUEZ.
* @Since 21/02/2026.
* Declaración de los métodos de respuesta en la interface para los cruds (creación, lectura (listar y consultar),
* edición y eliminación de un registro).
*/
@Service
public interface ResenaService {
    //DECLARACIÓN DE LOS METODOS DE RESPUESTA EN LA INTERFACE PARA LOS CRUDS QUE SON LOS METODOS PARA LA
    //CREACIÓN, LECTURA (LISTAR Y CONSULTAR), EDICIÓN Y ELIMINACIÓN DE UN REGISTRO:
    List<ResenaDTO> listarResenas(Long idLibro);
    void crearResena(ResenaDTO resenaDTO);
    ResenaDTO consultarResenaporId(Long idResena);
    void actualizarResena(Long idResena,ResenaDTO resenaDTO);
    void eliminarResena(Long idResena);
    Double consultarCalificacion(Long idLibro);
}

