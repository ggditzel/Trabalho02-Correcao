package br.ufsc.ine5605.funcionario;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JOptionPane;



public class MapeadorFuncionario {

	private HashMap<Integer, Funcionario> cacheFuncionarios = new HashMap<>();
	private final String nomeArquivo = "funcionarios.dsoo1";

	public MapeadorFuncionario() {
		load();
	}

	public void put(Funcionario funcionario) {
		cacheFuncionarios.put(funcionario.getMatricula(), funcionario);
		persist();
	}


	public Funcionario get(Integer matricula) {
		return cacheFuncionarios.get(matricula);
	}

	public Collection<Funcionario> getList() {
		return cacheFuncionarios.values();
	}

	public void remove(Funcionario funcionario) {
		cacheFuncionarios.remove(funcionario.getMatricula());
		persist();
	}

	public void load() {
		try {
			FileInputStream arquivo = new FileInputStream(nomeArquivo);
			ObjectInputStream leArquivoCargos = new ObjectInputStream(arquivo);
			this.cacheFuncionarios = (HashMap<Integer, Funcionario>) leArquivoCargos.readObject();
			arquivo.close();
			leArquivoCargos.close();
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "ClassNotFoundException", "Excecao", JOptionPane.ERROR_MESSAGE);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,
					String.format("Sera criado o arquivo \"%s\" para armazenar os funcionarios", nomeArquivo), "Aviso",
					JOptionPane.WARNING_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IOException", "Excecao", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void persist() {

		try {
			FileOutputStream arquivo = new FileOutputStream(nomeArquivo);
			ObjectOutputStream gravaArquivo = new ObjectOutputStream(arquivo);
			gravaArquivo.writeObject(cacheFuncionarios);

			arquivo.flush();
			gravaArquivo.flush();

			arquivo.close();
			gravaArquivo.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "FileNotFoundException", "Excecao", JOptionPane.ERROR_MESSAGE);
		}

		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "IOException", "Excecao", JOptionPane.ERROR_MESSAGE);
		}
	}

}
