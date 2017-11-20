package br.ufsc.ine5605;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JOptionPane;

import br.ufsc.ine5605.cargo.Cargo;

public class MapeadorGenerico<K, T extends Mapeavel<K>> {
	   private HashMap<K, T> cache = new HashMap<>();
	   
	    private String nomeArquivo;
	   
	   
	    public MapeadorGenerico(String nomeArquivo){
	        this.nomeArquivo = nomeArquivo;
	    	load(); 
	    }
	    /**
             * Adiciona um item na lista
             * @param objeto item que sera adicionado na lista
             */
	    public void put(T objeto){
	    	cache.put(objeto.getID(), objeto);
	        persist();
	    }
	   /**
            * Pega um elemento da lista
            * @param codigo identificador do objeto
            * @return objeto que possui o identificador da entrada
            */
	    public T get(K codigo){
	        return cache.get(codigo);
	    }
	   /**
            * Pega a lista de objetos
            * @return lista
            */
	    public Collection<T> getList(){
	        return cache.values();
	    }
	   /**
            * Remove um objeto da lista
            * @param objeto objeto a ser removido
            */
	    public void remove(T objeto){
	    	cache.remove(objeto.getID());
	        persist();
	    }
	    /**
             * Carrega a lista de objetos salvas no arquivo, caso nao haja arquivo cria um
             */
	    public void load() {
	    	try {
	    		FileInputStream arquivo = new FileInputStream(nomeArquivo);
	    		ObjectInputStream leArquivoCargos = new ObjectInputStream(arquivo);
				this.cache = (HashMap<K, T>) leArquivoCargos.readObject();
				arquivo.close();
				leArquivoCargos.close();
	    	} catch (ClassNotFoundException e) {
	    		JOptionPane.showMessageDialog(null, "ClassNotFoundException", "Excecao", JOptionPane.ERROR_MESSAGE);	
	    	} catch (FileNotFoundException e) {
	    		JOptionPane.showMessageDialog(null, String.format("Sera criado o arquivo \"%s\" para armazenar os dados", nomeArquivo), "Aviso", JOptionPane.WARNING_MESSAGE);
	    	} catch (IOException e) {
	    		JOptionPane.showMessageDialog(null, "IOException", "Excecao", JOptionPane.ERROR_MESSAGE);
	    	}
	    }
	    /**
             * Grava os dados no arquivo
             */
	    public void persist() {
	    	
	    	try {
				FileOutputStream arquivo = new FileOutputStream(nomeArquivo);
				ObjectOutputStream gravaArquivo = new ObjectOutputStream (arquivo);
				gravaArquivo.writeObject(cache);
				
				arquivo.flush();
				gravaArquivo.flush();
				
				arquivo.close();
				gravaArquivo.close();
			} catch (FileNotFoundException e){
				JOptionPane.showMessageDialog(null, "FileNotFoundException", "Excecao", JOptionPane.ERROR_MESSAGE);
			}
	    	
	    	
	    	catch (IOException e) {
	    		JOptionPane.showMessageDialog(null, "IOException", "Excecao", JOptionPane.ERROR_MESSAGE);
			}
	    	
	    }
}
