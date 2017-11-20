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

public class TelaAcesso extends TelaComGridBagLayout implements ActionListener{
	protected JLabel lbPergunta;
	protected JTextField tfMatricula;
	protected JButton btConfirma;
	
	protected int matricula;
	protected String data;
	protected Hora hora;
	
	public TelaAcesso() {
		this(3);
		java.util.Date agora = new java.util.Date();
		SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
		this.data = formata.format(agora);
		formata = new SimpleDateFormat("HH:mm");
		hora = ControladorHorario.getInstance().converte(formata.format(agora));
        }
	
	protected TelaAcesso(int nLinhasComponentes) {
		super("Acesso ao Financeiro");
		
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(400, 250);
		setLocationRelativeTo(null);
		
		
		lbPergunta = new JLabel("Insira sua matricula :");
		constraints.fill = GridBagConstraints.HORIZONTAL;
		adicionaComponente(lbPergunta, 0, nLinhasComponentes - 3, 1, 1);
		
		tfMatricula = new JTextField();
		adicionaComponente(tfMatricula, 0, nLinhasComponentes -2, 3, 1);
		
		btConfirma = new JButton("Confirmar");
		btConfirma.addActionListener(this);
		adicionaComponente(btConfirma, 0, nLinhasComponentes - 1, 1, 1);
		
	}
	
	public void setData(String data) {
		this.data = data;
	}
	public void setHora(Hora hora) {
		this.hora = hora;
	}
	/**
         * Abre janela mostrando mensagem de erro
         * @param motivo 
         */
	public void mostraNegacao(MotivoNegacaoAcesso motivo) {
		JOptionPane.showMessageDialog(null, motivo.getNome(), "Acesso Negado", JOptionPane.ERROR_MESSAGE);
	}
        /**
         * Abre janela confirmando acesso
         */
	public void confirmaAcesso() {
		JOptionPane.showMessageDialog(null,"Acesso Permitido");
	}

    @Override
    public void actionPerformed(ActionEvent ae) {
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
