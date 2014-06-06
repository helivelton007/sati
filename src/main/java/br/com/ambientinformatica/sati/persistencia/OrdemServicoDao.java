package br.com.ambientinformatica.sati.persistencia;

import java.util.Date;
import java.util.List;

import br.com.ambientinformatica.jpa.persistencia.Persistencia;
import br.com.ambientinformatica.sati.entidade.Cliente;
import br.com.ambientinformatica.sati.entidade.EnumEstadoOrdemServico;
import br.com.ambientinformatica.sati.entidade.OrdemServico;
import br.com.ambientinformatica.sati.util.SatiException;

public interface OrdemServicoDao extends Persistencia<OrdemServico>{
   
   public OrdemServico consultarPorId(Integer idOs) throws SatiException;

   public List<OrdemServico> listarPorDataEmissao( long id, Cliente cliente, Date dataHoraInicio, Date dataHoraFim, EnumEstadoOrdemServico estadoOsSelecionado) throws SatiException;

}
