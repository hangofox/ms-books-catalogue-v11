//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.web.controller;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.InventarioDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
* @Autor MARIA GABRIELA ZAPATA DIAZ.
* @Since 28/01/2026.
* Declaración del controlador.
*/
@RestController//DECLARACIÓN DEL CONTROLADOR PARA LOS CRUDS.
@RequestMapping("/inventarios")
@Tag(
    name = "Kardex / Inventarios",
    description = "Gestión de entradas, salidas y kardex de libros"
)
public class InventarioController {

  @Autowired//INYECTAMOS EL SERVICIO.
  private InventarioService inventarioService;

  //CONTROLADORES DE CRUDS (CREACIÓN, LECTURA (LISTAR Y CONSULTAR), EDICIÓN Y ELIMINACIÓN DE UN REGISTRO).

  //LISTAR KARDEX (HISTORIAL DE MOVIMIENTOS) DE UN LIBRO:
  @Operation(
      summary = "Obtener kardex de un libro",
      description = "Devuelve todos los movimientos de inventario (entradas y salidas) de un libro"
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Kardex obtenido correctamente"),
      @ApiResponse(responseCode = "404", description = "Libro no encontrado")
  })
  @GetMapping("/kardex/{idLibro}")
  public ResponseEntity<List<InventarioDTO>> listarKardex(@PathVariable Long idLibro) {
    List<InventarioDTO> inventarios = inventarioService.listarKardex(idLibro);

    if(inventarios.isEmpty()){
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<>(inventarios, HttpStatus.OK);
  }

  //REGISTRAR MOVIMIENTO DE ENTRADA:
  @Operation(
      summary = "Registrar entrada de inventario",
      description = "Registra una entrada de stock para un libro"
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Entrada registrada correctamente")
  })
  @PostMapping("/entrada")
  public ResponseEntity<InventarioDTO> registrarEntrada(@RequestBody InventarioDTO inventarioDTO) {
    inventarioService.registrarEntrada(inventarioDTO);
    return ResponseEntity.ok(inventarioDTO);
  }

  //REGISTRAR MOVIMIENTO DE SALIDA:
  @Operation(
      summary = "Registrar salida de inventario",
      description = "Registra una salida de stock. Falla si no hay stock suficiente"
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Salida registrada correctamente"),
      @ApiResponse(responseCode = "409", description = "Stock insuficiente")
  })
  @PostMapping("/salida")
  public ResponseEntity<Void> registrarSalida(@RequestBody InventarioDTO inventarioDTO) {
    try {
      inventarioService.registrarSalida(inventarioDTO);
      return ResponseEntity.ok().build();
    } catch (IllegalStateException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  //CONSULTAR STOCK ACTUAL DE UN LIBRO:
  @GetMapping("/stock/{idLibro}")
  public Integer consultarStock(@PathVariable Long idLibro) {
    return inventarioService.consultarStock(idLibro);
  }

}
