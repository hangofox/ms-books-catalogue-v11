//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.dao;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.ResenaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Resena;
import org.springframework.stereotype.Component;

/**
* @Autor LAURA VANESSA NARANJO RODRIGUEZ.
* @Since 28/01/2026.
* DAO para Reseña - Elasticsearch.
* @Migracion: Ya no usa relaciones JPA, solo almacena idLibro directamente.
*/
@Component//DECLARACIÓN DEL COMPONENTE PARA LOS METODOS DEL DAO.
public class ResenaDAO {
    
    /**
    * @Autor LAURA VANESSA NARANJO RODRIGUEZ.
    * @Since 28/01/2026.
    * @param resenaDTO
    * Recibe un DTO para crear un objeto autor.
    * @return autor
    */
    //Este metodo es para guardar los datos - conversión DTO → DOCUMENT.
    public Resena resena(ResenaDTO resenaDTO) {
        Resena resena = new Resena();
        resena.setIdResena(resenaDTO.getIdResena());
        resena.setTextoResena(resenaDTO.getTextoResena());
        resena.setEstadoResena("ACTIVO");
        resena.setCalificacionLibro(resenaDTO.getCalificacionLibro());
        resena.setIdUsuario(resenaDTO.getIdUsuario());
        
        //CAMBIO IMPORTANTE: Ya no busca el Libro, solo almacena el ID.
        resena.setIdLibro(resenaDTO.getIdLibro());
        
        return resena;
    }
    
    /**
    * @Autor LAURA VANESSA NARANJO RODRIGUEZ.
    * @Since 28/01/2026.
    * @param resena
    * Recibe un DTO para un objeto autor para crear un DTO.
    * @return autorDTO
    */
    //Este metodo es para presentar los datos - conversión DOCUMENT → DTO.
    public ResenaDTO resenaDTO(Resena resena){
        ResenaDTO resenaDTO = new ResenaDTO();
        resenaDTO.setIdResena(resena.getIdResena());
        
        //CAMBIO IMPORTANTE: Ahora idLibro es un campo directo, no navega relación.
        resenaDTO.setIdLibro(resena.getIdLibro());
        
        resenaDTO.setTextoResena(resena.getTextoResena());
        resenaDTO.setEstadoResena(resena.getEstadoResena());
        resenaDTO.setCalificacionLibro(resena.getCalificacionLibro());
        resenaDTO.setIdUsuario(resena.getIdUsuario());
        return resenaDTO;
    }
}