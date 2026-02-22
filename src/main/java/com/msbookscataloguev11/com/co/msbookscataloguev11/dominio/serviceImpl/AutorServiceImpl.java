//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.serviceImpl;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.Constantes.MensajesConstantes;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.RespuestaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.AutorDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service.AutorService;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.dao.AutorDAO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Autor;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository.AutorRepository;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
* @Autor HERNAN ADOLFO NUÑEZ GONZALEZ.
* @Since 28/01/2026.
* Esta es la declaración de la implementación del servicio.
* @Migracion a Elasticsearch: 19/02/2026
*/
@Service
public class AutorServiceImpl implements AutorService {
    @Autowired//INYECTAMOS EL DAO.
    private AutorDAO autorDAO;
    
    @Autowired//INYECTAMOS EL REPOSITORIO.
    private AutorRepository autorRepository;
    
    //MÉTODO ÚNICO PARA LISTAR/FILTRAR/ORDENAR/PAGINAR AUTORES:
    @Override
    public Slice<AutorDTO> listarAutores(String keyword, String orderBy, String orderMode, Pageable pageable) {
        //Construir sorting:
        Sort sort = buildSort(orderBy, orderMode);
        Pageable pageableWithSort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        
        //Obtener todos los autores (ES filtra y ordena):
        List<Autor> allAutores = StreamSupport.stream(autorRepository.findAll(pageableWithSort).spliterator(), false).toList();
        
        //Filtrar por keyword si existe:
        List<Autor> filteredAutores = allAutores;
        if (keyword != null && !keyword.trim().isEmpty()) {
            String keywordLower = keyword.toLowerCase();
            filteredAutores = allAutores.stream()
                .filter(a ->
                    (a.getNombresAutor() != null && a.getNombresAutor().toLowerCase().contains(keywordLower)) ||
                    (a.getPrimerApellidoAutor() != null && a.getPrimerApellidoAutor().toLowerCase().contains(keywordLower)) ||
                    (a.getSegundoApellidoAutor() != null && a.getSegundoApellidoAutor().toLowerCase().contains(keywordLower))
                ).toList();
        }
        
        //Paginar.
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredAutores.size());
        List<Autor> paginatedAutores = (start < filteredAutores.size()) ?
            filteredAutores.subList(start, end) : List.of();
            
        //Convertir a DTO
        List<AutorDTO> content = paginatedAutores.stream().map(autorDAO::autorDTO).toList();
        
        boolean hasNext = end < filteredAutores.size();
        return new SliceImpl<>(content, pageable, hasNext);
    }
    
    //CREAR REGISTRO:
    @Override
    public RespuestaDTO crearAutor(AutorDTO autorDTO) {
        autorDTO.setIdAutor(null); //Ignorar ID del cliente
        
        Autor autor = autorDAO.autor(autorDTO);
        
        //Generar ID numérico secuencial.
        Long nextId = IdGenerator.generateNextId(autorRepository.findAll(), Autor::getIdAutor);
        autor.setIdAutor(nextId);
        
        autorRepository.save(autor);
        return new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_CREADO_EXITO, true);
    }
    
    //LEER CONSULTA DE REGISTRO POR ID:
    @Override
    public RespuestaDTO consultarAutorporId(Long idAutor) {
        Optional<Autor> autorId = autorRepository.findByIdAutor(idAutor);
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
        
        if (autorId.isPresent()) {
           respuestaDTO.setAutorDTO(autorDAO.autorDTO(autorId.get()));
           respuestaDTO.setMensaje(MensajesConstantes.MSG_REGISTRO_CONSULTADO_EXITO);
           respuestaDTO.setBanderaexito(true);
        } else {
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
            respuestaDTO.setAutorDTO(null);
        }
        
        return respuestaDTO;
    }
    
    //MODIFICAR REGISTRO:
    @Override
    public RespuestaDTO actualizarAutor(AutorDTO autorDTO) {
        Optional<Autor> autorOpt = autorRepository.findByIdAutor(autorDTO.getIdAutor());
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_NO_ACTUALIZADO, false);
        
        if (autorOpt.isPresent()) {
            Autor autorExistente = autorOpt.get();
            Autor autorActualizado = autorDAO.autor(autorDTO);
            
            //CRÍTICO: Preservar el String id de ES y el idAutor numérico.
            autorActualizado.setId(autorExistente.getId());
            autorActualizado.setIdAutor(autorExistente.getIdAutor());
            
            autorRepository.save(autorActualizado);
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ACTUALIZADO_EXITO, true);
        } else {
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_NO_ACTUALIZADO, false);
            respuestaDTO.setAutorDTO(null);
        }
        
        return respuestaDTO;
    }
    
    //ELIMINAR REGISTRO:
    @Override
    public RespuestaDTO eliminarAutor(Long idAutor) {
        Optional<Autor> autorId = autorRepository.findByIdAutor(idAutor);
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
        
        if (autorId.isPresent()) {
            respuestaDTO.setAutorDTO(autorDAO.autorDTO(autorId.get()));
            autorRepository.delete(autorId.get());
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ELIMINADO_EXITO, true);
        } else {
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
            respuestaDTO.setAutorDTO(null);
        }
        
        return respuestaDTO;
    }
    
    private Sort buildSort(String orderBy, String orderMode) {
        Sort.Direction dir = "desc".equalsIgnoreCase(orderMode) ?
            Sort.Direction.DESC : Sort.Direction.ASC;
            
        if (orderBy == null || orderBy.isBlank() || "id".equalsIgnoreCase(orderBy)) {
            return Sort.by(dir, "idAutor");
        }
        
        switch (orderBy.toLowerCase()) {
            case "nombres":
                return Sort.by(dir, "nombresAutor");
            case "primerapellido":
                return Sort.by(dir, "primerApellidoAutor");
            case "segundoapellido":
                return Sort.by(dir, "segundoApellidoAutor");
            default:
                return Sort.by(dir, "idAutor");
        }
    }
}
