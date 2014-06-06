package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.entidade.Contato;

public interface ContatoDao extends Persistencia<Contato>{

	List<Contato> listarPorNome(String nome);
}
