//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Libro;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;
import java.util.Optional;

/**
* @Autor PD04. HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 02/02/2026.
* Repositorio Elasticsearch para Libros.
* @Migracion a Elasticsearch: 19/02/2026
*
* NOTA: Este repositorio ahora extiende ElasticsearchRepository en lugar de JpaRepository.
* Los métodos de consulta complejos con @Query nativos de SQL han sido eliminados.
* Las búsquedas complejas se implementan en el Service usando ElasticsearchOperations.
*/
public interface LibroRepository extends ElasticsearchRepository<Libro, String> {
    
    //Búsqueda por estado del libro.
    List<Libro> findByEstadoLibro(String estado);
    
    //Búsqueda por ID numérico del libro (no el ID de ES).
    Optional<Libro> findByIdLibro(Long idLibro);
    
    //NOTA: Los métodos de consulta compleja con @Query SQL nativos han sido eliminados.
    //Las búsquedas avanzadas (filtrado por múltiples campos, ordenamiento, búsqueda full-text,
    //search-as-you-type, facets) se implementan ahora en LibroServiceImpl usando ElasticsearchOperations.
    //con queries nativas de Elasticsearch (BoolQuery, MultiMatchQuery, NestedQuery, etc.).
}
