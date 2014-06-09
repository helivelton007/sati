package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import br.com.ambientinformatica.corporativo.entidade.Cep;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.util.SatiException;

public interface CepDao extends Persistencia<Cep>{

	List<Cep> listarPorNome(String cep) throws SatiException, PersistenciaException;

}
