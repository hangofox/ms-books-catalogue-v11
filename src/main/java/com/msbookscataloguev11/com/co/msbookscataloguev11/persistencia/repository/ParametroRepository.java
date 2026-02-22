package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository;

import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Parametro;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import java.util.Optional;

/**
* @Autor MARIA MAGDALENA PARRAGA CASTRO.
* @Since 28/01/2026.
* Repositorio Elasticsearch para Parámetros.
 * @Migracion a Elasticsearch: 19/02/2026.
*/
public interface ParametroRepository extends ElasticsearchRepository<Parametro, String> {
    
    //Búsqueda por ID numérico del autor (no el ID de ES).
    Optional<Parametro> findByIdParametro(Long idParametro);
}
