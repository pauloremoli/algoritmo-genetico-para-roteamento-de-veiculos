import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AG {

	private Populacao populacao;
	private double chanceMutacao, chanceCruzamento;
	public static int NUMERO_GERACOES, TAMANHO_POPULACAO, TAMANHO_INDIVIDUO,
			CAPACIDADE_VEICULO;
	public static double[][] DISTANCIAS;

	public AG(int num_geracoes, int tam_populacao, double prob_mut,
			double prob_cruzamento, int capacidadeVeiculo) {
		NUMERO_GERACOES = num_geracoes;
		chanceMutacao = prob_mut;
		chanceCruzamento = prob_cruzamento;
		TAMANHO_POPULACAO = tam_populacao;
		CAPACIDADE_VEICULO = capacidadeVeiculo;

	}

	public Individuo exec(Selecao selecao, Mutacao mutacao,
			Cruzamento cruzamento, List<Cliente> clientes) {
		populacao = new Populacao();
		TAMANHO_INDIVIDUO = clientes.size() - 1;
		DISTANCIAS = new double[clientes.size()][clientes.size()];
		populacao.gerarPopulacao(clientes);

		int cont = 0;
		while (cont <= NUMERO_GERACOES) {
			switch (selecao) {
			case Dizimacao:
				populacao = selecaoDizimacao(populacao);
				break;
			case Roleta:
				populacao = selecaoRoleta(populacao);
				break;
			}

			switch (cruzamento) {
			case PontoUnico:
				populacao = cruzamentoUP(populacao);
				break;
			case PMX:
				populacao = cruzamentoPMX(populacao);
				break;
			case OX:
				populacao = cruzamentoOX(populacao);
				break;
			}

			switch (mutacao) {
			case DM:
				populacao = mutacaoDM(populacao);
				break;
			case EM:
				populacao = mutacaoEM(populacao);
				break;
			case ISM:
				populacao = mutacaoISM(populacao);
				break;
			case IVM:
				populacao = mutacaoIVM(populacao);
				break;
			case SIM:
				populacao = mutacaoSIM(populacao);
				break;
			case SM:
				populacao = mutacaoSM(populacao);
				break;
			}
			cont++;
		}

		return populacao.getIndividuos().get(0);
	}

	public Populacao cruzamentoOX(Populacao novaPopulacao) {
		for (int j = 0; j < AG.TAMANHO_POPULACAO - 1; j += 2) {
			double p = Math.random();
			if (p <= chanceCruzamento) {
				Individuo pai1 = novaPopulacao.getIndividuos().get(j);
				Individuo pai2 = novaPopulacao.getIndividuos().get(j + 1);
				List<Integer> solucaoPai1 = pai1.getCromossomo();
				List<Integer> solucaoPai2 = pai2.getCromossomo();
				int x1 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
				int x2 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
				if (x2 < x1) {
					int x = x1;
					x1 = x2;
					x2 = x;
				}
				Individuo filho1 = new Individuo();
				Individuo filho2 = new Individuo();
				List<Integer> solucaoFilho1 = new ArrayList<Integer>();
				List<Integer> solucaoFilho2 = new ArrayList<Integer>();
				List<Integer> mapeamento1 = new ArrayList<Integer>();
				List<Integer> mapeamento2 = new ArrayList<Integer>();
				List<Integer> temp1 = new ArrayList<Integer>();
				List<Integer> temp2 = new ArrayList<Integer>();
				mapeamento1.addAll(solucaoPai1.subList(x1, x2));
				mapeamento2.addAll(solucaoPai2.subList(x1, x2));

				solucaoFilho1.addAll(mapeamento1);
				solucaoFilho2.addAll(mapeamento2);

				temp1.addAll(solucaoPai2);
				temp1.removeAll(mapeamento1);
				temp2.addAll(solucaoPai1);
				temp2.removeAll(mapeamento2);
				for (int i = x2; i < TAMANHO_INDIVIDUO; i++) {
					int x = temp1.remove(0);
					solucaoFilho1.add(x);
					x = temp2.remove(0);
					solucaoFilho2.add(x);

				}
				for (int i = 0; i < x2 - mapeamento1.size(); i++) {
					int x =  temp1.remove(0);
					solucaoFilho1.add(i, x);
					x = temp2.remove(0);
					solucaoFilho2.add(i, x);
				}

				filho1.setCromossomo(solucaoFilho1);
				filho2.setCromossomo(solucaoFilho2);
				filho1.avaliarFitness();
				filho2.avaliarFitness();
				if (filho1.getFitness() > pai1.getFitness()) {
					int index = novaPopulacao.getIndividuos().indexOf(pai1);
					novaPopulacao.getIndividuos().set(index, filho1);

				}
				if (filho2.getFitness() > pai2.getFitness()) {
					int index = novaPopulacao.getIndividuos().indexOf(pai2);
					novaPopulacao.getIndividuos().set(index, filho2);

				}

			}

		}

		Collections.sort(novaPopulacao.getIndividuos());
		return novaPopulacao;
	}

	public Populacao cruzamentoUP(Populacao nova_pop) {
		for (int j = 0; j < AG.TAMANHO_POPULACAO - 1; j += 2) {
			double p = Math.random();
			if (p <= chanceCruzamento) {
				Individuo pai1 = nova_pop.getIndividuos().get(j);
				Individuo pai2 = nova_pop.getIndividuos().get(j + 1);
				List<Integer> solucaoPai1 = pai1.getCromossomo();
				List<Integer> solucaoPai2 = pai2.getCromossomo();
				int x = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO - 1)) + 1;

				Individuo filho1 = new Individuo();
				Individuo filho2 = new Individuo();
				List<Integer> solucaoFilho1 = new ArrayList<Integer>();
				List<Integer> solucaoFilho2 = new ArrayList<Integer>();
				List<Integer> mapeamento1 = new ArrayList<Integer>();
				List<Integer> mapeamento2 = new ArrayList<Integer>();
				mapeamento1.addAll(solucaoPai1.subList(0, x));
				mapeamento2.addAll(solucaoPai2.subList(0, x));

				for (int i = 0; i < TAMANHO_INDIVIDUO; i++) {
					if (i < x) {
						solucaoFilho1.add(solucaoPai2.get(i));
						solucaoFilho2.add(solucaoPai1.get(i));
						continue;
					}

					if (mapeamento2.contains(solucaoPai1.get(i))) {
						int index = mapeamento2.indexOf(solucaoPai1.get(i));
						while (mapeamento2.contains(solucaoPai1.get(index))) {
							index = mapeamento2.indexOf(solucaoPai1.get(index));
						}
						solucaoFilho1.add(solucaoPai1.get(index));
					} else {
						solucaoFilho1.add(solucaoPai1.get(i));
					}

					if (mapeamento1.contains(solucaoPai2.get(i))) {
						int index = mapeamento1.indexOf(solucaoPai2.get(i));
						while (mapeamento1.contains(solucaoPai2.get(index))) {
							index = mapeamento1.indexOf(solucaoPai2.get(index));
						}
						solucaoFilho2.add(solucaoPai2.get(index));
					} else {
						solucaoFilho2.add(solucaoPai2.get(i));
					}
				}

				filho1.setCromossomo(solucaoFilho1);
				filho2.setCromossomo(solucaoFilho2);
				filho1.avaliarFitness();
				filho2.avaliarFitness();
				if (filho1.getFitness() > pai1.getFitness()) {
					int index = nova_pop.getIndividuos().indexOf(pai1);
					nova_pop.getIndividuos().set(index, filho1);

				}
				if (filho2.getFitness() > pai2.getFitness()) {
					int index = nova_pop.getIndividuos().indexOf(pai2);
					nova_pop.getIndividuos().set(index, filho2);

				}

			}

		}

		Collections.sort(nova_pop.getIndividuos());
		return nova_pop;
	}

	public Populacao mutacaoISM(Populacao pop) {
		for (int j = 0; j < AG.TAMANHO_POPULACAO - 1; j++) {
			double p = Math.random();
			if (p <= chanceMutacao) {
				Individuo pai = pop.getIndividuos().get(j);
				List<Integer> solucaoPai = pai.getCromossomo();

				int x1 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
				int x2 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));

				while (x1 == x2) {
					x2 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
				}

				Individuo filho = new Individuo();
				List<Integer> solucaoFilho = new ArrayList<Integer>();
				solucaoFilho.addAll(solucaoPai);
				solucaoFilho.add(x2, solucaoFilho.remove(x1));
				filho.setCromossomo(solucaoFilho);
				filho.avaliarFitness();

				if (filho.getFitness() > pai.getFitness()) {
					int index = pop.getIndividuos().indexOf(pai);
					pop.getIndividuos().set(index, filho);

				}
			}
		}
		Collections.sort(pop.getIndividuos());

		return pop;
	}

	public Populacao mutacaoIVM(Populacao pop) {
		for (int j = 0; j < AG.TAMANHO_POPULACAO - 1; j++) {
			double p = Math.random();
			if (p <= chanceMutacao) {
				Individuo pai = pop.getIndividuos().get(j);
				List<Integer> solucaoPai = pai.getCromossomo();
				List<Integer> mapeamento = new ArrayList<Integer>();
				int x1 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
				int x2 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
				while (x1 >= x2) {
					if (x1 > x2) {
						int x = x1;
						x1 = x2;
						x2 = x;
					} else {
						x2 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
					}
				}
				Individuo filho = new Individuo();
				List<Integer> solucaoFilho = new ArrayList<Integer>();
				solucaoFilho.addAll(solucaoPai);
				mapeamento.addAll(solucaoPai.subList(x1, x2));
				Collections.reverse(mapeamento);
				int x3 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO - mapeamento
						.size()));
				solucaoFilho.removeAll(mapeamento);
				solucaoFilho.addAll(x3, mapeamento);
				filho.setCromossomo(solucaoFilho);
				filho.avaliarFitness();
				if (filho.getFitness() > pai.getFitness()) {
					int index = pop.getIndividuos().indexOf(pai);
					pop.getIndividuos().set(index, filho);
				}
			}
		}
		Collections.sort(pop.getIndividuos());
		return pop;

	}

	public Populacao mutacaoSM(Populacao pop) {
		for (int j = 0; j < AG.TAMANHO_POPULACAO - 1; j++) {
			double p = Math.random();
			if (p <= chanceMutacao) {
				Individuo pai = pop.getIndividuos().get(j);
				List<Integer> solucaoPai = pai.getCromossomo();
				List<Integer> mapeamento = new ArrayList<Integer>();
				int x1 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
				int x2 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
				while (x1 >= x2) {
					if (x1 > x2) {
						int x = x1;
						x1 = x2;
						x2 = x;
					} else {
						x2 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
					}
				}
				Individuo filho = new Individuo();
				List<Integer> solucaoFilho = new ArrayList<Integer>();
				mapeamento.addAll(solucaoPai.subList(x1, x2));

				for (int i = 0; i < TAMANHO_INDIVIDUO; i++) {
					if (i >= x1 && i < x2) {
						int x = (int) (Math.random() * mapeamento.size());
						solucaoFilho.add(mapeamento.remove(x));
						continue;
					}

					solucaoFilho.add(solucaoPai.get(i));
				}

				filho.setCromossomo(solucaoFilho);
				filho.avaliarFitness();

				if (filho.getFitness() > pai.getFitness()) {
					int index = pop.getIndividuos().indexOf(pai);
					pop.getIndividuos().set(index, filho);

				}
			}
		}
		Collections.sort(pop.getIndividuos());

		return pop;

	}

	public Populacao mutacaoDM(Populacao pop) {
		for (int j = 0; j < AG.TAMANHO_POPULACAO - 1; j++) {
			double p = Math.random();
			if (p <= chanceMutacao) {
				Individuo pai = pop.getIndividuos().get(j);
				List<Integer> solucaoPai = pai.getCromossomo();
				List<Integer> mapeamento = new ArrayList<Integer>();
				int x1 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
				int x2 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
				while (x1 >= x2) {
					if (x1 > x2) {
						int x = x1;
						x1 = x2;
						x2 = x;
					} else {
						x2 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
					}
				}
				Individuo filho = new Individuo();
				List<Integer> solucaoFilho = new ArrayList<Integer>();

				solucaoFilho.addAll(solucaoPai);
				mapeamento.addAll(solucaoPai.subList(x1, x2));

				int x3 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO - mapeamento
						.size()));
				solucaoFilho.removeAll(mapeamento);
				solucaoFilho.addAll(x3, mapeamento);

				filho.setCromossomo(solucaoFilho);
				filho.avaliarFitness();

				if (filho.getFitness() > pai.getFitness()) {
					int index = pop.getIndividuos().indexOf(pai);
					pop.getIndividuos().set(index, filho);
				}
			}
		}
		Collections.sort(pop.getIndividuos());

		return pop;
	}

	public Populacao mutacaoEM(Populacao pop) {
		for (int j = 0; j < AG.TAMANHO_POPULACAO - 1; j++) {
			double p = Math.random();
			if (p <= chanceMutacao) {
				Individuo pai = pop.getIndividuos().get(j);
				List<Integer> solucaoPai = pai.getCromossomo();
				int x1 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
				int x2 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
				if (x2 < x1) {
					int x = x1;
					x1 = x2;
					x2 = x;
				}
				Individuo filho = new Individuo();
				List<Integer> solucaoFilho = new ArrayList<Integer>();
				for (int i = 0; i < TAMANHO_INDIVIDUO; i++) {
					if (i == x1) {
						solucaoFilho.add(solucaoPai.get(x2));
						continue;
					}
					if (i == x2) {
						solucaoFilho.add(solucaoPai.get(x1));
						continue;
					}
					solucaoFilho.add(solucaoPai.get(i));
				}

				filho.setCromossomo(solucaoFilho);
				filho.avaliarFitness();

				if (filho.getFitness() > pai.getFitness()) {
					int index = pop.getIndividuos().indexOf(pai);
					pop.getIndividuos().set(index, filho);

				}
			}
		}
		Collections.sort(pop.getIndividuos());
		return pop;
	}

	public Populacao mutacaoSIM(Populacao pop) {
		for (int j = 0; j < AG.TAMANHO_POPULACAO - 1; j++) {
			double p = Math.random();
			if (p <= chanceMutacao) {
				Individuo pai = pop.getIndividuos().get(j);
				List<Integer> solucaoPai = pai.getCromossomo();
				List<Integer> mapeamento = new ArrayList<Integer>();
				int x1 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
				int x2 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
				while (x1 >= x2) {
					if (x1 > x2) {
						int x = x1;
						x1 = x2;
						x2 = x;
					} else {
						x2 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
					}
				}
				Individuo filho = new Individuo();
				List<Integer> solucaoFilho = new ArrayList<Integer>();
				solucaoFilho.addAll(solucaoPai);
				mapeamento.addAll(solucaoPai.subList(x1, x2));
				Collections.reverse(mapeamento);
				solucaoFilho.removeAll(mapeamento);
				solucaoFilho.addAll(x1, mapeamento);
				filho.setCromossomo(solucaoFilho);
				filho.avaliarFitness();

				if (filho.getFitness() > pai.getFitness()) {
					int index = pop.getIndividuos().indexOf(pai);
					pop.getIndividuos().set(index, filho);
				}
			}
		}
		Collections.sort(pop.getIndividuos());
		return pop;
	}

	public Populacao cruzamentoPMX(Populacao nova_pop) {

		for (int j = 0; j < AG.TAMANHO_POPULACAO - 1; j += 2) {
			double p = Math.random();
			if (p <= chanceCruzamento) {
				Individuo pai1 = nova_pop.getIndividuos().get(j);
				Individuo pai2 = nova_pop.getIndividuos().get(j + 1);
				List<Integer> solucaoPai1 = pai1.getCromossomo();
				List<Integer> solucaoPai2 = pai2.getCromossomo();
				int x1 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
				int x2 = (int) (Math.random() * (AG.TAMANHO_INDIVIDUO));
				if (x2 < x1) {
					int x = x1;
					x1 = x2;
					x2 = x;
				}
				Individuo filho1 = new Individuo();
				Individuo filho2 = new Individuo();
				List<Integer> solucaoFilho1 = new ArrayList<Integer>();
				List<Integer> solucaoFilho2 = new ArrayList<Integer>();
				List<Integer> mapeamento1 = new ArrayList<Integer>();
				List<Integer> mapeamento2 = new ArrayList<Integer>();
				mapeamento1.addAll(solucaoPai1.subList(x1, x2));
				mapeamento2.addAll(solucaoPai2.subList(x1, x2));

				for (int i = 0; i < TAMANHO_INDIVIDUO; i++) {
					if (i >= x1 && i < x2) {
						solucaoFilho1.add(solucaoPai2.get(i));
						solucaoFilho2.add(solucaoPai1.get(i));
						continue;
					}
					if (mapeamento2.contains(solucaoPai1.get(i))) {
						int index = mapeamento2.indexOf(solucaoPai1.get(i))
								+ x1;
						while (mapeamento2.contains(solucaoPai1.get(index))) {
							index = mapeamento2.indexOf(solucaoPai1.get(index))
									+ x1;
						}
						solucaoFilho1.add(solucaoPai1.get(index));
					} else {
						solucaoFilho1.add(solucaoPai1.get(i));
					}

					if (mapeamento1.contains(solucaoPai2.get(i))) {
						int index = mapeamento1.indexOf(solucaoPai2.get(i))
								+ x1;
						while (mapeamento1.contains(solucaoPai2.get(index))) {
							index = mapeamento1.indexOf(solucaoPai2.get(index))
									+ x1;
						}
						solucaoFilho2.add(solucaoPai2.get(index));
					} else {
						solucaoFilho2.add(solucaoPai2.get(i));
					}
				}

				filho1.setCromossomo(solucaoFilho1);
				filho2.setCromossomo(solucaoFilho2);
				filho1.avaliarFitness();
				filho2.avaliarFitness();
				if (filho1.getFitness() > pai1.getFitness()) {
					int index = nova_pop.getIndividuos().indexOf(pai1);
					nova_pop.getIndividuos().set(index, filho1);

				}
				if (filho2.getFitness() > pai2.getFitness()) {
					int index = nova_pop.getIndividuos().indexOf(pai2);
					nova_pop.getIndividuos().set(index, filho2);

				}

			}

		}

		Collections.sort(nova_pop.getIndividuos());
		return nova_pop;
	}

	public Populacao selecaoDizimacao(Populacao pop) {
		Collections.sort(pop.getIndividuos());
		for (int i = 0; i < 50; i++) {
			pop.getIndividuos().remove(pop.getIndividuos().size() - 1);
		}
		for (int i = 0; i < 50; i++) {
			int x = (int) (Math.random() * pop.getIndividuos().size());
			pop.getIndividuos().add(pop.getIndividuos().get(x));
		}

		return pop;
	}

	public Populacao selecaoRoleta(Populacao pop) {
		List<Individuo> selecionados = new ArrayList<Individuo>();
		for (int j = 0; j < TAMANHO_POPULACAO; j++) {

			int soma = 0;
			for (Individuo i : pop.getIndividuos()) {
				soma += i.getCusto();
			}
			double r = Math.random();
			double somaP = 0;
			for (int i = 0; i < TAMANHO_POPULACAO; i++) {
				double x = pop.getIndividuos().get(i).getCusto() / soma;
				somaP += x;
				if (x < somaP) {
					selecionados.add(pop.getIndividuos().get(i));
					break;
				}
			}
		}
		return pop;
	}

	public Populacao selecaoTorneio(Populacao pop) {
		List<Individuo> torneio = new ArrayList<Individuo>();
		Populacao novaPopulacao = new Populacao();

		Collections.sort(pop.getIndividuos());
		for (int i = 0; i < TAMANHO_POPULACAO; i++) {
			for (int j = 0; i < 10; i++) {
				int x = (int) (Math.random() * pop.getIndividuos().size());
				torneio.add(pop.getIndividuos().get(x));
			}
			Collections.sort(torneio);
			novaPopulacao.getIndividuos().add(torneio.get(0));
		}

		return novaPopulacao;
	}

	/**
	 * @return the populacao
	 */
	public Populacao getPopulacao() {
		return populacao;
	}

	/**
	 * @param populacao
	 *            the populacao to set
	 */
	public void setPopulacao(Populacao populacao) {
		this.populacao = populacao;
	}

}
