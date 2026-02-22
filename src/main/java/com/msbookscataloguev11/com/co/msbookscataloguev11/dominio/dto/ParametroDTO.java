//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto;

//IMPORTACIÓN DE LIBRERIAS:
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
* @Autor MARIA MAGDALENA PARRAGA CASTRO.
* @Since 28/01/2026.
* Declaración del método DTO.
*/

@Data
public class ParametroDTO {
    @Schema(description = "ID del parametro.", nullable = true)
    private Long idParametro;
    @Schema(description = "Url base del libro.", example = "EJEMPLO: /archivos-relatos-papel/fotos-libros")
    private String urlBaseLibro;
}
