package br.ufsc.ine5605;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

public abstract class TelaComGridBagLayout extends JFrame {
	
	protected GridBagLayout layout;
	protected GridBagConstraints constraints;
	//protected Container container;
	
	public TelaComGridBagLayout() {
		this("Título Padrão");
	}

	public TelaComGridBagLayout (String titulo) {
		
		super(titulo);
		layout = new GridBagLayout();
		setLayout(layout);
		constraints = new GridBagConstraints();
	}
	
	/**
	 * Adiciona o componente ao GridBagLayout, tomando como parametros as "constraints".
	 * Outras constraints para o componente a ser adicionado devem ser declaradas externamente,
	 * antes de chamar este metodo.
	 * 
	 * @param componente componente a ser adicionado ao layout
	 * @param x posicao X do componente no grid
	 * @param y posicao Y do componente no grid
	 * @param largura largura do componente
	 * @param altura altura do componente
	 */
	protected void adicionaComponente (Component componente, int x, int y, int largura, int altura) {
		this.constraints.gridx = x;
		this.constraints.gridy = y;
		this.constraints.gridwidth = largura;
		this.constraints.gridheight = altura;
		add(componente, this.constraints);
	}
	
}
