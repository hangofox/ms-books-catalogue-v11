//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity;

//IMPORTACIÓN DE LIBRERIAS:
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

/**
* @Autor MARIA MAGDALENA PARRAGA CASTRO.
* @Since 28/01/2026.
* Declaración del documento Elasticsearch para Parámetros del sistema.
* @Migracion a Elasticsearch: 19/02/2026.
*/
@Data//DECLARACIÓN DE LA DATA PARA LOS DATOS DE LA TABLA (INDICE) DE LA BASE DE DATOS PARA LA ENTIDAD.
@Document(indexName = "i_parametros", createIndex = false)
public class Parametro {

    //ID del documento en Elasticsearch (auto-generado).
    @Id
    private String id;

    //ID numérico del parámetro (para compatibilidad con APIs externas).
    @Field(type = FieldType.Long)
    private Long idParametro;

    //URL base de los libros almacenada como keyword.
    @Field(type = FieldType.Keyword)
    private String urlBaseLibro;
}
