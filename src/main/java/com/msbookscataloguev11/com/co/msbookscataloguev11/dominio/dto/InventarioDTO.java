//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto;

//IMPORTACIÓN DE LIBRERIAS:
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

/**
* @Autor MARIA GABRIELA ZAPATA DIAZ.
* @Since 28/01/2026.
* Declaración del método DTO.
*/
@Data
public class InventarioDTO {
  @Schema(description = "ID del inventario, no es necesario colocar.", nullable = true)
  private Long idInventario;
  @Schema(description = "ID del libro.", example = "1")
  private Long idLibro;
  @Schema(description = "Nombre del tipo de movimiento.", example = "ENTRADA/SALIDA")
  private String tipoMovimiento;
  @Schema(description = "Fecha del inventario.", example = "2026-02-05")
  private String fechaInventario;
  @Schema(description = "Cantidad del movimiento.", example = "10")
  private Integer cantidadInventario;
}
