package br.com.contmatic.empresa.v1.model.contato;

public enum TipoTelefone {

	CELULAR("Celular", 9),
	RESIDENCIAL("Fixo", 8);
	
    private String descricao;
    
    private int tamanho;

	TipoTelefone(String descricao, int tamanho) {
		this.descricao = descricao;
		this.tamanho = tamanho;
	}

	public String getDescricao() {
		return descricao;
	}

	public int getTamanho() {
		return tamanho;
	}
}