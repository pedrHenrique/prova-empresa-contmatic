package br.com.contmatic.model.v1.telefone;

import br.com.contmatic.model.v1.empresa.endereco.TipoEstado;

public enum TipoDDD {
	
	DDD11("11", "São Paulo", TipoEstado.SP),
	DDD12("12", "São José dos Campos", TipoEstado.SP),
	DDD13("13", "Santos", TipoEstado.SP),
	DDD14("14", "Bauru", TipoEstado.SP),
	DDD15("15", "Sorocaba", TipoEstado.SP),
	DDD16("16", "Ribeirão Preto", TipoEstado.SP),
	DDD17("17", "São José do Rio Preto", TipoEstado.SP),
	DDD18("18", "Presidente Prudente", TipoEstado.SP),
	DDD19("19", "Campinas", TipoEstado.SP),
	DDD21("21", "Rio de Janeiro", TipoEstado.RJ),
	DDD22("22", "Campos dos Goytacazes", TipoEstado.RJ),
	DDD24("24", "Volta Redonda", TipoEstado.RJ),
	DDD27("27", "Vila Velha/Vitória", TipoEstado.ES),
	DDD28("28", "Cachoeiro de Itapemirim", TipoEstado.ES),
	DDD31("31", "Belo Horizonte", TipoEstado.MG),
	DDD32("32", "Juiz de Fora", TipoEstado.MG),
	DDD33("34", "Governador Valadares", TipoEstado.MG),
	DDD34("34", "Uberlândia", TipoEstado.MG),
	DDD35("35", "Poços de Caldas", TipoEstado.MG),
	DDD37("37", "Divinópolis", TipoEstado.MG),
	DDD38("38", "Montes Claros", TipoEstado.MG),
	DDD41("41", "Curitiba", TipoEstado.PR),
	DDD42("42", "Ponta Grossa", TipoEstado.PR),
	DDD43("43", "Londrina", TipoEstado.PR),
	DDD44("44", "Maringá", TipoEstado.PR),
	DDD45("45", "Foz do Iguaçú", TipoEstado.PR),
	DDD46("46", "Francisco Beltrão/Pato Branco", TipoEstado.PR),
	DDD47("47", "Joinville", TipoEstado.SC),
	DDD48("48", "Florianópolis", TipoEstado.SC),
	DDD49("49", "Chapecó", TipoEstado.SC),
	DDD51("51", "Porto Alegre", TipoEstado.RS),
	DDD53("53", "Pelotas", TipoEstado.RS),
	DDD54("54", "Caxias do Sul", TipoEstado.RS),
	DDD55("55", "Santa Maria", TipoEstado.RS),
	DDD61("61", "Brasília", TipoEstado.DF),
	DDD62("62", "Goiânia", TipoEstado.GO),
	DDD63("63", "Palmas", TipoEstado.TO),
	DDD64("64", "Rio Verde", TipoEstado.GO),
	DDD65("65", "Cuiabá", TipoEstado.MT),
	DDD66("66", "Rondonópolis", TipoEstado.MT),
	DDD67("67", "Campo Grande", TipoEstado.MS),
	DDD68("68", "Rio Branco", TipoEstado.AC),
	DDD69("69", "Porto Velho", TipoEstado.RO),
	DDD71("71", "Salvador", TipoEstado.BA),
	DDD73("73", "Ilhéus", TipoEstado.BA),
	DDD74("74", "Juazeiro", TipoEstado.BA),
	DDD75("75", "Feira de Santana", TipoEstado.BA),
	DDD77("77", "Barreiras", TipoEstado.BA),
	DDD79("79", "Aracaju", TipoEstado.SE),
	DDD81("81", "Recife", TipoEstado.PE),
	DDD82("82", "Maceió", TipoEstado.AL),
	DDD83("83", "João Pessoa", TipoEstado.PB),
	DDD84("84", "Natal", TipoEstado.RN),
	DDD85("85", "Fortaleza", TipoEstado.CE),
	DDD86("86", "Teresina", TipoEstado.PI),
	DDD87("87", "Petrolina", TipoEstado.PE),
	DDD88("88", "Juazeiro do Norte", TipoEstado.CE),
	DDD89("89", "Picos", TipoEstado.PI),
	DDD91("91", "Belém", TipoEstado.PA),
	DDD92("92", "Manaus", TipoEstado.AM),
	DDD93("93", "Santarém", TipoEstado.PA),
	DDD94("94", "Marabá", TipoEstado.PA),
	DDD95("95", "Boa Vista", TipoEstado.RR),
	DDD96("96", "Macapá", TipoEstado.AP),
	DDD97("97", "Coari", TipoEstado.AM),
	DDD98("98", "São Luís", TipoEstado.MA),
	DDD99("99", "Imperatriz", TipoEstado.MA);
	
	private String ddd;
	
	private String cidade;
	
	private TipoEstado estado;

	private TipoDDD(String ddd, String cidade, TipoEstado estado) {
		this.ddd = ddd;
		this.cidade = cidade;
		this.estado = estado;
	}

	public String getDdd() {
		return ddd;
	}

	public String getCidade() {
		return cidade;
	}

	public TipoEstado getEstado() {
		return estado;
	}
}
