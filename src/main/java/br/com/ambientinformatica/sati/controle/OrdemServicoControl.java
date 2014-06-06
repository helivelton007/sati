package br.com.ambientinformatica.sati.controle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.com.ambientinformatica.ambientjsf.util.UtilFaces;
import br.com.ambientinformatica.ambientjsf.util.UtilFacesRelatorio;
import br.com.ambientinformatica.jpa.exception.PersistenciaException;
import br.com.ambientinformatica.sati.entidade.Cliente;
import br.com.ambientinformatica.sati.entidade.EnumEstadoOrdemServico;
import br.com.ambientinformatica.sati.entidade.Equipamento;
import br.com.ambientinformatica.sati.entidade.ItemEquipamento;
import br.com.ambientinformatica.sati.entidade.ItemServico;
import br.com.ambientinformatica.sati.entidade.OrdemServico;
import br.com.ambientinformatica.sati.entidade.Servico;
import br.com.ambientinformatica.sati.persistencia.ClienteDao;
import br.com.ambientinformatica.sati.persistencia.EquipamentoDao;
import br.com.ambientinformatica.sati.persistencia.OrdemServicoDao;
import br.com.ambientinformatica.sati.persistencia.ServicoDao;
import br.com.ambientinformatica.sati.util.SatiException;
import br.com.ambientinformatica.util.UtilData;

@Controller("OrdemServicoControl")
@Scope("conversation")
public class OrdemServicoControl {

   @Autowired
   private ClienteDao clienteDao;

   @Autowired
   private OrdemServicoDao ordemServicoDao;   
   
   @Autowired
   private EquipamentoDao equipamentoDao;
   
   @Autowired
   private ServicoDao servicoDao;
   
   private Cliente cliente = new Cliente();
   
   private Cliente clienteList = new Cliente();

   private Servico servico = new Servico();
   
   private ItemServico itemServico = new ItemServico();
   
   private Long cpfCnpjConsulta;
   
   private OrdemServico ordemServico = new OrdemServico();
   
   private OrdemServico osCancelar = new OrdemServico();
   
   private List<OrdemServico> ordensServico = new ArrayList<OrdemServico>();
   
   private Equipamento equipamento = new Equipamento();
   
   private ItemEquipamento itemEquipamento = new ItemEquipamento();
   
   private EnumEstadoOrdemServico estadoOsSelecionado;
   
   private String codigoEquipamento;
   
   private Date dataHoraInicio = UtilData.getDataInicioMes(new Date());
   
   private Date dataHoraFim = UtilData.getDataFimMes(new Date());

   private Integer idOs;
   
	public List<Cliente> consultarCliente(String nome){
		try {
			return clienteDao.listarPorNome(nome);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
			return new ArrayList<Cliente>();
		}
	}
	
