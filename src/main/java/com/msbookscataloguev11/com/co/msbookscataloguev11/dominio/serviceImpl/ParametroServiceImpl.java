//DECLARACIÓN DE PAQUETES:
package com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.serviceImpl;

//IMPORTACIÓN DE LIBRERIAS:
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.Constantes.MensajesConstantes;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.RespuestaDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.dto.ParametroDTO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.dominio.service.ParametroService;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.dao.ParametroDAO;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.entity.Parametro;
import com.msbookscataloguev11.com.co.msbookscataloguev11.persistencia.repository.ParametroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

/**
* @Autor MARIA MAGDALENA PARRAGA CASTRO.
* @Since 28/01/2026.
* Esta es la declaración de la implementación del servicio de Parámetros.
* @Migracion a Elasticsearch: 19/02/2026
*/
@Service
public class ParametroServiceImpl implements ParametroService {
    
    @Autowired//INYECTAMOS EL DAO.
    private ParametroDAO parametroDAO;
    
    @Autowired//INYECTAMOS EL REPOSITORIO.
    private ParametroRepository parametroRepository;
    
    //LEER CONSULTA DE REGISTRO POR ID:
    @Override
    public RespuestaDTO consultarParametroporId(Long idParametro) {
        Optional<Parametro> parametroId = parametroRepository.findByIdParametro(Long.valueOf(idParametro));
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
        
        if (parametroId.isPresent()) {
            respuestaDTO.setParametroDTO(parametroDAO.parametroDTO(parametroId.get()));
            respuestaDTO.setMensaje(MensajesConstantes.MSG_REGISTRO_CONSULTADO_EXITO);
            respuestaDTO.setBanderaexito(true);
        } else {
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ID_NO_ENCONTRADO, false);
            respuestaDTO.setParametroDTO(null);
        }
        
        return respuestaDTO;
    }
    
    //MODIFICAR REGISTRO:
    @Override
    public RespuestaDTO actualizarParametro(ParametroDTO parametroDTO) {
        Optional<Parametro> parametroOpt = parametroRepository.findByIdParametro(parametroDTO.getIdParametro());
        RespuestaDTO respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_NO_ACTUALIZADO, false);
        
        if (parametroOpt.isPresent()) {
           Parametro parametroExistente = parametroOpt.get();
           Parametro parametroActualizado = parametroDAO.parametro(parametroDTO);
           
           //CRÍTICO: Preservar el String id de ES y el idParametro numérico.
           parametroActualizado.setId(parametroExistente.getId());
           parametroActualizado.setIdParametro(parametroExistente.getIdParametro());
           
           parametroRepository.save(parametroActualizado);
           respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_ACTUALIZADO_EXITO, true);
        } else {
            respuestaDTO = new RespuestaDTO(MensajesConstantes.MSG_REGISTRO_NO_ACTUALIZADO, false);
            respuestaDTO.setParametroDTO(null);
        }
        
        return respuestaDTO;
    }
}
