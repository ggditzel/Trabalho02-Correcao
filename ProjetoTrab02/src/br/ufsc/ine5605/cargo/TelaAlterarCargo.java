package br.ufsc.ine5605.cargo;

import java.awt.GridBagConstraints;
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

public class TelaAlterarCargo extends TelaComGridBagLayout {

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

	private JButton btHorarios = new JButton("Castrar Horarios");;
	private JButton btEditar;

	// dados para formar o cargo
	private int codigo = -1;
	private String nome = "";
	private boolean ehGerencial = false;
	private boolean possuiAcesso = false;
	private ArrayList<Horario> horariosPermitidos = new ArrayList<Horario>();
	
	// cargo cujos valores serao editados
	private Cargo cargoAnterior;

	public TelaAlterarCargo(Cargo cargo) {
		super("Edicao de Cargos");
		
		this.cargoAnterior = cargo;
		
		// considera os horarios definidos para o cargo antigo
		horariosPermitidos = cargoAnterior.getHorariosPermitidos();
		
    	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 250);
		//setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		
						
		ButtonActionListener btListener = new ButtonActionListener();
		GerencialListener rbGerencialListener = new GerencialListener();
		AcessoListener rbAcessoListener = new AcessoListener();

		//solicita nome do cargo, preenche com o antigo
		jlPedeNomeCargo = new JLabel("Nome do cargo");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(jlPedeNomeCargo, 0, 0, 1, 1);
		
		tfPedeNomeCargo = new JTextField(cargoAnterior.getNome());
		tfPedeNomeCargo.selectAll();
		adicionaComponente(tfPedeNomeCargo, 1, 0, 2, 1);
			
		//solicita codigo do cargo, preenche com o antigo
		jlPedeCodigoCargo = new JLabel("Codigo do cargo");
		adicionaComponente(jlPedeCodigoCargo, 0, 1, 1, 1);
		
		tfPedeCodigoCargo = new JTextField(Integer.toString(cargoAnterior.getCodigo()));
		tfPedeCodigoCargo.selectAll();
		adicionaComponente(tfPedeCodigoCargo, 1, 1, 2, 1);
		
		//solicita se eh gerencial, seleciona botao antigo
		lbGerencial = new JLabel("Este cargo eh gerencial?");
		adicionaComponente(lbGerencial, 0, 2, 1, 1);
		rbSimGerencial = new JRadioButton("Sim", cargoAnterior.getEhGerencial());
		adicionaComponente(rbSimGerencial, 1, 2, 1, 1);
		rbNaoGerencial = new JRadioButton("Nao", !cargoAnterior.getEhGerencial());
		adicionaComponente(rbNaoGerencial, 2, 2, 1, 1);		
		
		// agrupa logicamente os Radio Buttons de gerencia
		grupoRbGerencial = new ButtonGroup();
		grupoRbGerencial.add(rbNaoGerencial);
		grupoRbGerencial.add(rbSimGerencial);
		rbSimGerencial.addItemListener(rbGerencialListener);
		rbNaoGerencial.addItemListener(rbGerencialListener);

		
		// solicita se tem acesso, seleciona botao antigo
		lbAcesso = new JLabel("Este cargo possui acesso?");
		adicionaComponente(lbAcesso, 0, 3, 1, 1);
		rbSimAcesso = new JRadioButton("Sim", cargoAnterior.getPossuiAcesso());
		adicionaComponente(rbSimAcesso, 1, 3, 1, 1);
		rbNaoAcesso = new JRadioButton("Nao", !cargoAnterior.getPossuiAcesso());
		adicionaComponente(rbNaoAcesso, 2, 3, 1, 1);
		
		// agrupa logicamente os Radio Buttons de acesso
		grupoRbAcesso = new ButtonGroup();
		grupoRbAcesso.add(rbNaoAcesso);
		grupoRbAcesso.add(rbSimAcesso);
		rbSimAcesso.addItemListener(rbAcessoListener);
		rbNaoAcesso.addItemListener(rbAcessoListener);
			
		// seleciona visibilidade do botao de cadastro de horarios e de selecao de acesso
		if (cargoAnterior.getEhGerencial()) {
			lbAcesso.setEnabled(false);
			rbSimAcesso.setEnabled(false);
			rbNaoAcesso.setEnabled(false);
			btHorarios.setVisible(false);
			btHorarios.setEnabled(false);
		} else {
			if (cargoAnterior.getPossuiAcesso()) {
				btHorarios.setVisible(false);
				btHorarios.setEnabled(false);
			}
		}
		

		//btHorarios = new JButton("Castrar Horarios");
		btHorarios.addActionListener(btListener);
		adicionaComponente(btHorarios, 0, 4, 3, 1);
		
		btEditar = new JButton("Alterar");
		adicionaComponente(btEditar, 0, 5, 3, 1);
		btEditar.addActionListener(btListener);
		

	}
	
	private class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent evento) {
			boolean abortaCadastro = false;

			if (evento.getSource() == btHorarios) {
				horariosPermitidos = ControladorCargo.getInstance().pegaHorarios();
			}
			
			if (evento.getSource() == btEditar) {
				try {
					codigo = Integer.parseInt(tfPedeCodigoCargo.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Digite apenas numeros para o codigo", "Erro de Dados", JOptionPane.WARNING_MESSAGE);
					abortaCadastro = true;
				}
				
				// se ja existe cargo cadastrado com o codigo editado, aborta o cadastro
				if (ControladorCargo.getInstance().findCargoByCodigo(codigo) != null) {
					JOptionPane.showMessageDialog(null, "Codigo ja em uso, favor escolher outro", "Codigo ja em uso", JOptionPane.WARNING_MESSAGE);
					abortaCadastro = true;
				}
				
				nome = tfPedeNomeCargo.getText();
				ehGerencial = rbSimGerencial.isSelected();
				possuiAcesso = rbSimAcesso.isSelected();
				
				if (possuiAcesso && horariosPermitidos.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Cargo com permissao de acesso, mas sem horarios cadastrados. Cadastrar horario antes de prosseguir.", "Solicitacao para cadastro de horario", JOptionPane.ERROR_MESSAGE);
					abortaCadastro = true;
				}
				
				if (!abortaCadastro){
					ControladorCargo.getInstance().excluirCargo(cargoAnterior.getCodigo());
					ControladorCargo.getInstance().incluirCargo(new DadosCadastroCargo(codigo, nome, ehGerencial, possuiAcesso, horariosPermitidos));				
					dispose();
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
					btHorarios.setVisible(false);
					btHorarios.setEnabled(false);
				}
				if (rbNaoGerencial.isSelected()) {
					lbAcesso.setEnabled(true);
					rbSimAcesso.setEnabled(true);
					rbNaoAcesso.setEnabled(true);
					if (rbSimAcesso.isSelected()) {
						btHorarios.setVisible(true);
						btHorarios.setEnabled(true);
					} else {
						btHorarios.setVisible(false);
						btHorarios.setEnabled(false);
					}
				}
			}
		}
	}
	
	
	private class AcessoListener implements ItemListener {

		// de repente vale s� avisar que depois tem que cadastrar hor�rio de acesso, tem que ver como ficam as outras janelas
		public void itemStateChanged(ItemEvent evento) {
			if (evento.getStateChange() == ItemEvent.SELECTED) {
				if (rbSimAcesso.isSelected()) {
					btHorarios.setVisible(true);
					btHorarios.setEnabled(true);
				}
				
				if (rbNaoAcesso.isSelected()) {
					btHorarios.setVisible(false);
					btHorarios.setEnabled(false);
				}
			}
		}
	}

}
