//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.serviceImpl;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.Constantes.MensajesConstantes;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.RespuestaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.CategoriaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service.CategoriaService;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.dao.CategoriaDAO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Categoria;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository.CategoriaRepository;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
* @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 28/01/2026.
* Esta es la declaración de la implementación del servicio.
* Se inyectan DAOS y repositorios.
* @Migracion a Elasticsearch: 19/02/2026
*/
@Service
public class CategoriaServiceImpl implements CategoriaService {
    @Autowired//INYECTAMOS EL DAO.
    private CategoriaDAO categoriaDAO;
    
    @Autowired//INYECTAMOS EL REPOSITORIO.
    private CategoriaRepository categoriaRepository;
    
    //MÉTODO PARA LISTAR/FILTRAR/ORDENAR CATEGORIAS SIN PAGINACIÓN (PARA SELECTS):
    @Override
    public List<CategoriaDTO> listarCategoriasNoPaginacion(String keyword, String orderBy, String orderMode) {
        //Construir sorting
        Sort sort = buildSort(orderBy, orderMode);
        
        //Obtener todas las categorías con ordenamiento.
        List<Categoria> allCategorias = StreamSupport.stream(categoriaRepository.findAll(sort).spliterator(), false).toList();
        
        //Filtrar por keyword si existe
        List<Categoria> filteredCategorias = allCategorias;
        if (keyword != null && !keyword.trim().isEmpty()) {
            String keywordLower = keyword.toLowerCase();
            filteredCategorias = allCategorias.stream()
                .filter(c -> c.getNombreCategoria() != null && c.getNombreCategoria().toLowerCase().contains(keywordLower))
                .toList();
        }
        
        //Convertir entidades a DTOs
        List<CategoriaDTO> categoriaDTOS = new ArrayList<>();
        for (Categoria categoria : filteredCategorias) {
            categoriaDTOS.add(categoriaDAO.categoriaDTO(categoria));
        }
        
        return categoriaDTOS;
    }
    
    //MÉTODO ÚNICO PARA LISTAR/FILTRAR/ORDENAR/PAGINAR CATEGORIAS:
    @Override
    public Slice<CategoriaDTO> listarCategorias(String keyword, String orderBy, String orderMode, Pageable pageable) {
        //Construir sorting.
        Sort sort = buildSort(orderBy, orderMode);
        Pageable pageableWithSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        
        //Obtener todas las categorías (ES filtra y ordena).
        List<Categoria> allCategorias = StreamSupport.stream(categoriaRepository.findAll(pageableWithSort).spliterator(), false).toList();
        
        //Filtrar por keyword si existe.
        List<Categoria> filteredCategorias = allCategorias;
        if (keyword != null && !keyword.trim().isEmpty()) {
           String keywordLower = keyword.toLowerCase();
           filteredCategorias = allCategorias.stream()
               .filter(c -> c.getNombreCategoria() != null && c.getNombreCategoria().toLowerCase().contains(keywordLower))
               .toList();
        }
        
        //Paginar.
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredCategorias.size());
        List<Categoria> paginatedCategorias = (start < filteredCategorias.size()) ?
            filteredCategorias.subList(start, end) : List.of();
        
        //Convertir a DTO.
        List<CategoriaDTO> content = paginatedCategorias.stream().map(categoriaDAO::categoriaDTO).toList();
        
