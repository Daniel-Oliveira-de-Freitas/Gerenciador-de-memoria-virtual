package application;


import java.util.HashMap;
import java.util.LinkedList;

public class GerenciadorDeMemoria {
	private HashMap<Processo, TabelaDeProcesso> tabelasDePaginas;
	
	private LinkedList<Pagina> listaLRU;
	private LinkedList<Integer> framesLivres;
	
	private final int workingSetLimit;
	private int proximoEnderecoAreaDeSwap;
	
	
	public GerenciadorDeMemoria(int capacidade, int workingSetLimit) {
		this.tabelasDePaginas = new HashMap<Processo, TabelaDeProcesso>();
		this.listaLRU = new LinkedList<Pagina>();
		this.workingSetLimit = workingSetLimit;
		this.framesLivres = new LinkedList<Integer>();
		for (int i = 0; i < capacidade; i++) {
			framesLivres.add(i);
		}
		
		this.proximoEnderecoAreaDeSwap = -1;
	}
	
	public synchronized void alocaPagina(Pagina pagina) {
		Processo processo = pagina.getProcesso();
		GerenciadorController.recebeValor = String.format("Processo "+processo.toString()+" solicitou a página "+ pagina.getNumPagina() +".");
		GerenciadorController.recebeVal = String.format("Lista LRU: "+listaLRU);
		TabelaDeProcesso tabelaDoProcesso = tabelasDePaginas.get(processo);
		
		if (tabelaDoProcesso == null) {
			tabelaDoProcesso = new TabelaDeProcesso();
			tabelasDePaginas.put(processo, tabelaDoProcesso);
		}
		
		Integer enderecoPagina = tabelaDoProcesso.acessaPagina(pagina);
		if (enderecoPagina == null) { //pagina nao está na memoria virtual
			GerenciadorController.recebeValo = String.format("------- Page fault! --------");
			realizaAlocacao(pagina, processo, tabelaDoProcesso, proximoEnderecoAreaDeSwap--);
		}
		else if (enderecoPagina < 0) {
			realizaAlocacao(pagina, processo, tabelaDoProcesso, enderecoPagina);
		}
		
		listaLRU.remove(pagina);
		listaLRU.addLast(pagina);
		
		GerenciadorController.recebeValor = String.format("Lista LRU: "+String.valueOf(listaLRU));
		GerenciadorController.recebeVal = String.format(String.valueOf(tabelasDePaginas));
		GerenciadorController.recebeValo = String.format(String.valueOf(framesLivres));

	}

	private void realizaAlocacao(Pagina pagina, Processo processo,
			TabelaDeProcesso tabelaDoProcesso, Integer enderecoAlocacao) {
		if (tabelaDoProcesso.getWorkingSetCount() < workingSetLimit) {
			Integer frameLivre = framesLivres.poll();
			if (frameLivre != null) {
				tabelaDoProcesso.put(pagina, frameLivre);
				proximoEnderecoAreaDeSwap++;
			}
			else {
				GerenciadorController.recebeValor = String.format("Memória cheia");
				
				Pagina paginaLRU = getPaginaLRU();
				
				TabelaDeProcesso tabelaDoProcessoLRU = tabelasDePaginas.get(paginaLRU.getProcesso());
				Integer enderecoLRU = tabelaDoProcessoLRU.remove(paginaLRU);
				
				tabelaDoProcessoLRU.put(paginaLRU, enderecoAlocacao);				
				tabelaDoProcesso.put(pagina, enderecoLRU);
			}				
		}
		else {
			GerenciadorController.recebeValo = String.format("Processo "+processo.toString()+" atingiu o working set limit.");
			
			Pagina paginaLRU = tabelaDoProcesso.getPaginaLRU();
			listaLRU.remove(paginaLRU);
			
			Integer enderecoLRU = tabelaDoProcesso.remove(paginaLRU);
			
			tabelaDoProcesso.put(paginaLRU, enderecoAlocacao);			
			tabelaDoProcesso.put(pagina, enderecoLRU);
		}
	}

	public Pagina getPaginaLRU() {
		return listaLRU.poll();
	}
}
