package br.ufsc.ine5605.acesso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import br.ufsc.ine5605.funcionario.ControladorFuncionario;
import br.ufsc.ine5605.funcionario.Funcionario;
import br.ufsc.ine5605.horario.ControladorHorario;
import br.ufsc.ine5605.horario.Hora;


public class ControladorTentativaAcesso {
	private TelaTentativaAcesso tela;
	private ArrayList<TentativaAcesso> tentativas;
	private static ControladorTentativaAcesso instancia;
	private TelaAcesso telaAcesso;
	private TelaRelatorios telaRelatorios;
	private int ultimoIDtentativa;
	private MapeadorTentativaAcesso mapeador;
    
    private ControladorTentativaAcesso() {
    	tentativas = new ArrayList<>();
        tela = new TelaTentativaAcesso();
        telaAcesso = new TelaAcesso();
        mapeador = new MapeadorTentativaAcesso();
        ultimoIDtentativa = mapeador.getList().size();
    }
    public static ControladorTentativaAcesso getInstance(){
    	if(instancia == null){
    		instancia = new ControladorTentativaAcesso();
    	}
    	return instancia;
    }
	/**
	* Realiza uma tentativa de acesso, fazendo a validacao do acesso, criando um instancia de tentativa de acesso
	* e adicionando-a no ArrayList da classe
	*/
	public void iniciaTentativa() { 
		/*int opcao = tela.mostraOpcoes();
		String data;
		Hora hora;
		if(opcao == 0){
			return;
		} else if(opcao == 1) {
			java.util.Date agora = new java.util.Date();
			SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
			data = formata.format(agora);
			formata = new SimpleDateFormat("HH:mm");
			hora = ControladorHorario.getInstance().converte(formata.format(agora));
		} else {
			hora = ControladorHorario.getInstance().converte(tela.perguntaHora());
			data = tela.perguntaData();
		}
		int matricula = tela.perguntaMatricula();
		Funcionario funcionario = ControladorFuncionario.getInstance().findFuncionarioByMatricula(matricula);
		if(funcionario == null) {
			tentativas.add(new TentativaAcessoNegado(data, hora, matricula, MotivoNegacaoAcesso.MATRICULA_INEXISTENTE) );
			tela.mostraNegacao(MotivoNegacaoAcesso.MATRICULA_INEXISTENTE);
		} else if(!funcionario.getCargo().getPossuiAcesso()){
			tentativas.add(new TentativaAcessoNegado(data, hora, matricula, MotivoNegacaoAcesso.NAO_POSSUI_ACESSO) );
			funcionario.setNumeroAcessosNegados(funcionario.getNumeroAcessosNegados() + 1);
			tela.mostraNegacao(MotivoNegacaoAcesso.NAO_POSSUI_ACESSO);
		} else if(funcionario.getNumeroAcessosNegados() >= 3) {
			tentativas.add(new TentativaAcessoNegado(data, hora, matricula, MotivoNegacaoAcesso.ACESSO_BLOQUEADO) );
			funcionario.setNumeroAcessosNegados(funcionario.getNumeroAcessosNegados() + 1);
			tela.mostraNegacao(MotivoNegacaoAcesso.ACESSO_BLOQUEADO);
		} else if(!funcionario.getCargo().getEhGerencial() && !hora.estaPresente(funcionario.getCargo().getHorariosPermitidos()) ) {
			tentativas.add(new TentativaAcessoNegado(data, hora, matricula, MotivoNegacaoAcesso.HORARIO_NAO_PERMITIDO) );
			funcionario.setNumeroAcessosNegados(funcionario.getNumeroAcessosNegados() + 1);
			tela.mostraNegacao(MotivoNegacaoAcesso.HORARIO_NAO_PERMITIDO);
		}  else {
			tentativas.add(new TentativaAcessoPermitido(data, hora, matricula));
			tela.confirmaAcesso();
		}*/
		telaAcesso.setVisible(true);
	}
	/**
	* Chama a tela relacionada ao menu de tentativas e direciona de acordo com a resposta do usuario
	*/
	public void menuRelatorioTentativas(){
		if(telaRelatorios == null) {
			telaRelatorios = new TelaRelatorios();
		}
		telaRelatorios.setVisible(true);
		
		/*while(true){
		int opcao = tela.mostraMenuTentativas();
		switch(opcao){
				
				case 0:
					return;
				case 1:
					tela.listaTentativas(tentativas);
					break;
				case 2:
					tela.listaTentativas(getTentativasNegadas());
					break;
				case 3:
					tela.listaTentativas(getAcessos());
					break;
				case 4:
					int matricula = tela.perguntaMatricula();
					tela.listaTentativas(findTentativasByMatricula(matricula));
					break;
				case 5:
					matricula = tela.perguntaMatricula();
					tela.listaTentativas(findTentativasNegadasByMatricula(matricula));
					break;
				case 6:
					matricula = tela.perguntaMatricula();
					tela.listaTentativas(findAcessosByMatricula(matricula));
					break;
				case 7:
					int valorMotivo = tela.perguntaMotivo();
					if(valorMotivo == 0) break;
					MotivoNegacaoAcesso motivo = valorMotivo == 1 ? MotivoNegacaoAcesso.ACESSO_BLOQUEADO : valorMotivo == 2 ? MotivoNegacaoAcesso.HORARIO_NAO_PERMITIDO : valorMotivo == 3 ? MotivoNegacaoAcesso.MATRICULA_INEXISTENTE : MotivoNegacaoAcesso.NAO_POSSUI_ACESSO;
					tela.listaTentativas(findTentativasNegadasByMotivo(motivo));
					break;
			}
		}*/
		
		
	}
	
	
	public ArrayList<TentativaAcesso> getTentativasNegadas() {
		ArrayList<TentativaAcesso> tentativasNegadas = new ArrayList<>();
		
		for(TentativaAcesso t: mapeador.getList()){
			if(t instanceof TentativaAcessoNegado){
				tentativasNegadas.add(t);
			}
		}
		return tentativasNegadas;
	}
	
