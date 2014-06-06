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
public class ItemEquipamento {
	// comentario
	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = "geradorItemEquipamento")
	@SequenceGenerator(name = "geradorItemEquipamento", sequenceName = "gerador_item_equipamento", allocationSize = 1, initialValue = 1)
	private int id;

	@ManyToOne
	private Equipamento equipamento;

	private Integer quantidade = 1;

	@Transient
	public BigDecimal getValorTotal() {
		return equipamento.getValor().multiply(BigDecimal.valueOf(quantidade));
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}


	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
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
