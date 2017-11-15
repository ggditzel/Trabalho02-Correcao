package br.ufsc.ine5605;

public class HorarioRepetidoException extends Exception{
	public HorarioRepetidoException() {
		super("O cargo ja possui este horario de acesso.");
	}
}
