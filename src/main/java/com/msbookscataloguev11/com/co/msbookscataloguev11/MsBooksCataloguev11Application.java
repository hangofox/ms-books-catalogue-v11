//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11;

//IMPORTACIÓN DE LIBRERIAS:
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

//DECLARACIÓN DE LA CLASE PRINCIPAL:
@SpringBootApplication
@EnableDiscoveryClient
@EnableElasticsearchRepositories(basePackages = "com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository")
public class MsBooksCataloguev11Application extends SpringBootServletInitializer {
	/**
	* Método requerido para permitir que WebLogic gestione el despliegue del WAR.
	* Sobrescribimos el método configure para registrar esta clase como punto de entrada.
	*/
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MsBooksCataloguev11Application.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(MsBooksCataloguev11Application.class, args);
	}
}
/*//DECLARACIÓN DE LA CLASE PRINCIPAL:
@SpringBootApplication
public class msbookscataloguev11Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(msbookscataloguev11Application.class, args);
	}
}*/
