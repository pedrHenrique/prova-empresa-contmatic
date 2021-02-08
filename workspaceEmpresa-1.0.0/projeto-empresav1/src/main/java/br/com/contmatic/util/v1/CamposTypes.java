package br.com.contmatic.util.v1;

public final class CamposTypes {
	
	public static final int EMPRESA_RAZAO_SOCIAL_TAMANHO_MAX = 90;

	public static final int EMPRESA_RAZAO_SOCIAL_TAMANHO_MIN = 10;

	public static final int EMPRESA_NOME_FANTASIA_TAMANHO_MIN = 2;

	public static final int EMPRESA_NOME_FANTASIA_TAMANHO_MAX = 50;
	
	public static final String EMPRESA_CAMPO_RAZAOSOCIAL = "razaoSocial";

	public static final String EMPRESA_CAMPO_NOMEFANTASIA = "nomeFantasia";
	
	public static final int FUNCIONARIO_NOME_TAMANHO_MAX = 75;

	public static final int FUNCIONARIO_NOME_TAMANHO_MIN = 10;

	public static final double FUNCIONARIO_SALARIO_VALOR_MAX = 99999.99;

	public static final double FUNCIONARIO_SALARIO_VALOR_MIN = 0.0;
	
	public static final int CONTATO_EMAIL_TAMANHO_MAX = 55;

	public static final int CONTATO_EMAIL_TAMANHO_MIN = 5;

	public static final int DEPARTAMENTO_NOME_TAMANHO_MAX = 40;

	public static final int DEPARTAMENTO_NOME_TAMANHO_MIN = 2;

	public static final int DEPARTAMENTO_RAMAL_TAMANHO_MAX = 8;

	public static final int DEPARTAMENTO_RAMAL_TAMANHO_MIN = 3;

	public static final long DEPARTAMENTO_ID_TAMANHO_MAX = 2500L;

	public static final long DEPARTAMENTO_ID_TAMANHO_MIN = 0L;

	public static final int ENDERECO_TAMANHO_COMPLEMENTO = 30;

    public static final int ENDERECO_NUMERO_TAMANHO_MAX = 9999;

    public static final int ENDERECO_NUMERO_TAMANHO_MIN = 1;

	public static final int ENDERECO_TAMANHO_CEP = 8;

	public static final int ENDERECO_TAMANHO_MAX = 60;

	public static final int ENDERECO_TAMANHO_MIN = 3;
	
	public static final int CIDADE_NOME_TAMANHO_MIN = 0;

	public static final int CIDADE_NOME_TAMANHO_MAX = 65;

	public static final int ESTADO_TAMANHO_MIN = 2;

	public static final int ESTADO_TAMANHO_MAX = 50;
	
	public static final int TELEFONE_TAMANHO_FORMATACAO = 1;

	public static final int TELEFONE_TAMANHO_TELEFONE_MOVEL = 9;

	public static final int TELEFONE_TAMANHO_TELEFONE_FIXO = 8;
	
	public static final int TELEFONE_TAMANHO_TELEFONE_INTERNACIONAL_MIN = 4;
	
	public static final int TELEFONE_TAMANHO_TELEFONE_INTERNACIONAL_MAX = 15;

	public static final int TELEFONE_TAMANHO_DDD = 2;
	
	private CamposTypes() {
		
	}

}
