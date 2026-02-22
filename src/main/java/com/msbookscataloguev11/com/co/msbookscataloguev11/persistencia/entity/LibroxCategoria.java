//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity;

//IMPORTACIÓN DE LIBRERIAS:
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

/**
* @Autor DAVID GIOVANNI PAEZ OVALLE.
* @Since 21/02/2026.
* Declaración del documento Elasticsearch para LibroxCategorias.
* Representa la relación entre un Libro y una Categoría.
*/
@Data//DECLARACIÓN DE LA DATA PARA LOS DATOS DE LA TABLA (INDICE) DE LA BASE DE DATOS PARA LA ENTIDAD.
@Document(indexName = "i_librosxcategorias", createIndex = false)
public class LibroxCategoria {
    
    //ID del documento en Elasticsearch (auto-generado).
    @Id
    private String id;
    
    //ID numérico de la relación (para compatibilidad con APIs externas).
    @Field(type = FieldType.Long)
    private Long idLibroxCategoria;
    
    //ID numérico del libro relacionado.
    @Field(type = FieldType.Long)
    private Long idLibro;
    
    //ID numérico de la categoría relacionada.
    @Field(type = FieldType.Long)
    private Long idCategoria;
    
    /*//DECLARACIÓN DE LOS MÉTODOS SETTERS Y GETTERS DE LAS VARIABLES DECLARADAS DE LOS CAMPOS DE LA TABLA DE LA BASE DE DATOS DE LA ENTIDAD:
    public Long getIdLibroxCategoria() {
        return idLibroxCategoria;
    }
    public void setIdLibroxCategoria(Long idLibroxCategoria) {
        this.idLibroxCategoria = idLibroxCategoria;
    }
    public Long getIdLibro() {
        return idLibro;
    }
    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }
    public Long getIdCategoria() {
        return idCategoria;
    }
    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }*/
}
