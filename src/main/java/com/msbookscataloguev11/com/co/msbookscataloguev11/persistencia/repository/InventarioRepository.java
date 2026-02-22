//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Inventario;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;
import java.util.Optional;

/**
* @Autor MARIA GABRIELA ZAPATA DIAZ.
* @Since 28/01/2026.
* Repositorio Elasticsearch para Inventarios.
* @Migracion a Elasticsearch: 19/02/2026.
*/
public interface InventarioRepository extends ElasticsearchRepository<Inventario, String> {
  
  //Kardex de un libro (ya no usa libro.idLibro, ahora usa idLibro directo).
  List<Inventario> findByIdLibroOrderByFechaInventarioDesc(Long idLibro);
  
  //Búsqueda por ID numérico del inventario.
  Optional<Inventario> findByIdInventario(Long idInventario);
  
  //NOTA: El cálculo de stock se hace ahora en el Service en Java.
  //ya que no hay queries JPQL en Elasticsearch.
}
