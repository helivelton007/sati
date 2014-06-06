package br.com.ambientinformatica.sati.persistencia;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.corporativo.entidade.EnumUf;
import br.com.ambientinformatica.corporativo.entidade.Municipio;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.util.UtilLog;

@Repository("municipioDao")
public class MunicipioDaoJpa extends PersistenciaJpa<Municipio> implements MunicipioDao{

   private static final long serialVersionUID = 1L;

   @SuppressWarnings("unchecked")
   @Override
   public List<Municipio> listarPorDescricao(String descricao) throws PersistenciaException {
      try {
         if(descricao == null){
            throw new PersistenciaException("Informe a descrição do município para realizar a consulta");
         }
         Query query = em.createQuery("select m from Municipio m where upper(m.descricao) like upper(:descricao)");
         query.setParameter("descricao", "%" + descricao.toUpperCase() + "%");
         return query.getResultList();
      } catch (NoResultException e) {
         UtilLog.getLog().error("erro ao listar por descrição" + e.getMessage(), e);
         throw new PersistenciaException(e.getMessage(), e);
      }
   }

   @SuppressWarnings("unchecked")
   @Override
   public List<Municipio> listarPorDescricaoUf(String descricao, EnumUf uf) throws PersistenciaException {
      try {
         if(descricao == null){
            throw new PersistenciaException("Informe a descrição do município para realizar a consulta");
         }
         if(uf == null){
            throw new PersistenciaException("Informe a UF para realizar a consulta");
         }
         Query query = em.createQuery("select m from Municipio m where upper(m.descricao) like :descricao and m.uf = :uf");
         query.setParameter("descricao", "%" + descricao.toUpperCase() + "%");
         query.setParameter("uf", uf);
         return query.getResultList();
      } catch (Exception e) {
         UtilLog.getLog().error(e.getMessage(),  e);
         throw new PersistenciaException(e.getMessage(), e);
      }
   }

   @Override
   public Municipio consultarPorCodigoIbge(Integer codigoIbge) throws PersistenciaException {
      try {
         Query query = em.createQuery("select m from Municipio m where m.codigoIbge = :codigoIbge");
         query.setParameter("codigoIbge", codigoIbge);
         return (Municipio) query.getSingleResult();
      } catch (NoResultException e) {
         UtilLog.getLog().info("Não encontrado o município com o código: " + codigoIbge);
         return null;
      } catch (Exception e) {
         UtilLog.getLog().error(e.getMessage(), e);
         throw new PersistenciaException(e.getMessage(), e);
      }
   }

}
