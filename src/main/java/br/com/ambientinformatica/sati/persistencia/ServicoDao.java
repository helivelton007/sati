package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.entidade.Servico;
import br.com.ambientinformatica.sati.util.SatiException;

public interface ServicoDao extends Persistencia<Servico>{
	
	public List<Servico> listar( Integer id, String descricao) throws PersistenciaException;

	public Servico consultarPorDescricao( String descricao) throws SatiException;
	
	List<Servico> listarPorDescricao(String descricao) throws SatiException, PersistenciaException;
}
