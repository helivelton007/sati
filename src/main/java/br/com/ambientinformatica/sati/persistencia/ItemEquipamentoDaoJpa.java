package br.com.ambientinformatica.sati.persistencia;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.ItemEquipamento;

@Repository("ItemEquipamentoDao")
public class ItemEquipamentoDaoJpa extends PersistenciaJpa<ItemEquipamento> implements ItemEquipamentoDao{

   private static final long serialVersionUID = 1L;

}
