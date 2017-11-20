package br.ufsc.ine5605.acesso;

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
        private TelaAcessoFicticio telaAcessoFicticio;
	private TelaRelatorios telaRelatorios;
	private int ultimoIDtentativa;
	private MapeadorTentativaAcesso mapeador;
    
    private ControladorTentativaAcesso() {
    	tentativas = new ArrayList<>();
        tela = new TelaTentativaAcesso();
        telaAcessoFicticio = new TelaAcessoFicticio();
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
	* Abre a janela do terminhal de acesso
	*/
	public void iniciaTentativa() { 
		
		telaAcesso.setVisible(true);
	}
        /**
         * Abre a janela do terminal de acesso com horarios ficticios
         */
        public void iniciaTeste() { 
            telaAcessoFicticio.setVisible(true);
        }
	/**
	* Abre a tela de relatorios 
	*/
	public void menuRelatorioTentativas(){
		if(telaRelatorios == null) {
			telaRelatorios = new TelaRelatorios();
		}
		telaRelatorios.setVisible(true);
		
	}
	
	/**
         * Pega as tentativas de acesso negadas
         * @return ArrayList com as tentativas negadas
         */
	public ArrayList<TentativaAcesso> getTentativasNegadas() {
		ArrayList<TentativaAcesso> tentativasNegadas = new ArrayList<>();
		
		for(TentativaAcesso t: mapeador.getList()){
			if(t instanceof TentativaAcessoNegado){
				tentativasNegadas.add(t);
			}
		}
		return tentativasNegadas;
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
        /**
         * Valida uma tentativa de acesso de acordo com as regras do trabalho
         * @param matricula
         * @param data
         * @param hora 
         */
	public void validaTentativa(int matricula, String data, Hora hora) {
		
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

	




}
