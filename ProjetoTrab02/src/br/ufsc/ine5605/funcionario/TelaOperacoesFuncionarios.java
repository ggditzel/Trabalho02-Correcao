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

		tabelaFunc = new JTable();
		tabelaFunc.setPreferredScrollableViewportSize(new Dimension(600, 60));
		tabelaFunc.setFillsViewportHeight(true);
		spTab = new JScrollPane(tabelaFunc);
		atualizaTabela();
		lbNomeTab = new JLabel("Funcionarios cadastrados");
		btCadastrar = new JButton("Cadastrar");
		btEditar = new JButton("Editar");
		btExcluir = new JButton("Excluir");
		btCadastrar.addActionListener(btListener);
		btEditar.addActionListener(btListener);
		btExcluir.addActionListener(btListener);

		adicionaComponente(lbNomeTab, 0, 0, 1, 1);
		adicionaComponente(spTab, 0, 1, 1, 1);

		JPanel botoesInferiores = new JPanel();
		botoesInferiores.setLayout(new FlowLayout());
		botoesInferiores.add(btCadastrar);
		botoesInferiores.add(btEditar);
		botoesInferiores.add(btExcluir);
		botoesInferiores.setVisible(true);
		adicionaComponente(botoesInferiores, 0, 2, 1, 1);

	}

	public void atualizaTabela() {
		DefaultTableModel tabelaaMod = new DefaultTableModel();
		tabelaaMod.addColumn("Nome");
		tabelaaMod.addColumn("Matricula");
		tabelaaMod.addColumn("Cargo");
		tabelaaMod.addColumn("Salario");
		tabelaaMod.addColumn("Telefone");
		tabelaaMod.addColumn("Data Nascimento");

		listaGeral = ControladorFuncionario.getInstance().listarFuncionarios();
		if (listaGeral.size() == 0) {
			tabelaaMod.addRow(new Object[] { "Nenhum", "funcionario", "cadastrado", "no", "sistema", " :(" });
			tabelaFunc.setModel(tabelaaMod);
			this.repaint();
		}

		for (Funcionario a : listaGeral) {
			int matricula = a.getMatricula();
			String nome = a.getNome();
			String cargo = a.getCargo().getNome();
			String salario = a.getSalario() + "";
			String telefone = a.getTelefone();
			String dtNasc = a.getDataNascimento();
			tabelaaMod.addRow(new Object[] { nome, matricula, cargo, salario, telefone, dtNasc });
			tabelaFunc.setModel(tabelaaMod);
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
					int matricula = (int) tabelaFunc.getValueAt(linha, 1);
					ControladorFuncionario.getInstance().telaEditarFuncionario(matricula);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Selecione um funcionario da lista", "Selecao nula",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			else if (e.getSource() == btExcluir) {
				try {
					int linha = tabelaFunc.getSelectedRow();
					int matricula = (int) tabelaFunc.getValueAt(linha, 1);
					ControladorFuncionario.getInstance().excluiFuncionarioByMatricula(matricula);
					atualizaTabela();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Selecione um funcionario da lista", "Selecao nula",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}


	}

	public void erroNaoTemCargo() {
		JOptionPane.showMessageDialog(null, "E necessario ao menos um cargo cadastrado para cadastrar funcionarios",
				"favor cadastrar cargo", JOptionPane.ERROR_MESSAGE);

	}
}
