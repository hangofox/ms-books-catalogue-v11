//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto;

//IMPORTACIÓN DE LIBRERIAS:
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
* @Autor DAVID PAEZ OVALLE.
* @Since 21/02/2026.
* Declaración del método DTO.
*/
@Data//DECLARACIÓN DE LA DATA PARA LOS DATOS DE LA TABLA DE LA BASE DE DATOS PARA LOS DTO.
//Anotacion de lombok que me crea automaticamente los get, set constructor.
public class LibroxCategoriaDTO {
    
    //DECLARACIÓN DE LAS VARIABLES DE LOS CAMPOS DE LA TABLA DE LA BASE DE DATOS PARA LOS DTO:
    @Schema(description = "ID de la relación libro-categoría.", nullable = true)
    private Long idLibroxCategoria;
    @Schema(description = "ID del libro.", example = "EJEMPLO: 1")
    private Long idLibro;
    @Schema(description = "ID de la categoría.", example = "EJEMPLO: 2")
    private Long idCategoria;
    @Schema(description = "Datos del libro relacionado.", nullable = true)
    private LibroDTO libroDTO;
    @Schema(description = "Datos de la categoría relacionada.", nullable = true)
    private CategoriaDTO categoriaDTO;
}
