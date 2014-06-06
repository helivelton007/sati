package br.com.ambientinformatica.sati.entidade;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;


@Entity
public class Contato implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	
	@GeneratedValue(generator = "contato_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "contato_seq", sequenceName = "contato_seq", allocationSize = 1, initialValue = 1)
	
	private Integer id;
	private String nome;
	private String telefone;
	
	
	@Override
	public String toString() {
		return "Contato [id=" + id + ", nome=" + nome + ", telefone="
				+ telefone + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Contato other = (Contato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Integer getId() {
		return id;
	}


}
