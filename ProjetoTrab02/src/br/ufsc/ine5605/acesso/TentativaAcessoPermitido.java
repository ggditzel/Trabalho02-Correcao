package br.ufsc.ine5605.acesso;

import br.ufsc.ine5605.horario.Hora;

public class TentativaAcessoPermitido extends TentativaAcesso {

	public TentativaAcessoPermitido(String data, Hora hora, int matricula, int id) {
		super(data, hora, matricula, id);
	}
	@Override
	public String toString(){
		return "" + matricula + " acessou a sala no dia " + data + " as " + hora;
	}
	
}
