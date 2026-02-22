//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.serviceImpl;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.Constantes.MensajesConstantes;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.LibroxCategoriaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.RespuestaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service.LibroxCategoriaService;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.dao.LibroxCategoriaDAO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.LibroxCategoria;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository.LibroxCategoriaRepository;
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
* @Autor DAVID GIOVANNI PAEZ OVALLE.
* @Since 21/02/2026.
* Esta es la declaración de la implementación del servicio.
* Se inyectan DAOS y repositorios.
*/
@Service
public class LibroxCategoriaServiceImpl implements LibroxCategoriaService {
    
    @Autowired//INYECTAMOS EL DAO.
    private LibroxCategoriaDAO libroxCategoriaDAO;
    
    @Autowired//INYECTAMOS EL REPOSITORIO.
    private LibroxCategoriaRepository libroxCategoriaRepository;

    //LISTAR TODOS LOS LIBROS SIN PAGINACIÓN (PARA SELECTS):
    @Override
    public List<LibroxCategoriaDTO> listaTodo() {
        List<LibroxCategoria> lista = StreamSupport.stream(libroxCategoriaRepository.findAll().spliterator(), false).toList();
        return lista.stream().map(libroxCategoriaDAO::libroxCategoriaDTO).toList();
    }
    
    //MÉTODO ÚNICO PARA LISTAR/FILTRAR/ORDENAR/PAGINAR LIBROS X CATEGORIAS:
    @Override
    public Slice<LibroxCategoriaDTO> listarLibroxCategorias(String keyword, String orderBy, String orderMode, Pageable pageable) {
        //Construir sorting
        Sort sort = buildSort(orderBy, orderMode);
        Pageable pageableWithSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        
        //Obtener todas las relaciones
        List<LibroxCategoria> allRelaciones = StreamSupport.stream(libroxCategoriaRepository.findAll(pageableWithSort).spliterator(), false).toList();
        
        //Paginar.
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), allRelaciones.size());
        List<LibroxCategoria> paginatedRelaciones = (start < allRelaciones.size()) ?
            allRelaciones.subList(start, end) : List.of();
            
        //Convertir a DTO.
        List<LibroxCategoriaDTO> content = paginatedRelaciones.stream().map(libroxCategoriaDAO::libroxCategoriaDTO).toList();
        
        boolean hasNext = end < allRelaciones.size();
        return new SliceImpl<>(content, pageable, hasNext);
    }
    
    //CREAR REGISTRO:
    @Override
    public RespuestaDTO crearLibroxCategoria(LibroxCategoriaDTO libroxCategoriaDTO) {
        Optional<LibroxCategoria> relacionExistente = libroxCategoriaRepository.findByIdLibroAndIdCategoria(libroxCategoriaDTO.getIdLibro(), libroxCategoriaDTO.getIdCategoria());
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_NO_CREADO, false);
        
        //DECLARACIÓN E INICIALIZACIÓN DE LAS BANDERAS EN CERO (0):
        long banderaDuplicado = 0;
        
        if (relacionExistente.isPresent()) {
           banderaDuplicado = 1;
        }
        
        if (banderaDuplicado == 1) {
           respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_YA_EXISTE, false);
           respuestaDTO.setLibroxCategoriaDTO(null);
        }
        if (banderaDuplicado == 0) {
            libroxCategoriaDTO.setIdLibroxCategoria(null);//Ignorar ID del cliente.
            
            LibroxCategoria libroxCategoria = libroxCategoriaDAO.libroxCategoria(libroxCategoriaDTO);
            
            //Generar ID numérico secuencial.
            Long nextId = IdGenerator.generateNextId(libroxCategoriaRepository.findAll(), LibroxCategoria::getIdLibroxCategoria);
            libroxCategoria.setIdLibroxCategoria(nextId);
            
            libroxCategoriaRepository.save(libroxCategoria);
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_CREADO_EXITO, true);
        }
        
        return respuestaDTO;
    }
    
    //LEER CONSULTA DE REGISTRO POR ID:
    @Override
    public RespuestaDTO consultarLibroxCategoriaById(Long idLibroxCategoria) {
        Optional<LibroxCategoria> libroxCategoriaOpt = libroxCategoriaRepository.findByIdLibroxCategoria(idLibroxCategoria);
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
        
        if (libroxCategoriaOpt.isPresent()) {
            respuestaDTO.setLibroxCategoriaDTO(libroxCategoriaDAO.libroxCategoriaDTO(libroxCategoriaOpt.get()));
            respuestaDTO.setMensaje(MensajesConstantes.MSG_REGISTRO_CONSULTADO_EXITO);
            respuestaDTO.setBanderaexito(true);
        } else {
            respuestaDTO.setLibroxCategoriaDTO(null);
        }
        
        return respuestaDTO;
    }
    
    //LEER CONSULTA DE REGISTROS POR ID DEL LIBRO:
    @Override
    public RespuestaDTO consultarLibroxCategoriasByLibro(Long idLibro) {
        List<LibroxCategoria> relaciones = libroxCategoriaRepository.findByIdLibro(idLibro);
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
        
        if (!relaciones.isEmpty()) {
           List<LibroxCategoriaDTO> dtos = relaciones.stream().map(libroxCategoriaDAO::libroxCategoriaDTO).toList();
           respuestaDTO.setLibrosxCategoriasDTO(dtos);
           respuestaDTO.setMensaje(MensajesConstantes.MSG_REGISTRO_CONSULTADO_EXITO);
           respuestaDTO.setBanderaexito(true);
        } else {
            respuestaDTO.setLibrosxCategoriasDTO(null);
        }
        
        return respuestaDTO;
    }
    
    //LEER CONSULTA DE REGISTROS POR ID DE LA CATEGORÍA:
    @Override
    public RespuestaDTO consultarLibroxCategoriasByCategoria(Long idCategoria) {
        List<LibroxCategoria> relaciones = libroxCategoriaRepository.findByIdCategoria(idCategoria);
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
        
        if (!relaciones.isEmpty()) {
           List<LibroxCategoriaDTO> dtos = relaciones.stream().map(libroxCategoriaDAO::libroxCategoriaDTO).toList();
           respuestaDTO.setLibrosxCategoriasDTO(dtos);
           respuestaDTO.setMensaje(MensajesConstantes.MSG_REGISTRO_CONSULTADO_EXITO);
           respuestaDTO.setBanderaexito(true);
        } else {
            respuestaDTO.setLibrosxCategoriasDTO(null);
        }
        
        return respuestaDTO;
    }
    
    //MODIFICAR REGISTRO:
    @Override
    public RespuestaDTO actualizarLibroxCategoria(LibroxCategoriaDTO libroxCategoriaDTO) {
        Optional<LibroxCategoria> libroxCategoriaOpt = libroxCategoriaRepository.findByIdLibroxCategoria(libroxCategoriaDTO.getIdLibroxCategoria());
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_NO_ACTUALIZADO, false);
        
        if (libroxCategoriaOpt.isPresent()) {
           LibroxCategoria libroxCategoriaExistente = libroxCategoriaOpt.get();
           LibroxCategoria libroxCategoriaActualizada = libroxCategoriaDAO.libroxCategoria(libroxCategoriaDTO);
           
           //CRÍTICO: Preservar el String id de ES y el idLibroxCategoria numérico.
           libroxCategoriaActualizada.setId(libroxCategoriaExistente.getId());
           libroxCategoriaActualizada.setIdLibroxCategoria(libroxCategoriaExistente.getIdLibroxCategoria());
           
           libroxCategoriaRepository.save(libroxCategoriaActualizada);
           respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ACTUALIZADO_EXITO, true);
        } else {
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_NO_ACTUALIZADO, false);
            respuestaDTO.setLibroxCategoriaDTO(null);
        }
        
        return respuestaDTO;
    }
    
    //ELIMINAR REGISTRO:
    @Override
    public RespuestaDTO eliminarLibroxCategoria(Long idLibroxCategoria) {
        Optional<LibroxCategoria> libroxCategoriaOpt = libroxCategoriaRepository.findByIdLibroxCategoria(idLibroxCategoria);
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
        
        if (libroxCategoriaOpt.isPresent()) {
            libroxCategoriaRepository.delete(libroxCategoriaOpt.get());
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ELIMINADO_EXITO, true);
        } else {
            respuestaDTO.setLibroxCategoriaDTO(null);
        }
        
        return respuestaDTO;
    }
    
    //MÉTODO AUXILIAR PARA CONSTRUIR EL ORDENAMIENTO:
    private Sort buildSort(String orderBy, String orderMode) {
        Sort.Direction dir = "desc".equalsIgnoreCase(orderMode) ?
            Sort.Direction.DESC : Sort.Direction.ASC;
        
        if (orderBy == null || orderBy.isBlank() || "id".equalsIgnoreCase(orderBy)) {
            return Sort.by(dir, "idLibroxCategoria");
        }
        
        switch (orderBy.toLowerCase()) {
            case "idlibro":
                return Sort.by(dir, "idLibro");
            case "idcategoria":
                return Sort.by(dir, "idCategoria");
            default:
                return Sort.by(dir, "idLibroxCategoria");
        }
    }
}
