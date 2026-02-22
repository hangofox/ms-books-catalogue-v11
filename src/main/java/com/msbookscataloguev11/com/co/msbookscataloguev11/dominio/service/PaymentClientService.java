//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service;

//IMPORTACIÓN DE LIBRERIAS:
import org.springframework.web.reactive.function.client.WebClient;

/**
* @Autor MARIA GABRIELA ZAPATA DIAZ.
* @Since 21/02/2026.
* Declaración de los métodos de respuesta en la interface para calculos de STOCK.
*/
//DECLARACIÓN DE LA INTERFACE DE LA CLASE PRINCIPAL DEL SERVICIO:
public class PaymentClientService {
    
    private final WebClient webClient;
    public PaymentClientService(WebClient.Builder builder) {
        this.webClient= builder.baseUrl("lb://payment-service").build();
    }
    
    public Integer cuantosFacturados(Long idLibro){
        return webClient.get()
                .uri("/productos/facturados/{id}",idLibro)
                .retrieve().bodyToMono(Integer.class).block();
    }
}
