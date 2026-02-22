//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Autor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.Optional;

/**
* @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 28/01/2026.
* Repositorio Elasticsearch para Autores.
* @Migracion a Elasticsearch: 19/02/2026.
*/
public interface AutorRepository extends ElasticsearchRepository<Autor, String> {
    
    //Búsqueda por ID numérico del autor (no el ID de ES).
    Optional<Autor> findByIdAutor(Long idAutor);
    
    //NOTA: Las consultas complejas con ordenamiento se implementan en el Service.
    //usando ElasticsearchOperations con sorting dinámico.
}
