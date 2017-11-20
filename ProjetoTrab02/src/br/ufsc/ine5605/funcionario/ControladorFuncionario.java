package br.ufsc.ine5605.funcionario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JOptionPane;

import br.ufsc.ine5605.cargo.Cargo;
import br.ufsc.ine5605.cargo.ControladorCargo;

public class ControladorFuncionario {
	private MapeadorFuncionario mapeadorF;
	private Collection<Funcionario> listaFuncionarios;
	private Funcionario funcEdit;
	private static ControladorFuncionario instancia;
	private TelaFuncionario tela;
	private TelaInformFuncionario telaCadastroFuncionario;
	private TelaInformFuncionario telaEditFuncionario;
	private TelaOperacoesFuncionarios telaOperacoesFuncionarios;

	/****** OPCOES DO MENU PRINCIPAL ******/
	private final String[] opcoesMenuPrincipal = { "Voltar", "Listar Funcionarios Cadastrados", "Cadastrar Funcionario",
			"Excluir Funcionario", "Alterar Cadastro Funcionario" };

	/****** OPCOES DO MENU PARA EDICAO DOS FUNCIONARIOS ******/
	private final String[] opcoesMenuEditarFuncionario = { "Voltar", "Alterar Matricula", "Alterar Nome",
			"Alterar Cargo", "Alterar Telefone", "Alterar dataNascimento", "Alterar Salario" };

	private ControladorFuncionario() {
		this.listaFuncionarios = new ArrayList<>();
		mapeadorF = new MapeadorFuncionario();
	}

	public static ControladorFuncionario getInstance() {
		if (instancia == null) {
			instancia = new ControladorFuncionario();
		}
		return instancia;
	}

	public Collection<Funcionario> getListaFuncionarios() {
		return mapeadorF.getList();
	}

	public void incluirFuncionario() {

		DadosCadastroFuncionario funcionario = tela.IncluirFuncionario();

		if (findFuncionarioByMatricula(funcionario.matricula) == null
				&& findFuncionarioByNome(funcionario.nome) == null) {
			listaFuncionarios.add(new Funcionario(funcionario.matricula, funcionario.nome, funcionario.cargo,
					funcionario.telefone, funcionario.dataNascimento, funcionario.salario));

			tela.mostraMensagem("Funcionario cadastrado com sucesso.");
		} else {
			tela.mostraMensagem(
					"Jï¿½ existe cadastro com esta matrï¿½cula e ou com este nome no sistema, cheque a lista e tente novamente");
		}

	}

	/**
	 * Chama a tela que mostra o menu principal (relacionado a Funcionarios)
	 */
	public void mostraMenu() {
		int opcao = -1;
		do {
			opcao = tela.mostraMenu(opcoesMenuPrincipal);
			switch (opcao) {
			case 0:
				break;
			case 1:
				printarFuncionariosCompleto();
				break;
			case 2:
				incluirFuncionario();
				break;
			case 3:
				if (!listaFuncionarios.isEmpty()) {
					excluirFuncionario();
				} else {
					tela.mostraMensagem("Nao ha funcionarios para serem excluidos");
				}
				break;
			case 4:
				if (!listaFuncionarios.isEmpty()) {
					alterarCadastroFuncionario();
				} else {
					tela.mostraMensagem("Nao ha funcionarios para editar");
				}
				break;
			}
		} while (opcao != 0);
	}

