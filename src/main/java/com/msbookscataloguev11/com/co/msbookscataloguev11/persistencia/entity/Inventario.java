//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.utils.TipoMovimiento;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import java.time.LocalDateTime;

/**
* @Autor MARIA GABRIELA ZAPATA DIAZ.
* @Since 28/01/2026.
* Declaración del documento Elasticsearch para Inventarios (kardex de stock).
* @Migracion a Elasticsearch: 19/02/2026.
*/
@Data//DECLARACIÓN DE LA DATA PARA LOS DATOS DE LA TABLA (INDICE) DE LA BASE DE DATOS PARA LA ENTIDAD.
@Document(indexName = "i_inventarios", createIndex = false)
public class Inventario {
    
    //ID del documento en Elasticsearch (auto-generado).
    @Id
    private String id;

    //ID numérico del inventario (para compatibilidad con APIs externas).
    @Field(type = FieldType.Long)
    private Long idInventario;
    
    //Referencia al libro por ID (sin relación JPA).
    @Field(type = FieldType.Long)
    private Long idLibro;
    
    //Tipo de movimiento del inventario (ENTRADA o SALIDA).
    @Field(type = FieldType.Keyword)
    private TipoMovimiento tipoMovimiento;
    
    //Fecha y hora del movimiento de inventario.
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    private LocalDateTime fechaInventario;
    
    //Cantidad de unidades del movimiento de inventario.
    @Field(type = FieldType.Integer)
    private Integer cantidadInventario;
}
