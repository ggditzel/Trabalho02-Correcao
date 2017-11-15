package br.ufsc.ine5605;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class TelaMenuPrincipal extends TelaComGridBagLayout {
	private JLabel lbPergunta;
	private JButton btGerenciarSistema;
	private JButton btAcessarSala;
	
	public TelaMenuPrincipal() {
		super("Menu Principal");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 250);
		//setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		setVisible(true);
		
		ButtonActionListener btListener = new ButtonActionListener();

		lbPergunta = new JLabel("O que voce deseja fazer?");
		adicionaComponente(lbPergunta, 0, 0, 1, 1);
		
		btGerenciarSistema = new JButton("Gerenciar Sistema");
		btGerenciarSistema.addActionListener(btListener);
		adicionaComponente(btGerenciarSistema, 0, 1, 1, 1);
		
		btAcessarSala = new JButton("Acessar o financeiro");
		btAcessarSala.addActionListener(btListener);
		adicionaComponente(btAcessarSala, 0, 2, 1, 1);
		
	}
	
	private class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btGerenciarSistema) {
				ControladorPrincipal.getInstance().gerenciarSistema();
			} else if(e.getSource() == btAcessarSala) {
				ControladorPrincipal.getInstance().iniciarTerminal();
			}
			
		}
		
	}
}
