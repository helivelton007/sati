package br.com.ambientinformatica.sati.persistencia;

import java.util.Date;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.ambientinformatica.jpa.persistencia.PersistenciaJpa;
import br.com.ambientinformatica.sati.entidade.Cliente;
import br.com.ambientinformatica.sati.entidade.EnumEstadoOrdemServico;
import br.com.ambientinformatica.sati.entidade.OrdemServico;
import br.com.ambientinformatica.sati.util.SatiException;
import br.com.ambientinformatica.util.UtilLog;

@Repository("OrdemServicoDao")
public class OrdemServicoDaoJpa extends PersistenciaJpa<OrdemServico> implements OrdemServicoDao{

   private static final long serialVersionUID = 1L;

   
//   @Autowired
//   private ClienteDao clienteDao;
//   
//   @Autowired
//   private MunicipioDao municipioDao;
//   
//   @Autowired
//   private ItemEquipamentoDao itemEquipamentoDao;
   

   public OrdemServico consultarPorId(Integer id) throws SatiException{
      try {
         Query query = em.createQuery("select os from OrdemServico os where os.id = :id");
         query.setParameter("id", id);
         return (OrdemServico) query.getSingleResult();
      } catch (NoResultException e) {
         return null;
      } catch (Exception e) {
         UtilLog.getLog().error(e.getMessage(), e);
         throw new SatiException(e.getMessage(), e);
      }
   }


	@SuppressWarnings("unchecked")
	@Override
	public List<OrdemServico> listarPorDataEmissao( long id, Cliente cliente, Date dataHoraInicio, Date dataHoraFim, EnumEstadoOrdemServico estadoOsSelecionado) throws SatiException{
		try{
			String sql = "select distinct os from OrdemServico os " +
					"left join fetch os.cliente c " +
					//"left join fetch os.itensServicos is " +
					"where 1 = 1 ";
			if(id != 0){
				sql += " and os.id =:id";
			}
			if(dataHoraInicio != null && dataHoraFim != null){
				sql += " and os.dataEmissao >= :dataHoraInicio and os.dataEmissao <= :dataHoraFim";
			}
			if(cliente != null && cliente.getId() > 0){
				sql += " and os.requisitante = :requisitante";
			}
			if(estadoOsSelecionado != null){
				sql += " and os.estado = :estado";
			}else{
				sql += " and os.estado != :estado";
			}
			Query query = em.createQuery(sql);
			if(id != 0){
				query.setParameter("id", id);
			}
			if(dataHoraInicio != null){
				query.setParameter("dataHoraInicio", dataHoraInicio);
			}
			if(dataHoraFim != null){
				query.setParameter("dataHoraFim", dataHoraFim);
			}
			if(cliente != null && cliente.getId() > 0){
				query.setParameter("requisitante", cliente);
			}
			if(estadoOsSelecionado != null){
				query.setParameter("estado", estadoOsSelecionado);
			}else{
				query.setParameter("estado", EnumEstadoOrdemServico.CANCELADA);
			}
			return query.getResultList();
		}catch(Exception e){
			UtilLog.getLog().error(e.getMessage(), e);
			throw new SatiException(e.getMessage());
		}
	}


}
