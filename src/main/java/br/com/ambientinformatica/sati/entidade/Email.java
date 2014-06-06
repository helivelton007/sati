/**************************************************
 * Propriedade Intelectual da Ambient Informática 
 * (www.ambientinformatica.com.br). 
 * 
 * PROIBIDA A CÓPIA OU UTILIZAÇÃO POR TERCEIROS SEM
 * PRÉVIA AUTORIZAÇÃO.
 **************************************************
 */
package br.com.ambientinformatica.sati.entidade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Email {
 
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "geradorEmail")
	@SequenceGenerator(name="geradorEmail", sequenceName = "gerador_email", allocationSize=1, initialValue=1)
	private Integer id = 0;
	
	private String descricao;
	
	private boolean permiteEnvio = true;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isPermiteEnvio() {
		return permiteEnvio;
	}

	public void setPermiteEnvio(boolean permiteEnvio) {
		this.permiteEnvio = permiteEnvio;
	}

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
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
      Email other = (Email) obj;
      if (descricao == null) {
         if (other.descricao != null)
            return false;
      } else if (!descricao.equals(other.descricao))
         return false;
      if (id == null) {
         if (other.id != null)
            return false;
      } else if (!id.equals(other.id))
         return false;
      return true;
   }
	
	

	
}
 
