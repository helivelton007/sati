package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.Endereco;
import br.com.ambientinformatica.sati.util.SatiException;
import br.com.ambientinformatica.util.UtilLog;

@Repository("enderecoDao")
public class EnderecoDaoJpa extends PersistenciaJpa<Endereco> implements EnderecoDao{

   private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
   @Override
   public List<Endereco> listarPorCep(String cep) throws SatiException, PersistenciaException {
			try{
				String sql = "select distinct c from Endereco c where upper(c.cep) like upper(:cep)";
				Query query = em.createQuery(sql);
				query.setParameter("nome", "%" + cep + "%");
				return query.getResultList();
			}catch(Exception e){
				UtilLog.getLog().error(e.getMessage(), e);
				throw new PersistenciaException(e.getMessage());
			}
   }

}
