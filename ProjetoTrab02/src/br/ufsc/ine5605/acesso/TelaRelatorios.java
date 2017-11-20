package br.ufsc.ine5605.acesso;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.ufsc.ine5605.TelaComGridBagLayout;

public class TelaRelatorios extends TelaComGridBagLayout{
	
	private JLabel lbFiltrarMotivo;
	private JButton btFiltrarMotivo;
	private JComboBox<String> cbFiltrarMotivo;
	private JLabel lbFiltrarMatricula;
	private JButton btFiltrarMatricula;
	private JTextField tfFiltrarMatricula;
	
	private JTable tbTabela;
	private JLabel lbNomeTabela;
	private JScrollPane spTabela;
	
	private JButton btReset;
	
	
	public TelaRelatorios() {
		
		super("Relatorios de acesso");
		setSize(1000, 250);
		setLocationRelativeTo(null);
		
		ButtonActionListener btListener = new ButtonActionListener();
		
		lbFiltrarMotivo = new JLabel("Filtrar por motivo: ");
		adicionaComponente(lbFiltrarMotivo, 0, 0, 1, 1);
		
		cbFiltrarMotivo = new JComboBox(MotivoNegacaoAcesso.values());
		adicionaComponente(cbFiltrarMotivo, 1, 0, 1, 1);
		
		btFiltrarMotivo = new JButton("Filtrar");
		btFiltrarMotivo.addActionListener(btListener);
		adicionaComponente(btFiltrarMotivo, 2, 0, 1, 1);
		
		lbFiltrarMatricula = new JLabel("Filtrar por matricula: ");
		adicionaComponente(lbFiltrarMatricula, 0, 1, 1, 1);
		
		tfFiltrarMatricula = new JTextField("Matricula");
		adicionaComponente(tfFiltrarMatricula, 1, 1, 5, 1);
		
		btFiltrarMatricula = new JButton("Filtrar");
		btFiltrarMatricula.addActionListener(btListener);
		adicionaComponente(btFiltrarMatricula, 2, 1, 1, 1);
		
		
		lbNomeTabela = new JLabel("Tentativas de acesso");
		adicionaComponente(lbNomeTabela, 1, 2, 1, 1);
		
		tbTabela = new JTable();
		tbTabela.setPreferredScrollableViewportSize(new Dimension(900, 120));
		tbTabela.setFillsViewportHeight(true);
		
		atualizaTabela();
		
		spTabela = new JScrollPane(tbTabela);
		adicionaComponente(spTabela, 0, 3, 10, 6);
		
		btReset = new JButton("Resetar filtros");
		btReset.addActionListener(btListener);
		adicionaComponente(btReset, 0, 10, 1, 1);
		
	}
	/**
         * Atualiza os valores da tabela
         */
	public void atualizaTabela() {
		atualizaTabela(ControladorTentativaAcesso.getInstance().getTentativas());
	}
	/**
         * Atualiza os valores da tabela para os valores de uma lista
         * @param lista 
         */
	public void atualizaTabela(Collection<TentativaAcesso> lista) {
		DefaultTableModel modeloTabela = new DefaultTableModel();
		modeloTabela.addColumn("Matricula");
                modeloTabela.addColumn("Nome");
		modeloTabela.addColumn("Conseguiu acessar?");
		modeloTabela.addColumn("Motivo");
		modeloTabela.addColumn("Data");
		modeloTabela.addColumn("Horario");

		
		
		if (lista.isEmpty()) {
			modeloTabela.addRow(new Object[] {"", "", "", "", "",""});
			tbTabela.setModel(modeloTabela);
			this.repaint();
		} else {
			for(TentativaAcesso t: lista) {
				int matricula = t.getMatricula();
                                String nome = t.getNome();
				String acessou = t instanceof TentativaAcessoPermitido ? "Sim" : "Nao";
				String motivo = t instanceof TentativaAcessoPermitido ? "" : ((TentativaAcessoNegado) t).getMotivo().getNome();
				String data = t.getData();
				String horario = t.getHora().toString();
				modeloTabela.addRow(new Object[] {matricula, nome, acessou, motivo, data, horario});
				tbTabela.setModel(modeloTabela);
				repaint();
			}
		}
	}
	
	
	private class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btFiltrarMotivo) {
				atualizaTabela(ControladorTentativaAcesso.getInstance().findTentativasNegadasByMotivo((MotivoNegacaoAcesso) cbFiltrarMotivo.getSelectedItem()));
			} else if(e.getSource() == btFiltrarMatricula) {
				try{
					atualizaTabela(ControladorTentativaAcesso.getInstance().findTentativasByMatricula(Integer.parseInt(tfFiltrarMatricula.getText())));		
				} catch (NumberFormatException exception ){
					JOptionPane.showMessageDialog(null, "Digite apenas numeros para a matricula", "Erro de Dados", JOptionPane.ERROR_MESSAGE);
				}
			} else if(e.getSource() == btReset) {
				atualizaTabela();
			}
			
		}
		
	}
}
