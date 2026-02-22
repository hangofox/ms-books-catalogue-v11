//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Categoria;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.Optional;

/**
* @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 28/01/2026.
* Repositorio Elasticsearch para Categorías.
* @Migracion a Elasticsearch: 19/02/2026
*/
public interface CategoriaRepository extends ElasticsearchRepository<Categoria, String> {
    
    //Búsqueda por nombre exacto.
    Optional<Categoria> findByNombreCategoria(String nombreCategoria);
    
    //Búsqueda por ID numérico de la categoría (no el ID de ES).
    Optional<Categoria> findByIdCategoria(Long idCategoria);
    
    //NOTA: Las consultas complejas con ordenamiento se implementan en el Service.
    //usando ElasticsearchOperations con sorting dinámico.
}
