//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.serviceImpl;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.InventarioDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service.InventarioService;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.dao.InventarioDAO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Inventario;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository.InventarioRepository;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.utils.TipoMovimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
* @Autor MARIA GABRIELA ZAPATA DIAZ.
* @Since 28/01/2026.
* Esta es la declaración de la implementación del servicio.
* @Migracion a Elasticsearch: 19/02/2026
*/
@Service
@Transactional
public class InventarioServiceImpl implements InventarioService {
  
  @Autowired//INYECTAMOS EL DAO.
  private InventarioDAO inventarioDAO;
  
  @Autowired//INYECTAMOS EL REPOSITORIO.
  private InventarioRepository inventarioRepository;
  
  //REGISTRAR MOVIMIENTO DE ENTRADA:
  @Override
  public void registrarEntrada(InventarioDTO inventarioDTO) {
    Inventario inventario = inventarioDAO.inventario(inventarioDTO, TipoMovimiento.ENTRADA);
    inventarioRepository.save(inventario);
  }
  
  //REGISTRAR MOVIMIENTO DE SALIDA:
  @Override
  public void registrarSalida(InventarioDTO inventarioDTO) {
    
    Integer stockActual = this.consultarStock(inventarioDTO.getIdLibro());
        
    if (stockActual < inventarioDTO.getCantidadInventario()) {
       throw new IllegalStateException("Stock insuficiente");
    }
    
    Inventario inventario = inventarioDAO.inventario(inventarioDTO, TipoMovimiento.SALIDA);
    inventarioRepository.save(inventario);
  }
  
  //CONSULTAR STOCK ACTUAL DE UN LIBRO:
  @Override
  public Integer consultarStock(Long idLibro) {
    //CAMBIO IMPORTANTE: El cálculo ya no se hace con @Query JPQL, se hace en Java
    List<Inventario> movimientos = inventarioRepository.findByIdLibroOrderByFechaInventarioDesc(idLibro);
    
    int stock = 0;
    for (Inventario inv : movimientos) {
      if (inv.getTipoMovimiento() == TipoMovimiento.ENTRADA) {
         stock += inv.getCantidadInventario();
      } else { // SALIDA
         stock -= inv.getCantidadInventario();
      }
    }
    
    return stock;
  }
  
  //LISTAR KARDEX (HISTORIAL DE MOVIMIENTOS) DE UN LIBRO:
  @Override
  public List<InventarioDTO> listarKardex(Long idLibro) {
    
    List<Inventario> inventarios = inventarioRepository.findByIdLibroOrderByFechaInventarioDesc(idLibro);
    
    return inventarios.stream().map(inventarioDAO::inventarioDTO).toList();
  }
}
