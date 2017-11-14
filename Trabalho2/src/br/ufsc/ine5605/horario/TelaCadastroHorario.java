package br.ufsc.ine5605.horario;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import br.ufsc.ine5605.HorarioRepetidoException;
import br.ufsc.ine5605.TelaComGridBagLayout;

public class TelaCadastroHorario extends TelaComGridBagLayout {
	
	private JLabel lbPedeInicio;
	private JComboBox<Integer> cbHoraInicio;
	private JComboBox<Integer> cbMinutoInicio;
	
	private JLabel lbPedeFim;
	private JComboBox<Integer> cbHoraFim;
	private JComboBox<Integer> cbMinutoFim;
	
	private JButton btCadastrar;
	private JLabel lbMensagem;
	
	private Integer[] horas = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
	private Integer[] minutos = new Integer[60];
	
	private int horaInicio;
	private int minInicio;
	private int horaFim;
	private int minFim;
	private ArrayList<Horario> horarios;
	
	public TelaCadastroHorario() {
		super("Cadastro de Horarios de um cargo");
		
		for(int i = 0; i < 60; i++) {
			minutos[i] = i;
		}
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 200);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);

		
		ButtonActionListener btListener = new ButtonActionListener();
		
		lbPedeInicio = new JLabel("Insira o horario de inicio da permissao de acesso:   ");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(lbPedeInicio, 0, 0, 1, 1);
		
		cbHoraInicio = new JComboBox(horas);
		adicionaComponente(cbHoraInicio, 1, 0, 1, 1);
		
		cbMinutoInicio = new JComboBox(minutos);
		adicionaComponente(cbMinutoInicio, 2, 0, 1, 1);
		
		lbPedeFim = new JLabel("Insira o horario do final da permissao de acesso:   ");
		adicionaComponente(lbPedeFim, 0, 1, 1, 1);
		
		cbHoraFim = new JComboBox(horas);
		adicionaComponente(cbHoraFim, 1, 1, 1, 1);
		
		cbMinutoFim = new JComboBox(minutos);
		adicionaComponente(cbMinutoFim, 2, 1, 1, 1);
		
		btCadastrar = new JButton("Cadastrar");
		adicionaComponente(btCadastrar, 0, 2, 3, 1);
		
		btCadastrar.addActionListener(btListener);
		
		lbMensagem = new JLabel("(PARA FINALIZAR OS CADASTROS, FECHE A JANELA)");
		adicionaComponente(lbMensagem, 0, 4, 3, 1);
		
	}
	
	private class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			horaInicio = (int) cbHoraInicio.getSelectedItem();
			minInicio = (int) cbMinutoInicio.getSelectedItem();
			horaFim = (int) cbHoraFim.getSelectedItem();
			minFim = (int) cbMinutoFim.getSelectedItem();
			try {
				ControladorHorario.getInstance().adicionaHorario(horaInicio, minInicio, horaFim, minFim, horarios);
				JOptionPane.showMessageDialog(null, "Horario adicionado com sucesso");

			} catch (HorarioRepetidoException exception) {
				JOptionPane.showMessageDialog(null, exception.getMessage(), "Erro no Cadastro de Horario", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}

	public void setHorarios(ArrayList<Horario> horariosCargo) {
		this.horarios = horariosCargo;
		
	}
	
}