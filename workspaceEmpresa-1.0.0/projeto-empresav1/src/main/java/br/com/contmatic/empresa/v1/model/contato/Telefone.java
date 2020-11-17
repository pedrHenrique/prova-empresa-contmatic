package br.com.contmatic.empresa.v1.model.contato;

import java.util.regex.Pattern;

import br.com.contmatic.empresa.v1.util.RegexModel;

public class Telefone {
	
	private String numeroTelefone;
	
	/** O ddd - Setado no setNumeroTelefone */
	private TipoDDD ddd;
	
	/** O tipo - setado automaticamente se passado como nulo. */
	private TipoTelefone tipoTelefone;
	
	public Telefone(String numeroTelefone, TipoTelefone tipo) {
		setNumeroTelefone(numeroTelefone);
		setDdd(numeroTelefone.replaceAll(RegexModel.FORMATATELEFONE, "").substring(0, 2));
		setTipoTelefone(tipo);
	}
	
	public Telefone(String numeroTelefone) {
		setNumeroTelefone(numeroTelefone);		
		setDdd(numeroTelefone.replaceAll(RegexModel.FORMATATELEFONE, "").substring(0, 2));
		setTipoTelefone(null);
	}

	public String getNumeroTelefone() {
		return numeroTelefone;
	}

	public void setNumeroTelefone(String telefone) {
		if (telefone == null) {
			throw new NullPointerException("Telefone não pode estar vazio"); 
		
		} else if (!Pattern.compile(RegexModel.TELEFONECELULAR).matcher(telefone).matches()) { 	
			throw new IllegalArgumentException("Este formato de telefone passado não pode ser aceito."
					+ "\nPor favor, insira algo como XX 12345678, (XX)91234-5678");	
			
		} else {
			this.numeroTelefone = formataTextoTelefone(telefone);	
		}
	}

	public TipoDDD getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		System.out.println("DDD informado foi:" + ddd);
		
		try {
			this.ddd = (Enum.valueOf(TipoDDD.class, "DDD" + ddd));
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("O DDD " + ddd + " não é válido.");
		}
	}

	public TipoTelefone getTipoTelefone() {
		return tipoTelefone;
	}

	public void setTipoTelefone(TipoTelefone tipo) {
		int tamanhoTel = getNumeroTelefone().replaceAll(RegexModel.FORMATATELEFONE, "").substring(2).length();
		System.out.println("Formatação do telefone no setTipoTelefone:" + getNumeroTelefone().replaceAll(RegexModel.FORMATATELEFONE, "").substring(2));
		System.out.println("Valor tamanho do tipo Comercial:" + TipoTelefone.COMERCIAL.getTamanho());
		
		if ((tamanhoTel == TipoTelefone.COMERCIAL.getTamanho() ||
			 tamanhoTel == TipoTelefone.COMERCIAL.getTamanho() - 1) && tipo == TipoTelefone.COMERCIAL) {
			this.tipoTelefone = tipo;			
		
		} else if (tamanhoTel == TipoTelefone.CELULAR.getTamanho() && (tipo == null || tipo == TipoTelefone.CELULAR)) {
			this.tipoTelefone = TipoTelefone.CELULAR;
			
		} else if (tamanhoTel == TipoTelefone.RESIDENCIAL.getTamanho() && (tipo == null || tipo == TipoTelefone.COMERCIAL)) {
			this.tipoTelefone = TipoTelefone.RESIDENCIAL;
			
		} else {
			throw new IllegalArgumentException("O Tipo de telefone inserido não condiz com o telefone de contato informado!");
		}
		
	}
	
	// Não sei onde propriamente inserir esses métodos auxiliares.
    public static String formataTextoTelefone(String telefone) {
        telefone = telefone.replaceAll("\\s", "");
    	// Não permite que os espaços em brancos atrapalhem a obtenção do tamanho real do telefone.

        switch (telefone.length()) { // Verifica se o contato já está com a sua formatação certa. Se não estiver, formata e devolve o contato, se estiver, só retorna o contato formatado
            case 10:
                return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 6) + "-" + telefone.substring(6);// fixo
            case 11:
                return "(" + telefone.substring(0, 2) + ")" + telefone.substring(2, 7) + "-" + telefone.substring(7); // celular
            default:
                return telefone; // Se cair aqui. O contato passado já está formatado.
        }
    }
	
	public static void main(String[] args) {
		Telefone tel = new Telefone("31 922273454", TipoTelefone.COMERCIAL);
		System.out.println(tel.toString());
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numeroTelefone == null) ? 0 : numeroTelefone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Telefone other = (Telefone) obj;
		if (numeroTelefone == null) {
			if (other.numeroTelefone != null) {
				return false;
			}
		} else if (!numeroTelefone.equals(other.numeroTelefone)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Telefone [numeroTelefone=" + numeroTelefone + ", ddd=" + ddd + ", tipoTelefone=" + tipoTelefone + "]";
	}
}
