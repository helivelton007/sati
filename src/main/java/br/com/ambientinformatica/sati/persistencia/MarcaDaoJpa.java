package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.Marca;

@Repository("marcaDao")
public class MarcaDaoJpa extends PersistenciaJpa<Marca> implements MarcaDao {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Marca> listarPorNome(String nome) {
		Query q = this.em
				.createQuery("from Marca as a where a.nome like :nome");
		q.setParameter("nome", "%" + nome + "%");
		return q.getResultList();
	}

}
