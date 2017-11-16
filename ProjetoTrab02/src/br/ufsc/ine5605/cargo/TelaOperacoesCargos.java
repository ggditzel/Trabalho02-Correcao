package br.ufsc.ine5605.cargo;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import br.ufsc.ine5605.TelaComGridBagLayout;
import br.ufsc.ine5605.horario.Horario;

@SuppressWarnings("serial")
public class TelaOperacoesCargos extends TelaComGridBagLayout {

	private JButton btCadastrar;
	private JButton btExcluir;
	private JButton btEditar;
	private JButton btAtualizar;
	
	private JLabel lbNomeTabela;
	private JTable tabelaCargos;
	private JScrollPane spTabela;
	
	private Collection<Cargo> listaTabela;
	
	public TelaOperacoesCargos () {
		
		super("Operacoes com Cargos");
		setSize(800, 250);
		setLocationRelativeTo(null);
		
		ButtonActionListener btListener = new ButtonActionListener();
		btCadastrar = new JButton("Cadastrar");
		btCadastrar.addActionListener(btListener);
		btExcluir = new JButton("Excluir");
		btExcluir.addActionListener(btListener);
		btEditar = new JButton("Editar");
		btEditar.addActionListener(btListener);
		btAtualizar = new JButton("Atualizar");
		btAtualizar.addActionListener(btListener);
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
		botoesInferiores.add(btAtualizar);
		botoesInferiores.setVisible(true);
		adicionaComponente(botoesInferiores, 0, 2, 1, 1);
		
	}

	private void atualizaTabela() {
		
		DefaultTableModel modeloTabela = new DefaultTableModel();
		modeloTabela.addColumn("Codigo");
		modeloTabela.addColumn("Cargo");
		modeloTabela.addColumn("Gerencial?");
		modeloTabela.addColumn("Acesso?");
		modeloTabela.addColumn("Horarios");
		
		// o controlador le do arquivo
		listaTabela = ControladorCargo.getInstance().getListaCargos();
		if (listaTabela.isEmpty()) {
			modeloTabela.addRow(new Object[] {"", "", "", "", ""});
			tabelaCargos.setModel(modeloTabela);
			this.repaint();
		}
		
		for (Cargo c : listaTabela) {
			int codigo = c.getCodigo();
			String nome = c.getNome();
			String gerencial = c.getEhGerencial() ? "Sim" : "Nao";
			String acesso = c.getEhGerencial() ? "Sim" : c.getPossuiAcesso() ? "Sim" : "Nao";
			List<Horario> horarios = c.getHorariosPermitidos(); 
			modeloTabela.addRow(new Object[] {codigo, nome, gerencial, acesso, horarios.toString()});
			tabelaCargos.setModel(modeloTabela);
			this.repaint();
		}
		
	}

	private class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btCadastrar) {
				ControladorCargo.getInstance().telaIncluirCargo();
			} else if(e.getSource() == btExcluir) {
				int linha = tabelaCargos.getSelectedRow();
				int codigo = (int) tabelaCargos.getValueAt(linha, 0);
				ControladorCargo.getInstance().excluirCargo(codigo);
				atualizaTabela();
			} else if(e.getSource() == btEditar) {
				int linha = tabelaCargos.getSelectedRow();
				int codigo = (int) tabelaCargos.getValueAt(linha, 0);
				ControladorCargo.getInstance().alterarCargo(codigo);
				
			} else if(e.getSource() == btAtualizar){
				atualizaTabela();
			}
			
		}
		
	}
}
