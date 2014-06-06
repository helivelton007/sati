package br.com.ambientinformatica.sati.entidade;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class OrdemServico {

	@Id
	@GeneratedValue(generator = "ordemServico_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ordemServico_seq", sequenceName = "ordemServico_seq", allocationSize = 1, initialValue = 1)
	private long id;

	@ManyToOne
	private Cliente cliente;

	@ManyToOne
	private Cliente tecnico;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataEmissao = new Date();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataAbertura = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicioAtendimento = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFechamento = new Date();

	private String descricaoProblema;

	@ManyToMany(cascade = { CascadeType.ALL, CascadeType.REMOVE }, fetch = FetchType.LAZY)
	private Set<ItemServico> itensServicos = new HashSet<ItemServico>();

	@ManyToMany(cascade = { CascadeType.ALL, CascadeType.REMOVE })
	private List<ItemEquipamento> itensEquipamentos = new ArrayList<ItemEquipamento>();

	@Enumerated(EnumType.STRING)
	private EnumEstadoOrdemServico estado = EnumEstadoOrdemServico.EM_EDICAO;

	public void addItemEquipamento(ItemEquipamento itemEquipamento) {
		itensEquipamentos.add(itemEquipamento);
	}

	public List<ItemEquipamento> getItensEquipamentos() {
		return itensEquipamentos;
	}

	public void addItemServico(ItemServico item) {
		if(!itensServicos.contains(item)){
			itensServicos.add(item);
		}
	}

	public Set<ItemServico> getItensServico() {
		return itensServicos;
	}
	
	public List<ItemServico> getItensServicoList() {
		return new ArrayList<ItemServico>(itensServicos);
	}

	public void removeItemEquipamento(ItemEquipamento item){
      itensEquipamentos.remove(item);
   }
	
	public void removeItemServico(ItemServico item){
      itensServicos.remove(item);
   }
	
	public void cancelarOs(){
		estado = EnumEstadoOrdemServico.CANCELADA;
	}
	
	public void emitirOs(){
		dataEmissao = new Date();
		estado = EnumEstadoOrdemServico.NOVA;
	}
	
	public void atenderOrdemServico() {
		dataInicioAtendimento = new Date();
		estado = EnumEstadoOrdemServico.ATENDENDO;
	}

	public void fecharOrdemServico() {
		dataFechamento = new Date();
		estado = EnumEstadoOrdemServico.ATENDIDA;
	}

	public boolean isCancelada(){
		return estado == EnumEstadoOrdemServico.CANCELADA;
	}

	public  boolean isEditavel(){
		return estado == EnumEstadoOrdemServico.EM_EDICAO;
	}
	
	public boolean isImprimivel(){
		return estado == EnumEstadoOrdemServico.NOVA;
	}
	
	/*
	 * Equals e HashCode
	 */
	@Override
   public int hashCode() {
	   final int prime = 31;
	   int result = 1;
	   result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
	   result = prime * result + (int) (id ^ (id >>> 32));
	   return result;
   }

	@Override
   public boolean equals(Object obj) {
	   if (this == obj)
		   return true;
	   if (obj == null)
		   return false;
	   if (getClass() != obj.getClass())
		   return false;
	   OrdemServico other = (OrdemServico) obj;
	   if (cliente == null) {
		   if (other.cliente != null)
			   return false;
	   } else if (!cliente.equals(other.cliente))
		   return false;
	   if (id != other.id)
		   return false;
	   return true;
   }	
	/*
	 * Getters and Setters
	 */
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getTecnico() {
		return tecnico;
	}

	public void setTecnico(Cliente tecnico) {
		this.tecnico = tecnico;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public Date getDataInicioAtendimento() {
		return dataInicioAtendimento;
	}

	public Date getDataFechamento() {
		return dataFechamento;
	}

	public String getDescricaoProblema() {
		return descricaoProblema;
	}

	public void setDescricaoProblema(String descricaoProblema) {
		this.descricaoProblema = descricaoProblema;
	}

	public long getId() {
		return id;
	}

	public EnumEstadoOrdemServico getEstado() {
		return estado;
	}

	
	
}
