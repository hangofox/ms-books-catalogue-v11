//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity;

//IMPORTACIÓN DE LIBRERIAS:
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

/**
* @Autor LAURA VANESSA NARANJO RODRIGUEZ.
* @Since 28/01/2026.
* Declaración del documento Elasticsearch para Reseñas de libros.
* @Migracion a Elasticsearch: 19/02/2026.
*/
@Data//DECLARACIÓN DE LA DATA PARA LOS DATOS DE LA TABLA (INDICE) DE LA BASE DE DATOS PARA LA ENTIDAD.
@Document(indexName = "i_resenas", createIndex = false)
public class Resena {
    
    //ID del documento en Elasticsearch (auto-generado).
    @Id
    private String id;
    
    //ID numérico de la reseña (para compatibilidad con APIs externas).
    @Field(type = FieldType.Long)
    private Long idResena;
    
    //Texto de la reseña del libro.
    @Field(type = FieldType.Text)
    private String textoResena;
    
    //Estado de la reseña (activo, inactivo, etc.).
    @Field(type = FieldType.Keyword)
    private String estadoResena;
    
    //Calificación del libro de 1 a 5.
    @Field(type = FieldType.Integer)
    private Integer calificacionLibro;
    
    //ID del usuario que registró la reseña.
    @Field(type = FieldType.Integer)
    private int idUsuario;
    
    //Referencia al libro por ID (sin relación JPA).
    @Field(type = FieldType.Long)
    private Long idLibro;
}
