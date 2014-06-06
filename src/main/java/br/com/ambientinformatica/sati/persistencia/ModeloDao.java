package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.entidade.Modelo;

public interface ModeloDao extends Persistencia<Modelo> {

	List<Modelo> listarPorNome(String nome);

}
