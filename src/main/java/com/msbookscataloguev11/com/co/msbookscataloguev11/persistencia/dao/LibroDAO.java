//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.dao;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.AutorDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.LibroDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.CategoriaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Autor;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.AutorData;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.CategoriaData;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Libro;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Categoria;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository.AutorRepository;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
* @Autor PD04. HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 02/02/2026.
* DAO para Libro - Elasticsearch.
* @Actualizacion David Paez 04/02/2026.
* @Migracion a Elasticsearch: 19/02/2026
*
* CAMBIOS IMPORTANTES:
* - Ya no maneja entidades JPA relacionadas, sino objetos embebidos (AutorData, CategoriaData).
* - Las categorías se mapean de IDs a objetos CategoriaData embebidos.
*/

@Component//DECLARACIÓN DEL COMPONENTE PARA LOS METODOS DEL DAO.
public class LibroDAO {
    
    @Autowired//INYECTAMOS EL REPOSITORIO.
    private CategoriaRepository categoriaRepository;
    
    @Autowired//INYECTAMOS EL REPOSITORIO.
    private AutorRepository autorRepository;
    
    //Conversión DTO → DOCUMENT.
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
        
        //MAPEAR AUTOR: Buscar en repositorio y embeber AutorData.
        if (libroDTO.getAutorDTO() != null && libroDTO.getAutorDTO().getIdAutor() != null) {
           Optional<Autor> autorOpt = autorRepository.findByIdAutor(libroDTO.getAutorDTO().getIdAutor());
           if (autorOpt.isPresent()) {
              Autor autor = autorOpt.get();
              AutorData autorData = new AutorData();
              autorData.setIdAutor(autor.getIdAutor());
              autorData.setNombresAutor(autor.getNombresAutor());
              autorData.setPrimerApellidoAutor(autor.getPrimerApellidoAutor());
              autorData.setSegundoApellidoAutor(autor.getSegundoApellidoAutor());
              libro.setAutor(autorData);
            }
        }
        
        //MAPEAR CATEGORIAS: De IDs a objetos CategoriaData embebidos.
        if (libroDTO.getCategoriasIds() != null && !libroDTO.getCategoriasIds().isEmpty()) {
           List<CategoriaData> categoriasData = new ArrayList<>();
           for (Long idCat : libroDTO.getCategoriasIds()) {
               Optional<Categoria> catOpt = categoriaRepository.findByIdCategoria(idCat);
               if (catOpt.isPresent()) {
                  Categoria cat = catOpt.get();
                  CategoriaData catData = new CategoriaData();
                  catData.setIdCategoria(cat.getIdCategoria());
                  catData.setNombreCategoria(cat.getNombreCategoria());
                  categoriasData.add(catData);
               }
           }
           libro.setCategorias(categoriasData);
        }
        
        return libro;
    }
    
    //Conversión DOCUMENT → DTO.
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
        
        //MAPEAR AUTOR EMBEBIDO: AutorData → AutorDTO
        if (libro.getAutor() != null) {
           AutorDTO autorDTO = new AutorDTO();
           autorDTO.setIdAutor(libro.getAutor().getIdAutor());
           autorDTO.setNombresAutor(libro.getAutor().getNombresAutor());
           autorDTO.setPrimerApellidoAutor(libro.getAutor().getPrimerApellidoAutor());
           autorDTO.setSegundoApellidoAutor(libro.getAutor().getSegundoApellidoAutor());
           libroDTO.setAutorDTO(autorDTO);
        }
        
        //MAPEAR CATEGORIAS EMBEBIDAS: List<CategoriaData> → List<CategoriaDTO>
        if (libro.getCategorias() != null && !libro.getCategorias().isEmpty()) {
           List<CategoriaDTO> categoriasDTO = libro.getCategorias().stream()
               .map(catData -> {
                   CategoriaDTO catDTO = new CategoriaDTO();
                   catDTO.setIdCategoria(catData.getIdCategoria());
                   catDTO.setNombreCategoria(catData.getNombreCategoria());
                   return catDTO;
               }).toList();
           libroDTO.setCategorias(categoriasDTO);
        }
        
        return libroDTO;
    }
}
