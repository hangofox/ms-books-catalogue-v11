//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto;

//IMPORTACIÓN DE LIBRERIAS:
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
* @Autor PD04. HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 02/02/2026.
* Declaración de la entidad.
* @Actualizacion David Paez 07/02/2026.
*/
@Data//DECLARACIÓN DE LA DATA PARA LOS DATOS DE LA TABLA DE LA BASE DE DATOS PARA LOS DTO.
//Anotacion de lombok que me crea automaticamente los get, set constructor.
public class LibroDTO {
    
    //DECLARACIÓN DE LAS VARIABLES DE LOS CAMPOS DE LA TABLA DE LA BASE DE DATOS PARA LOS DTO:
    @Schema(description = "ID del libro.", nullable = true)
    private Long idLibro;
    //private Long idAutor;
    @Schema(description = "Titulo del libro.", example = "EJEMPLO: CIEN AÑOS DE SOLEDAD")
    private String tituloLibro;
    @Schema(description = "Fecha de publicación del libro.", example = "EJEMPLO: 2026-02-05")
    private String fechaPublicacionLibro;
    @Schema(description = "Relato del resumen del libro.", example = "EJEMPLO: Trata de la historia de una pareja...")
    private String sinopsisLibro;
    @Schema(description = "Código ISBN del libro.", example = "EJEMPLO: 978-92-95055-02-5")
    private String codigoIsbnLibro;
    @Schema(description = "Precio del libro.", example = "EJEMPLO: 100,0")
    private double precioLibro;
    @Schema(description = "Formato del libro.", example = "EJEMPLO: FISICO/DIGITAL")
    private String formatoLibro;
    @Schema(description = "Nombre de archivo de imagen del libro.", example = "EJEMPLO: 1AWHKMN.jpg")
    private String nombreArchivoImagenLibro;
    @Schema(description = "Estado del libro.", example = "EJEMPLO: ACTIVO/INACTIVO/VISIBLE")
    private String estadoLibro;
    
    private AutorDTO autorDTO;
    
    //IDS PARA ASIGNAR CATEGORIAS (REQUEST).
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Long> categoriasIds;
    
    //CATEGORIAS COMPLETAS (RESPONSE).
    private List<CategoriaDTO> categorias;
    
    public AutorDTO getAutorDTO() {
        return autorDTO;
    }
    public void setAutorDTO(AutorDTO autorDTO) {
        this.autorDTO = autorDTO;
    }
}