//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.dao;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.InventarioDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Inventario;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.utils.TipoMovimiento;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

/**
* @Autor MARIA GABRIELA ZAPADA DIAZ.
* @Since 28/01/2026.
* DAO para Inventario - Elasticsearch.
* @Migracion: Ya no usa relaciones JPA, solo almacena idLibro directamente.
*/

@Component//DECLARACIÓN DEL COMPONENTE PARA LOS METODOS DEL DAO.
public class InventarioDAO {
  
  //Este método es para guardar los datos. Se hace la conversión DTO → DOCUMENT.
  public Inventario inventario(InventarioDTO dto, TipoMovimiento tipoMovimiento) {
    
    Inventario inventario = new Inventario();
    inventario.setIdInventario(dto.getIdInventario());
    
    //CAMBIO IMPORTANTE: Ya no busca el Libro, solo almacena el ID.
    inventario.setIdLibro(dto.getIdLibro());
    
    inventario.setTipoMovimiento(tipoMovimiento);
    inventario.setCantidadInventario(dto.getCantidadInventario());
    inventario.setFechaInventario(LocalDateTime.now());
    
    return inventario;
  }
  
  //Este método es para presentar los datos. Se hace la conversión DOCUMENT → DTO.
  public InventarioDTO inventarioDTO(Inventario inventario) {
    
    InventarioDTO dto = new InventarioDTO();
    dto.setIdInventario(inventario.getIdInventario());
    
    //CAMBIO IMPORTANTE: Ahora idLibro es un campo directo, no navega relación.
    dto.setIdLibro(inventario.getIdLibro());
    
    dto.setTipoMovimiento(inventario.getTipoMovimiento().name());
    dto.setCantidadInventario(inventario.getCantidadInventario());
    dto.setFechaInventario(inventario.getFechaInventario().toString());
    
    return dto;
  }
}
