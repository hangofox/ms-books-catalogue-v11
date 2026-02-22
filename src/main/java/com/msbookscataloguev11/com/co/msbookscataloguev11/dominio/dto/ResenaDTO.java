//IMPORTACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto;

//IMPORTACIÓN DE LIBRERIAS:
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
* @Autor LAURA VANESSA NARANJO RODRIGUEZ.
* @Since 28/01/2026.
* Declaración del método DTO.
*/

@Data
public class ResenaDTO {
    
    @Schema(description = "ID de la reseña del libro.", nullable = true)
    private Long idResena;
    @Schema(description = "Texto de la reseña de un libro", example = "Excelente libro")
    private String textoResena;
    @Schema(description = "Estado de la resena del libro", example = "ACTIVO/INACTIVO")
    private String estadoResena;
    @Schema(description = "Calificación del 1 al 5 de un libro", example = "5")
    private Integer calificacionLibro;
    @Schema(description = "Codigo del usuario", example = "1")
    private int idUsuario;
    @Schema(description = "Codigo del libro", example = "1")
    private Long idLibro;
}
