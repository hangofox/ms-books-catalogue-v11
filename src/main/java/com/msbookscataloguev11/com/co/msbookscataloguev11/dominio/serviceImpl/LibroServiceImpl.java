//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.serviceImpl;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.Constantes.MensajesConstantes;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.RespuestaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.LibroDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service.LibroService;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.dao.LibroDAO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Categoria;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.CategoriaData;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Libro;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository.CategoriaRepository;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository.LibroRepository;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Autor;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository.AutorRepository;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository.LibroxCategoriaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

/**
* @Autor PD04. HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 02/02/2026.
* Declaración de la entidad.
* @Actualizacion David Paez 06/02/2026.
* Esta es la declaración de la implementación del servicio.
* Se inyectan DAOS y repositorios.
* @Migracion a Elasticsearch: 19/02/2026.
*/
@Service
public class LibroServiceImpl implements LibroService {
    
    @Autowired//INYECTAMOS EL DAO.
    private LibroDAO libroDAO;
    
    @Autowired//INYECTAMOS EL REPOSITORIO.
    private LibroRepository libroRepository;
    
    @Autowired//INYECTAMOS EL REPOSITORIO.
    private CategoriaRepository categoriaRepository;
    
    @Autowired//INYECTAMOS EL REPOSITORIO.
    private AutorRepository autorRepository;

    @Autowired//INYECTAMOS EL REPOSITORIO.
    private LibroxCategoriaRepository libroxCategoriaRepository;
    
    //LISTAR TODOS LOS LIBROS VISIBLES:
    @Override
    public List<LibroDTO> listaTodo() {
        List<Libro> listaLibros = libroRepository.findByEstadoLibro("VISIBLE");
        return listaLibros.stream().map(libroDAO::libroDTO).toList();
    }
    
    //LISTAR TODOS LOS LIBROS SIN PAGINACIÓN (PARA SELECTS):
    @Override
    public List<LibroDTO> listarLibrosNoPaginacion(String orderBy, String orderMode) {
        Sort sort = buildSort(orderBy, orderMode);
        List<Libro> listaLibros = StreamSupport.stream(libroRepository.findAll(sort).spliterator(), false).toList();
        return listaLibros.stream().map(libroDAO::libroDTO).toList();
    }
    
    private Sort buildSort(String orderBy, String orderMode) {
        Sort.Direction dir = "desc".equalsIgnoreCase(orderMode)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;
                
        //default: ordenar por idLibro.
        if (orderBy == null || orderBy.isBlank() || "id".equalsIgnoreCase(orderBy)) {
            return Sort.by(dir, "idLibro");
        }
        
        switch (orderBy.toLowerCase()) {
            case "titulo":
                return Sort.by(dir, "tituloLibro.keyword");
            case "fecha":
                return Sort.by(dir, "fechaPublicacionLibro");
            case "precio":
                return Sort.by(dir, "precioLibro");
            default:
                return Sort.by(dir, "idLibro");
        }
    }
    
    //MÉTODO ÚNICO PARA LISTAR/FILTRAR/ORDENAR/PAGINAR LIBROS:
    @Override
    public Slice<LibroDTO> listarLibros(
            String keyword,
            String titulo,
            String sinopsisLibro,
            String codigoIsbnLibro,
            String formatoLibro,
            String estadoLibro,
            String fechaPublicacionLibro,
            Long idCategoria,
            String nombreCategoria,
            Long idAutor,
            String nombresAutor,
            String primerApellidoAutor,
            String segundoApellidoAutor,
            Double minPrecio,
            Double maxPrecio,
            String orderBy,
            String orderMode,
            Pageable pageable
    ) {
        //Construir sorting.
        Sort sort = buildSort(orderBy, orderMode);
        Pageable pageableWithSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        
        //Obtener todos los libros (ES filtra y ordena)
        List<Libro> allLibros = StreamSupport.stream(libroRepository.findAll(pageableWithSort).spliterator(), false).toList();
        
        //Filtrar por todos los criterios.
        List<Libro> filteredLibros = allLibros.stream()
            .filter(libro -> filterByAllCriteria(
                libro, keyword, titulo, sinopsisLibro, codigoIsbnLibro,
                formatoLibro, estadoLibro, fechaPublicacionLibro,
                idCategoria, nombreCategoria, idAutor,
                nombresAutor, primerApellidoAutor, segundoApellidoAutor,
                minPrecio, maxPrecio
            ))
            .toList();
        
        //Paginar.
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredLibros.size());
        List<Libro> paginatedLibros = (start < filteredLibros.size()) ?
            filteredLibros.subList(start, end) : List.of();
            
