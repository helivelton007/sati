package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.corporativo.entidade.Cep;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.util.SatiException;
import br.com.ambientinformatica.util.UtilLog;

@Repository("cepDao")
public class CepDaoJpa extends PersistenciaJpa<Cep> implements CepDao{

   private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
   @Override
   public List<Cep> listarPorNome(String cep) throws SatiException, PersistenciaException {
			try{
				String sql = "select distinct c from Cep c where upper(c.cep) like upper(:cep)";
				Query query = em.createQuery(sql);
				query.setParameter("cep", "%" + cep + "%");
				return query.getResultList();
			}catch(Exception e){
				UtilLog.getLog().error(e.getMessage(), e);
				throw new PersistenciaException(e.getMessage());
			}
   }
}
