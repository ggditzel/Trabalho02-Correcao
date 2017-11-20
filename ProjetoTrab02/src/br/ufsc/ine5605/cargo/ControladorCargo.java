package br.ufsc.ine5605.cargo;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JOptionPane;

import br.ufsc.ine5605.CargoComFuncionarioAssociadoException;
import br.ufsc.ine5605.funcionario.ControladorFuncionario;
import br.ufsc.ine5605.funcionario.Funcionario;
import br.ufsc.ine5605.horario.ControladorHorario;
import br.ufsc.ine5605.horario.Horario;

public class ControladorCargo {
	private ArrayList<Cargo> listaCargos;
	private TelaOperacoesCargos telaOperacoesCargos;
	private TelaCadastroCargo telaCadastroCargo;
	private MapeadorCargo mapeador;
	private static ControladorCargo instancia;
	
	private ControladorCargo() {
        listaCargos = new ArrayList<>();
        mapeador = new MapeadorCargo();
    }
	
    public static ControladorCargo getInstance(){
    	if(instancia == null){
    		instancia = new ControladorCargo();
    	}
    	return instancia;
    }
    
    /**
     * 
     * @return lista de objetos do tipo Cargo, lidos do arquivo
     */
    public Collection<Cargo> getListaCargos() {
    	return mapeador.getList();
    }
    
    /**
     * Chama a tela que mostra o menu principal (relacionado a Cargos)
     */
    public void mostraMenu(){

    	telaOperacoesCargos = new TelaOperacoesCargos();
    	telaOperacoesCargos.setVisible(true);
    }
	

    /**
     * Chama tela para inclusao de cargos
     */
    public void telaIncluirCargo(){
    	telaCadastroCargo = new TelaCadastroCargo();
		telaCadastroCargo.setVisible(true);
    }
    
    /**
     * Chama tela para edicao de cargos
     * @param codigo codigo do cargo a ser alterado
     */
    public void alterarCargo(int codigo) {
    	TelaAlterarCargo telaAlterarCargo = new TelaAlterarCargo(findCargoByCodigo(codigo));
    	telaAlterarCargo.setVisible(true);
    }
    
    /**
     * Atualiza a referencia do cargo no arquivo de persistencia
     * @param cargo a ser atualizado
     */
    public void gravarCargo(Cargo cargo) {
    	mapeador.put(cargo);
    }
		
    /**
     * Inclui um novo cargo no arquivo de persistencia	
     * @param cargo objeto com as informacoes para criacao do novo cargo
     */
	public void incluirCargo(DadosCadastroCargo cargo) { 
		
		if (mapeador.get(cargo.codigo) == null ) {		
				mapeador.put(new Cargo (cargo.codigo, cargo.nome, cargo.ehGerencial, cargo.possuiAcesso, cargo.horariosPermitidos));
		} else {
			JOptionPane.showMessageDialog(null, "Ja existe cargo cadastrado com este codigo, tente novamente");
		}
	}
	
	
	/**
	 * Exclui um cargo do arquivo, desde que nao haja nenhum funcionario associado a ele.
	 * @param codigo codigo do cargo a ser excluido
	 * @throws CargoComFuncionarioAssociadoException excecao para o caso de ja haver funcionario associado
	 */
    public void excluirCargo(int codigo) throws CargoComFuncionarioAssociadoException {
    	
    	for (Funcionario f : ControladorFuncionario.getInstance().listarFuncionarios()) {
    		if (f.getCargo().toString().equals(mapeador.get(codigo).toString())) {
    			throw new CargoComFuncionarioAssociadoException();
    		}
    	}

    	mapeador.remove(mapeador.get(codigo));

	}
	
	/**
	 * Retorna um cargo associado a um codigo de busca
	 * @param codigo codigo do cargo a ser buscado
	 * @return Cargo solicitado
	 */
    public Cargo findCargoByCodigo(int codigo){
		return mapeador.get(codigo);
	}


	/**
	 * Chama o controlador de horarios para iniciar o cadastro de horarios pertmitidos
	 * @return
	 */
    public ArrayList<Horario> pegaHorarios() {
		
		return ControladorHorario.getInstance().iniciaCadastro();
	}
	
	/**
	 * Atualiza a tabela que mostra a lista de cargos na tela de operacoes com cargos 
	 */
    public void atualizaTabela() {
		telaOperacoesCargos.atualizaTabela();
	}

}
