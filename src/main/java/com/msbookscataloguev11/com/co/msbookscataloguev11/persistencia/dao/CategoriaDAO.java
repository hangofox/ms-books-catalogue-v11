//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.dao;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.CategoriaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Categoria;
import org.springframework.stereotype.Component;

/**
* @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 28/01/2026.
* Declaración del método DAO.
*/
@Component//DECLARACIÓN DEL COMPONENTE PARA LOS METODOS DEL DAO.
public class CategoriaDAO {
    /**
    * @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
    * @Since 28/01/2026.
    * @param categoriaDTO
    * Recibe un DTO para crear un objeto categoria.
    * @return categoria
    */
    public Categoria categoria(CategoriaDTO categoriaDTO){
        Categoria categoria = new Categoria();
        categoria.setIdCategoria(categoriaDTO.getIdCategoria());
        categoria.setNombreCategoria(categoriaDTO.getNombreCategoria());
        
        return categoria;
    }
    
    /**
    * @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
    * @Since 28/01/2026.
    * @param categoria
    * Recibe un DTO para un objeto categoria para crear un DTO.
    * @return categoriaDTO
    */
    public CategoriaDTO categoriaDTO(Categoria categoria){
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setIdCategoria(categoria.getIdCategoria());
        categoriaDTO.setNombreCategoria(categoria.getNombreCategoria());
        
        return categoriaDTO;
    }
}
