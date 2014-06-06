package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import br.com.ambientinformatica.corporativo.entidade.EnumUf;
import br.com.ambientinformatica.corporativo.entidade.Municipio;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;

public interface MunicipioDao extends Persistencia<Municipio>{
   
   Municipio consultarPorCodigoIbge(Integer codigoIbge) throws PersistenciaException;
   
   List<Municipio> listarPorDescricao(String descricao) throws PersistenciaException;
   
   List<Municipio> listarPorDescricaoUf(String descricao, EnumUf uf) throws PersistenciaException;

}
