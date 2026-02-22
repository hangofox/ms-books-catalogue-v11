//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity;

//IMPORTACIÓN DE LIBRERIAS:
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import java.util.ArrayList;
import java.util.List;

/**
* @Autor PD04. HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 28/01/2026.
* Declaración del documento Elasticsearch para Libros.
* @Actualizacion David Paez 04/02/2026.
* @Migracion a Elasticsearch: 19/02/2026.
*
* Este documento usa tipos de campo de Elasticsearch optimizados para búsqueda:
* - search_as_you_type: Para autocompletado y búsqueda mientras se escribe.
* - text: Para búsqueda de texto completo (full-text search).
* - keyword: Para filtros exactos y facets.
* - nested: Para objetos embebidos con búsqueda independiente.
*/
@Data
@Document(indexName = "i_libros", createIndex = false)
public class Libro {
    
    //ID del documento en Elasticsearch (auto-generado).
    @Id
    private String id;
    
    //ID numérico del libro (para compatibilidad con APIs externas).
    @Field(type = FieldType.Long)
    private Long idLibro;
    
    //Título con search_as_you_type para autocompletado.
    @MultiField(
        mainField = @Field(type = FieldType.Search_As_You_Type),
        otherFields = {
            @InnerField(suffix = "keyword", type = FieldType.Keyword)
        }
    )
    private String tituloLibro;
    
    //Datos del autor embebidos (denormalización para búsqueda eficiente).
    @Field(type = FieldType.Object)
    private AutorData autor;
    
    //Fecha de publicación (keyword para filtros exactos).
    @Field(type = FieldType.Keyword)
    private String fechaPublicacionLibro;
    
    //Sinopsis con búsqueda de texto completo.
    @Field(type = FieldType.Text)
    private String sinopsisLibro;

    //ISBN como keyword para búsqueda exacta.
    @Field(type = FieldType.Keyword)
    private String codigoIsbnLibro;
    
    //Precio como double para rangos.
    @Field(type = FieldType.Double)
    private double precioLibro;
    
    //Formato como keyword para facets (ej: DIGITAL, IMPRESO).
    @Field(type = FieldType.Keyword)
    private String formatoLibro;
    
    //Nombre del archivo de imagen.
    @Field(type = FieldType.Keyword)
    private String nombreArchivoImagenLibro;
    
    //Estado como keyword para facets (ej: VISIBLE, OCULTO).
    @Field(type = FieldType.Keyword)
    private String estadoLibro;
    
    //Categorías embebidas como nested para búsqueda independiente y facets
    @Field(type = FieldType.Nested)
    private List<CategoriaData> categorias = new ArrayList<>();
    
    /*//DECLARACIÓN DE LOS MÉTODOS SETTERS Y GETTERS DE LAS VARIABLES DECLARADAS DE LOS CAMPOS DE LA TABLA DE LA BASE DE DATOS DE LA ENTIDAD:
    public Long getIdLibro() {
        return idLibro;
    }
    public void setIdLibro(Long idLibro) {
        this.idLibro = idLibro;
    }
    public String getTituloLibro() {
        return tituloLibro;
    }
    public void setTituloLibro(String tituloLibro) {
        this.tituloLibro = tituloLibro;
    }
    public Autor getAutor() {
        return autor;
    }
    public void setAutor(Autor autor) {
        this.autor = autor;
    }
    public int getAnioPublicacionLibro() {
        return anioPublicacionLibro;
    }
    public void setAnioPublicacionLibro(int anioPublicacionLibro) {
        this.anioPublicacionLibro = anioPublicacionLibro;
    }
    public String getSinopsisLibro() {
        return sinopsisLibro;
    }
    public void setSinopsisLibro(String sinopsisLibro) {
        this.sinopsisLibro = sinopsisLibro;
    }
    public String getCodigoIsbnLibro() {
        return codigoIsbnLibro;
    }
    public void setCodigoIsbnLibro(String codigoIsbnLibro) {
        this.codigoIsbnLibro = codigoIsbnLibro;
    }
    public double getPrecioLibro() {
        return precioLibro;
    }
    public void setPrecioLibro(double precioLibro) {
        this.precioLibro = precioLibro;
    }
    public String getFormatoLibro() {
        return formatoLibro;
    }
    public void setFormatoLibro(String formatoLibro) {
        this.formatoLibro = formatoLibro;
    }
    public String getNombreArchivoImagenLibro() {
        return nombreArchivoImagenLibro;
    }
    public void setNombreArchivoImagenLibro(String nombreArchivoImagenLibro) {
        this.nombreArchivoImagenLibro = nombreArchivoImagenLibro;
    }
    public String getEstadoLibro() {
        return estadoLibro;
    }
    public void setEstadoLibro(String estadoLibro) {
        this.estadoLibro = estadoLibro;
    }*/
}
