//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity;

//IMPORTACIÓN DE LIBRERIAS:
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
* @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 19/02/2026.
* Clase para datos de Categoría embebidos en el documento Libro de Elasticsearch.
* Denormalización para optimizar búsquedas y facets.
*/
@Data//DECLARACIÓN DE LA DATA PARA LOS DATOS DE LA TABLA (INDICE) DE LA BASE DE DATOS PARA LA ENTIDAD.
public class CategoriaData {
    
    //ID numérico de la categoría (para compatibilidad con APIs externas).
    @Field(type = FieldType.Long)
    private Long idCategoria;
    
    //Nombre de la categoría como keyword para facets y búsqueda exacta.
    @Field(type = FieldType.Keyword)
    private String nombreCategoria;
}