        boolean hasNext = end < filteredCategorias.size();
        return new SliceImpl<>(content, pageable, hasNext);
    }
    
    //CREAR REGISTRO:
    @Override
    public RespuestaDTO crearCategoria(CategoriaDTO categoriaDTO) {
        Optional<Categoria> categoriaNombre = categoriaRepository.findByNombreCategoria(categoriaDTO.getNombreCategoria().toUpperCase());
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_NO_CREADO, false);
        
        //DECLARACIÓN E INICIALIZACIÓN DE LAS BANDERAS EN CERO (0):
        long banderaNombreRegistroEncontrado = 0;
        
        if (categoriaNombre.isPresent()) {
           banderaNombreRegistroEncontrado = 1;
        }
        
        if (banderaNombreRegistroEncontrado == 1) {
           respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_NOMBRE_YA_EXISTE, false);
           respuestaDTO.setCategoriaDTO(null);
        }
        if (banderaNombreRegistroEncontrado == 0) {
           categoriaDTO.setIdCategoria(null); //Ignorar ID del cliente
           
           Categoria categoria = categoriaDAO.categoria(categoriaDTO);
           
           //Generar ID numérico secuencial.
           Long nextId = IdGenerator.generateNextId(categoriaRepository.findAll(), Categoria::getIdCategoria);
           categoria.setIdCategoria(nextId);
           
           categoriaRepository.save(categoria);
           respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_CREADO_EXITO, true);
        }
        
        return respuestaDTO;
    }
    
    //LEER CONSULTA DE REGISTRO POR ID:
    @Override
    public RespuestaDTO consultarCategoriaporId(Long idCategoria) {
        Optional<Categoria> categoriaId = categoriaRepository.findByIdCategoria(Long.valueOf(idCategoria));
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
        
        if (categoriaId.isPresent()) {
            respuestaDTO.setCategoriaDTO(categoriaDAO.categoriaDTO(categoriaId.get()));
            respuestaDTO.setMensaje(MensajesConstantes.MSG_REGISTRO_CONSULTADO_EXITO);
            respuestaDTO.setBanderaexito(true);
        } else {
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
            respuestaDTO.setCategoriaDTO(null);
        }
        
        return respuestaDTO;
    }
    
    //LEER CONSULTA DE REGISTRO POR NOMBRE:
    @Override
    public RespuestaDTO consultarCategoriaporNombre(String nombreCategoria) {
        Optional<Categoria> categoriaNombre = categoriaRepository.findByNombreCategoria(nombreCategoria);
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_NOMBRE_NO_ENCONTRADO, false);
        
        if (categoriaNombre.isPresent()) {
            respuestaDTO.setCategoriaDTO(categoriaDAO.categoriaDTO(categoriaNombre.get()));
            respuestaDTO.setMensaje(MensajesConstantes.MSG_REGISTRO_CONSULTADO_EXITO);
            respuestaDTO.setBanderaexito(true);
        } else {
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_NOMBRE_NO_ENCONTRADO, false);
            respuestaDTO.setCategoriaDTO(null);
        }
        
        return respuestaDTO;
    }
    
    //MODIFICAR REGISTRO:
    @Override
    public RespuestaDTO actualizarCategoria(CategoriaDTO categoriaDTO) {
        Optional<Categoria> categoriaOpt = categoriaRepository.findByIdCategoria(categoriaDTO.getIdCategoria());
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_NO_ACTUALIZADO, false);
        
        if (categoriaOpt.isPresent()) {
            Categoria categoriaExistente = categoriaOpt.get();
            Categoria categoriaActualizada = categoriaDAO.categoria(categoriaDTO);
            
            //CRÍTICO: Preservar el String id de ES y el idCategoria numérico.
            categoriaActualizada.setId(categoriaExistente.getId());
            categoriaActualizada.setIdCategoria(categoriaExistente.getIdCategoria());
            
            categoriaRepository.save(categoriaActualizada);
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ACTUALIZADO_EXITO, true);
        } else {
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_NO_ACTUALIZADO, false);
            respuestaDTO.setCategoriaDTO(null);
        }
        
        return respuestaDTO;
    }
    
    //ELIMINAR REGISTRO:
    @Override
    public RespuestaDTO eliminarCategoria(Long idCategoria) {
        Optional<Categoria> categoriaId = categoriaRepository.findByIdCategoria(idCategoria);
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
        
        if (categoriaId.isPresent()) {
           respuestaDTO.setCategoriaDTO(categoriaDAO.categoriaDTO(categoriaId.get()));
           categoriaRepository.delete(categoriaId.get());
           respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ELIMINADO_EXITO, true);
        } else {
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
            respuestaDTO.setCategoriaDTO(null);
        }
        
        return respuestaDTO;
    }
    
    private Sort buildSort(String orderBy, String orderMode) {
        Sort.Direction dir = "desc".equalsIgnoreCase(orderMode) ?
            Sort.Direction.DESC : Sort.Direction.ASC;
        
        if (orderBy == null || orderBy.isBlank() || "id".equalsIgnoreCase(orderBy)) {
           return Sort.by(dir, "idCategoria");
        }
        
        if ("nombre".equalsIgnoreCase(orderBy)) {
           return Sort.by(dir, "nombreCategoria");
        }
        
        return Sort.by(dir, "idCategoria");
    }
}