        //Convertir a DTO
        List<LibroDTO> content = paginatedLibros.stream().map(libroDAO::libroDTO).toList();
        
        boolean hasNext = end < filteredLibros.size();
        return new SliceImpl<>(content, pageable, hasNext);
    }
    
    //MÉTODO AUXILIAR PARA FILTRAR POR TODOS LOS CRITERIOS:
    private boolean filterByAllCriteria(
            Libro libro,
            String keyword,
            String titulo,
            String sinopsisLibro,
            String codigoIsbnLibro,
            String formatoLibro,
            String estadoLibro,
            String fechaPublicacionLibro,
            Long idCategoria,
            String nombreCategoria,
            Long idAutor,
            String nombresAutor,
            String primerApellidoAutor,
            String segundoApellidoAutor,
            Double minPrecio,
            Double maxPrecio
    ) {
        //Filtro por keyword (busca en título, sinopsis, ISBN).
        if (keyword != null && !keyword.trim().isEmpty()) {
            String kw = keyword.toLowerCase();
            boolean matchesKeyword =
                (libro.getTituloLibro() != null && libro.getTituloLibro().toLowerCase().contains(kw)) ||
                (libro.getSinopsisLibro() != null && libro.getSinopsisLibro().toLowerCase().contains(kw)) ||
                (libro.getCodigoIsbnLibro() != null && libro.getCodigoIsbnLibro().toLowerCase().contains(kw));
            if (!matchesKeyword) return false;
        }
        
        //Filtro por título.
        if (titulo != null && !titulo.trim().isEmpty()) {
            if (libro.getTituloLibro() == null ||
                !libro.getTituloLibro().toLowerCase().contains(titulo.toLowerCase())) {
                return false;
            }
        }
        
        //Filtro por sinopsis.
        if (sinopsisLibro != null && !sinopsisLibro.trim().isEmpty()) {
            if (libro.getSinopsisLibro() == null ||
               !libro.getSinopsisLibro().toLowerCase().contains(sinopsisLibro.toLowerCase())) {
               return false;
            }
        }
        
        //Filtro por ISBN.
        if (codigoIsbnLibro != null && !codigoIsbnLibro.trim().isEmpty()) {
            if (libro.getCodigoIsbnLibro() == null ||
               !libro.getCodigoIsbnLibro().toLowerCase().contains(codigoIsbnLibro.toLowerCase())) {
               return false;
            }
        }
        
        //Filtro por formato.
        if (formatoLibro != null && !formatoLibro.trim().isEmpty()) {
            if (libro.getFormatoLibro() == null ||
               !libro.getFormatoLibro().equalsIgnoreCase(formatoLibro)) {
               return false;
            }
        }
        
        //Filtro por estado.
        if (estadoLibro != null && !estadoLibro.trim().isEmpty()) {
            if (libro.getEstadoLibro() == null ||
               !libro.getEstadoLibro().equalsIgnoreCase(estadoLibro)) {
               return false;
            }
        }
        
        //Filtro por fecha de publicación.
        if (fechaPublicacionLibro != null && !fechaPublicacionLibro.trim().isEmpty()) {
            if (libro.getFechaPublicacionLibro() == null ||
                !libro.getFechaPublicacionLibro().equals(fechaPublicacionLibro)) {
                return false;
            }
        }
        
        //Filtro por ID de categoría.
        if (idCategoria != null) {
           if (libro.getCategorias() == null || libro.getCategorias().isEmpty()) {
              return false;
           }
           boolean hasCategory = libro.getCategorias().stream()
                .anyMatch(c -> c.getIdCategoria().equals(idCategoria));
           if (!hasCategory) return false;
        }
        
        //Filtro por nombre de categoría.
        if (nombreCategoria != null && !nombreCategoria.trim().isEmpty()) {
           if (libro.getCategorias() == null || libro.getCategorias().isEmpty()) {
              return false;
           }
           String nombreCatLower = nombreCategoria.toLowerCase();
           boolean hasCategory = libro.getCategorias().stream()
                .anyMatch(c -> c.getNombreCategoria() != null &&
                              c.getNombreCategoria().toLowerCase().contains(nombreCatLower));
           if (!hasCategory) return false;
        }
        
        //Filtro por ID de autor.
        if (idAutor != null) {
            if (libro.getAutor() == null || !libro.getAutor().getIdAutor().equals(idAutor)) {
                return false;
            }
        }
        
        //Filtro por nombres de autor.
        if (nombresAutor != null && !nombresAutor.trim().isEmpty()) {
            if (libro.getAutor() == null || libro.getAutor().getNombresAutor() == null ||
                !libro.getAutor().getNombresAutor().toLowerCase().contains(nombresAutor.toLowerCase())) {
                return false;
            }
        }
        
        //Filtro por primer apellido de autor.
        if (primerApellidoAutor != null && !primerApellidoAutor.trim().isEmpty()) {
            if (libro.getAutor() == null || libro.getAutor().getPrimerApellidoAutor() == null ||
                !libro.getAutor().getPrimerApellidoAutor().toLowerCase().contains(primerApellidoAutor.toLowerCase())) {
                return false;
            }
        }
        
        //Filtro por segundo apellido de autor.
        if (segundoApellidoAutor != null && !segundoApellidoAutor.trim().isEmpty()) {
           if (libro.getAutor() == null || libro.getAutor().getSegundoApellidoAutor() == null ||
              !libro.getAutor().getSegundoApellidoAutor().toLowerCase().contains(segundoApellidoAutor.toLowerCase())) {
              return false;
           }
        }
        
        //Filtro por precio mínimo.
        if (minPrecio != null) {
           if (libro.getPrecioLibro() < minPrecio) {
              return false;
           }
        }
        
        //Filtro por precio máximo.
        if (maxPrecio != null) {
           if (libro.getPrecioLibro() > maxPrecio) {
              return false;
           }
        }
        
        return true;
    }
    
    //MÉTODO PARA LISTAR LIBROS POR CRITERIOS INDIVIDUALES Y COMBINADOS:
    @Override
    public Slice<LibroDTO> buscarLibrosPorCriterios(
            String tituloLibro,
            String fechaPublicacionLibro,
            String sinopsisLibro,
            String codigoIsbnLibro,
            String precioLibro,
            String formatoLibro,
            String estadoLibro,
            String orderBy,
            String orderMode,
            Pageable pageable) {
            
        //Construir sorting.
        Sort sort = buildSort(orderBy, orderMode);
        Pageable pageableWithSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        
        //Obtener todos los libros.
        List<Libro> allLibros = StreamSupport.stream(libroRepository.findAll(pageableWithSort).spliterator(), false).toList();
        
        //Filtrar por criterios.
        List<Libro> filteredLibros = allLibros.stream()
            .filter(libro -> {
                //Filtro por título
                if (tituloLibro != null && !tituloLibro.trim().isEmpty()) {
                    if (libro.getTituloLibro() == null ||
                        !libro.getTituloLibro().toLowerCase().contains(tituloLibro.toLowerCase())) {
                        return false;
                    }
                }
                
                //Filtro por fecha.
                if (fechaPublicacionLibro != null && !fechaPublicacionLibro.trim().isEmpty()) {
                    if (libro.getFechaPublicacionLibro() == null ||
                        !libro.getFechaPublicacionLibro().equals(fechaPublicacionLibro)) {
                        return false;
                    }
                }
                
                //Filtro por sinopsis.
                if (sinopsisLibro != null && !sinopsisLibro.trim().isEmpty()) {
                    if (libro.getSinopsisLibro() == null ||
                        !libro.getSinopsisLibro().toLowerCase().contains(sinopsisLibro.toLowerCase())) {
                        return false;
                    }
                }
                
                //Filtro por ISBN.
                if (codigoIsbnLibro != null && !codigoIsbnLibro.trim().isEmpty()) {
                    if (libro.getCodigoIsbnLibro() == null ||
                        !libro.getCodigoIsbnLibro().toLowerCase().contains(codigoIsbnLibro.toLowerCase())) {
                        return false;
                    }
                }
                
                //Filtro por precio (como String, convertir a Double).
                if (precioLibro != null && !precioLibro.trim().isEmpty()) {
                    try {
                        double precio = Double.parseDouble(precioLibro);
                        if (libro.getPrecioLibro() != precio) {
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
                
                //Filtro por formato.
                if (formatoLibro != null && !formatoLibro.trim().isEmpty()) {
                    if (libro.getFormatoLibro() == null ||
                        !libro.getFormatoLibro().equalsIgnoreCase(formatoLibro)) {
                        return false;
                    }
                }
                
                //Filtro por estado.
                if (estadoLibro != null && !estadoLibro.trim().isEmpty()) {
                    if (libro.getEstadoLibro() == null ||
                        !libro.getEstadoLibro().equalsIgnoreCase(estadoLibro)) {
                        return false;
                    }
                }
                
                return true;
            })
            .toList();
            
        //Paginar.
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredLibros.size());
        List<Libro> paginatedLibros = (start < filteredLibros.size()) ?
            filteredLibros.subList(start, end) : List.of();

        //Convertir a DTO.
        List<LibroDTO> content = paginatedLibros.stream().map(libroDAO::libroDTO).toList();
        
        boolean hasNext = end < filteredLibros.size();
        return new SliceImpl<>(content, pageable, hasNext);
    }
    
    //CREAR REGISTRO:
    @Override
    public RespuestaDTO crearLibro(LibroDTO libroDTO) {
        
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_NO_CREADO, false);
        
        //1) Ignorar ID (autoincremental).
        libroDTO.setIdLibro(null);
        
        //2) Validar que venga el idAutor.
        if (libroDTO.getAutorDTO() == null || libroDTO.getAutorDTO().getIdAutor() == null) {
            return new RespuestaDTO("Debes enviar autorDTO.idAutor", false);
        }
        
        Long idAutor = libroDTO.getAutorDTO().getIdAutor();
        
        //3) Buscar el autor REAL en BD usando findByIdAutor (no findById que usa String).
        Optional<Autor> autorOpt = autorRepository.findByIdAutor(idAutor);
        if (!autorOpt.isPresent()) {
            return new RespuestaDTO("No existe autor con id: " + idAutor, false);
        }
        
        //4) Construir el libro desde el DAO (DAO ya maneja AutorData y CategoriaData embebidos).
        Libro libro = libroDAO.libro(libroDTO);
        
        //5) Generar ID numérico secuencial.
        Long nextId = IdGenerator.generateNextId(libroRepository.findAll(), Libro::getIdLibro);
        libro.setIdLibro(nextId);
        
        //6) Guardar.
        libroRepository.save(libro);
        
        return new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_CREADO_EXITO, true);
    }
    
    //CARGUE MASIVO DE LIBROS:
    @Override
    public RespuestaDTO crearLibrosMasivo(List<LibroDTO> librosDTO) {
        if (librosDTO == null || librosDTO.isEmpty()) {
           return new RespuestaDTO(MensajesConstantes.MSG_REGISTROS_NO_CREADOS, false);
        }
        
        //Obtener el ID máximo actual una sola vez para evitar colisiones.
        long nextId = IdGenerator.generateNextId(libroRepository.findAll(), Libro::getIdLibro);
        
        List<Libro> librosParaGuardar = new ArrayList<>();
        int creados = 0;
        int errores = 0;
        
        for (LibroDTO libroDTO : librosDTO) {
            //Validar que venga el idAutor.
            if (libroDTO.getAutorDTO() == null || libroDTO.getAutorDTO().getIdAutor() == null) {
                errores++;
                continue;
            }
            
            //Verificar que el autor exista en BD.
            Optional<Autor> autorOpt = autorRepository.findByIdAutor(libroDTO.getAutorDTO().getIdAutor());
            if (!autorOpt.isPresent()) {
               errores++;
               continue;
            }
            
            //Ignorar ID enviado y asignar el secuencial.
            libroDTO.setIdLibro(null);
            Libro libro = libroDAO.libro(libroDTO);
            libro.setIdLibro(nextId++);
            librosParaGuardar.add(libro);
            creados++;
        }
        
        //Guardar todos los libros válidos en un solo batch.
        if (!librosParaGuardar.isEmpty()) {
           libroRepository.saveAll(librosParaGuardar);
        }
        
        String mensaje = String.format(MensajesConstantes.MSG_CARGUE_MASIVO_COMPLETADO, creados, errores);
        return new RespuestaDTO(mensaje, creados > 0);
    }
    
    //LEER CONSULTA DE REGISTRO POR ID:
    @Override
    public RespuestaDTO consultarLibroporId(Long idLibro) {
        Optional<Libro> libroId = libroRepository.findByIdLibro(Long.valueOf(idLibro));
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
        
        if (libroId.isPresent()) {
            respuestaDTO.setLibroDTO(libroDAO.libroDTO(libroId.get()));
            respuestaDTO.setMensaje(MensajesConstantes.MSG_REGISTRO_CONSULTADO_EXITO);
            respuestaDTO.setBanderaexito(true);
        } else {
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
            respuestaDTO.setLibroDTO(null);
        }
        
        return respuestaDTO;
    }
    
    //MODIFICAR REGISTRO:
    @Override
    public RespuestaDTO actualizarLibro(LibroDTO libroDTO) {
        Optional<Libro> libroOpt = libroRepository.findByIdLibro(libroDTO.getIdLibro());
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_NO_ACTUALIZADO, false);
        
        if (libroOpt.isPresent()) {
            Libro libroExistente = libroOpt.get();
            Libro libroActualizado = libroDAO.libro(libroDTO);
            
            //CRÍTICO: Preservar el String id de ES y el idLibro numérico
            libroActualizado.setId(libroExistente.getId());
            libroActualizado.setIdLibro(libroExistente.getIdLibro());
            
            libroRepository.save(libroActualizado);
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ACTUALIZADO_EXITO, true);
        } else {
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_NO_ACTUALIZADO, false);
            respuestaDTO.setLibroDTO(null);
        }
        
        return respuestaDTO;
    }
    
    //ELIMINAR REGISTRO:
    @Override
    public RespuestaDTO eliminarLibro(Long idLibro) {
        Optional<Libro> libroId = libroRepository.findByIdLibro(idLibro);
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
        
        if (libroId.isPresent()) {
            respuestaDTO.setLibroDTO(libroDAO.libroDTO(libroId.get()));
            libroRepository.delete(libroId.get());
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ELIMINADO_EXITO, true);
        } else {
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
            respuestaDTO.setLibroDTO(null);
        }
        
        return respuestaDTO;
    }
    
    //ELIMINACIÓN MASIVA DE LIBROS (TRUNCATE):
    @Override
    public RespuestaDTO eliminarTodosLosLibros() {
        libroRepository.deleteAll();
        libroxCategoriaRepository.deleteAll();
        return new RespuestaDTO(MensajesConstantes.MSG_REGISTROS_VACIADOS_EXITO, true);
    }
    
    //AGREGAR CATEGORÍA A LIBRO:
    @Override
    public RespuestaDTO agregarCategoriaALibro(Long idLibro, Long idCategoria) {
        
        Optional<Libro> libroOpt = libroRepository.findByIdLibro(idLibro);
        if (!libroOpt.isPresent()) {
            return new RespuestaDTO("LIBRO NO ENCONTRADO", false);
        }
        
        Optional<Categoria> catOpt = categoriaRepository.findByIdCategoria(idCategoria);
        if (!catOpt.isPresent()) {
            return new RespuestaDTO("CATEGORIA NO ENCONTRADA", false);
        }
        
        Libro libro = libroOpt.get();
        Categoria categoria = catOpt.get();
        
        //Crear CategoriaData desde Categoria
        CategoriaData catData = new CategoriaData();
        catData.setIdCategoria(categoria.getIdCategoria());
        catData.setNombreCategoria(categoria.getNombreCategoria());
        
        if (libro.getCategorias() == null) {
           libro.setCategorias(new ArrayList<>());
        }
        
        //Verificar si ya existe.
        boolean exists = libro.getCategorias().stream()
            .anyMatch(c -> c.getIdCategoria().equals(idCategoria));
            
        if (!exists) {
           libro.getCategorias().add(catData);
           libroRepository.save(libro);
        }
        
        return new RespuestaDTO("CATEGORIA AGREGADA AL LIBRO CORRECTAMENTE", true);
    }
    
    //ELIMINAR CATEGORÍA DE LIBRO:
    @Override
    public RespuestaDTO eliminarCategoriaDeLibro(Long idLibro, Long idCategoria) {
        
        Optional<Libro> libroOpt = libroRepository.findByIdLibro(idLibro);
        if (!libroOpt.isPresent()) {
            return new RespuestaDTO("LIBRO NO ENCONTRADO", false);
        }
        
        Libro libro = libroOpt.get();
        if (libro.getCategorias() == null || libro.getCategorias().isEmpty()) {
            return new RespuestaDTO("EL LIBRO NO TIENE CATEGORIAS ASIGNADAS", false);
        }
        
        //Remover por idCategoria
        boolean removed = libro.getCategorias().removeIf(c -> c.getIdCategoria().equals(idCategoria));
        if (!removed) {
            return new RespuestaDTO("LA CATEGORIA NO ESTÁ ASIGNADA A ESTE LIBRO", false);
        }
        
        libroRepository.save(libro);
        return new RespuestaDTO("CATEGORIA ELIMINADA DEL LIBRO CORRECTAMENTE", true);
    }
    
    //REEMPLAZAR CATEGORÍAS DE LIBRO:
    @Override
    public RespuestaDTO reemplazarCategoriasDeLibro(Long idLibro, List<Long> categoriasIds) {
        
        Optional<Libro> libroOpt = libroRepository.findByIdLibro(idLibro);
        if (!libroOpt.isPresent()) {
            return new RespuestaDTO("LIBRO NO ENCONTRADO", false);
        }
        
        Libro libro = libroOpt.get();
        
        List<CategoriaData> nuevasCategorias = new ArrayList<>();
        if (categoriasIds != null) {
            for (Long idCat : categoriasIds) {
                Optional<Categoria> catOpt = categoriaRepository.findByIdCategoria(idCat);
                if (!catOpt.isPresent()) {
                   return new RespuestaDTO("CATEGORIA NO ENCONTRADA: " + idCat, false);
                }
                
                Categoria cat = catOpt.get();
                CategoriaData catData = new CategoriaData();
                catData.setIdCategoria(cat.getIdCategoria());
                catData.setNombreCategoria(cat.getNombreCategoria());
                nuevasCategorias.add(catData);
            }
        }
        
        libro.setCategorias(nuevasCategorias);
        libroRepository.save(libro);
        
        return new RespuestaDTO("CATEGORIAS REEMPLAZADAS CORRECTAMENTE", true);
    }
}
