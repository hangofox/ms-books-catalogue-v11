//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity;

//IMPORTACIÓN DE LIBRERIAS:
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
* @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 19/02/2026.
* Clase para datos de Autor embebidos en el documento Libro de Elasticsearch.
* Denormalización para optimizar búsquedas.
*/
@Data//DECLARACIÓN DE LA DATA PARA LOS DATOS DE LA TABLA (INDICE) DE LA BASE DE DATOS PARA LA ENTIDAD.
public class AutorData {
    
    //ID del documento en Elasticsearch (auto-generado).
    @Field(type = FieldType.Long)
    private Long idAutor;
    
    //Nombres con search_as_you_type para autocompletado.
    @Field(type = FieldType.Search_As_You_Type)
    private String nombresAutor;
    
    //Primer apellido con search_as_you_type.
    @Field(type = FieldType.Search_As_You_Type)
    private String primerApellidoAutor;
    
    //Segundo apellido con search_as_you_type.
    @Field(type = FieldType.Search_As_You_Type)
    private String segundoApellidoAutor;
}
