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

	private JLabel jlMsg;
	private JLabel jlPedeNome;
	private JLabel jlPedeMatricula;
	private JLabel jlPedeTelefone;
	private JLabel jlPedeDataNasci;
	private JLabel jlPedeSalario;
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
	private JTextField tfEntraSalario;
	/*
	 * ComboBoxes Cargo, Dia,Mes e Ano
	 */
	private JComboBox cBCargos;
	private JComboBox<String> cBDia;
	private JComboBox<String> cBMes;
	private JComboBox<String> cBAno;
	private JComboBox<String> cBDiaa;
	private JComboBox<String> cBMess;
	private JComboBox<String> cBAnoo;
	/*
	 * Botao cadastrar
	 */
	private JButton btCadastrar;
	private JButton btEditar;
	private JButton btCancelar;

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
		setAlwaysOnTop(true);
		ButtonActionListener btListener = new ButtonActionListener();

		/*
		 * Label Nome, x=0 y=0 largura e altura=1 TextField Nome x=1 y=0 largura=2
		 * altura=1
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
		adicionaComponente(jlDescMatricula, 2, 1, 1, 1);
		/*
		 * 
		 * x=0 y=2 ComboBoc de cargos FALTA TESTAR
		 */

		cBCargos = new JComboBox<Cargo>();

		for (Cargo c : ControladorCargo.getInstance().getListaCargos()) {
			cBCargos.addItem(c);
		}

		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(cBCargos, 1, 2, 1, 1);
		/*
		 * x=0 y=3 Label de Telefone x=1 y=3 TextField de Telefone
		 */
		jlPedeTelefone = new JLabel("Telefone");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeTelefone, 0, 3, 1, 1);

		tfEntraTelefone = new JTextField("");
		tfEntraTelefone.selectAll();
		adicionaComponente(tfEntraTelefone, 1, 3, 2, 1);

		jlPedeSalario = new JLabel("Salario");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeSalario, 0, 5, 1, 1);

		tfEntraSalario = new JTextField("");
		tfEntraTelefone.selectAll();
		adicionaComponente(tfEntraSalario, 1, 5, 1, 1);

		/*
		 * x=0 y=4 Label de dataNasci x=1 y=4 ComboBox de Dia x=2 y=4 ComboBox de Mes
		 * x=3 y=4 ComboBox de Ano
		 */
		String[] diaString = new String[31];
		String[] mesString = new String[12];
		String[] anoString = new String[100];
		for (int a = 0; a < 31; a++) {
			diaString[a] = a + a + "";
		}
		for (int b = 0; b < 12; b++) {
			mesString[b] = b + "";
		}
		for (int c = 0; c < 100; c++) {
			anoString[c] = 2017 - c + "";
		}
		jlPedeDataNasci = new JLabel("Data Nascimento");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeDataNasci, 0, 4, 1, 1);

		 cBDiaa = new JComboBox(diaString);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(cBDiaa, 1, 4, 1, 1);

		 cBMess = new JComboBox(mesString);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(cBMess, 2, 4, 1, 1);

		cBAnoo = new JComboBox(anoString);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(cBAnoo, 3, 4, 1, 1);

		btCadastrar = new JButton("Cadastrar");
		adicionaComponente(btCadastrar, 0, 6, 3, 1);

		btCadastrar.addActionListener(btListener);
	}

	/*
	 * Construtor com parametro Funcionario, tela de edicao de funcionarios
	 */
	public TelaInformFuncionario(Funcionario funcionario) {

		super("Editar funcionario selecionado");

		nome = funcionario.getNome();
		String matriculaa = funcionario.getMatricula() + "";
		telefone = funcionario.getTelefone();
		String salario = funcionario.getSalario() + "";

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent wev) {

				JOptionPane.showMessageDialog(null, "Nao foi possivel fechar a janela", "Clique em Editar ou Cancelar",
						JOptionPane.ERROR_MESSAGE);
			}

		});

		setSize(400, 250);
		setAlwaysOnTop(true);
		ButtonActionListener btListener = new ButtonActionListener();

		/*
		 * Label Nome, x=0 y=0 largura e altura=1 TextField Nome x=1 y=0 largura=2
		 * altura=1
		 */
		jlPedeNome = new JLabel("Edite o nome ");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeNome, 0, 0, 1, 1);

		tfEntraNome = new JTextField(nome);
		tfEntraNome.selectAll();
		adicionaComponente(tfEntraNome, 1, 0, 2, 1);
		/*
		 * Labels de Matricula(x=0 y=1, x=2 y=1) e TextField(x=1 y=1)
		 */
		jlPedeMatricula = new JLabel("Edite a matricula ");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeMatricula, 0, 1, 1, 1);

		tfEntraMatricula = new JTextField(matriculaa);
		tfEntraMatricula.selectAll();
		adicionaComponente(tfEntraMatricula, 1, 1, 2, 1);

		jlDescMatricula = new JLabel("Apenas numeros");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlDescMatricula, 2, 1, 1, 1);
		/*
		 * 
		 * x=0 y=2 ComboBoc de cargos FALTA TESTAR
		 */

		cBCargos = new JComboBox<Cargo>();
		cBCargos.addItem(ControladorCargo.getInstance().getListaCargos());
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(cBCargos, 0, 2, 1, 1);

		/*
		 * x=0 y=3 Label de Telefone x=1 y=3 TextField de Telefone
		 */
		jlPedeTelefone = new JLabel("edite o telefone");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeTelefone, 0, 3, 1, 1);

		tfEntraTelefone = new JTextField(telefone);
		tfEntraTelefone.selectAll();
		adicionaComponente(tfEntraTelefone, 1, 3, 2, 1);

		jlPedeSalario = new JLabel("Edite o salario");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeSalario, 0, 5, 1, 1);

		tfEntraSalario = new JTextField(salario);
		tfEntraTelefone.selectAll();
		adicionaComponente(tfEntraSalario, 1, 5, 1, 1);

		/*
		 * x=0 y=4 Label de dataNasci x=1 y=4 ComboBox de Dia x=2 y=4 ComboBox de Mes
		 * x=3 y=4 ComboBox de Ano
		 */
		int[] diaString = new int[31];
		String[] mesString = new String[12];
		String[] anoString = new String[100];
		for (int a = 0; a < 31; a++) {
			diaString[a] = a ;
		}
		for (int b = 0; b < 12; b++) {
			mesString[b] = b + "";
		}
		for (int c = 0; c < 100; c++) {
			anoString[c] = 2017 - c + "";
		}
		jlPedeDataNasci = new JLabel("Data Nascimento");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeDataNasci, 0, 4, 1, 1);

		JComboBox cBDia = new JComboBox();
		cBDia.addItem(diaString);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(cBDia, 1, 4, 1, 1);

		JComboBox cBMes = new JComboBox();
		cBMes.addItem(mesString);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(cBMes, 2, 4, 1, 1);

		JComboBox cBAno = new JComboBox();
		cBAno.addItem(anoString);
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(cBAno, 3, 4, 1, 1);

		btEditar = new JButton("Editar");
		adicionaComponente(btCadastrar, 0, 6, 1, 1);

		btCancelar = new JButton("CANCELAR");
		adicionaComponente(btCancelar, 1, 6, 1, 1);

		btEditar.addActionListener(btListener);

	}

	private class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evento) {
			boolean abortaCadastro = false;
			boolean abortaEdicao = false;

			if (evento.getSource() == btCadastrar) {
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
				}
				nome = tfEntraNome.getText();

				Cargo cargoCb = (Cargo) cBCargos.getSelectedItem();

				int codigoE = cargoCb.getCodigo();
				Cargo cargoEscolhido = ControladorCargo.getInstance().findCargoByCodigo(codigoE);

				telefone = tfEntraTelefone.getText();
				String dataN =  (String) (cBDiaa.getSelectedItem() + "/" + cBMess.getSelectedItem() + "/" +cBAnoo.getSelectedItem());

				if ((!abortaCadastro)) {
					ControladorFuncionario.getInstance().novoIncluirFuncionario(new DadosCadastroFuncionario(matricula,
							nome, cargoEscolhido, telefone, dataN, salario));
					
				}
				
				
				
				/*
				 * inicio das operacoes de EDICAO
				 */
				
				
				
				
				

				if (evento.getSource() == btEditar) {
					try {
						matricula = Integer.parseInt(tfEntraMatricula.getText());
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Digite apenas numeros para a matricula", "Erro de Dados",
								JOptionPane.ERROR_MESSAGE);
						abortaEdicao = true;
					}
					try {
						salario = Integer.parseInt(tfEntraSalario.getText());
					} catch (NumberFormatException e) {
						JOptionPane.showMessageDialog(null, "Digite apenas numeros para o salario", "Erro de Dados",
								JOptionPane.ERROR_MESSAGE);
						abortaEdicao = true;
					}

					if ((!abortaEdicao)
							&& (ControladorFuncionario.getInstance().findFuncionarioByMatricula(matricula) != null)) {
						JOptionPane.showMessageDialog(null, "Numero de matricula indisponivel", "favor digitar outro",
								JOptionPane.ERROR_MESSAGE);

					}

					nome = tfEntraNome.getText();
					Cargo cargoEditado = (Cargo) cBCargos.getSelectedItem();
					telefone = tfEntraTelefone.getText();
					dataNascimento = cBDia + "/" + cBMes + "/" + cBAno;

					if ((!abortaEdicao)
							&& (ControladorFuncionario.getInstance().findFuncionarioByMatricula(matricula) == null)) {

						ControladorFuncionario.getInstance().editarTodosOsDados(matricula, nome, cargoEditado, telefone,
								dataNascimento, salario);
						JOptionPane.showMessageDialog(null, "Funcionario editado com sucesso");

					}
					if (evento.getSource() == btCancelar) {
						ControladorFuncionario.getInstance().cancelouEdicao();
						dispose();
						JOptionPane.showMessageDialog(null, "Edicao cancelada");

					}
				}
			}
		}
	}

