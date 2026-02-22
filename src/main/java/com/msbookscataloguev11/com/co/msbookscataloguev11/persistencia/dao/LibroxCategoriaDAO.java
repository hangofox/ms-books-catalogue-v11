//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.dao;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.LibroxCategoriaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Categoria;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Libro;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.LibroxCategoria;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository.CategoriaRepository;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;

/**
* @Autor DAVID GIOVANNI PAEZ OVALLE.
* @Since 21/02/2026.
* Declaración del método DAO.
*/
@Component//DECLARACIÓN DEL COMPONENTE PARA LOS METODOS DEL DAO.
public class LibroxCategoriaDAO {
    
    @Autowired//INYECTAMOS EL REPOSITORIO.
    private LibroRepository libroRepository;
    
    @Autowired//INYECTAMOS EL REPOSITORIO.
    private LibroDAO libroDAO;
    
    @Autowired//INYECTAMOS EL REPOSITORIO.
    private CategoriaRepository categoriaRepository;
    
    @Autowired//INYECTAMOS EL REPOSITORIO.
    private CategoriaDAO categoriaDAO;
    /**
    * @Autor DAVID GIOVANNI PAEZ OVALLE.
    * @Since 21/02/2026.
    * @param libroxCategoriaDTO
    * Recibe un DTO para crear un objeto libroxCategoria.
    * @return libroxCategoria
    */
    public LibroxCategoria libroxCategoria(LibroxCategoriaDTO libroxCategoriaDTO){
        LibroxCategoria libroxCategoria = new LibroxCategoria();
        libroxCategoria.setIdLibroxCategoria(libroxCategoriaDTO.getIdLibroxCategoria());
        libroxCategoria.setIdLibro(libroxCategoriaDTO.getIdLibro());
        libroxCategoria.setIdCategoria(libroxCategoriaDTO.getIdCategoria());
        
        return libroxCategoria;
    }
    
    /**
    * @Autor DAVID GIOVANNI PAEZ OVALLE.
    * @Since 21/02/2026.
    * @param libroxCategoria
    * Recibe un objeto libroxCategoria para crear un DTO.
    * @return libroxCategoriaDTO
    */
    public LibroxCategoriaDTO libroxCategoriaDTO(LibroxCategoria libroxCategoria){
        LibroxCategoriaDTO libroxCategoriaDTO = new LibroxCategoriaDTO();
        libroxCategoriaDTO.setIdLibroxCategoria(libroxCategoria.getIdLibroxCategoria());
        libroxCategoriaDTO.setIdLibro(libroxCategoria.getIdLibro());
        libroxCategoriaDTO.setIdCategoria(libroxCategoria.getIdCategoria());
        
        //MAPEAR LIBRO RELACIONADO.
        if (libroxCategoria.getIdLibro() != null) {
           Optional<Libro> libroOpt = libroRepository.findByIdLibro(libroxCategoria.getIdLibro());
           libroOpt.ifPresent(libro -> libroxCategoriaDTO.setLibroDTO(libroDAO.libroDTO(libro)));
        }
        
        //MAPEAR CATEGORIA RELACIONADA.
        if (libroxCategoria.getIdCategoria() != null) {
           Optional<Categoria> catOpt = categoriaRepository.findByIdCategoria(libroxCategoria.getIdCategoria());
           catOpt.ifPresent(cat -> libroxCategoriaDTO.setCategoriaDTO(categoriaDAO.categoriaDTO(cat)));
        }
        
        return libroxCategoriaDTO;
    }
}
