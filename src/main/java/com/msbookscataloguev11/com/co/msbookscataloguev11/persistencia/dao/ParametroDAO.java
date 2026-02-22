//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.dao;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.ParametroDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Parametro;
import org.springframework.stereotype.Component;

/**
* @Autor MARIA PARRAGA.
* @Since 28/01/2026.
* Declaración del método DAO.
*/
@Component//DECLARACIÓN DEL COMPONENTE PARA LOS METODOS DEL DAO.
public class ParametroDAO {
    
    /**
    * @Autor MARIA MAGDALENA PARRAGA CASTRO.
    * @Since 28/01/2026.
    * @param parametroDTO
    * Recibe un DTO para crear un objeto autor.
    * @return autor
    */
    public Parametro parametro(ParametroDTO parametroDTO) {
        Parametro parametro = new Parametro();
        parametro.setIdParametro(parametroDTO.getIdParametro());
        parametro.setUrlBaseLibro(parametroDTO.getUrlBaseLibro());
        
        return parametro;
    }
    
    /**
    * @Autor MARIA MAGDALENA PARRAGA CASTRO.
    * @Since 28/01/2026.
    * @param parametro
    * Recibe un DTO para un objeto autor para crear un DTO.
    * @return autorDTO
    */
    public ParametroDTO parametroDTO(Parametro parametro){
        ParametroDTO parametroDTO = new ParametroDTO();
        parametroDTO.setIdParametro(parametro.getIdParametro());
        parametroDTO.setUrlBaseLibro(parametro.getUrlBaseLibro());
        
        return parametroDTO;
    }
}
