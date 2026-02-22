//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.LibroxCategoria;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;
import java.util.Optional;

/**
* @Autor DAVID GIOVANNI PAEZ OVALLE.
* @Since 21/02/2026.
* Repositorio Elasticsearch para LibroxCategorias.
*/
public interface LibroxCategoriaRepository extends ElasticsearchRepository<LibroxCategoria, String> {
    
    //Búsqueda por ID numérico de la relación (no el ID de ES).
    Optional<LibroxCategoria> findByIdLibroxCategoria(Long idLibroxCategoria);
    
    //Búsqueda de todas las relaciones de un libro.
    List<LibroxCategoria> findByIdLibro(Long idLibro);
    
    //Búsqueda de todas las relaciones de una categoría.
    List<LibroxCategoria> findByIdCategoria(Long idCategoria);
    
    //Búsqueda para validar duplicados (un libro no puede estar dos veces en la misma categoría).
    Optional<LibroxCategoria> findByIdLibroAndIdCategoria(Long idLibro, Long idCategoria);
    
    //NOTA: Las consultas complejas con ordenamiento se implementan en el Service.
    //usando ElasticsearchOperations con sorting dinámico.
}
