import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Individuo implements Comparable<Individuo> {

	private List<Integer> cromossomo = new ArrayList<Integer>();
	private double fitness;
	private double custo;

	@Override
	public int compareTo(Individuo i) {

		return (int) (custo - i.custo);
	}

	public List<Integer> getCromossomo() {
		return cromossomo;
	}

	public void setCromossomo(List<Integer> solucao) {
		
		this.cromossomo = solucao;
		
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	@Override
	public String toString() {
		return cromossomo.toString() + " Fitness: " + fitness;
	}

	public double getCusto() {
		return custo;
	}

	public void setCusto(double soma) {
		this.custo = soma;
	}

	public void gerarAleatorio(List<Cliente> clientes) {
		cromossomo = new ArrayList<Integer>();
		List<Integer> temp = new ArrayList<Integer>();
		for (int i = 0; i < clientes.size(); i++) {
			temp.add(clientes.get(i).getId());
			for (int j = 0; j < clientes.size(); j++) {
				Point p1 = clientes.get(i).getPonto();
				Point p2 = clientes.get(j).getPonto();
				if (i == j)
					continue;
				double  x  = Math.sqrt( Math.pow( (p1.x - p2.x),2 ) + Math.pow( (p1.y - p2.y),2 ) );
				AG.DISTANCIAS[i][j] = x;
			}
		}
		for (int i = 0; i < AG.TAMANHO_INDIVIDUO; i++) {
			if (clientes.size() == 0)
				break;
			int r = (int) (Math.random() * temp.size() - 1) + 1;
			cromossomo.add(temp.get(r));
			temp.remove(r);

		}
	}

	public void avaliarCusto() {
		double soma = AG.DISTANCIAS[0][cromossomo.get(0)];
		int qtdeVeiculos = 1;
		for (int i = 0; i < AG.TAMANHO_INDIVIDUO; i++) {
			if (i == AG.TAMANHO_INDIVIDUO - 1) {
				soma += AG.DISTANCIAS[cromossomo.get(i)][0];
				break;
			}
			soma += AG.DISTANCIAS[cromossomo.get(i)][cromossomo.get(i + 1)];
			if (soma > qtdeVeiculos * AG.CAPACIDADE_VEICULO) {
				soma += AG.DISTANCIAS[cromossomo.get(i)][0];
				soma += AG.DISTANCIAS[0][cromossomo.get(i + 1)];
				qtdeVeiculos++;
			}
		}
		setCusto(soma);
	}

	public void avaliarFitness() {
		avaliarCusto();
		fitness = -1 * custo + Populacao.PIOR_CUSTO;
			
	}
}
