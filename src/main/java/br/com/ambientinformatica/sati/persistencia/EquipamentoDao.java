package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.entidade.Equipamento;

public interface EquipamentoDao extends Persistencia<Equipamento> {
	
	List<Equipamento> listarPorNome(String nome);

}
