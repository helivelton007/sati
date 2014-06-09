package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.entidade.Endereco;
import br.com.ambientinformatica.sati.util.SatiException;

public interface EnderecoDao extends Persistencia<Endereco>{

	List<Endereco> listarPorCep(String cep) throws SatiException, PersistenciaException;

}
