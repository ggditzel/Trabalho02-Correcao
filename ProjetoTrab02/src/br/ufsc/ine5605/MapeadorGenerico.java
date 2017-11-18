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
	    	load(); // implementar metodo de load, conforme slide
	    }
	    
	    public void put(T objeto){
	    	cache.put(objeto.getID(), objeto);
	        persist(); // fazer metodo privado, de acordo com o que tem nos slides
	    }
	   
	    public T get(K codigo){
	        return cache.get(codigo);
	    }
	   
	    public Collection<T> getList(){
	        return cache.values();
	    }
	   
	    public void remove(T objeto){
	    	cache.remove(objeto.getID());
	        persist();
	    }
	    
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
