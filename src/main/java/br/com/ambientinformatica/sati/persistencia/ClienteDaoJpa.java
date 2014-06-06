package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.Cliente;
import br.com.ambientinformatica.sati.util.SatiException;
import br.com.ambientinformatica.util.UtilLog;

@Repository("clienteDao")
public class ClienteDaoJpa extends PersistenciaJpa<Cliente> implements ClienteDao{

   private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
   @Override
   public List<Cliente> listarPorNome(String nome) throws SatiException, PersistenciaException {
			try{
				String sql = "select distinct c from Cliente c where upper(c.nome) like upper(:nome)";
				Query query = em.createQuery(sql);
				query.setParameter("nome", "%" + nome + "%");
				return query.getResultList();
			}catch(Exception e){
				UtilLog.getLog().error(e.getMessage(), e);
				throw new PersistenciaException(e.getMessage());
			}
   }

}
