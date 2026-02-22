//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.config;

//IMPORTACIÓN DE LIBRERIAS:
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.opensearch.client.RestClient;
import org.opensearch.data.client.osc.OpenSearchConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import java.net.URI;

/**
* @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 19/02/2026.
* Declaración del opensearch.
* Configuracion del cliente OpenSearch usando el nuevo Java client (opensearch-java / OSC).
* PROBLEMA RAIZ: OpenSearchConfiguration.opensearchRestClient() usaba puerto 9200 por defecto
* ignorando el puerto 443 del ClientConfiguration.
* SOLUCION: Override de opensearchRestClient() para crear RestClient con HttpHost explicito
* (scheme=https, port=443) y credenciales Basic Auth.
*/
@Configuration//DECLARACIÓN DE COMPONENT PARA INYECTAR LA CONFIGURACIÓN.
public class OpenSearchConfig extends OpenSearchConfiguration {
    
    //URL del servidor OpenSearch (Bonsai Cloud), inyectada desde application.properties.
    @Value("${opensearch.uris}")
    private String opensearchUri;
    
    //Usuario de autenticación Basic Auth, inyectado desde application.properties.
    @Value("${opensearch.username}")
    private String username;
    
    //Contraseña de autenticación Basic Auth, inyectada desde application.properties.
    @Value("${opensearch.password}")
    private String password;
    
    //CONFIGURACIÓN DEL CLIENTE: requerido por la clase abstracta, define host, SSL y credenciales.
    @Override
    public ClientConfiguration clientConfiguration() {
        //Parsear la URI para extraer host y puerto.
        URI uri = URI.create(opensearchUri);
        String host = uri.getHost();
        int port = uri.getPort() == -1 ? 443 : uri.getPort();
        return ClientConfiguration.builder()
                .connectedTo(host + ":" + port)
                .usingSsl()
                .withBasicAuth(username, password)
                .build();
    }
    
    //OVERRIDE DEL REST CLIENT: fuerza conexión HTTPS al puerto 443 con Basic Auth.
    //Sin este override, la implementación padre usaba puerto 9200 (HTTP por defecto).
    @Bean
    @Override
    public RestClient opensearchRestClient(ClientConfiguration clientConfiguration) {
        //Parsear la URI para extraer host, puerto y esquema (https).
        URI uri = URI.create(opensearchUri);
        String host = uri.getHost();
        int port = uri.getPort() == -1 ? 443 : uri.getPort();
        String scheme = uri.getScheme();
        
        //Configurar proveedor de credenciales Basic Auth.
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username, password));
                
        //Construir y retornar el RestClient con host, puerto, esquema y credenciales.
        return RestClient.builder(new HttpHost(host, port, scheme))
                .setHttpClientConfigCallback(builder ->
                        builder.setDefaultCredentialsProvider(credentialsProvider))
                .build();
    }
}
