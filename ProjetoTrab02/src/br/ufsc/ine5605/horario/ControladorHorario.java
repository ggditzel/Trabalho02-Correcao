package br.ufsc.ine5605.horario;

import java.util.ArrayList;

import br.ufsc.ine5605.HorarioRepetidoException;
import br.ufsc.ine5605.cargo.Cargo;
import br.ufsc.ine5605.cargo.ControladorCargo;


public class ControladorHorario {
    private static ControladorHorario instancia;
    private TelaCadastroHorario telaCadastro;
     

    private ControladorHorario() {

        telaCadastro = new TelaCadastroHorario();
    }
    public static ControladorHorario getInstance(){
    	if(instancia == null){
    		instancia = new ControladorHorario();
    	}
    	return instancia;
    }
     

    /**
     * Realiza o cadastro de horarios para um cargo
     * @return
     *	ArrayList que contem os horarios que serao adicionados ao cargo
     */
    public ArrayList<Horario> iniciaCadastro(){
    	ArrayList<Horario> horariosCargo = new ArrayList<>();
    	telaCadastro.setHorarios(horariosCargo);
    	telaCadastro.setVisible(true);
    	return horariosCargo;

    }

    
    /**
     * Faz a diferenca de 2 ArrayLists  
     * @param lista1
     * 	lista da qual sera tirada a diferenca
     * @param lista2
     * 	lista que tirara a diferenca
     * @return
     *	ArrayList que possui os elementos que pertencem a lista1 mas nao pertencem a lista2
     */
    private ArrayList<Horario> diferenca(ArrayList<Horario> lista1, ArrayList<Horario> lista2) {
    	
    	ArrayList<Horario> diferenca = new ArrayList<>();
    	diferenca.addAll(lista1);
    	diferenca.removeAll(lista2);
    	return diferenca;
    	
    }
    
    /**
     * Verifica se um horario ja pertence a lista de horarios 
     * @param inicio
     * 	inicio do horario que sera comparado
     * @param fim
     * 	final do horario que sera comparado
     * @return
     * true, se o horario ja estiver cadastrado, ou false, se a hora nao estiver cadastrado ainda
     */
    private boolean possuiHorario(Hora inicio, Hora fim, ArrayList<Horario> horarios) {
    	for(Horario i: horarios) {
    		if(i.getInicio().toString().equals(inicio.toString()) && i.getFim().toString().equals(fim.toString())){
    			return true;
    		}
    	}
    	return false;
    }
    /**
    * Verifica se um horario cadastrado esta em uso, se pertence a algum cargo
    * @param horario
    *	Horario que sera verificado
    * @return
    *	true, se o horario estiver sendo utilizado por algum cargo, ou false, se nao estiver
    */
    private boolean horarioEhUtilizado(Horario horario){
    	for(Cargo c: ControladorCargo.getInstance().getListaCargos()) {
    		if(c.getHorariosPermitidos().contains(horario)){
    			return true;
    		}
    	}
    	return false;
    }
     /**
     * Converte um String no formato hh:mm em uma Hora
     * @param hora
     *	String que sera convertido
     * @return
     *	Hora referente ao String do parametro
     */
     public Hora converte(String hora) {
		return new Hora(Integer.parseInt(hora.substring(0,2)), Integer.parseInt(hora.substring(3)));
	}

    /**
     * Verifica se uma lista de horarios possui um horario especifico
     * @param horaInicio
     * @param minInicio
     * @param horaFim
     * @param minFim
     * @param horarios
     * @return 
     */
    private boolean possuiHorario(int horaInicio, int minInicio, int horaFim,
			int minFim, ArrayList<Horario> horarios) {
    	for(Horario i: horarios) {
    		if(i.getInicio().getHora() == horaInicio && i.getInicio().getMinuto() == minInicio && i.getFim().getHora() == horaFim && i.getFim().getMinuto() == minFim){
    			
    			return true;
    		}
    	}
    	return false;
    }
    /**
     * Adiciona um Horario a um cargo
     * @param horaInicio
     * @param minInicio
     * @param horaFim
     * @param minFim
     * @param horariosCargo
     * @throws HorarioRepetidoException caso o cargo ja tenha esse horario
     */
	public void adicionaHorario(int horaInicio, int minInicio, int horaFim,
			int minFim, ArrayList<Horario> horariosCargo) throws HorarioRepetidoException {
		if(!possuiHorario(horaInicio, minInicio, horaFim, minFim, horariosCargo) ) {
			horariosCargo.add(new Horario(horaInicio, minInicio, horaFim, minFim));
		} else {
			throw new HorarioRepetidoException();
		}
	}
}
