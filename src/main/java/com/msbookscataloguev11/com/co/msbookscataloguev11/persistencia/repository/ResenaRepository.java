//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Resena;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.List;
import java.util.Optional;

/**
* @Autor LAURA VANESSA NARANJO RODRIGUEZ.
* @Since 28/01/2026.
* Repositorio Elasticsearch para Reseñas.
* @Migracion a Elasticsearch: 19/02/2026.
*/
public interface ResenaRepository extends ElasticsearchRepository<Resena, String> {
    
    //Listar reseñas por libro (ya no usa libro.idLibro, ahora usa idLibro directo).
    List<Resena> findByIdLibro(Long idLibro);
    
    //Búsqueda por ID numérico de la reseña.
    Optional<Resena> findByIdResena(Long idResena);
    
    //NOTA: El cálculo de promedio se hace ahora en el Service en Java.
    //ya que no hay queries JPQL en Elasticsearch.
}
