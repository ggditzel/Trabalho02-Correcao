package br.ufsc.ine5605.cargo;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import br.ufsc.ine5605.TelaComGridBagLayout;
import br.ufsc.ine5605.horario.Horario;

public class TelaCadastroCargo extends TelaComGridBagLayout {

	private JLabel jlMensagem;
	private JLabel jlPedeNomeCargo;
	private JLabel jlPedeCodigoCargo;
	private JTextField tfPedeNomeCargo;
	private JTextField tfPedeCodigoCargo;

	// Gerencial?
	private JLabel lbGerencial;
	private JRadioButton rbSimGerencial;
	private JRadioButton rbNaoGerencial;
	private ButtonGroup grupoRbGerencial;

	// Acesso?
	private JLabel lbAcesso;
	private JRadioButton rbSimAcesso;
	private JRadioButton rbNaoAcesso;
	private ButtonGroup grupoRbAcesso;

	private JButton btCadastrar;

	// dados para formar o cargo
	private int codigo = -1;
	private String nome = "";
	private boolean ehGerencial = false;
	private boolean possuiAcesso = false;
	private ArrayList<Horario> horariosPermitidos = new ArrayList<Horario>();

	public TelaCadastroCargo() {
		super("Cadastro de Cargos");
				
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 250);
		//setAlwaysOnTop(true);
		setLocationRelativeTo(null);
						
		ButtonActionListener btListener = new ButtonActionListener();
		GerencialListener rbGerencialListener = new GerencialListener();
		AcessoListener rbAcessoListener = new AcessoListener();

		jlPedeNomeCargo = new JLabel("Nome do cargo");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeNomeCargo, 0, 0, 1, 1);
		
		tfPedeNomeCargo = new JTextField("Nome");
		tfPedeNomeCargo.selectAll();
		adicionaComponente(tfPedeNomeCargo, 1, 0, 2, 1);
			
		jlPedeCodigoCargo = new JLabel("Codigo do cargo");
		adicionaComponente(jlPedeCodigoCargo, 0, 1, 1, 1);
		
		tfPedeCodigoCargo = new JTextField("Codigo");
		tfPedeCodigoCargo.selectAll();
		adicionaComponente(tfPedeCodigoCargo, 1, 1, 2, 1);
		
		
		lbGerencial = new JLabel("Este cargo eh gerencial?");
		adicionaComponente(lbGerencial, 0, 2, 1, 1);
		
		
		rbSimGerencial = new JRadioButton("Sim", false);
		adicionaComponente(rbSimGerencial, 1, 2, 1, 1);
		
		rbNaoGerencial = new JRadioButton("Nao", true);
		adicionaComponente(rbNaoGerencial, 2, 2, 1, 1);
		
		grupoRbGerencial = new ButtonGroup();
		grupoRbGerencial.add(rbNaoGerencial);
		grupoRbGerencial.add(rbSimGerencial);
		rbSimGerencial.addItemListener(rbGerencialListener);
		rbNaoGerencial.addItemListener(rbGerencialListener);

		lbAcesso = new JLabel("Este cargo possui acesso?");
		adicionaComponente(lbAcesso, 0, 3, 1, 1);
		
		rbSimAcesso = new JRadioButton("Sim", false);
		adicionaComponente(rbSimAcesso, 1, 3, 1, 1);
		
		rbNaoAcesso = new JRadioButton("Nao", true);
		adicionaComponente(rbNaoAcesso, 2, 3, 1, 1);
		
		grupoRbAcesso = new ButtonGroup();
		grupoRbAcesso.add(rbNaoAcesso);
		grupoRbAcesso.add(rbSimAcesso);
		rbSimAcesso.addItemListener(rbAcessoListener);
		rbNaoAcesso.addItemListener(rbAcessoListener);

		btCadastrar = new JButton("Cadastrar");
		adicionaComponente(btCadastrar, 0, 4, 3, 1);
		
		btCadastrar.addActionListener(btListener);
		
		jlMensagem = new JLabel("(PARA FINALIZAR OS CADASTROS, FECHE A JANELA)");
		adicionaComponente(jlMensagem, 0, 5, 3, 1);
	}

	private void valoresDefaultJanela() {
		tfPedeNomeCargo.setText("Nome");
		tfPedeNomeCargo.selectAll();
		tfPedeNomeCargo.requestFocusInWindow();
		tfPedeCodigoCargo.setText("Codigo");
		tfPedeCodigoCargo.selectAll();
		rbSimGerencial.setSelected(false);
		rbNaoGerencial.setSelected(true);
		rbSimAcesso.setSelected(false);
		rbNaoAcesso.setSelected(true);
	}
	
	private class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evento) {
			boolean abortaCadastro = false;

			if (evento.getSource() == btCadastrar) {
				try {
					codigo = Integer.parseInt(tfPedeCodigoCargo.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Digite apenas numeros para o codigo", "Erro de Dados", JOptionPane.ERROR_MESSAGE);
					abortaCadastro = true;
				}
				
				nome = tfPedeNomeCargo.getText();
				ehGerencial = rbSimGerencial.isSelected();
				possuiAcesso = rbSimAcesso.isSelected();
				
				if (!abortaCadastro){
					if(!ehGerencial && possuiAcesso){
						horariosPermitidos = ControladorCargo.getInstance().pegaHorarios();
					}
					ControladorCargo.getInstance().incluirCargo(new DadosCadastroCargo(codigo, nome, ehGerencial, possuiAcesso, horariosPermitidos));
					valoresDefaultJanela();
				}
			}
		}

	}

	private class GerencialListener implements ItemListener {

		public void itemStateChanged(ItemEvent evento) {
			if (evento.getStateChange() == ItemEvent.SELECTED) {
				if (rbSimGerencial.isSelected()) {
					lbAcesso.setEnabled(false);
					rbSimAcesso.setEnabled(false);
					rbNaoAcesso.setEnabled(false);
				}
				if (!rbSimGerencial.isSelected()) {
					lbAcesso.setEnabled(true);
					rbSimAcesso.setEnabled(true);
					rbNaoAcesso.setEnabled(true);
				}
			}
		}
	}
	
	
	private class AcessoListener implements ItemListener {

		// de repente vale s� avisar que depois tem que cadastrar hor�rio de acesso, tem que ver como ficam as outras janelas
		public void itemStateChanged(ItemEvent evento) {
			if (evento.getStateChange() == ItemEvent.SELECTED) {
				if (rbSimAcesso.isSelected()) {
					//horariosPermitidos = ControladorHorario.getInstance().iniciaCadastro();
				}
			}
		}
	}

}
