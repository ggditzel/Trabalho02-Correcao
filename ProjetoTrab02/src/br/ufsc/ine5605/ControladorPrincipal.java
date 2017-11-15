package br.ufsc.ine5605;

import java.util.Collection;

import br.ufsc.ine5605.acesso.ControladorTentativaAcesso;
import br.ufsc.ine5605.cargo.Cargo;
import br.ufsc.ine5605.cargo.MapeadorCargo;

public class ControladorPrincipal {
	private TelaPrincipal tela;
	
	private Collection<Cargo> lista;

	private final String[] opcoesMenuPrincipal = { "Sair", "Gerenciar Sistema", "Iniciar Terminal de Acesso" };

	// para alterar horarios, apenas dentro das operacoes com cargos
	private final String[] opcoesGerenciarSistema = { "Voltar", "Operacoes com Cargos", "Operacoes com Funcionarios",
														"Relatorios de Acesso"};
	private static ControladorPrincipal instancia;

	private ControladorPrincipal() {
		tela = new TelaPrincipal();
    }
    public static ControladorPrincipal getInstance(){
    	if(instancia == null){
    		instancia = new ControladorPrincipal();
    	}
    	return instancia;
    }
	

	public void inicia() {
		/*int opcao = -1;
		do {
			opcao = tela.mostraMenu(opcoesMenuPrincipal);
			switch (opcao) {
			case 0: // sair
				System.exit(0);
				break;
			case 1: // gerenciar sistema
				gerenciarSistema();
				break;
			case 2: // iniciar terminal de acesso
				iniciarTerminal();
			}
		} while (opcao != 0);
	*/
		
		// so pra teste, pra mostrar se ta gravando tudo no arquivo
		// a cada cadastro de cargo, ao abrir o programa a lista deve ter aumentado
		new TelaMenuPrincipal().setVisible(true);;
	}

	public void gerenciarSistema() {
		int opcao = -1;
//		do {
//			opcao = tela.mostraMenu(opcoesGerenciarSistema);
//			switch (opcao) {
//			case 0: // voltar
//				break;
//			case 1: // operacoes com cargos
//				ControladorCargo.getInstance().mostraMenu();
//				break;
//			case 2: // operacoes com funcionarios
//				ControladorFuncionario.getInstance().mostraMenu();
//				break;
//			case 3: // relatiorios de acesso
//				ControladorTentativaAcesso.getInstance().menuRelatorioTentativas();
//				break;
//			}
//		} while (opcao != 0);
		
		new TelaGerenciarSistema().setVisible(true);
	}

	public void iniciarTerminal() {
		ControladorTentativaAcesso.getInstance().iniciaTentativa();
	}
}