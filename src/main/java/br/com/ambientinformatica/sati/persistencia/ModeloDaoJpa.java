package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.Modelo;

@Repository("modeloDao")
public class ModeloDaoJpa extends PersistenciaJpa<Modelo> implements ModeloDao {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Modelo> listarPorNome(String nome) {
		Query q = this.em
				.createQuery("from Modelo as a where a.nome like :nome");
		q.setParameter("nome", "%" + nome + "%");
		return q.getResultList();
	}

}
