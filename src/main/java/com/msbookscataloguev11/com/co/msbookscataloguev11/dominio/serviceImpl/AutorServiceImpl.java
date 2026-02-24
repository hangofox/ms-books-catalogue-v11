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
        //Obtener TODOS los autores sin sorting de OS (los campos Search_As_You_Type no son sorteables en OS).
        List<Autor> allAutores = StreamSupport.stream(autorRepository.findAll().spliterator(), false).toList();

        //Filtrar por keyword si existe.
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

        //Ordenar en memoria (evita delegar el sort a OS con campos no sorteables).
        filteredAutores = sortAutoresInMemory(filteredAutores, orderBy, orderMode);

        //Paginar.
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filteredAutores.size());
        List<Autor> paginatedAutores = (start < filteredAutores.size()) ?
            filteredAutores.subList(start, end) : List.of();

        //Convertir a DTO.
        List<AutorDTO> content = paginatedAutores.stream().map(autorDAO::autorDTO).toList();

        boolean hasNext = end < filteredAutores.size();
        return new SliceImpl<>(content, pageable, hasNext);
    }

    //ORDENAR AUTORES EN MEMORIA:
    private List<Autor> sortAutoresInMemory(List<Autor> autores, String orderBy, String orderMode) {
        boolean desc = "desc".equalsIgnoreCase(orderMode);
        String field = (orderBy == null || orderBy.isBlank()) ? "id" : orderBy.toLowerCase();

        List<Autor> sorted = new java.util.ArrayList<>(autores);
        sorted.sort((a, b) -> {
            int cmp;
            switch (field) {
                case "nombres":
                    String na = a.getNombresAutor() != null ? a.getNombresAutor().toLowerCase() : "";
                    String nb = b.getNombresAutor() != null ? b.getNombresAutor().toLowerCase() : "";
                    cmp = na.compareTo(nb);
                    break;
                case "primerapellido":
                    String pa = a.getPrimerApellidoAutor() != null ? a.getPrimerApellidoAutor().toLowerCase() : "";
                    String pb = b.getPrimerApellidoAutor() != null ? b.getPrimerApellidoAutor().toLowerCase() : "";
                    cmp = pa.compareTo(pb);
                    break;
                case "segundoapellido":
                    String sa = a.getSegundoApellidoAutor() != null ? a.getSegundoApellidoAutor().toLowerCase() : "";
                    String sb2 = b.getSegundoApellidoAutor() != null ? b.getSegundoApellidoAutor().toLowerCase() : "";
                    cmp = sa.compareTo(sb2);
                    break;
                default:
                    Long ia = a.getIdAutor() != null ? a.getIdAutor() : 0L;
                    Long ib = b.getIdAutor() != null ? b.getIdAutor() : 0L;
                    cmp = ia.compareTo(ib);
            }
            return desc ? -cmp : cmp;
        });
        return sorted;
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
    
}

