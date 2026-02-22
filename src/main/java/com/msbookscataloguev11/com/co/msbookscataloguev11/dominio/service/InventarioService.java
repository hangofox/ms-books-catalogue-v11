//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.InventarioDTO;
import java.util.List;

/**
* @Autor MARIA GABRIELA ZAPATA DIAZ.
* @Since 28/01/2026.
* Declaración de los métodos de respuesta en la interface para los cruds (creación, lectura (listar y consultar),
* edición y eliminación de un registro).
*/
public interface InventarioService {
  
  //DECLARACIÓN DE LOS METODOS DE RESPUESTA EN LA INTERFACE PARA LOS CRUDS QUE SON LOS METODOS PARA LA
  //CREACIÓN, LECTURA (LISTAR Y CONSULTAR), EDICIÓN Y ELIMINACIÓN DE UN REGISTRO:
  //MÉTODO ÚNICO PARA LISTAR/FILTRAR/ORDENAR/PAGINAR CATEGORIAS:
  List<InventarioDTO> listarKardex(Long idLibro);
  void registrarEntrada(InventarioDTO inventarioDTO);
  void registrarSalida(InventarioDTO inventarioDTO);
  Integer consultarStock(Long idLibro);
}


