package br.ufsc.ine5605.cargo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class MapeadorCargo {
	   
    private HashMap<Integer, Cargo> cacheCargos = new HashMap<>();
   
    private final String nomeArquivo = "cargos.dsoo1";
   
   
    public MapeadorCargo(){
        load(); // implementar metodo de load, conforme slide
    }
    
    public void put(Cargo cargo){
    	cacheCargos.put(cargo.getCodigo(), cargo);
        persist(); // fazer metodo privado, de acordo com o que tem nos slides
    }
   
    public Cargo get(Integer codigo){
        return cacheCargos.get(codigo);
    }
   
    public Collection<Cargo> getList(){
        return cacheCargos.values();
    }
   
    public void remove(Cargo cargo){
        cacheCargos.remove(cargo.getCodigo());
        persist();
    }
    
    public void load() {
    	try {
    		FileInputStream arquivo = new FileInputStream(nomeArquivo);
    		ObjectInputStream leArquivoCargos = new ObjectInputStream(arquivo);
			this.cacheCargos = (HashMap<Integer, Cargo>) leArquivoCargos.readObject();
			arquivo.close();
			leArquivoCargos.close();
    	} catch (ClassNotFoundException e) {
    		JOptionPane.showMessageDialog(null, "ClassNotFoundException", "Excecao", JOptionPane.ERROR_MESSAGE);	
    	} catch (FileNotFoundException e) {
    		JOptionPane.showMessageDialog(null, String.format("Sera criado o arquivo \"%s\" para armazenar os cargos", nomeArquivo), "Aviso", JOptionPane.WARNING_MESSAGE);
    	} catch (IOException e) {
    		JOptionPane.showMessageDialog(null, "IOException", "Excecao", JOptionPane.ERROR_MESSAGE);
    	}
    }
    
    public void persist() {
    	
    	try {
			FileOutputStream arquivo = new FileOutputStream(nomeArquivo);
			ObjectOutputStream gravaArquivo = new ObjectOutputStream (arquivo);
			gravaArquivo.writeObject(cacheCargos);
			
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