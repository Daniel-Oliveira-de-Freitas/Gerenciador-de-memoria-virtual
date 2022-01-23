package application;

import java.util.HashMap;
import java.util.LinkedList;

public class TabelaDeProcesso extends HashMap<Pagina, Integer> {
	
	private LinkedList<Pagina> listaLRU;
	private int workingSetCount;
	
	public TabelaDeProcesso() {
		this.workingSetCount = 0;
		this.listaLRU = new LinkedList<Pagina>();
	}

	
	@Override
	public Integer put(Pagina pagina, Integer endereco) {
		if (endereco >= 0) {
			workingSetCount++;
			listaLRU.addLast(pagina);
		}
		return super.put(pagina, endereco);
	}
	
	
	@Override
	public Integer remove(Object pagina) {
		Integer endereco = get(pagina);
		
		if (endereco != null && endereco >= 0) {
			workingSetCount--;
			listaLRU.remove(pagina);
		}
		
		return super.remove(pagina);
	}
	
	
	public int getWorkingSetCount() {
		return workingSetCount;
	}

	public Integer acessaPagina(Pagina pagina) {
		Integer endereco = get(pagina);
		if (endereco != null && endereco >= 0) {
			listaLRU.remove(pagina);
			listaLRU.addLast(pagina);
		}
		return endereco;
	}
	
	
	public Pagina getPaginaLRU() {
		return listaLRU.poll();
	}
	
	
}

