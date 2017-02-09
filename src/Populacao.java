import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Populacao {

	private List<Individuo> individuos;
	private Cliente deposito;
	public static double PIOR_CUSTO;

	public Populacao() {
		individuos = new ArrayList<Individuo>(AG.TAMANHO_POPULACAO);

	}

	public Cliente getDeposito() {
		return deposito;
	}

	public void setDeposito(Cliente deposito) {
		this.deposito = deposito;
	}

	/**
	 * @return the individuos
	 */
	public List<Individuo> getIndividuos() {
		return individuos;
	}

	/**
	 * @param individuos
	 *            the individuos to set
	 */
	public void setIndividuos(List<Individuo> individuos) {
		this.individuos = individuos;
	}

	public void gerarPopulacao(List<Cliente> clientes) {
		
		for (int i = 0; i < AG.TAMANHO_POPULACAO; i++) {
			Individuo ind = new Individuo();
			ind.gerarAleatorio(clientes);
			encontrarPiorCusto();
			ind.avaliarCusto();
			ind.avaliarFitness();
			individuos.add(ind);
		}

		Collections.sort(individuos);
		
	}

	public void encontrarPiorCusto(){
		double x = 0;
		for(int i = 0; i < AG.DISTANCIAS.length; i++){
			for(int j = 0; j < AG.DISTANCIAS.length; j++){
				if(AG.DISTANCIAS[i][j] > x)
					x = AG.DISTANCIAS[i][j];
			}
		}
		x *= AG.TAMANHO_INDIVIDUO;
		PIOR_CUSTO = x;
		
	}
}
