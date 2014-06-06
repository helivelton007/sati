package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.entidade.Marca;

public interface MarcaDao extends Persistencia<Marca> {

	List<Marca> listarPorNome(String nome);

}
