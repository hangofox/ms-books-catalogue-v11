//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity;

//IMPORTACIÓN DE LIBRERIAS:
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

/**
* @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 28/01/2026.
* Declaración del documento Elasticsearch para Categorías.
* @Migracion a Elasticsearch: 19/02/2026
*/
@Data//DECLARACIÓN DE LA DATA PARA LOS DATOS DE LA TABLA (INDICE) DE LA BASE DE DATOS PARA LA ENTIDAD.
@Document(indexName = "i_categorias", createIndex = false)
public class Categoria {

    //ID del documento en Elasticsearch (auto-generado).
    @Id
    private String id;

    //ID numérico de la categoría (para compatibilidad con APIs externas).
    @Field(type = FieldType.Long)
    private Long idCategoria;

    //Nombre de la categoría como keyword para facets y búsqueda exacta.
    @MultiField(
        mainField = @Field(type = FieldType.Keyword),
        otherFields = {
            @InnerField(suffix = "text", type = FieldType.Text)
        }
    )
    private String nombreCategoria;
    
    /*//DECLARACIÓN DE LOS MÉTODOS SETTERS Y GETTERS DE LAS VARIABLES DECLARADAS DE LOS CAMPOS DE LA TABLA DE LA BASE DE DATOS DE LA ENTIDAD:
    public Long getIdCategoria() {
        return idCategoria;
    }
    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }
    public String getNombreCategoria() {
        return nombreCategoria;
    }
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }*/
}
