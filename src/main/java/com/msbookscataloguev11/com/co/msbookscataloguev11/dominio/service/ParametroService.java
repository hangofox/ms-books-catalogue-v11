//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.ParametroDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.RespuestaDTO;

/**
* @Autor MARIA MAGDALENA PARRAGA CASTRO.
* @Since 28/01/2026.
* Declaración de los métodos de respuesta en la interface para los cruds (consulta y edición) de un registro.
*/
public interface ParametroService {
    //DECLARACIÓN DE LOS METODOS DE RESPUESTA EN LA INTERFACE PARA LOS CRUDS QUE SON LOS METODOS PARA LA
    //CONSULTA Y MODIFICACIÓN DE UN REGISTRO:
    RespuestaDTO consultarParametroporId(Long idParametro);
    RespuestaDTO actualizarParametro(ParametroDTO ParametroDTO);
}
