//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.web.controller;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.ResenaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service.ResenaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
* @Autor LAURA VANESSA NARANJO RODRIGUEZ.
* @Since 28/01/2026.
* Declaración del controlador.
*/
@RestController//DECLARACIÓN DEL CONTROLADOR PARA LOS CRUDS.
@RequestMapping("/resenas")
public class ResenaController {
    
    @Autowired//INYECTAMOS EL SERVICIO.
    private ResenaService resenaService;
    
    //CONTROLADORES DE CRUDS (CREACIÓN, LECTURA (LISTAR Y CONSULTAR), EDICIÓN Y ELIMINACIÓN DE UN REGISTRO).
    
    //LISTAR RESEÑAS POR LIBRO:
    @Operation(
            summary = "Obtener las reseñas de un libro",
            description = "Devuelve todas las reseñas ingresadas por los usuarios en un libro"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reseñas obtenidas correctamente"),
            @ApiResponse(responseCode = "404", description = "Reseñas no encontradas")
    })
    @GetMapping("/libro/{idLibro}")
    public ResponseEntity<List<ResenaDTO>> listarResenas(@PathVariable Long idLibro) {
        List<ResenaDTO> resenas = resenaService.listarResenas(idLibro);
        if (resenas.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(resenas, HttpStatus.OK);
    }
    
    //CONSULTAR CALIFICACIÓN PROMEDIO DE UN LIBRO:
    @GetMapping("/calificacion/{idLibro}")
    public Double consultarCalificacion(@PathVariable Long idLibro) {
        return resenaService.consultarCalificacion(idLibro);
    }
    
    //CREAR REGISTRO:
    @Operation(
            summary = "Crea una reseña",
            description = "Crea la reseña con el cuerpo JSON enviado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reseña creada"),
            @ApiResponse(responseCode = "400", description = "Reseña con calificación errada, solo recibe del 1 al 5")
    })
    @PostMapping
    public ResponseEntity<ResenaDTO> crearResena(@RequestBody ResenaDTO resenaDTO) {
        try {
            resenaService.crearResena(resenaDTO);
            return ResponseEntity.ok(resenaDTO);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    //LEER CONSULTA DE REGISTRO POR ID:
    @Operation(
            summary = "Consultar reseña por Id",
            description = "Consulta la reseña por el Id de la reseña"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reseña obtenida correctamente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron reseñas con el id ingresado")
    })
    @GetMapping("/{idResena}")
    public ResponseEntity<ResenaDTO> consultarResenaporId(@PathVariable Long idResena) {
        try {
            ResenaDTO resenaDTO = resenaService.consultarResenaporId(idResena);
            return ResponseEntity.ok(resenaDTO);
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    //MODIFICAR REGISTRO:
    @Operation(
            summary = "Actualizar reseña",
            description = "Actualizar reseña con Id y cuerpo de JSON enviado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reseña actualizada correctamente"),
    })
    @PutMapping("/{idResena}")
    public ResponseEntity<Void> actualizarResena(@PathVariable Long idResena, @RequestBody ResenaDTO resenaDTO) {
        try {
            resenaService.actualizarResena(idResena, resenaDTO);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    //ELIMINAR REGISTRO:
    @Operation(
            summary = "Eliminar reseña",
            description = "Elimina la reseña por el Id de la reseña"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reseña eliminada correctamente"),
            @ApiResponse(responseCode = "404", description = "La reseña no existe")
    })
    @DeleteMapping("/{idResena}")
    public ResponseEntity<Void> eliminarResena(@PathVariable Long idResena) {
        try {
            resenaService.eliminarResena(idResena);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