	public void mostraMenuEd() {
		int opcao = -1;
		boolean eae = false;
		do {
			eae = false;

			opcao = tela.mostraMenu(opcoesMenuEditarFuncionario);
			switch (opcao) {
			case 0:
				eae = true;
				break;

			case 1:
				printarFuncionariosCompleto();
				boolean eae1 = false;
				tela.mostraMensagem("Digite a matricula(vide lista) do funcionario cuja matricula sera alterada");
				int matEnt = 0;
				int matNov = 0;
				do {
					matEnt = tela.leInteiroPositivo();
					if (findFuncionarioByMatricula(matEnt) == null) {
						tela.mostraMensagem("Esta matrï¿½cula nï¿½o estï¿½ cadastrada, tente novamente");
						printarFuncionariosCompleto();
					} else {
						tela.mostraMensagem(
								"Funcionario de matricula '" + matEnt + "' selecionado, digite a nova matricula");
						matNov = tela.leInteiroPositivo();
						if (findFuncionarioByMatricula(matNov) != null) {
							tela.mostraMensagem(
									"Este numero de matricula ja esta vinculado a outro cadastro, tente novamente");

						} else {
							findFuncionarioByMatricula(matEnt).setMatricula(matNov);
							eae1 = true;
							tela.mostraMensagem("Matricula alterada com sucesso");
						}
					}

				} while (!eae1);
				break;

			case 2:
				printarFuncionariosCompleto();
				tela.mostraMensagem("Digite a matricula(vide lista) do funcionario");

				alteraNome();
				break;
			case 3:
				printarFuncionariosCompleto();
				Funcionario funcA = null;
				do {
					tela.mostraMensagem("Digite a matricula do funcionario cujo cargo serï¿½ alterado");
					int maat = tela.leInteiroPositivo();
					if (findFuncionarioByMatricula(maat) != null) {
						funcA = findFuncionarioByMatricula(maat);
						eae = true;
					} else {
						tela.mostraMensagem("Matricula invalida");
					}
				} while (!eae);
				boolean eae2 = false;
				do {

					tela.mostraMensagem("Digite o cï¿½digo do cargo da lista");
					printaListaCargoNomeCodigoHorario();

					int novoCc = tela.leInteiroPositivo();
					if (ControladorCargo.getInstance().findCargoByCodigo(novoCc) == null) {
						tela.mostraMensagem("Codigo de cargo invalido, selecione codigo da lista");
					} else {
						funcA.setCargo(ControladorCargo.getInstance().findCargoByCodigo(novoCc));
						eae2 = true;
					}
				} while (!eae2);
				break;
			case 4:
				printarFuncionariosCompleto();
				tela.mostraMensagem("Digite a matricula do funcionario cujo telefone serï¿½ alterado");
				int mat = tela.leInteiroPositivo();
				eae = false;
				do {

					if (findFuncionarioByMatricula(mat) != null) {

						tela.mostraMensagem("Digite o novo telefone do funcionario:");
						String telefone = tela.leitor.nextLine();
						if (telefone.matches("^[0-9]+$")) {
							tela.mostraMensagem("Telefone alterado com sucesso");
							findFuncionarioByMatricula(mat).setTelefone(telefone);

							eae = true;
						} else {

							System.out.println("DIGITE APENAS NUMEROS");
						}

					} else {
						tela.mostraMensagem("Matricula invalida, cheque a lista e digite novamente");

						mat = tela.leInteiroPositivo();

					}
				} while (!eae);
				break;

			case 5:
				printarFuncionariosCompleto();
				tela.mostraMensagem("Digite a matricula do funcionario cuja Data de Nascimento serï¿½ alterada");
				mat = tela.leInteiroPositivo();
				eae = false;
				do {

					if (findFuncionarioByMatricula(mat) != null) {

						System.out.println("digite a data de nascimento do funcionario(No formato ddMMaaaa)");
						String dataNascimento = tela.leitor.nextLine();
						if (dataNascimento.matches("^[0-9]{8}$")) {

							findFuncionarioByMatricula(mat).setDataNascimento(dataNascimento);
							tela.mostraMensagem("Data de nascimento alterada para :"
									+ (formataDataPraPrintar(findFuncionarioByMatricula(mat).getDataNascimento())));
							eae = true;
						}
					} else {
						tela.mostraMensagem("Digite uma matricula valida");
						mat = tela.leInteiroPositivo();
					}
				} while (!eae);
				break;

			case 6:
				printarFuncionariosCompleto();
				tela.mostraMensagem("Digite a matricula do funcionario cujo salï¿½rio serï¿½ alterado");
				mat = tela.leInteiroPositivo();
				eae = false;
				do {

					if (findFuncionarioByMatricula(mat) != null) {
						System.out.println("digite o salario do Funcionario");
						int salario = tela.leInteiroPositivo();
						findFuncionarioByMatricula(mat).setSalario(salario);
						tela.mostraMensagem("Salï¿½rio alterado para: " + salario + " com sucesso.");
						eae = true;
					} else {
						tela.mostraMensagem("Digite uma matricula valida");
						mat = tela.leInteiroPositivo();
					}

				} while (!eae);

			}
		} while (!eae);

	}