	public List<Servico> consultarServico(String query){
		List<Servico> servicos = new ArrayList<Servico>();
		try {
			servicos = servicoDao.listar(null, query);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
		return servicos;
	}	
	
	public void addServico(ActionEvent evt){
		if(servico != null){
			itemServico.setServico(servico);
			ordemServico.addItemServico(itemServico);
			servico = new Servico();
			itemServico = new ItemServico();
		} else {
			UtilFaces.addMensagemFaces("Selecione um serviço");
		}
	}
	
	public List<SelectItem> getStatusOrdemServicos(){
		return UtilFaces.getListEnum(EnumEstadoOrdemServico.values());
	}
	
	
   public void adicionarItem(ActionEvent evt){
   
   }
   
   public void confirmar(){
   	ordemServico.emitirOs();
   	try {
	      ordemServicoDao.alterar(ordemServico);
      } catch (PersistenciaException e) {
	    UtilFaces.addMensagemFaces("Houve um erro ao Emitir Ordem de Serviço");
      }
   }
   
   public void salvar(ActionEvent evt){
      try {
      	if(ordemServico.getCliente() == null || ordemServico.getCliente().getId() == 0){
      		UtilFaces.addMensagemFaces("É necessário escolher um cliente");
      	}
      	ordemServicoDao.incluir(ordemServico);
         UtilFaces.addMensagemFaces("Ordem de Serviço Salva com sucesso");
      } catch (Exception e) {
         UtilFaces.addMensagemFaces(e);
      }
   }
   
   public void listar(ActionEvent evt){
      ordensServico.clear();
      try {
         if(dataHoraFim.getTime() - dataHoraInicio.getTime() > 2678400000L){
            throw new SatiException("O intervalo entre a data final e a data inicial deve ser de no máximo 31 dias");
         }else{
            if(idOs != null && idOs > 0){
               ordensServico.add(ordemServicoDao.consultarPorId(idOs));
            }else{
               ordensServico = ordemServicoDao.listarPorDataEmissao(0L,clienteList, dataHoraInicio, dataHoraFim, estadoOsSelecionado);
            }
         }
      } catch (Exception e) {
         UtilFaces.addMensagemFaces(e);
      }
   }
   
	public void imprimir(ActionEvent evt){
		try {
			OrdemServico osImprimir = (OrdemServico) UtilFaces.getValorParametro(evt, "osImprimir");
			if(osImprimir != null){
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("ordemServico", osImprimir);
				UtilFacesRelatorio.gerarRelatorioFaces("jasper/ordemServico.jasper", osImprimir.getItensServico(), parametros);
			}
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
	
	public void cancelar(ActionEvent evt){
		try {
			osCancelar = ordemServicoDao.consultar(osCancelar.getId());
			osCancelar.cancelarOs();
			ordemServicoDao.alterar(osCancelar);
			listar(null);
		} catch (Exception e) {
			UtilFaces.addMensagemFaces(e);
		}
	}
   
	public void limpar(ActionEvent evt){
		ordemServico = new OrdemServico();
		itemServico = new ItemServico();
		itemEquipamento = new ItemEquipamento();
	}
	
   
//   public String abrirOrdemServico(){
//      cliente = ordemServico.getCliente();
//      cpfCnpjConsulta = cliente.getCpfCnpj();
//      return "ordemServico";
//   }
   
//   public void excluirItemEquipamento(ActionEvent evt){
//      ItemEquipamento itemEquipamentoExcluir = (ItemEquipamento) UtilFaces.getValorParametro(evt, "itemEquipamentoExcluir");
//      ordemServico.removeItemEquipamento(itemEquipamentoExcluir);
//   }
   
   
   public Equipamento consultarEquipamento(ActionEvent evt){
      try {

      } catch (Exception e) {
         UtilFaces.addMensagemFaces(e);
      }
      return equipamento;
   }

   /**
    * Retorna o valor total de equipamentos para o Serviço
    * 
    * @return valorTotal
    */
   public BigDecimal getValorTotalEquipamentos(){
      BigDecimal valorTotal = BigDecimal.ZERO;
      if(ordemServico.getItensEquipamentos() != null){
         for(ItemEquipamento ie : ordemServico.getItensEquipamentos()){
            valorTotal = valorTotal.add(ie.getValorTotal() != null ? ie.getValorTotal() : BigDecimal.ZERO);
         }
      }
      return valorTotal;
   }
   
   public List<SelectItem> getTiposOs(){
      return new ArrayList<SelectItem>(UtilFaces.getListEnum(EnumEstadoOrdemServico.values()));
   }
   
   /*
    * Getters And Setters
    */
   public void setOrdemServico(OrdemServico ordemServico) {
      try {
         this.ordemServico = ordemServicoDao.consultar(ordemServico.getId());
      } catch (Exception e) {
         UtilFaces.addMensagemFaces(e);
      }
   }

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getCpfCnpjConsulta() {
		return cpfCnpjConsulta;
	}

	public void setCpfCnpjConsulta(Long cpfCnpjConsulta) {
		this.cpfCnpjConsulta = cpfCnpjConsulta;
	}

	public List<OrdemServico> getOrdensServico() {
		return ordensServico;
	}

	public void setOrdensServico(List<OrdemServico> ordensServico) {
		this.ordensServico = ordensServico;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public ItemEquipamento getItemEquipamento() {
		return itemEquipamento;
	}

	public void setItemEquipamento(ItemEquipamento itemEquipamento) {
		this.itemEquipamento = itemEquipamento;
	}

	public Cliente getClienteList() {
		return clienteList;
	}

	public void setClienteList(Cliente clienteList) {
		this.clienteList = clienteList;
	}

	public EnumEstadoOrdemServico getEstadoOsSelecionado() {
		return estadoOsSelecionado;
	}

	public void setEstadoOsSelecionado(EnumEstadoOrdemServico estadoOsSelecionado) {
		this.estadoOsSelecionado = estadoOsSelecionado;
	}

	public String getCodigoEquipamento() {
		return codigoEquipamento;
	}

	public void setCodigoEquipamento(String codigoEquipamento) {
		this.codigoEquipamento = codigoEquipamento;
	}

	public Date getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(Date dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public Date getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(Date dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public Integer getIdOs() {
		return idOs;
	}

	public void setIdOs(Integer idOs) {
		this.idOs = idOs;
	}

	public OrdemServico getOrdemServico() {
		return ordemServico;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public OrdemServico getOsCancelar() {
		return osCancelar;
	}

	public void setOsCancelar(OrdemServico osCancelar) {
		this.osCancelar = osCancelar;
	}

   
}
