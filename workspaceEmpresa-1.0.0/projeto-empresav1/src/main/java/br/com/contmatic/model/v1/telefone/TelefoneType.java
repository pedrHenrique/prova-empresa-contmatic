package br.com.contmatic.model.v1.telefone;

public enum TelefoneType {

	CELULAR_NACIONAL("Celular", 9),
	RESIDENCIAL_NACIONAL("Fixo", 8),
	INTERNACIONAL("Internacional");
	
    private String descricao;
    
    private int tamanho;

	TelefoneType(String descricao, int tamanho) {
		this.descricao = descricao;
		this.tamanho = tamanho;
	}
	
	TelefoneType(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public int getTamanho() {
		return tamanho;
	}
}