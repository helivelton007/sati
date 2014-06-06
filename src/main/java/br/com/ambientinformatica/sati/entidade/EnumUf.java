package br.com.ambientinformatica.sati.entidade;

public enum EnumUf {
	AC(" Acre "), 
	AL(" Alagoas "), 
	AP(" Amapa "), 
	AM(" Amazonas "), 
	BA(" Bahia "), 
	CE(" Ceara "), 
	DF(" Distrito Federal "), 
	ES(" Espirito Santo "), 
	GO(" Goias "), 
	MA(" Maranhao "), 
	MT(" Mato Grosso "), 
	MS(" Mato Grosso do Sul "), 
	MG(" Minas Gerais "), 
	PA(" Para "), 
	PB(" Paraiba "), 
	PE(" Pernambuco "), 
	PR(" Parana "), 
	PI(" Piaui "), 
	RJ(" Rio De Janeiro "), 
	RN(" Rio Grande do Norte "), 
	RS(" Rio Grande do Sul "), 
	RO(" Rondonia "), 
	RR(" Roraima "), 
	SC(" Santa Catarina "), 
	SE(" Sergipe "), 
	SP(" Sao Paulo "), 
	TO(" Tocantins ");

	private String descricao;

	EnumUf(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
