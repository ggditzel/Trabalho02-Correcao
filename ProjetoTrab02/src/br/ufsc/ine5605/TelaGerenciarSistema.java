package br.ufsc.ine5605;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import br.ufsc.ine5605.acesso.ControladorTentativaAcesso;
import br.ufsc.ine5605.cargo.ControladorCargo;
import br.ufsc.ine5605.funcionario.ControladorFuncionario;


public class TelaGerenciarSistema extends TelaComGridBagLayout {

	private JButton btOperacoesCargos;
	private JButton btOperacoesFuncionarios;
	private JButton btRelatoriosAcesso;
	
	public TelaGerenciarSistema() {
		super("Gerenciamento do Sistema");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 250);
		//setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		//setVisible(true);
		
		ButtonActionListener btListener = new ButtonActionListener();

		btOperacoesCargos = new JButton("Operacoes com Cargos");
		btOperacoesCargos.addActionListener(btListener);
		adicionaComponente(btOperacoesCargos, 0, 0, 1, 1);
		
		btOperacoesFuncionarios = new JButton("Operacoes com Funcionarios");
		btOperacoesFuncionarios.addActionListener(btListener);
		adicionaComponente(btOperacoesFuncionarios, 0, 1, 1, 1);
		
		btRelatoriosAcesso = new JButton("Relatorios de Acesso");
		btRelatoriosAcesso.addActionListener(btListener);
		adicionaComponente(btRelatoriosAcesso, 0, 2, 1, 1);
		
	}
	
	private class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btOperacoesCargos) {
				ControladorCargo.getInstance().mostraMenu();
			} else if(e.getSource() == btOperacoesFuncionarios) {
				ControladorFuncionario.getInstance().mostraMenu();
			} else if(e.getSource() == btRelatoriosAcesso) {
				ControladorTentativaAcesso.getInstance().menuRelatorioTentativas();
			}
		}
		
	}
}
