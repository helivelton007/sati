/**************************************************
 * Propriedade Intelectual da Ambient Informática 
 * (www.ambientinformatica.com.br). 
 * 
 * PROIBIDA A CÓPIA OU UTILIZAÇÃO POR TERCEIROS SEM
 * PRÉVIA AUTORIZAÇÃO.
 **************************************************
 */
package br.com.ambientinformatica.sati.entidade;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;

@Entity
public class ItemServico {

   @Id
   @GeneratedValue(strategy=SEQUENCE, generator = "geradorItemServico")
   @SequenceGenerator(name="geradorItemServico", sequenceName = "gerador_item_servico", allocationSize=1, initialValue=1)
   private int id;
   
   @ManyToOne
   private Servico servico;
   
   private Integer quantidade = 1;

   @Transient
   public BigDecimal getValorTotal(){
      return servico.getValor().multiply(BigDecimal.valueOf(quantidade));
   }
   
   
   @Override
   public int hashCode() {
	   final int prime = 31;
	   int result = 1;
	   result = prime * result + ((servico == null) ? 0 : servico.hashCode());
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
	   ItemServico other = (ItemServico) obj;
	   if (servico == null) {
		   if (other.servico != null)
			   return false;
	   } else if (!servico.equals(other.servico))
		   return false;
	   return true;
   }



	public Servico getServico() {
      return servico;
   }
   public void setServico(Servico servico) {
      this.servico = servico;
   }
   public Integer getQuantidade() {
      return quantidade;
   }
   public void setQuantidade(Integer quantidade) {
      this.quantidade = quantidade;
   }
   public int getId() {
      return id;
   }
}
