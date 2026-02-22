//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.serviceImpl;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.ResenaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service.ResenaService;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.dao.ResenaDAO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Resena;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository.ResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
* @Autor LAURA VANESSA NARANJO RODRIGUEZ.
* @Since 28/01/2026.
* Esta es la declaración de la implementación del servicio.
* Se inyectan DAOS y repositorios.
* @Migracion a Elasticsearch: 19/02/2026
*/
@Service
public class ResenaServiceImpl implements ResenaService {
    @Autowired//INYECTAMOS EL DAO.
    private ResenaRepository resenaRepository;

    @Autowired//INYECTAMOS EL REPOSITORIO.
    private ResenaDAO resenaDAO;

    //LISTAR RESEÑAS POR LIBRO:
    @Override
    public List<ResenaDTO> listarResenas(Long idLibro) {
        List<Resena> resenas = resenaRepository.findByIdLibro(idLibro);
        return resenas.stream().map(resenaDAO::resenaDTO).toList();
    }
    
    //CREAR REGISTRO:
    @Override
    public void crearResena(ResenaDTO resenaDTO) {
        if (resenaDTO.getCalificacionLibro() < 1 || resenaDTO.getCalificacionLibro() > 5) {
            throw new IllegalStateException("La calificación debe estar entre 1 y 5");
        }
        resenaRepository.save(resenaDAO.resena(resenaDTO));
    }
    
    //LEER CONSULTA DE REGISTRO POR ID:
    @Override
    public ResenaDTO consultarResenaporId(Long idResena) {
        //CAMBIO: Usar findByIdResena (Long) en lugar de findById (String)
        Optional<Resena> resenaId = resenaRepository.findByIdResena(idResena);
        if (resenaId.isEmpty()) {
            throw new IllegalStateException("La reseña no existe");
        }
        return resenaDAO.resenaDTO(resenaId.get());
    }
    
    //MODIFICAR REGISTRO:
    @Override
    public void actualizarResena(Long idResena, ResenaDTO resenaDTO) {
        //CAMBIO: Usar findByIdResena (Long) en lugar de findById (String)
        Optional<Resena> resenaOptional = resenaRepository.findByIdResena(idResena);
        if (resenaOptional.isEmpty()) {
            throw new IllegalStateException("La reseña no existe");
        }
        Resena resena = resenaOptional.get();
        
        //Actualizar campos.
        resena.setCalificacionLibro(resenaDTO.getCalificacionLibro());
        resena.setTextoResena(resenaDTO.getTextoResena());
        
        //Guardar (ES hace UPDATE por el id).
        resenaRepository.save(resena);
    }
    
    //ELIMINAR REGISTRO:
    @Override
    public void eliminarResena(Long idResena) {
        //CAMBIO: Usar findByIdResena (Long) en lugar de findById (String)
        Optional<Resena> resenaId = resenaRepository.findByIdResena(idResena);
        if (resenaId.isPresent()) {
            resenaRepository.delete(resenaId.get());
        } else {
            throw new IllegalStateException("Esta reseña no existe");
        }
    }
    
    //CONSULTAR CALIFICACIÓN PROMEDIO DE UN LIBRO:
    @Override
    public Double consultarCalificacion(Long idLibro) {
        //CAMBIO IMPORTANTE: El cálculo ya no se hace con @Query JPQL, se hace en Java
        List<Resena> resenas = resenaRepository.findByIdLibro(idLibro);

        if (resenas.isEmpty()) {
            return 0.0;
        }
        
        double suma = resenas.stream()
            .mapToInt(Resena::getCalificacionLibro)
            .average()
            .orElse(0.0);
            
        return suma;
    }
}
