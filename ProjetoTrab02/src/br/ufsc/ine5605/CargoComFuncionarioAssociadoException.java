package br.ufsc.ine5605;

public class CargoComFuncionarioAssociadoException extends Exception {
	public CargoComFuncionarioAssociadoException() {
		this("O cargo ja possui funcionarios associados, impossivel excluir");
	}
	
	public CargoComFuncionarioAssociadoException(String msg) {
		super(msg);
	}
}
