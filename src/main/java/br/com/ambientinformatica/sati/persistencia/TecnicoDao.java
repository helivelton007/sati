package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.entidade.Tecnico;

public interface TecnicoDao extends Persistencia<Tecnico> {

	List<Tecnico> listarPorNome(String nome);

}
