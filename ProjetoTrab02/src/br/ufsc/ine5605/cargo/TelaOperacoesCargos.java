package br.ufsc.ine5605.cargo;

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

import br.ufsc.ine5605.CargoComFuncionarioAssociadoException;
import br.ufsc.ine5605.TelaComGridBagLayout;
import br.ufsc.ine5605.horario.Horario;

@SuppressWarnings("serial")
public class TelaOperacoesCargos extends TelaComGridBagLayout {

	private JButton btCadastrar;
	private JButton btExcluir;
	private JButton btEditar;
	
	private JLabel lbNomeTabela;
	private JTable tabelaCargos;
	private JScrollPane spTabela;
	
	private Collection<Cargo> listaTabela;
	
	/** 
	 * Tela de operacoes com cargos, permite: cadastrar, editar e excluir cargos
	 */
	public TelaOperacoesCargos () {
		
		super("Operacoes com Cargos");
		setSize(700, 250);
		setLocationRelativeTo(null);
		
		ButtonActionListener btListener = new ButtonActionListener();
		btCadastrar = new JButton("Cadastrar");
		btCadastrar.addActionListener(btListener);
		btExcluir = new JButton("Excluir");
		btExcluir.addActionListener(btListener);
		btEditar = new JButton("Editar");
		btEditar.addActionListener(btListener);
		lbNomeTabela = new JLabel("Cargos Cadastrados");
		
		tabelaCargos = new JTable();
		tabelaCargos.setPreferredScrollableViewportSize(new Dimension(600, 60));
		tabelaCargos.setFillsViewportHeight(true);
		tabelaCargos.getTableHeader().setReorderingAllowed(false);
		spTabela = new JScrollPane(tabelaCargos);
		atualizaTabela();
		
		adicionaComponente(lbNomeTabela, 0, 0, 1, 1);
		adicionaComponente(spTabela, 0, 1, 1, 1);
		
		// organiza os botoes inferiores em um painel separado, estilo FlowLayout
		JPanel botoesInferiores = new JPanel();
		botoesInferiores.setLayout(new FlowLayout());
		botoesInferiores.add(btCadastrar);
		botoesInferiores.add(btEditar);
		botoesInferiores.add(btExcluir);
		//botoesInferiores.add(btAtualizar);
		botoesInferiores.setVisible(true);
		adicionaComponente(botoesInferiores, 0, 2, 1, 1);
		
	}

	/**
	 * Atualiza a tabela (lista) de cargos exibida na tela, apos cadastro, exclusao ou edicao de cargos
	 */
	public void atualizaTabela() {
		
		DefaultTableModel modeloTabela = new DefaultTableModel();
		modeloTabela.addColumn("Codigo");
		modeloTabela.addColumn("Cargo");
		modeloTabela.addColumn("Gerencial?");
		modeloTabela.addColumn("Acesso?");
		modeloTabela.addColumn("Horarios Permitidos");
		
		// o controlador le do arquivo
		listaTabela = ControladorCargo.getInstance().getListaCargos();
		if (listaTabela.isEmpty()) {
			modeloTabela.addRow(new Object[] {"", "", "", "", ""});
			tabelaCargos.setModel(modeloTabela);
			this.repaint();
		}
		
		// cria as linhas da tabela
		for (Cargo c : listaTabela) {
			int codigo = c.getCodigo();
			String nome = c.getNome();
			String gerencial = c.getEhGerencial() ? "Sim" : "Nao";
			String acesso = c.getEhGerencial() ? "Sim" : c.getPossuiAcesso() ? "Sim" : "Nao";
			List<Horario> horarios = c.getHorariosPermitidos(); 
			modeloTabela.addRow(new Object[] {codigo, nome, gerencial, acesso, c.getEhGerencial() ? "Qualquer horario" : !c.getPossuiAcesso()? "Nenhum horario" : horarios.toString()});
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
				try {
					int linha = tabelaCargos.getSelectedRow();
					int codigo = (int) tabelaCargos.getValueAt(linha, 0);
					ControladorCargo.getInstance().excluirCargo(codigo);
				} catch (CargoComFuncionarioAssociadoException ex) { // excecao criada para evitar a exclusao, caso ja haja funcionario associado
					JOptionPane.showMessageDialog(null, ex.getMessage(), ex.getMessage(), JOptionPane.WARNING_MESSAGE);
				
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Selecione um cargo da lista", "Cargo Nao Selecionado", JOptionPane.WARNING_MESSAGE);
				}
				atualizaTabela();
			} else if(e.getSource() == btEditar) {
				
				try {
					int linha = tabelaCargos.getSelectedRow();
					int codigo = (int) tabelaCargos.getValueAt(linha, 0);
					ControladorCargo.getInstance().alterarCargo(codigo);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Selecione um cargo da lista", "Cargo Nao Selecionado", JOptionPane.WARNING_MESSAGE);
				}
				
			}
			
		}
		
	}
}
