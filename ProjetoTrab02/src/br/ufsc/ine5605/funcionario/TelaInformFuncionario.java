package br.ufsc.ine5605.funcionario;

import java.awt.Component;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.CloseAction;

import br.ufsc.ine5605.TelaComGridBagLayout;
import br.ufsc.ine5605.cargo.Cargo;
import br.ufsc.ine5605.cargo.ControladorCargo;
import br.ufsc.ine5605.cargo.DadosCadastroCargo;
import br.ufsc.ine5605.horario.Horario;

public class TelaInformFuncionario extends TelaComGridBagLayout {

	/*
	 * Labels descricao de campo
	 */

	private JLabel jlPedeNome;
	private JLabel jlPedeMatricula;
	private JLabel jlPedeTelefone;
	private JLabel jlPedeCargo;
	private JLabel jlPedeDataNasci;
	private JLabel jlPedeSalario;
	/*
	 * Metodo para inicializar as Labels de: Nome, Telefone, Cargo, DataNascimento, Salario
	 */
	public void incializaLabels() {
		jlPedeNome = new JLabel("Nome");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeNome, 0, 0, 1, 1);
		jlPedeCargo = new JLabel("Selecione o cargo");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeCargo, 0, 2, 1, 1);
		jlPedeTelefone = new JLabel("Telefone");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeTelefone, 0, 3, 1, 1);
		jlPedeDataNasci = new JLabel("Data Nascimento");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeDataNasci, 0, 4, 1, 1);
		jlPedeSalario = new JLabel("Salario");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeSalario, 0, 5, 1, 1);

	}
	/*
	 * Campos para inserir Nome, Matricula Telefone e Ano
	 */
	private JTextField tfEntraNome;
	private JTextField tfEntraMatricula;
	private JTextField tfEntraTelefone;
	private JTextField tfEntraSalario;
	
	public void inicializaTextFields(String tFnome, String tFtelefone, int tFsalario) {
		tfEntraNome = new JTextField(tFnome);
		tfEntraNome.selectAll();
		adicionaComponente(tfEntraNome, 1, 0, 2, 1);
		tfEntraTelefone = new JTextField(tFtelefone);
		tfEntraTelefone.selectAll();
		adicionaComponente(tfEntraTelefone, 1, 3, 2, 1);
		tfEntraSalario = new JTextField(tFsalario);
		tfEntraTelefone.selectAll();
		adicionaComponente(tfEntraSalario, 1, 5, 1, 1);

	}

	/*
	 * ComboBoxes Cargo, Dia,Mes e Ano
	 */
	private JComboBox cBCargos;
	private JComboBox<String> cBDia;
	private JComboBox<String> cBMes;
	private JComboBox<String> cBAno;

	public void inicializaCBs() {
		String[] diaString = new String[31];
		String[] mesString = new String[12];
		String[] anoString = new String[100];
		for (int a = 1; a <= 31; a++) {
			diaString[a-1] = a + "";
		}
		for (int b = 1; b < 13; b++) {
			mesString[b-1] = b + "";
		}
		for (int c = 0; c < 100; c++) {
			anoString[c] = 2017 - c + "";
		}
		cBDia = new JComboBox(diaString);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(cBDia, 1, 4, 1, 1);

		cBMes = new JComboBox(mesString);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(cBMes, 2, 4, 1, 1);

		cBAno = new JComboBox(anoString);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(cBAno, 3, 4, 1, 1);

		cBCargos = new JComboBox<Cargo>();
		for (Cargo c : ControladorCargo.getInstance().getListaCargos()) {
			cBCargos.addItem(c);
		}
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(cBCargos, 1, 2, 1, 1);

	}

	/*
	 * Botao cadastrar
	 */
	private JButton btCadastrar;
	private JButton btEditar;

	int matricula;
	String nome;
	Cargo cargo;
	String telefone;
	String dataNascimento;
	int salario;

	/*
	 * Construtor sem parametros, tela de inclusao de funcionario
	 */
	public TelaInformFuncionario() {
		super("Cadastro de Funcionarios");
		// configuracao de janela e inicializacao
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 250);
		setLocationRelativeTo(null);
		ButtonActionListener btListener = new ButtonActionListener();
		inicializaCBs();
		inicializaTextFields("", "", 0);
		incializaLabels();
		tfEntraMatricula = new JTextField("");
		tfEntraMatricula.selectAll();
		jlPedeMatricula = new JLabel("Matricula");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeMatricula, 0, 1, 1, 1);
		adicionaComponente(tfEntraMatricula, 1, 1, 2, 1);
		btCadastrar = new JButton("Cadastrar");
		adicionaComponente(btCadastrar, 0, 6, 3, 1);

		btCadastrar.addActionListener(btListener);
	}

	/*
	 * Construtor com parametro Funcionario, tela de edicao de funcionarios
	 */
	public TelaInformFuncionario(Funcionario funcionario) {

		super("Editar funcionario selecionado");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setSize(400, 250);
		setLocationRelativeTo(null);
		ButtonActionListener btListener2 = new ButtonActionListener();
		inicializaCBs();
		inicializaTextFields(funcionario.getNome(), funcionario.getTelefone(), funcionario.getSalario());
		incializaLabels();

		btEditar = new JButton("Editar");
		adicionaComponente(btEditar, 0, 7, 1, 1);

		btEditar.addActionListener(btListener2);

	}

	public void erroMatEmUso() {
		JOptionPane.showMessageDialog(null, "Numero de matricula indisponivel", "favor digitar outro",
				JOptionPane.ERROR_MESSAGE);
	}

	private class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evento) {
			boolean abortaCadastro = false;
			boolean abortaEdicao = false;
			//Checa se a matricula e o salario sao Int e envia todos os dados como um DadosCadastroFuncionario para o controlador
			if (evento.getActionCommand().equals("Cadastrar")) {
				try {
					matricula = Integer.parseInt(tfEntraMatricula.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Digite apenas numeros para a matricula", "Erro de Dados",
							JOptionPane.ERROR_MESSAGE);
					abortaCadastro = true;
				}
				try {
					salario = Integer.parseInt(tfEntraSalario.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Digite apenas numeros para o salario", "Erro de Dados",
							JOptionPane.ERROR_MESSAGE);
					abortaCadastro = true;
				}

				nome = tfEntraNome.getText();

				Cargo cargoCb = (Cargo) cBCargos.getSelectedItem();

				int codigoE = cargoCb.getCodigo();
				Cargo cargoEscolhido = ControladorCargo.getInstance().findCargoByCodigo(codigoE);

				telefone = tfEntraTelefone.getText();
				String dataN = (String) (cBDia.getSelectedItem() + "/" + cBMes.getSelectedItem() + "/"
						+ cBAno.getSelectedItem());

				if ((!abortaCadastro)) {
					ControladorFuncionario.getInstance().novoIncluirFuncionario(
							new DadosCadastroFuncionario(matricula, nome, cargoEscolhido, telefone, dataN, salario));

				}
			}

			// inicio das operacoes de EDICAO
			 
			// checa se o salario e um Int e chama o metodo editarTodosOsDados, passando os dados digitados na tela como um DadosCadastroFuncionario
			if (evento.getActionCommand().equals("Editar")) {

				try {
					salario = Integer.parseInt(tfEntraSalario.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Digite apenas numeros para o salario", "Erro de Dados",
							JOptionPane.ERROR_MESSAGE);
					abortaEdicao = true;
				}

				if ((!abortaEdicao)) {

					nome = tfEntraNome.getText();
					Cargo cargoEditado = (Cargo) cBCargos.getSelectedItem();
					telefone = tfEntraTelefone.getText();
					dataNascimento = (String) cBDia.getSelectedItem() + "/" + cBMes.getSelectedItem() + "/"
							+ cBAno.getSelectedItem();

					ControladorFuncionario.getInstance().editarTodosOsDados(matricula, nome, cargoEditado, telefone,
							dataNascimento, salario);

				}

			}
		}
	}
	/*
	 * Envia mensagem "Funcionario cadastrado com sucesso"
	 */
	public void msgCadOK() {
		JOptionPane.showMessageDialog(null, "Funcionario cadastrado com sucesso");
		
	}
	/*
	 * Envia mensagem "Funcionario editado com sucesso"
	 */
	public void msgEditOk() {
		JOptionPane.showMessageDialog(null, "Funcionario editado com sucesso");
		
	}
}