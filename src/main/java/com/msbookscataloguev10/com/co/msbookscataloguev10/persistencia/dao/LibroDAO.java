//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev10.com.co.msbookscataloguev10.persistencia.dao;

//IMPORTACIÓN DE LIBRERIAS:

import com.msbookscataloguev10.com.co.msbookscataloguev10.dominio.dto.LibroDTO;
import com.msbookscataloguev10.com.co.msbookscataloguev10.dominio.dto.CategoriaDTO;
import com.msbookscataloguev10.com.co.msbookscataloguev10.persistencia.entity.Libro;
import com.msbookscataloguev10.com.co.msbookscataloguev10.persistencia.entity.Categoria;
import com.msbookscataloguev10.com.co.msbookscataloguev10.persistencia.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * @Autor PD04. HERNAN ADOLFO NUÑEZ GONZALEZ.
 * @Since 02/02/2026.
 * Declaración de la entidad.
 * @Actualizacion David Paez 04/02/2026.
 */

@Component//DECLARACIÓN DEL COMPONENTE PARA LOS METODOS DEL DAO.
public class LibroDAO {
    
    @Autowired//INYECTAMOS EL DAO DE AUTOR.
    private AutorDAO autorDAO;

    //Resuelve ids de categorias a entidades
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private CategoriaDAO categoriaDAO;

    public Libro libro(LibroDTO libroDTO){
        Libro libro = new Libro();
        libro.setIdLibro(libroDTO.getIdLibro());
        libro.setTituloLibro(libroDTO.getTituloLibro());
        libro.setFechaPublicacionLibro(libroDTO.getFechaPublicacionLibro());
        libro.setSinopsisLibro(libroDTO.getSinopsisLibro());
        libro.setCodigoIsbnLibro(libroDTO.getCodigoIsbnLibro());
        libro.setPrecioLibro(libroDTO.getPrecioLibro());
        libro.setFormatoLibro(libroDTO.getFormatoLibro());
        libro.setNombreArchivoImagenLibro(libroDTO.getNombreArchivoImagenLibro());
        libro.setEstadoLibro(libroDTO.getEstadoLibro());

        // MAPEAR CATEGORIAS SI EXISTEN IDS EN EL DT
        if  (libroDTO.getCategoriasIds() != null && !libroDTO.getCategoriasIds().isEmpty()) {
            Set<Categoria> categorias = new HashSet<>();
            for (Long idCat : libroDTO.getCategoriasIds()) {
                Optional<Categoria> catOpt = categoriaRepository.findByIdCategoria(idCat);
                catOpt.ifPresent(categorias::add);
            }
            libro.setCategorias(categorias);
        }


        return libro;
    }






    public LibroDTO libroDTO(Libro libro){
        LibroDTO libroDTO = new LibroDTO();
        libroDTO.setIdLibro(libro.getIdLibro());
        libroDTO.setTituloLibro(libro.getTituloLibro());
        libroDTO.setFechaPublicacionLibro(libro.getFechaPublicacionLibro());
        libroDTO.setSinopsisLibro(libro.getSinopsisLibro());
        libroDTO.setCodigoIsbnLibro(libro.getCodigoIsbnLibro());
        libroDTO.setPrecioLibro(libro.getPrecioLibro());
        libroDTO.setFormatoLibro(libro.getFormatoLibro());
        libroDTO.setNombreArchivoImagenLibro(libro.getNombreArchivoImagenLibro());
        libroDTO.setEstadoLibro(libro.getEstadoLibro());
        
        //MAPEAR EL AUTOR SI EXISTE EN LA ENTIDAD:
        if (libro.getAutor() != null) {
           libroDTO.setAutorDTO(autorDAO.autorDTO(libro.getAutor()));
        }


        //MAPEAR CATEGORIAS (ENTITY -> DTO)
        if (libro.getCategorias() != null && !libro.getCategorias().isEmpty()) {
            List<CategoriaDTO> categoriaDTOS = new ArrayList<>();
            for (Categoria c : libro.getCategorias()) {
                categoriaDTOS.add(categoriaDAO.categoriaDTO(c));
            }
            libroDTO.setCategorias(categoriaDTOS);
        }


        return libroDTO;
    }
}
