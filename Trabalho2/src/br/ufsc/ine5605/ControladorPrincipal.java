package br.ufsc.ine5605;

import java.util.ArrayList;

import br.ufsc.ine5605.acesso.ControladorTentativaAcesso;
import br.ufsc.ine5605.cargo.ControladorCargo;
import br.ufsc.ine5605.cargo.TelaCadastroCargo;
import br.ufsc.ine5605.funcionario.ControladorFuncionario;
import br.ufsc.ine5605.horario.ControladorHorario;
import br.ufsc.ine5605.horario.TelaHorario;

public class ControladorPrincipal {
	private TelaPrincipal tela;

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
		new TelaMenuPrincipal();
	}

	public void gerenciarSistema() {
		int opcao = -1;
		do {
			opcao = tela.mostraMenu(opcoesGerenciarSistema);
			switch (opcao) {
			case 0: // voltar
				break;
			case 1: // operacoes com cargos
				ControladorCargo.getInstance().mostraMenu();
				break;
			case 2: // operacoes com funcionarios
				ControladorFuncionario.getInstance().mostraMenu();
				break;
			case 3: // relatiorios de acesso
				ControladorTentativaAcesso.getInstance().menuRelatorioTentativas();
				break;
			}
		} while (opcao != 0);
	}

	public void iniciarTerminal() {
		ControladorTentativaAcesso.getInstance().iniciaTentativa();
	}
}