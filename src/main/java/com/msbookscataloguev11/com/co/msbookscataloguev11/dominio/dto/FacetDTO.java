//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto;

//IMPORTACIÓN DE LIBRERIAS:
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

/**
* @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 25/02/2026.
* DTO para representar un facet de Elasticsearch/OpenSearch.
* Contiene el nombre del campo agrupado y sus buckets (valor + conteo de documentos).
* Usado en el endpoint GET /libros/facets para faceted search.
*/
@Data
public class FacetDTO {

    //Nombre del campo de OpenSearch sobre el que se agrupó (ej: "por_formato", "por_estado").
    private String campo;

    //Lista de valores únicos encontrados con su conteo de documentos.
    private List<BucketDTO> buckets;

    public FacetDTO(String campo, List<BucketDTO> buckets) {
        this.campo = campo;
        this.buckets = buckets;
    }

    /**
    * Representa un valor único (bucket) dentro del facet, con su conteo de documentos.
    */
    @Data
    @AllArgsConstructor
    public static class BucketDTO {
        //Valor del campo (ej: "DIGITAL", "VISIBLE", "Novela").
        private String valor;
        //Número de documentos que tienen ese valor.
        private long cantidad;
    }
}
