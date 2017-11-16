package br.ufsc.ine5605.cargo;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.ufsc.ine5605.TelaComGridBagLayout;

@SuppressWarnings("serial")
public class TelaOperacoesCargos extends TelaComGridBagLayout {

	private JButton btCadastrar;
	private JButton btExcluir;
	private JButton btEditar;
	
	private JLabel lbNomeTabela;
	private JTable tabelaCargos;
	private JScrollPane spTabela;
	
	public TelaOperacoesCargos () {
		
		super("Operacoes com Cargos");
		setSize(800, 250);
		btCadastrar = new JButton("Cadastrar");
		btExcluir = new JButton("Excluir");
		btEditar = new JButton("Editar");
		lbNomeTabela = new JLabel("Cargos Cadastrados");
		
		tabelaCargos = new JTable();
		tabelaCargos.setPreferredScrollableViewportSize(new Dimension(400, 60));
		tabelaCargos.setFillsViewportHeight(true);
		spTabela = new JScrollPane(tabelaCargos);
		atualizaTabela();
		
		adicionaComponente(lbNomeTabela, 0, 0, 1, 1);
		adicionaComponente(spTabela, 0, 1, 1, 1);
		
		// organiza os botoes inferiores
		JPanel botoesInferiores = new JPanel();
		botoesInferiores.setLayout(new FlowLayout());
		botoesInferiores.add(btCadastrar);
		botoesInferiores.add(btEditar);
		botoesInferiores.add(btExcluir);
		botoesInferiores.setVisible(true);
		adicionaComponente(botoesInferiores, 0, 2, 1, 1);
		
	}

	private void atualizaTabela() {
		DefaultTableModel modeloTabela = new DefaultTableModel();
		modeloTabela.addColumn("Codigo");
		modeloTabela.addColumn("Cargo");
		modeloTabela.addColumn("Gerencial");
		
		// no futuro recebera uma lista de um arquivo
		for (int i = 0; i < 10; i++) {
			modeloTabela.addRow(new Object[] {i, "Cargo " + i, "Nao"});		
		}
		
		tabelaCargos.setModel(modeloTabela);
		this.repaint();
	}
	
}
