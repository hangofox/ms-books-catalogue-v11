//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.utils;

//IMPORTACIÓN DE LIBRERIAS:
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.StreamSupport;

/**
* @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 28/01/2026.
* Utilidad para generar IDs numéricos secuenciales en Elasticsearch.
* Elasticsearch usa String como _id, pero necesitamos IDs numéricos para compatibilidad.
*/
public class IdGenerator {
    
    /**
    * Genera el siguiente ID numérico basado en los registros existentes.
    *
    * @param all Todos los registros existentes.
    * @param idExtractor Función para extraer el Long id de cada entidad.
    * @param <T> Tipo de entidad
    * @return El siguiente ID disponible
    */
    public static <T> Long generateNextId(Iterable<T> all, Function<T, Long> idExtractor) {
        return StreamSupport.stream(all.spliterator(), false)
            .map(idExtractor)
            .filter(Objects::nonNull)
            .mapToLong(Long::longValue)
            .max()
            .orElse(0L) + 1;
    }
}