	private void alteraNome() {

		boolean eae = false;
		do {
			int mat = tela.leInteiroPositivo();

			if (findFuncionarioByMatricula(mat) != null) {
				tela.mostraMensagem("Funcionario de matricula " + mat + " selecionado para alteraï¿½ï¿½o de nome.");
				String nomeT = tela.pedeNome();
				findFuncionarioByMatricula(mat).setNome(nomeT);
				printarFuncionariosCompleto();
				eae = true;
			} else {
				tela.mostraMensagem("Matricula Invalida, cheque novamente a lista");
				printarFuncionariosCompleto();
			}

		} while (!eae);

	}

	private void alterarCadastroFuncionario() {
		mostraMenuEd();

	}

	public void excluirFuncionario() {
		int matricula = tela.leInteiroPositivo();
		if (findFuncionarioByMatricula(matricula) != null) {
			Funcionario funcionario = findFuncionarioByMatricula(matricula);
			listaFuncionarios.remove(funcionario);
			tela.mostraMensagem("Funcionaro de Matricula " + matricula + " foi removido do sistema.");
		} else
			tela.mostraMensagem("Nï¿½o existe Funcionï¿½rio cadastrado com este nï¿½mero de matrï¿½cula");
	}

	public Collection<Funcionario> listarFuncionarios() {
		return mapeadorF.getList(); // retorna para imprimir na tela
	}

	public ArrayList<Funcionario> listarFuncionariosPorCargo(int codigo) {
		ArrayList<Funcionario> listaPorCargo = new ArrayList<>();
		for (Funcionario f : listaFuncionarios) {
			if (f.getCargo().getCodigo() == codigo) {
				listaPorCargo.add(f);
			}
		}
		return listaPorCargo; // retorna para imprimir na tela
	}

	public Funcionario findFuncionarioByMatricula(int matricula) {
		return mapeadorF.get(matricula);
	}

	public Funcionario findFuncionarioByNome(String nome) {
		Funcionario funcionario = null;
		for (Funcionario f : listaFuncionarios) {
			if (f.getNome().equals(nome)) {
				funcionario = f;
			}
		}
		return funcionario;
	}

	/*
	 * Printa lista no formato: Codigo Cargo (a) + Nome Cargo (a) Horarios de acesso
	 * do (a)
	 */

	public void printaListaCargoNomeCodigoHorario() {

		// tela.mostraMensagem(
		// "===Lista de Cargos possui " +
		// ControladorCargo.getInstance().getListaCargos().size() + " cargos===");

		// for (int a = 0; a < ControladorCargo.getInstance().getListaCargos().size();
		// a++) {
		// tela.mostraMensagem(
		// " ---/Cï¿½digo :" +
		// (ControladorCargo.getInstance().getListaCargos().get(a).getCodigo()));
		// tela.mostraMensagem(" ---/Cargo: " +
		// (ControladorCargo.getInstance().getListaCargos().get(a).getNome()));
		// if (ControladorCargo.getInstance().getListaCargos().get(a).getEhGerencial())
		// {
		// tela.mostraMensagem("----CARGO COM ACESSO IRRESTRITO AO FINANCEIRO----");
		// } else {
		// tela.mostraMensagem("Este cargo pode acessar o financeiro durante o intervalo
		// :"
		// +
		// (ControladorCargo.getInstance().getListaCargos().get(a).getHorariosPermitidos().toString()));

	}
	// tela.mostraMensagem("==============================================");
	// }

	// }

	public String formataDataPraPrintar(String a) {
		String dataPraPrintar;

		dataPraPrintar = a.substring(0, 2) + "/";
		dataPraPrintar = dataPraPrintar + a.substring(2, 4) + "/";
		dataPraPrintar = dataPraPrintar + a.substring(4);
		return dataPraPrintar;
	}

