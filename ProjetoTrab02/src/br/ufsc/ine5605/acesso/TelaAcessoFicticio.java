/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufsc.ine5605.acesso;

import br.ufsc.ine5605.horario.Hora;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author lucas
 */
public class TelaAcessoFicticio extends TelaAcesso {
    private JLabel lbData;
    private JComboBox<String> cbDia;
    private JComboBox<String> cbMes;
    private JComboBox<String> cbAno;
    
    private JLabel lbHora;
    private JComboBox<Integer> cbHora;
    private JComboBox<Integer> cbMin;
    private Integer[] horas = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23};
    private Integer[] minutos;
    
    
    
    public TelaAcessoFicticio() {
        super(5);
        
        lbData = new JLabel("Data: ");
        adicionaComponente(lbData, 0, 0, 1, 1);
        lbHora = new JLabel("Hora: ");
        adicionaComponente(lbHora, 0, 1, 1, 1);
        inicializaCBs();
        
    }
    /**
     * Gera os valores das comboBoxes e as adiciona na tela
     */
    public void inicializaCBs() {
		String[] diaString = new String[31];
		String[] mesString = new String[12];
		String[] anoString = new String[100];
                minutos = new Integer[60];
                for(int i = 0; i < 60; i++) {
			minutos[i] = i;
		}
		for (int a = 1; a <= 31; a++) {
			diaString[a-1] = a + "";
		}
		for (int b = 1; b <= 12; b++) {
			mesString[b-1] = b + "";
		}
		for (int c = 0; c < 100; c++) {
			anoString[c] = 2017 - c + "";
		}
		cbDia = new JComboBox(diaString);
		adicionaComponente(cbDia, 1, 0, 1, 1);

		cbMes = new JComboBox(mesString);
		adicionaComponente(cbMes, 2, 0, 1, 1);

		cbAno = new JComboBox(anoString);
		adicionaComponente(cbAno, 3, 0, 1, 1);
                cbHora = new JComboBox(horas);
                adicionaComponente(cbHora, 1, 1, 1, 1);
                cbMin = new JComboBox(minutos);
		adicionaComponente(cbMin, 2, 1, 1, 1);

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
        setData((String) cbDia.getSelectedItem() + "/" + cbMes.getSelectedItem() + "/"
							+ cbAno.getSelectedItem());
        setHora(new Hora((int)cbHora.getSelectedItem(), (int) cbMin.getSelectedItem()));
	if(respostaOk) {
            ControladorTentativaAcesso.getInstance().validaTentativa(matricula, data, hora);
            tfMatricula.setText("");
	}
    }

}
