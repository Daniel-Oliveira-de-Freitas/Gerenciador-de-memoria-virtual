package application;

import java.util.Random;

public class Processo extends Thread {
	private final int pid;
	private final int numPaginas;
	private final GerenciadorDeMemoria gerenciadorDeMemoria;

	private final Random random;

	public Processo(int pid, int numPaginas, GerenciadorDeMemoria gerenciadorDeMemoria) {
		this.pid = pid;
		this.numPaginas = numPaginas;
		this.gerenciadorDeMemoria = gerenciadorDeMemoria;
		this.random = new Random();
	}

	@Override
	public void run() {
		while (true) {
			solicitaPagina();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				break;
			}

		}

	GerenciadorController.recebeValor = String.format("Processo "+toString()+" terminou.");
	}

		
	public static Processo[] criarProcessos(int numPaginas,
			GerenciadorDeMemoria gerenciadorDeMemoria, int numProcessos) {
		Processo[] processos = new Processo[numProcessos];

		for (int i = 0; i < processos.length; i++) {
			Processo processo = new Processo(i, numPaginas, gerenciadorDeMemoria);
			processo.start();

			processos[i] = processo;

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return processos;
	}



	public static void terminarProcessos(Processo[] processos) {
		
		for (int i = 0; i < processos.length; i++) {
			processos[i].interrupt();
		}
		
	}

	private void solicitaPagina() {

		int numPagina = random.nextInt(numPaginas);

		Pagina pagina = new Pagina(this, numPagina);

		gerenciadorDeMemoria.alocaPagina(pagina);
	}

	@Override
	public String toString() {
		return "P" + pid;
	}

	public int getPid() {
		return pid;
	}

	@Override
	public int hashCode() {
		return pid;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Processo other = (Processo) obj;
		if (pid != other.pid)
			return false;
		return true;
	}

}