	public void printarFuncionariosCompleto() {
		if (listaFuncionarios.size() == 0) {
			tela.mostraMensagem("===Lista de Funcionï¿½rios vazia===");

		}
		for (int a = 0; a < listaFuncionarios.size(); a++) {
			// Funcionario fTemp = (listarFuncionarios().get(a));
			// tela.mostraMensagem("---/NOME " + (fTemp.getNome()) + " ---/MATRICULA :" +
			// (fTemp.getMatricula())
			// + " ---/CARGO : " + (fTemp.getCargo().getNome()) + " ---/TELEFONE : " +
			// (fTemp.getTelefone())
			// + " ---/DATA NASCIMENTO : " +
			// (formataDataPraPrintar(fTemp.getDataNascimento()))
			// + " ---/SALARIO : " + (fTemp.getSalario()));
			tela.mostraMensagem("=");
		}
	}
	//===========================================================================================================================
	//===========================================================================================================================
	//===========================================================================================================================
	//===========================================================================================================================
	//===========================================================================================================================
	
	//Daqui pra frente codigo novo
	/*
	 * Chama a tela principal, com a lista de funcionarios em um Grid
	 */
	public void telaOperacoesFuncionarios() {
		telaOperacoesFuncionarios = new TelaOperacoesFuncionarios();
		telaOperacoesFuncionarios.setVisible(true);
	}
	
	/*
	 * Chama a tela de cadastro de funcionarios.
	 */
	public void telaCadastroFuncionario() {
		if (!ControladorCargo.getInstance().getListaCargos().isEmpty()) {
			telaCadastroFuncionario = new TelaInformFuncionario();
			telaCadastroFuncionario.setVisible(true);
		} else {
			telaOperacoesFuncionarios.erroNaoTemCargo();
		}
	}

	/*
	 * Insere na lista de funcionarios o novo funcionario e da SetVisible FALSE na
	 * janela
	 */
	public void novoIncluirFuncionario(DadosCadastroFuncionario funcionario) {
		if ((mapeadorF.get(funcionario.matricula)) != null) {
			telaCadastroFuncionario.erroMatEmUso();
		} else {
			Funcionario funcTemp = new Funcionario(funcionario.matricula, funcionario.nome, funcionario.cargo,
					funcionario.telefone, funcionario.dataNascimento, funcionario.salario);
			telaCadastroFuncionario.msgCadOK();
			telaCadastroFuncionario.setVisible(false);
			mapeadorF.put(funcTemp);
			mapeadorF.persist();
			telaOperacoesFuncionarios.atualizaTabela();
		}

	}
	

	/*
	 * recebe como parametro um inteiro, referente a matricula de um funcionario selecionado
	 * o objeto funcEdit guarda o funcionario a ser editado
	 */
	public void telaEditarFuncionario(int matricula) {
		funcEdit = mapeadorF.get(matricula);
		telaEditFuncionario = new TelaInformFuncionario(funcEdit);
		telaEditFuncionario.setVisible(true);
	}
	/*
	 * Chama o mapeador para excluir o funcionario cuja matricula é a entrada do metodo
	 */
	public void excluiFuncionarioByMatricula(int matricula) {
		mapeadorF.remove(mapeadorF.get(matricula));

	}
	/*
	 * recebe int matricula, String nome, Cargo cargoEditado, String telefone,
			String dataNascimento, int salario
			edita o funcionario selecionado
			atualiza a tabela da telaOperacoesFuncionarios
			envia mensagem de confirmacao ou de falha
	 */

	public void editarTodosOsDados(int matricula, String nome, Cargo cargoEditado, String telefone,
			String dataNascimento, int salario) {
		if (mapeadorF.get(matricula) == null) {
			funcEdit.setNome(nome);
			funcEdit.setCargo(cargoEditado);
			funcEdit.setDataNascimento(dataNascimento);
			funcEdit.setSalario(salario);
			funcEdit.setTelefone(telefone);
			mapeadorF.persist();
			telaOperacoesFuncionarios.atualizaTabela();
			telaEditFuncionario.msgEditOk();
			telaEditFuncionario.setVisible(false);
		}
		else {
			telaEditFuncionario.erroMatEmUso();
		}
	}
}
