//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.dao;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.AutorDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Autor;
import org.springframework.stereotype.Component;

/**
* @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 28/01/2026.
* Declaración del método DAO.
*/
@Component//DECLARACIÓN DEL COMPONENTE PARA LOS METODOS DEL DAO.
public class AutorDAO {
    /**
    * @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
    * @Since 28/01/2026.
    * @param autorDTO
    * Recibe un DTO para crear un objeto autor.
    * @return autor
    */
    public Autor autor(AutorDTO autorDTO){
        Autor autor = new Autor();
        autor.setIdAutor(autorDTO.getIdAutor());
        autor.setNombresAutor(autorDTO.getNombresAutor().toUpperCase());
        autor.setPrimerApellidoAutor(autorDTO.getPrimerApellidoAutor().toUpperCase());
        autor.setSegundoApellidoAutor(autorDTO.getSegundoApellidoAutor());
        
        return autor;
    }
    
    /**
    * @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
    * @Since 28/01/2026.
    * @param autor
    * Recibe un DTO para un objeto autor para crear un DTO.
    * @return autorDTO
    */
    public AutorDTO autorDTO(Autor autor){
        AutorDTO autorDTO = new AutorDTO();
        autorDTO.setIdAutor(autor.getIdAutor());
        autorDTO.setNombresAutor(autor.getNombresAutor().toUpperCase());
        autorDTO.setPrimerApellidoAutor(autor.getPrimerApellidoAutor().toUpperCase());
        autorDTO.setSegundoApellidoAutor(autor.getSegundoApellidoAutor());
        
        return autorDTO;
    }
}
