//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto;

//IMPORTACIÓN DE LIBRERIAS:
import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
* @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 28/01/2026.
* Declaración del método DTO.
*/

//DECLARACIÓN DE LA DATA PARA LOS DATOS DE LA TABLA DE LA BASE DE DATOS PARA LOS RESPONSE DE LOS DTO.
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RespuestaDTO {
    
    //DECLARACIÓN DE LAS VARIABLES DE RESPUESTA DEL DTO:
    private AutorDTO autorDTO;
    private CategoriaDTO categoriaDTO;
    private LibroDTO libroDTO;
    private LibroxCategoriaDTO libroxCategoriaDTO;
    private ParametroDTO parametroDTO;
    private List<AutorDTO> autoresDTO;
    private List<CategoriaDTO> categoriasDTO;
    private List<LibroDTO> librosDTO;
    private List<LibroxCategoriaDTO> librosxCategoriasDTO;
    private List<ParametroDTO> parametrosDTO;
    private String mensaje;
    private boolean banderaexito;
    
    //DECLARACIÓN DE LOS MÉTODOS SETTERS Y GETTERS DE LAS VARIABLES DE RESPUESTA DECLARADAS DEL DTO Y LOS MENSAJES GENERADOS POR LOS CRUDS
    //QUE SON LOS METODOS PARA LA CREACIÓN, LECTURA (LISTAR Y CONSULTAR), EDICIÓN Y ELIMINACIÓN DE UN REGISTRO:
    public AutorDTO getAutorDTO() {
        return autorDTO;
    }
    public void setAutorDTO(AutorDTO autorDTO) {
        this.autorDTO = autorDTO;
    }
    public CategoriaDTO getCategoriaDTO() {
        return categoriaDTO;
    }
    public void setCategoriaDTO(CategoriaDTO categoriaDTO) {
        this.categoriaDTO = categoriaDTO;
    }
    public LibroDTO getLibroDTO() {
        return libroDTO;
    }
    public void setLibroDTO(LibroDTO libroDTO) {
        this.libroDTO = libroDTO;
    }
    public LibroxCategoriaDTO getLibroxCategoriaDTO() {
        return libroxCategoriaDTO;
    }
    public void setLibroxCategoriaDTO(LibroxCategoriaDTO libroxCategoriaDTO) {
        this.libroxCategoriaDTO = libroxCategoriaDTO;
    }
    public ParametroDTO getParametroDTO() {
        return parametroDTO;
    }
    public void setParametroDTO(ParametroDTO parametroDTO) {
        this.parametroDTO = parametroDTO;
    }
    public List<AutorDTO> getAutoresDTO() {
        return autoresDTO;
    }
    public void setAutoresDTO(List<AutorDTO> autoresDTO) {this.autoresDTO = autoresDTO;}
    public List<CategoriaDTO> getCategoriasDTO() {
        return categoriasDTO;
    }
    public void setCategoriasDTO(List<CategoriaDTO> categoriasDTO) {
        this.categoriasDTO = categoriasDTO;
    }
    public List<LibroDTO> getLibrosDTO() {
        return librosDTO;
    }
    public void setLibrosDTO(List<LibroDTO> librosDTO) {
        this.librosDTO = librosDTO;
    }
    public List<LibroxCategoriaDTO> getLibrosxCategoriasDTO() {
        return librosxCategoriasDTO;
    }
    public void setLibrosxCategoriasDTO(List<LibroxCategoriaDTO> librosxCategoriasDTO) {
        this.librosxCategoriasDTO = librosxCategoriasDTO;
    }
    public List<ParametroDTO> getParametrosDTO() {
        return parametrosDTO;
    }
    public void setParametrosDTO(List<ParametroDTO> parametrosDTO) {
        this.parametrosDTO = parametrosDTO;
    }
    public RespuestaDTO() {
    }
    public RespuestaDTO(String mensaje, boolean banderaexito) {
        this.mensaje = mensaje;
        this.banderaexito = banderaexito;
    }
}
