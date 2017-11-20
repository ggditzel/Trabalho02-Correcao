package br.ufsc.ine5605;


import br.ufsc.ine5605.acesso.ControladorTentativaAcesso;

public class ControladorPrincipal {
	
	private static ControladorPrincipal instancia;

	private ControladorPrincipal() {

    }
    public static ControladorPrincipal getInstance(){
    	if(instancia == null){
    		instancia = new ControladorPrincipal();
    	}
    	return instancia;
    }
	
    /**
    * Abre a tela do menu principal do sistema
    */
    public void inicia() {
        new TelaMenuPrincipal().setVisible(true);
    }
    /**
    * Abre a tela de gerenciar sistema 
    */
    public void gerenciarSistema() {
	new TelaGerenciarSistema().setVisible(true);
    }
    /**
     * Abre a tela do teminal de acesso
     */
    public void iniciarTerminal() {
        ControladorTentativaAcesso.getInstance().iniciaTentativa();
    }
    /**
     * Abre a tela do terminal de acesso com horario ficticio
     */
    public void iniciarTerminalFicticio() {
        ControladorTentativaAcesso.getInstance().iniciaTeste();
    }
}
