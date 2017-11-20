package br.ufsc.ine5605.acesso;

import java.io.Serializable;

import br.ufsc.ine5605.Mapeavel;
import br.ufsc.ine5605.funcionario.ControladorFuncionario;
import br.ufsc.ine5605.horario.Hora;

public abstract class TentativaAcesso implements Serializable, Mapeavel<Integer>{
	protected String data;
	protected Hora hora;
	protected int matricula;
        protected final String nome;
	protected Integer id;
	
	
	public TentativaAcesso(String data, Hora hora, int matricula, int id) {
		this.data = data;
		this.hora = hora;
		this.matricula = matricula;
		this.id = id;
                this.nome = ControladorFuncionario.getInstance().findFuncionarioByMatricula(matricula) == null ? "" : ControladorFuncionario.getInstance().findFuncionarioByMatricula(matricula).getNome();
	}
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Hora getHora() {
		return hora;
	}
	public void setHora(Hora hora) {
		this.hora = hora;
	}
	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}

	public Integer getID() {
		return id;
	}
	public void setID() {
		this.id = id;
	}
	public String getNome() {
            return nome;
        }
}
