package br.com.ambientinformatica.sati.entidade;

import br.com.ambientinformatica.util.IEnum;

public enum EnumEstadoOrdemServico implements IEnum{
   
	EM_EDICAO ("Aberta - Em edição"),
   NOVA("Nova"),
   ATENDENDO("Atendendo"),
   ATENDIDA("Atendida"),
   CANCELADA("Cancelada");
   
   private String descricao;

   EnumEstadoOrdemServico(String descricao){
   	this.descricao = descricao;
   }
   
	@Override
   public String getDescricao() {
	   return descricao;
   }

}
