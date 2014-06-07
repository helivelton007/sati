package br.com.ambientinformatica.sati.entidade;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import br.com.ambientinformatica.corporativo.entidade.Cep;


@Entity
public class Cliente {

	@Id
	@GeneratedValue(generator = "cliente_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "cliente_seq", sequenceName = "cliente_seq", allocationSize = 1, initialValue = 1)
	private long id;

	private String nome;
	private String celular;
	private String telefone;
	private String email;
	private String endereco;
	private String cidade;
	
	@ManyToOne
	private Cep cep;
	
	
	public void setCep(Cep cep) {
		this.cep = cep;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public void setEnderecos(Set<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public void setTelefones(Set<Telefone> telefones) {
		this.telefones = telefones;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	public void setDataCadastramento(Date dataCadastramento) {
		this.dataCadastramento = dataCadastramento;
	}

	public void setEnderecoAtivo(Endereco enderecoAtivo) {
		this.enderecoAtivo = enderecoAtivo;
	}

	private String bairro;
	
	private String razaoSocial;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_cliente")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<Endereco> enderecos = new HashSet<Endereco>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_cliente")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Set<Telefone> telefones = new HashSet<Telefone>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_pessoa")
	@Cascade(org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private List<Email> emails = new ArrayList<Email>();

	private long cpfCnpj;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastramento = new Date();

	@Transient
	private Endereco enderecoAtivo;

	@Transient
	public String getLogradouro() {
		if (getEnderecoAtivo() != null) {
			return getEnderecoAtivo().getLogradouroComplemento().toUpperCase();
		} else {
			return "";
		}
	}

	@Transient
	public String getMunicipio() {
		if (getEnderecoAtivo() != null) {
			return getEnderecoAtivo().getMunicipio().getDescricao()
					.toUpperCase();
		} else {
			return "";
		}
	}

	@Transient
	public String getUnidadeFederativa() {
		if (getEnderecoAtivo() != null) {
			return getEnderecoAtivo().getMunicipio().getUf().toString();
		} else {
			return "";
		}
	}

	@Transient
	public String getBairro() {
		if (getEnderecoAtivo() != null) {
			return getEnderecoAtivo().getBairro().toUpperCase();
		} else {
			return "";
		}
	}

	@Transient
	public String getCep() {
		if (getEnderecoAtivo() != null) {
			return getEnderecoAtivo().getCep();
		} else {
			return "";
		}
	}

	@Transient
	public String getEnderecoTexto() {
		if (getEnderecoAtivo() != null) {
			return getEnderecoAtivo().getEndereco().toUpperCase();
		} else {
			return "";
		}
	}

	@Transient
	public String getEnderecoEtiquetaTexto() {
		if (enderecos.size() > 0) {
			return getEnderecosLista().get(0).getEnderecoEtiqueta();
		} else {
			return "";
		}
	}

	/**
	 * Retorna o telefone comercial do cliente. Se este não existir, retorna o
	 * primeiro telefone da lista encontrado.
	 * 
	 * @return
	 */
	@Transient
	public String getTelefonePrincipalTexto() {
		Telefone telefone = null;
		for (Telefone tel : telefones) {
			telefone = tel;
			if (tel.getTipo() == EnumTipoTelefone.COMERCIAL) {
				break;
			}
		}
		if (telefone != null) {
			return telefone.getNumero();
		} else {
			return "";
		}
	}

	/**
	 * retorna uma string com todos os telefones separados por "|"
	 * 
	 * @return
	 */
	@Transient
	public String getTelefoneTexto() {
		String listaTelefoneTexto = "";
		for (int i = 0; i < telefones.size(); i++) {
			if (i == 0) {
				listaTelefoneTexto += telefones.iterator().next().getNumero();
			} else {
				listaTelefoneTexto += " | "
						+ telefones.iterator().next().getNumero();
			}
		}
		return listaTelefoneTexto;
	}

	/**
	 * retorna uma string com todos os emails separados por "|"
	 * 
	 * @return
	 */
	@Transient
	public String getEmailTexto() {
		String listaDeEmailTexto = "";
		for (int i = 0; i < emails.size(); i++) {
			if (i == 0) {
				listaDeEmailTexto = emails.get(i).getDescricao();
			} else {
				listaDeEmailTexto += " | " + emails.get(i).getDescricao();
			}

		}
		return listaDeEmailTexto;
	}

	public void removeTelefone(Telefone telefone) {
		telefones.remove(telefone);
	}

	public void removeEndereco(Endereco endereco) {
		enderecos.remove(endereco);
	}

	public void removeEmail(Email email) {
		emails.remove(email);
	}

	public void addTelefone(Telefone tel) {
		telefones.add(tel);
	}

	public void addEndereco(Endereco end) {
		enderecos.add(end);
	}

	public void addEmail(Email email) {
		emails.add(email);
	}

	public Set<Endereco> getEnderecos() {
		return Collections.unmodifiableSet(enderecos);
	}

	public List<Endereco> getEnderecosLista() {
		List<Endereco> lista = new ArrayList<Endereco>(enderecos);
		Collections.sort(lista);
		return lista;
	}

	@Transient
	public Endereco getEnderecoAtivo() {
		if (enderecoAtivo == null) {
			enderecoAtivo = getEnderecoCompleto();
		}
		return enderecoAtivo;
	}

	@Transient
	public Endereco getEnderecoCompleto() {
		for (Endereco end : getEnderecos()) {
			boolean logradouroPreenc = end.getLogradouro() != null
					&& end.getLogradouro().length() > 0;
			boolean complementoPreenc = end.getComplemento() != null
					&& end.getComplemento().length() > 0;
			boolean numeroPreenc = end.getNumero() != null
					&& end.getNumero().length() > 0;
			boolean bairroPreenc = end.getBairro() != null
					&& end.getBairro().length() > 0;
			boolean cepPreenc = end.getCep() != null
					&& end.getCep().length() > 0;
			boolean municipioPreenc = end.getMunicipio() != null;

			if (logradouroPreenc && complementoPreenc && numeroPreenc
					&& bairroPreenc && cepPreenc && municipioPreenc
					&& end.isAtivo()) {
				return end;
			}

		}
		return null;

	}

	public Set<Telefone> getTelefones() {
		return Collections.unmodifiableSet(telefones);
	}

	public List<Telefone> getListaTelefones() {
		return new ArrayList<Telefone>(telefones);
	}

	public List<Email> getEmails() {
		return Collections.unmodifiableList(emails);
	}

	public long getId() {
		return id;
	}

	public Date getDataCadastramento() {
		return dataCadastramento;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public long getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(long cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (cpfCnpj ^ (cpfCnpj >>> 32));
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
		Cliente other = (Cliente) obj;
		if (cpfCnpj != other.cpfCnpj)
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", razaoSocial="
				+ razaoSocial + ", cpfCnpj=" + cpfCnpj + "]";
	}

	
	
}
