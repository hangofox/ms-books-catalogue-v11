//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity;

//IMPORTACIÓN DE LIBRERIAS:
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

/**
* @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 28/01/2026.
* Declaración del documento Elasticsearch para Autores.
* @Migracion a Elasticsearch: 19/02/2026.
*/
@Data//DECLARACIÓN DE LA DATA PARA LOS DATOS DE LA TABLA (INDICE) DE LA BASE DE DATOS PARA LA ENTIDAD.
@Document(indexName = "i_autores", createIndex = false)
public class Autor {
    
    //ID del documento en Elasticsearch (auto-generado).
    @Id
    private String id;

    //ID numérico del autor (para compatibilidad con APIs externas).
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
    
    /*//DECLARACIÓN DE LOS MÉTODOS SETTERS Y GETTERS DE LAS VARIABLES DECLARADAS DE LOS CAMPOS DE LA TABLA DE LA BASE DE DATOS DE LA ENTIDAD:
    public Long getIdAutor() {
        return idAutor;
    }
    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }
    public String getNombresAutor() {
        return nombresAutor;
    }
    public void setNombresAutor(String nombresAutor) {
        this.nombresAutor = nombresAutor;
    }
    public String getPrimerApellidoAutor() {
        return primerApellidoAutor;
    }
    public void setPrimerApellidoAutor(String primerApellidoAutor) {
        this.primerApellidoAutor = primerApellidoAutor;
    }
    public String getSegundoApellidoAutor() {
        return segundoApellidoAutor;
    }
    public void setSegundoApellidoAutor(String segundoApellidoAutor) {
        this.segundoApellidoAutor = segundoApellidoAutor;
    }*/
}
