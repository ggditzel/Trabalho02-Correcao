package br.ufsc.ine5605.funcionario;


import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import br.ufsc.ine5605.TelaComGridBagLayout;
import br.ufsc.ine5605.cargo.Cargo;
import br.ufsc.ine5605.cargo.ControladorCargo;
import br.ufsc.ine5605.cargo.DadosCadastroCargo;
import br.ufsc.ine5605.horario.Horario;


public class TelaCadastroFuncionario extends TelaComGridBagLayout {
	
	/*
	 *  Labels descricao de campo
	 */
	 
	private JLabel jlMsg;
	private JLabel jlPedeNome;
	private JLabel jlPedeCargo;
	private JLabel jlPedeMatricula;
	private JLabel jlPedeTelefone;
	private JLabel jlPedeDataNasci;
	/*
	 * Labels descricao regra de campo
	 */
	private JLabel jlDescMatricula;
	/*
	 * Campos para inserir Nome, Matricula Telefone e Ano
	 */
	private JTextField tfEntraNome;
	private JTextField tfEntraMatricula;
	private JTextField tfEntraTelefone;
	private JTextField tfEntraAno;
	/*
	 * ComboBoxes Cargo, Dia e Mes
	 */
	private JComboBox grupoRbCargos;
	private JComboBox grupoRbDia;
	private JComboBox grupoRbMes;
	/*
	 * Botao cadastrar
	 */
	private JButton btCadastrar;
	
	int matricula = -1;
	String nome;
	Cargo cargo;
	String telefone;
	String dataNascimento;
	int salario;
	
	public TelaCadastroFuncionario() {
		super("Cadastro de Funcionarios");
		
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 250);
		setAlwaysOnTop(true);
		ButtonActionListener btListener = new ButtonActionListener();
		
		/*
		 * Label Nome, x=0 y=0 largura e altura=1
		 * TextField Nome x=1 y=0 largura=2 altura=1
		 */
		jlPedeNome = new JLabel("Nome do Funcionario");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeNome, 0, 0, 1, 1);
		
		tfEntraNome = new JTextField("");
		tfEntraNome.selectAll();
		adicionaComponente(tfEntraNome, 1, 0, 2, 1);
		/*
		 * Labels de Matricula(x=0 y=1, x=2 y=1) e TextField(x=1 y=1) 
		 */
		jlPedeMatricula = new JLabel("Matricula");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeMatricula, 0, 1, 1, 1);
		
		tfEntraMatricula = new JTextField("");
		tfEntraMatricula.selectAll();
		adicionaComponente(tfEntraMatricula, 1, 1, 2, 1);
		
		jlDescMatricula = new JLabel("Apenas numeros");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlDescMatricula, 2 , 1, 1, 1);
		/*
		 * FALTA TUDO, NAO SEI FAZE
		 */
		jlPedeCargo = new JLabel("Cargo");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlDescMatricula, 0, 2, 1, 1);
		/*
		 * Telefone
		 */
		jlPedeTelefone = new JLabel("Telefone");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeTelefone, 0, 3, 1, 1);
		
		tfEntraTelefone = new JTextField("");
		tfEntraTelefone.selectAll();
		adicionaComponente(tfEntraTelefone, 1, 3, 2, 1);
		
		
		btCadastrar = new JButton("Cadastrar");
		adicionaComponente(btCadastrar, 0, 4, 3, 1);
		
		btCadastrar.addActionListener(btListener);
	}
	private class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evento) {
			boolean abortaCadastro = false;

			if (evento.getSource() == btCadastrar) {
				try {
					matricula = Integer.parseInt(tfEntraMatricula.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Digite apenas numeros para a matricula", "Erro de Dados", JOptionPane.ERROR_MESSAGE);
					abortaCadastro = true;
				}
				
				nome = tfEntraNome.getText();
				
				if ((!abortaCadastro) && (ControladorFuncionario.getInstance().findFuncionarioByMatricula(matricula) != null)) {
					JOptionPane.showMessageDialog(null, "Numero de matricula indisponivel", "favor digitar outro", JOptionPane.ERROR_MESSAGE);
					
					
				}
				
				
				if ((!abortaCadastro) && (ControladorFuncionario.getInstance().findFuncionarioByMatricula(matricula) != null)){
					ControladorFuncionario.getInstance().novoIncluirFuncionario(new DadosCadastroFuncionario(matricula, nome, cargo, telefone, dataNascimento, salario));
					JOptionPane.showMessageDialog(null, "Funcionario cadastrado com sucesso");
				}
			}
		}
	}
}
	

