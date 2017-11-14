package br.ufsc.ine5605.acesso;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import br.ufsc.ine5605.TelaComGridBagLayout;
import br.ufsc.ine5605.horario.ControladorHorario;
import br.ufsc.ine5605.horario.Hora;

public class TelaAcesso extends TelaComGridBagLayout{
	private JLabel lbPergunta;
	private JTextField tfMatricula;
	private JButton btConfirma;
	
	private int matricula;
	private String data;
	private Hora hora;
	
	public TelaAcesso() {
		this(3);
		java.util.Date agora = new java.util.Date();
		SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
		this.data = formata.format(agora);
		formata = new SimpleDateFormat("HH:mm");
		hora = ControladorHorario.getInstance().converte(formata.format(agora));
	}
	
	protected TelaAcesso(int nComponentes) {
		super("Acesso ao Financeiro");
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 250);
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		
		ButtonActionListener btListener = new ButtonActionListener();
		
		lbPergunta = new JLabel("Insira sua matricula :");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(lbPergunta, 0, nComponentes - 3, 1, 1);
		
		tfMatricula = new JTextField();
		adicionaComponente(tfMatricula, 0, nComponentes -2, 3, 1);
		
		btConfirma = new JButton("Confirmar");
		btConfirma.addActionListener(btListener);
		adicionaComponente(btConfirma, 0, nComponentes - 1, 1, 1);
		
	}
	
	public void setData(String data) {
		this.data = data;
	}
	public void setHora(Hora hora) {
		this.hora = hora;
	}
	private class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			boolean respostaOk = true;
			try {
				matricula = Integer.parseInt(tfMatricula.getText());
			} catch (NumberFormatException exception) {
				JOptionPane.showMessageDialog(null, "Digite apenas numeros para a matricula", "Formato de Matricula invalido", JOptionPane.ERROR_MESSAGE);
				respostaOk = false;
			}
			if(respostaOk) {
				ControladorTentativaAcesso.getInstance().validaTentativa(matricula, data, hora);
				tfMatricula.setText("");
			}
		}
	
	}
}
