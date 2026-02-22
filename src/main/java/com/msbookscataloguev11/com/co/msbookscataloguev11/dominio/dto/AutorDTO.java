//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto;

//IMPORTACIÓN DE LIBRERIAS:
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
* @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 28/01/2026.
* Declaración del método DTO.
*/
@Data//DECLARACIÓN DE LA DATA PARA LOS DATOS DE LA TABLA DE LA BASE DE DATOS PARA LOS DTO.
//Anotacion de lombok que me crea automaticamente los get, set constructor.
public class AutorDTO {
    
    //DECLARACIÓN DE LAS VARIABLES DE LOS CAMPOS DE LA TABLA DE LA BASE DE DATOS PARA LOS DTO:
    @Schema(description = "ID del autor.", nullable = true)
    private Long idAutor;
    @Schema(description = "Nombres del autor.", example = "EJEMPLO: GABRIEL JOSE")
    private String nombresAutor;
    @Schema(description = "Primer apellido.", example = "EJEMPLO: GARCIA")
    private String primerApellidoAutor;
    @Schema(description = "Segundo apellido.", example = "EJEMPLO: MARQUEZ")
    private String segundoApellidoAutor;
}