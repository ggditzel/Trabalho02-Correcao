package br.ufsc.ine5605.funcionario;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.ufsc.ine5605.TelaComGridBagLayout;
import br.ufsc.ine5605.cargo.ControladorCargo;


public class TelaOperacoesFuncionarios extends TelaComGridBagLayout {

	private Collection<Funcionario> listaGeral;

	private JButton btCadastrar;
	private JButton btEditar;
	private JButton btExcluir;

	private JTable tabelaFunc;
	private JScrollPane spTab;
	private JLabel lbNomeTab;

	public TelaOperacoesFuncionarios() {

		super("Operacoes com Funcionarios");
		setSize(700, 250);
		setLocationRelativeTo(null);
		ButtonActionListener btListener = new ButtonActionListener();

		lbNomeTab = new JLabel("Funcionarios cadastrados");

		// ButtonActionListener btListener = new ButtonActionListener();
		btCadastrar = new JButton("Cadastrar");
		// btCadastrar.addActionListener(btListener);

		btEditar = new JButton("Editar");
		// btEditar.addActionListener(btListener);

		btExcluir = new JButton("Excluir");
		// btExcluir.addActionListener(btListener);

		tabelaFunc = new JTable();
		tabelaFunc.setPreferredScrollableViewportSize(new Dimension(600, 60));
		tabelaFunc.setFillsViewportHeight(true);
		spTab = new JScrollPane(tabelaFunc);
		 atualizaTabela();

		adicionaComponente(lbNomeTab, 0, 0, 1, 1);
		adicionaComponente(spTab, 0, 1, 1, 1);

		JPanel botoesInferiores = new JPanel();
		botoesInferiores.setLayout(new FlowLayout());
		botoesInferiores.add(btCadastrar);
		botoesInferiores.add(btEditar);
		botoesInferiores.add(btExcluir);
		botoesInferiores.setVisible(true);
		adicionaComponente(botoesInferiores, 0, 2, 1, 1);
		btCadastrar.addActionListener(btListener);
		btEditar.addActionListener(btListener);
		btExcluir.addActionListener(btListener);
		

	}

		public void atualizaTabela() {
		DefaultTableModel tabelaa = new DefaultTableModel();
		tabelaa.addColumn("Nome");
		tabelaa.addColumn("Matricula");
		tabelaa.addColumn("Cargo");
		tabelaa.addColumn("Salario");
		tabelaa.addColumn("Telefone");
		tabelaa.addColumn("Data Nascimento");

		listaGeral = ControladorFuncionario.getInstance().listarFuncionarios();
		if (listaGeral.size() == 0) {
			tabelaa.addRow(new Object[] { "Nenhum", "funcionario", "cadastrado", "no", "sistema", " :(" });
			tabelaFunc.setModel(tabelaa);
			this.repaint();
		}

		for (Funcionario a : listaGeral) {
			String matricula = a.getMatricula() + "";

			String nome = a.getNome();
			String cargo = a.getCargo().getNome();
			String salario = a.getSalario() + "";
			String telefone = a.getTelefone();
			String dtNasc = a.getDataNascimento();
			tabelaa.addRow(new Object[] { nome, matricula, cargo, salario, telefone, dtNasc });
			tabelaFunc.setModel(tabelaa);
			this.repaint();

		}


	}

	private class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Botao cadastrar = chama telaCadastroFuncionario
			if (e.getSource() == btCadastrar) {
				ControladorFuncionario.getInstance().telaCadastroFuncionario();
			}
			// Botao Editar joga para tela de edicao o funcionario da linha selecionada
			else if (e.getSource() == btEditar) {

				try {
					int linha = tabelaFunc.getSelectedRow();
					int matricula = (int) tabelaFunc.getValueAt(linha, 0);
					ControladorFuncionario.getInstance().telaEditarFuncionario(matricula);

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Selecione um funcionario da lista",
							"Nenhum funcionario selecionado", JOptionPane.ERROR_MESSAGE);
				}

			}
			else if (e.getSource() == btExcluir) {
				
				
				int linha = tabelaFunc.getSelectedRow();
				int matricula = (int) tabelaFunc.getValueAt(linha, 0);
				ControladorFuncionario.getInstance().excluiFuncionarioByMatricula(matricula);
				atualizaTabela();
			}
		}
	}
}
