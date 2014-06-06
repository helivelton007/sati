package br.com.ambientinformatica.sati.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

import br.com.ambientinformatica.corporativo.entidade.Cep;
import br.com.ambientinformatica.corporativo.entidade.Municipio;

@Entity
public class Endereco implements Comparable<Endereco>{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "geradorEndereco")
	@SequenceGenerator(name = "geradorEndereco", sequenceName = "gerador_endereco", initialValue = 1, allocationSize = 1)
	private Integer id;

	private String logradouro;
	
	private String complemento;
	
	private String numero;

	private String bairro;

	private String cep;

	private boolean ativo = true;
	
	@ManyToOne
	private Municipio municipio;

   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((id == null) ? super.hashCode() : id.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if(obj != null && obj.getClass().equals(getClass())){
         return obj.hashCode() == hashCode();
      }else{
         return false;
      }
   }

   /**
	 * Retorna uma String contendo o endereco completo
	 * formatado para a Etiqueta de Cliente
	 * @return
	 */
	@Transient
	public String getEnderecoEtiqueta(){
	   String m = "";
	   String n = "";
	   if(municipio != null){
	      m = municipio.getDescricao() + " - " + municipio.getUf();
	   }
	   if(numero.equals(null) || numero.equals("")){
	      n = "SN";
	   }else{
	      n = numero;
	   }
	   String s = getLogradouroComplemento() + ", ";
	   s += n;
	   s += ", " + getBairro();
	   s += "\n CEP: " + getCep();
	   s += " - " + m;
	   return s.toUpperCase();
	}

	/**
	 * Retorna uma String contendo o endereco completo 
	 * 
	 * @return
	 */
	@Transient
	public String getEndereco(){
	   String munTxt = "";
	   if(municipio != null){
	      munTxt = municipio.getDescricao() + " - " + municipio.getUf();
	   }
	   String endTxt = String.format("%s %s %s CEP: %s - %s", getLogradouroComplemento(), getNumero(), getBairro(), getCep(), munTxt);
	   return endTxt.toUpperCase();
	}
	
	
	/**
	 * Retorna apenas o logradouro e o complemento do endereco
	 * @return
	 */
   @Transient
	public String getLogradouroComplemento(){
	   return getLogradouro() + " "+ getComplemento();
	}

   public Integer getId() {
		return id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	public void setObjetoCep(Cep cep){
	   this.bairro = cep.getBairro();
	   this.municipio = cep.getMunicipio();
	   this.cep = cep.getCep();
	   this.logradouro = cep.getLogradouro();
	}

	public Municipio getMunicipio() {
		return municipio;
	}

	public void setMunicipio(Municipio municipio) {
		this.municipio = municipio;
	}

   public String getComplemento() {
      return complemento;
   }

   public void setComplemento(String complemento) {
      this.complemento = complemento;
   }

   public String getNumero() {
      return numero;
   }

   public void setNumero(String numero) {
      this.numero = numero;
   }

   @Override
   public int compareTo(Endereco o) {
      if(o != null && o.getId() != null && getId() != null){
         return getId() - o.getId(); 
      }else{
         return 0;
      }
   }

   public boolean isAtivo() {
      return ativo;
   }

   public void setAtivo(boolean ativo) {
      this.ativo = ativo;
   }

}