	public ArrayList<TentativaAcesso> getAcessos() {
		ArrayList<TentativaAcesso> acessos = new ArrayList<>();
		for(TentativaAcesso t: mapeador.getList()){
			if(t instanceof TentativaAcessoPermitido){
				acessos.add(t);
			}
		}
		return acessos;
	}
	/**
	* Encontra todas as tentativas de acesso de uma matricula
	* @param matricula
	*	Matricula que sera usada para encontrar as tentativas
	* @return
	*	ArrayList com todas as tentativas da matricula
	*/
	public ArrayList<TentativaAcesso> findTentativasByMatricula(int matricula) {
		ArrayList<TentativaAcesso> tentativasDaMatricula = new ArrayList<>();
		for(TentativaAcesso t: mapeador.getList()) {
			if(t.getMatricula() == matricula) {
				tentativasDaMatricula.add(t);
			}
		}
		return tentativasDaMatricula;
	}
	/**
	* Encontra todas as tentativas de acesso negadas de uma matricula
	* @param matricula
	*	Matricula que sera usada para encontrar as tentativas
	* @return
	*	ArrayList com todas as tentativas negadas da matricula
	*/
	public ArrayList<TentativaAcesso> findTentativasNegadasByMatricula(int matricula) {
		ArrayList<TentativaAcesso> tentativasDaMatricula = new ArrayList<>();
		for(TentativaAcesso t: findTentativasByMatricula(matricula)) {
			if(t instanceof TentativaAcessoNegado){
				tentativasDaMatricula.add(t);
			}
		}
		return tentativasDaMatricula;
	}
	/**
	* Encontra todos os acesso de uma matricula
	* @param matricula
	*	Matricula que sera usada para encontrar os acessos
	* @return
	*	ArrayList com todas os acessos da matricula
	*/
	public ArrayList<TentativaAcesso> findAcessosByMatricula(int matricula) {
		ArrayList<TentativaAcesso> acessosDaMatricula = new ArrayList<>();
		for(TentativaAcesso t: findTentativasByMatricula(matricula)) {
			if(t instanceof TentativaAcessoPermitido){
				acessosDaMatricula.add(t);
			}
		}
		return acessosDaMatricula;
	}
	/**
	* Encontra todas as tentativas de acesso negadas por um motivo
	* @param motivo
	*	Motivo que sera usada para encontrar as tentativas negadas
	* @return
	*	ArrayList com todas as tentativas negadas pelo motivo
	*/
	public ArrayList<TentativaAcesso> findTentativasNegadasByMotivo(MotivoNegacaoAcesso motivo) {
		ArrayList<TentativaAcesso> tentativasDoMotivo = new ArrayList<>();
		for(TentativaAcesso t: mapeador.getList()) {
			if(t instanceof TentativaAcessoNegado) {	
				if(((TentativaAcessoNegado) (t)).getMotivo().equals(motivo)){
					tentativasDoMotivo.add(t);
				}
			}
		}
		return tentativasDoMotivo;
	}
	public void validaTentativa(int matricula, String data, Hora hora) {
		java.util.Date agora = new java.util.Date();
		SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
		data = formata.format(agora);
		formata = new SimpleDateFormat("HH:mm");
		hora = ControladorHorario.getInstance().converte(formata.format(agora));
		Funcionario funcionario = ControladorFuncionario.getInstance().findFuncionarioByMatricula(matricula);
		if(funcionario == null) {
			tentativas.add(new TentativaAcessoNegado(data, hora, matricula, MotivoNegacaoAcesso.MATRICULA_INEXISTENTE, ultimoIDtentativa + 1) );
			mapeador.put(new TentativaAcessoNegado(data, hora, matricula, MotivoNegacaoAcesso.MATRICULA_INEXISTENTE, ultimoIDtentativa + 1));
			telaAcesso.mostraNegacao(MotivoNegacaoAcesso.MATRICULA_INEXISTENTE);
		} else if(!funcionario.getCargo().getPossuiAcesso() && !funcionario.getCargo().getEhGerencial()){
			tentativas.add(new TentativaAcessoNegado(data, hora, matricula, MotivoNegacaoAcesso.NAO_POSSUI_ACESSO, ultimoIDtentativa + 1) );
			mapeador.put(new TentativaAcessoNegado(data, hora, matricula, MotivoNegacaoAcesso.NAO_POSSUI_ACESSO, ultimoIDtentativa + 1) );
			funcionario.setNumeroAcessosNegados(funcionario.getNumeroAcessosNegados() + 1);
			telaAcesso.mostraNegacao(MotivoNegacaoAcesso.NAO_POSSUI_ACESSO);
		} else if(funcionario.getNumeroAcessosNegados() >= 3) {
			tentativas.add(new TentativaAcessoNegado(data, hora, matricula, MotivoNegacaoAcesso.ACESSO_BLOQUEADO, ultimoIDtentativa + 1) );
			mapeador.put(new TentativaAcessoNegado(data, hora, matricula, MotivoNegacaoAcesso.ACESSO_BLOQUEADO, ultimoIDtentativa + 1) );
			funcionario.setNumeroAcessosNegados(funcionario.getNumeroAcessosNegados() + 1);
			telaAcesso.mostraNegacao(MotivoNegacaoAcesso.ACESSO_BLOQUEADO);
		} else if(!funcionario.getCargo().getEhGerencial() && !hora.estaPresente(funcionario.getCargo().getHorariosPermitidos()) ) {
			tentativas.add(new TentativaAcessoNegado(data, hora, matricula, MotivoNegacaoAcesso.HORARIO_NAO_PERMITIDO, ultimoIDtentativa + 1) );
			mapeador.put(new TentativaAcessoNegado(data, hora, matricula, MotivoNegacaoAcesso.HORARIO_NAO_PERMITIDO, ultimoIDtentativa + 1) );
			funcionario.setNumeroAcessosNegados(funcionario.getNumeroAcessosNegados() + 1);
			telaAcesso.mostraNegacao(MotivoNegacaoAcesso.HORARIO_NAO_PERMITIDO);
		}  else {
			tentativas.add(new TentativaAcessoPermitido(data, hora, matricula, ultimoIDtentativa + 1));
			mapeador.put(new TentativaAcessoPermitido(data, hora, matricula, ultimoIDtentativa + 1));
			telaAcesso.confirmaAcesso();
		}
		ultimoIDtentativa++;
		
	}
	public Hora converteHora(String hora) {
		return ControladorHorario.getInstance().converte(hora);
	}
	public Collection<TentativaAcesso> getTentativas() {
		return mapeador.getList();
	}
	public Collection<TentativaAcesso> findTentativasByMotivo(
			MotivoNegacaoAcesso selectedItem) {
	
		return null;
	}
	




}
