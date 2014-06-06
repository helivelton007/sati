package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.Servico;
import br.com.ambientinformatica.sati.util.SatiException;
import br.com.ambientinformatica.util.UtilLog;

@Repository("servicoDao")
public class ServicoDaoJpa extends PersistenciaJpa<Servico> implements ServicoDao{

   private static final long serialVersionUID = 1L;

	@SuppressWarnings({ "unchecked" })
	public List<Servico> listar(Integer id, String descricao)
			throws PersistenciaException {
		try {
			String sql = "select e from Servico e where 1 = 1";
			if (id != null && id > 0) {
				sql += " and e.id = :id";
			}
			if (descricao != null && !descricao.isEmpty()) {
				sql += " and upper(e.descricao) like upper(:descricao)";
			}
			Query query = em.createQuery(sql);
			if (id != null && id>0) {
				query.setParameter("id", id);
			}
			if (descricao != null && !descricao.isEmpty()) {
				query.setParameter("descricao", "%" + descricao + "%");
			}
			return query.getResultList();
		} catch (Exception e) {
			UtilLog.getLog().error(e.getMessage(), e);
			throw new PersistenciaException(e.getMessage());
		}
	}

	public Servico consultarPorDescricao(String descricao) throws SatiException {
		String sql = "select e from Servico e where upper(e.descricao) = upper(:descricao)";
		Query query = em.createQuery(sql);
		query.setParameter("descricao", descricao);
		try {
			return (Servico) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}