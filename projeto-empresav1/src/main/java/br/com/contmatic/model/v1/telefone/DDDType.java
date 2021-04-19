package br.com.contmatic.model.v1.telefone;

import br.com.contmatic.model.v1.endereco.EstadoType;

public enum DDDType {
	
	DDD11("11", "São Paulo", EstadoType.SP),
	DDD12("12", "São José dos Campos", EstadoType.SP),
	DDD13("13", "Santos", EstadoType.SP),
	DDD14("14", "Bauru", EstadoType.SP),
	DDD15("15", "Sorocaba", EstadoType.SP),
	DDD16("16", "Ribeirão Preto", EstadoType.SP),
	DDD17("17", "São José do Rio Preto", EstadoType.SP),
	DDD18("18", "Presidente Prudente", EstadoType.SP),
	DDD19("19", "Campinas", EstadoType.SP),
	DDD21("21", "Rio de Janeiro", EstadoType.RJ),
	DDD22("22", "Campos dos Goytacazes", EstadoType.RJ),
	DDD24("24", "Volta Redonda", EstadoType.RJ),
	DDD27("27", "Vila Velha/Vitória", EstadoType.ES),
	DDD28("28", "Cachoeiro de Itapemirim", EstadoType.ES),
	DDD31("31", "Belo Horizonte", EstadoType.MG),
	DDD32("32", "Juiz de Fora", EstadoType.MG),
	DDD33("34", "Governador Valadares", EstadoType.MG),
	DDD34("34", "Uberlândia", EstadoType.MG),
	DDD35("35", "Poços de Caldas", EstadoType.MG),
	DDD37("37", "Divinópolis", EstadoType.MG),
	DDD38("38", "Montes Claros", EstadoType.MG),
	DDD41("41", "Curitiba", EstadoType.PR),
	DDD42("42", "Ponta Grossa", EstadoType.PR),
	DDD43("43", "Londrina", EstadoType.PR),
	DDD44("44", "Maringá", EstadoType.PR),
	DDD45("45", "Foz do Iguaçú", EstadoType.PR),
	DDD46("46", "Francisco Beltrão/Pato Branco", EstadoType.PR),
	DDD47("47", "Joinville", EstadoType.SC),
	DDD48("48", "Florianópolis", EstadoType.SC),
	DDD49("49", "Chapecó", EstadoType.SC),
	DDD51("51", "Porto Alegre", EstadoType.RS),
	DDD53("53", "Pelotas", EstadoType.RS),
	DDD54("54", "Caxias do Sul", EstadoType.RS),
	DDD55("55", "Santa Maria", EstadoType.RS),
	DDD61("61", "Brasília", EstadoType.DF),
	DDD62("62", "Goiânia", EstadoType.GO),
	DDD63("63", "Palmas", EstadoType.TO),
	DDD64("64", "Rio Verde", EstadoType.GO),
	DDD65("65", "Cuiabá", EstadoType.MT),
	DDD66("66", "Rondonópolis", EstadoType.MT),
	DDD67("67", "Campo Grande", EstadoType.MS),
	DDD68("68", "Rio Branco", EstadoType.AC),
	DDD69("69", "Porto Velho", EstadoType.RO),
	DDD71("71", "Salvador", EstadoType.BA),
	DDD73("73", "Ilhéus", EstadoType.BA),
	DDD74("74", "Juazeiro", EstadoType.BA),
	DDD75("75", "Feira de Santana", EstadoType.BA),
	DDD77("77", "Barreiras", EstadoType.BA),
	DDD79("79", "Aracaju", EstadoType.SE),
	DDD81("81", "Recife", EstadoType.PE),
	DDD82("82", "Maceió", EstadoType.AL),
	DDD83("83", "João Pessoa", EstadoType.PB),
	DDD84("84", "Natal", EstadoType.RN),
	DDD85("85", "Fortaleza", EstadoType.CE),
	DDD86("86", "Teresina", EstadoType.PI),
	DDD87("87", "Petrolina", EstadoType.PE),
	DDD88("88", "Juazeiro do Norte", EstadoType.CE),
	DDD89("89", "Picos", EstadoType.PI),
	DDD91("91", "Belém", EstadoType.PA),
	DDD92("92", "Manaus", EstadoType.AM),
	DDD93("93", "Santarém", EstadoType.PA),
	DDD94("94", "Marabá", EstadoType.PA),
	DDD95("95", "Boa Vista", EstadoType.RR),
	DDD96("96", "Macapá", EstadoType.AP),
	DDD97("97", "Coari", EstadoType.AM),
	DDD98("98", "São Luís", EstadoType.MA),
	DDD99("99", "Imperatriz", EstadoType.MA);
	
	private String ddd;
	
	private String cidade;
	
	private EstadoType estado;

	private DDDType(String ddd, String cidade, EstadoType estado) {
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

	public EstadoType getEstado() {
		return estado;
	}
}
