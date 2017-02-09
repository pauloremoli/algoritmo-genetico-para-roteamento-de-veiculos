import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main extends JFrame {

	/**
	 * @param args
	 */
	private BufferedImage buffer;
	private JLabel lblParametrosMetodos, lblSelecao, lblCruzamento, lblMutacao,
			lblTamanhoPopulacao, lblProbMutacao, lblProbCruzamento,
			lblNumeroGeracoes, lblLista, lblVeiculo, lblMelhorCusto,
			lblNumVeiculos, lblArquivoClientes, lblEscala;

	private JTextField txtTamanhoPopulacao, txtProbCruzamento, txtProbMutacao,
			txtNumeroGeracoes, txtCapacidadeVeiculo, txtEscala;
	private JComboBox cmbSelecao, cmbCruzamento, cmbMutacao;
	private JButton btnExecutar, btnClientes;
	private JPanel pnlMetodo, pnlArquivo, pnlCanvas;
	private JFileChooser fcClientes;
	private List<Cliente> clientes;
	private MyCanvas canvas;

	public Main() {

		clientes = new ArrayList<Cliente>();
		pnlCanvas = new JPanel();
		pnlCanvas.setLayout(null);
		pnlCanvas.setBounds(300, 0, 700, 700);
		add(pnlCanvas);

		pnlMetodo = new JPanel();
		pnlMetodo.setLayout(null);
		pnlMetodo.setBounds(0, 0, 300, 250);
		add(pnlMetodo);

		pnlArquivo = new JPanel();
		pnlArquivo.setLayout(null);
		pnlArquivo.setBounds(0, 300, 300, 300);
		add(pnlArquivo);

		lblLista = new JLabel("Lista Clientes: ");
		lblLista.setBounds(10, 310, 100, 20);
		pnlArquivo.add(lblLista);

		lblArquivoClientes = new JLabel();
		lblArquivoClientes.setBounds(100, 310, 100, 20);
		pnlArquivo.add(lblArquivoClientes);

		fcClientes = new JFileChooser();
		btnClientes = new JButton("Selecionar arquivo");
		btnClientes.setBounds(10, 340, 140, 20);
		pnlArquivo.add(btnClientes);
		btnClientes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fcClientes.showOpenDialog(null);
				File f = fcClientes.getSelectedFile();

				if (f != null) {
					lblArquivoClientes.setText(f.getName());
					lerPontos(f);
				}
			}

		});

		lblEscala = new JLabel("Escala: ");
		lblEscala.setBounds(10, 400, 120, 20);
		pnlArquivo.add(lblEscala);

		txtEscala = new JTextField("5");
		txtEscala.setBounds(140, 400, 40, 20);
		pnlArquivo.add(txtEscala);

		lblVeiculo = new JLabel("Capacidade Veículo: ");
		lblVeiculo.setBounds(10, 260, 120, 20);
		pnlArquivo.add(lblVeiculo);

		txtCapacidadeVeiculo = new JTextField("160");
		txtCapacidadeVeiculo.setBounds(140, 260, 40, 20);
		pnlArquivo.add(txtCapacidadeVeiculo);

		lblMelhorCusto = new JLabel("Melhor Custo: ");
		lblMelhorCusto.setBounds(10, 440, 200, 20);
		pnlArquivo.add(lblMelhorCusto);

		lblNumVeiculos = new JLabel("Número de veículos: ");
		lblNumVeiculos.setBounds(10, 470, 200, 20);
		pnlArquivo.add(lblNumVeiculos);

		lblParametrosMetodos = new JLabel("Parâmetros do Método");
		lblParametrosMetodos.setBounds(10, 10, 150, 20);
		pnlMetodo.add(lblParametrosMetodos);

		buffer = new BufferedImage(700, 700, 1);
		Graphics g = buffer.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 700, 700);

		canvas = new MyCanvas(buffer);
		canvas.setBounds(0, 0, 700, 700);
		pnlCanvas.add(canvas);

		lblSelecao = new JLabel("Seleção: ");
		lblSelecao.setBounds(10, 40, 150, 20);
		pnlMetodo.add(lblSelecao);
		String[] selecao = new String[] { "Dizimacao", "Roleta", "Torneio" };
		cmbSelecao = new JComboBox(selecao);
		cmbSelecao.setBounds(100, 40, 100, 20);
		pnlMetodo.add(cmbSelecao);
		lblCruzamento = new JLabel("Cruzamento: ");
		lblCruzamento.setBounds(10, 70, 150, 20);
		pnlMetodo.add(lblCruzamento);
		String[] crossovers = new String[] { "Ponto Unico", "PMX", "OX" };
		cmbCruzamento = new JComboBox(crossovers);
		cmbCruzamento.setBounds(100, 70, 100, 20);
		pnlMetodo.add(cmbCruzamento);
		lblMutacao = new JLabel("Mutação: ");
		lblMutacao.setBounds(10, 100, 150, 20);
		pnlMetodo.add(lblMutacao);
		String[] mutacao = new String[] { "EM", "SIM", "DM", "ISM", "IVM", "SM" };
		cmbMutacao = new JComboBox(mutacao);
		cmbMutacao.setBounds(100, 100, 100, 20);
		pnlMetodo.add(cmbMutacao);
		lblTamanhoPopulacao = new JLabel("Tamanho População: ");
		lblTamanhoPopulacao.setBounds(10, 140, 150, 20);
		pnlMetodo.add(lblTamanhoPopulacao);
		txtTamanhoPopulacao = new JTextField("100");
		txtTamanhoPopulacao.setBounds(140, 140, 40, 20);
		pnlMetodo.add(txtTamanhoPopulacao);
		lblProbCruzamento = new JLabel("Taxa de Cruzamento: ");
		lblProbCruzamento.setBounds(10, 170, 150, 20);
		pnlMetodo.add(lblProbCruzamento);
		txtProbCruzamento = new JTextField("0.6");
		txtProbCruzamento.setBounds(140, 170, 40, 20);
		pnlMetodo.add(txtProbCruzamento);
		lblProbMutacao = new JLabel("Taxa de Mutação: ");
		lblProbMutacao.setBounds(10, 200, 150, 20);
		pnlMetodo.add(lblProbMutacao);
		txtProbMutacao = new JTextField("0.95");
		txtProbMutacao.setBounds(140, 200, 40, 20);
		pnlMetodo.add(txtProbMutacao);
		lblNumeroGeracoes = new JLabel("Número de gerações: ");
		lblNumeroGeracoes.setBounds(10, 230, 150, 20);
		pnlMetodo.add(lblNumeroGeracoes);
		txtNumeroGeracoes = new JTextField("10000");
		txtNumeroGeracoes.setBounds(140, 230, 40, 20);
		pnlMetodo.add(txtNumeroGeracoes);

		btnExecutar = new JButton("Executar");
		btnExecutar.setBounds(10, 600, 120, 20);
		pnlArquivo.add(btnExecutar);
		btnExecutar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String text = (String) cmbSelecao.getSelectedItem();
				Selecao selecao;
				if (text.equals("Dizimacao")) {
					selecao = Selecao.Dizimacao;
				} else if (text.equals("Roleta")) {
					selecao = Selecao.Roleta;
				} else if (text.equals("Torneio")) {
					selecao = Selecao.Torneio;
				} else {
					JOptionPane.showMessageDialog(null,
							"Métodos de seleção inválido");
					return;
				}

				text = (String) cmbCruzamento.getSelectedItem();
				Cruzamento cruzamento;
				if (text.equals("Ponto Unico")) {
					cruzamento = Cruzamento.PontoUnico;
				} else if (text.equals("PMX")) {
					cruzamento = Cruzamento.PMX;
				} else if (text.equals("OX")) {
					cruzamento = Cruzamento.OX;
				} else {
					JOptionPane.showMessageDialog(null,
							"Operador de crossover inválido");
					return;
				}

				text = (String) cmbMutacao.getSelectedItem();
				Mutacao mutacao;

				if (text.equals("EM")) {
					mutacao = Mutacao.EM;
				} else if (text.equals("SIM")) {
					mutacao = Mutacao.SIM;
				} else if (text.equals("DM")) {
					mutacao = Mutacao.DM;
				} else if (text.equals("ISM")) {
					mutacao = Mutacao.ISM;
				} else if (text.equals("IVM")) {
					mutacao = Mutacao.IVM;
				} else if (text.equals("SM")) {
					mutacao = Mutacao.SM;
				} else {
					JOptionPane.showMessageDialog(null,
							"Operador de mutação inválido");
					return;
				}
				int tamPopulacao = 0;
				int numGeracoes = 0;
				double probCruzamento = 0.0;
				double probMutacao = 0.0;
				try {
					tamPopulacao = Integer.parseInt(txtTamanhoPopulacao
							.getText());
					probCruzamento = Double.parseDouble(txtProbCruzamento
							.getText());
					probMutacao = Double.parseDouble(txtProbMutacao.getText());
					numGeracoes = Integer.parseInt(txtNumeroGeracoes.getText());
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,
							"Parâmetro(s) Inválido(s)");
					return;
				}

				if (clientes.size() == 0) {
					JOptionPane.showMessageDialog(null,
							"Lista de clientes a serem visitados vazia");
					return;
				}
				int capacidadeVeiculo = Integer.parseInt(txtCapacidadeVeiculo
						.getText());
				if (capacidadeVeiculo < 1) {
					JOptionPane.showMessageDialog(null,
							"Capacidade do veículos inválida");
					return;
				}

				/*
				 * System.out.println("variando método de selecao");
				 * 
				 * for(Selecao sel: Selecao.values()){ Individuo melhor;
				 * List<Individuo> melhores = new ArrayList<Individuo>();
				 * for(int i = 0; i< 10; i++){ AG ag = new AG(numGeracoes,
				 * tamPopulacao, probMutacao, probCruzamento,
				 * capacidadeVeiculo); melhor = ag.exec(sel, mutacao,
				 * cruzamento, clientes); melhores.add(melhor); } int
				 * somaFitness = 0; int somaCusto = 0; for(Individuo i:
				 * melhores){ somaFitness+= i.getFitness(); somaCusto +=
				 * i.getCusto(); } Collections.sort(melhores);
				 * System.out.println("Seleção: " + sel);
				 * System.out.println("Mutação: " + mutacao);
				 * System.out.println("Cruzamento: " + cruzamento);
				 * System.out.println("Melhor Custo: "+
				 * melhores.get(0).getCusto());
				 * System.out.println("Media Custo: " + somaCusto/
				 * melhores.size() +"\n"); }
				 * 
				 * System.out.println("variando operador de mutacao");
				 * 
				 * for(Mutacao m: Mutacao.values()){ Individuo melhor;
				 * List<Individuo> melhores = new ArrayList<Individuo>();
				 * for(int i = 0; i< 10; i++){ AG ag = new AG(numGeracoes,
				 * tamPopulacao, probMutacao, probCruzamento,
				 * capacidadeVeiculo); melhor = ag.exec(selecao, m, cruzamento,
				 * clientes); melhores.add(melhor); } int somaFitness = 0; int
				 * somaCusto = 0; for(Individuo i: melhores){ somaFitness+=
				 * i.getFitness(); somaCusto += i.getCusto(); }
				 * Collections.sort(melhores); System.out.println("Seleção: " +
				 * selecao); System.out.println("Mutação: " + m);
				 * System.out.println("Cruzamento: " + cruzamento);
				 * System.out.println("Melhor Custo: "+
				 * melhores.get(0).getCusto());
				 * System.out.println("Media Custo: " + somaCusto/
				 * melhores.size()+"\n"); }
				 * 
				 * System.out.println("variando operador de crossover");
				 * 
				 * for(Cruzamento c: Cruzamento.values()){ Individuo melhor;
				 * List<Individuo> melhores = new ArrayList<Individuo>();
				 * for(int i = 0; i< 10; i++){ AG ag = new AG(numGeracoes,
				 * tamPopulacao, probMutacao, probCruzamento,
				 * capacidadeVeiculo); melhor = ag.exec(selecao, mutacao, c,
				 * clientes); melhores.add(melhor); } int somaFitness = 0; int
				 * somaCusto = 0; for(Individuo i: melhores){ somaFitness+=
				 * i.getFitness(); somaCusto += i.getCusto(); }
				 * Collections.sort(melhores); System.out.println("Seleção: " +
				 * selecao); System.out.println("Mutação: " + mutacao);
				 * System.out.println("Cruzamento: " + c);
				 * System.out.println("Melhor Custo: "+
				 * melhores.get(0).getCusto());
				 * System.out.println("Media Custo: " + somaCusto/
				 * melhores.size() +"\n"); }
				 * 
				 * System.out.println("Variando probabilidade de Mutacao");
				 * for(double j = 0; j <= 1; j+= 0.03 ){ Individuo melhor;
				 * List<Individuo> melhores = new ArrayList<Individuo>();
				 * for(int i = 0; i< 10; i++){ AG ag = new AG(numGeracoes,
				 * tamPopulacao, j, probCruzamento, capacidadeVeiculo); melhor =
				 * ag.exec(selecao, mutacao, cruzamento, clientes);
				 * melhores.add(melhor); } int somaFitness = 0; int somaCusto =
				 * 0; for(Individuo i: melhores){ somaFitness+= i.getFitness();
				 * somaCusto += i.getCusto(); } Collections.sort(melhores);
				 * System.out.println("Prob Mutacao: "+ j);
				 * System.out.println("Seleção: " + selecao);
				 * System.out.println("Mutação: " + mutacao);
				 * System.out.println("Cruzamento: " + cruzamento);
				 * System.out.println("Melhor Custo: "+
				 * melhores.get(0).getCusto());
				 * System.out.println("Media Custo: " +
				 * somaCusto/melhores.size()+"\n"); }
				 * 
				 * 
				 * System.out.println("Variando prob cruzamento"); for(double j
				 * = 0; j <= 1; j+= 0.1){ Individuo melhor; List<Individuo>
				 * melhores = new ArrayList<Individuo>(); for(int i = 0; i< 10;
				 * i++){ AG ag = new AG(numGeracoes, tamPopulacao, probMutacao,
				 * j, capacidadeVeiculo); melhor = ag.exec(selecao, mutacao,
				 * cruzamento, clientes); melhores.add(melhor); } int
				 * somaFitness = 0; int somaCusto = 0; for(Individuo i:
				 * melhores){ somaFitness+= i.getFitness(); somaCusto +=
				 * i.getCusto(); } Collections.sort(melhores);
				 * System.out.println("Prob Cruzamento: "+ j);
				 * System.out.println("Seleção: " + selecao);
				 * System.out.println("Mutação: " + mutacao);
				 * System.out.println("Cruzamento: " + cruzamento);
				 * System.out.println("Melhor Custo: "+
				 * melhores.get(0).getCusto());
				 * System.out.println("Media Custo: " +
				 * somaCusto/melhores.size()+"\n"); }
				 */

				/*
				 * System.out.println("variacao tamanho população");
				 * List<Individuo> melhores = new ArrayList<Individuo>();
				 * for(int j = 20; j <= 300; j += 20){ for(int i = 0; i< 10;
				 * i++){ Individuo melhor; AG ag = new AG(numGeracoes, j,
				 * probMutacao, probCruzamento, capacidadeVeiculo); melhor =
				 * ag.exec(selecao, mutacao, cruzamento, clientes);
				 * melhores.add(melhor); }
				 * 
				 * int somaFitness = 0; double somaCusto = 0; for(Individuo i:
				 * melhores){ somaFitness+= i.getFitness(); somaCusto +=
				 * i.getCusto(); } Collections.sort(melhores);
				 * System.out.println("Tamanho população: " + j);
				 * System.out.println("Seleção: " + selecao);
				 * System.out.println("Mutação: " + mutacao);
				 * System.out.println("Cruzamento: " + cruzamento);
				 * System.out.println("Melhor Custo: "+
				 * melhores.get(0).getCusto());
				 * System.out.println("Media Custo: " + somaCusto/
				 * melhores.size() +"\n");
				 */

				/*
				 * // execução normal 10x
				 * 
				 * Individuo melhor; List<Individuo> melhores = new
				 * ArrayList<Individuo>(); for(int i = 0; i< 10; i++){ AG ag =
				 * new AG(numGeracoes, tamPopulacao, probMutacao,
				 * probCruzamento, capacidadeVeiculo); melhor = ag.exec(selecao,
				 * mutacao, cruzamento, clientes); melhores.add(melhor);
				 * 
				 * }
				 * 
				 * 
				 * Collections.sort(melhores);
				 * lblMelhorCusto.setText("Melhor Custo: " +
				 * melhores.get(0).getCusto()); int somaFitness = 0; double
				 * somaCusto = 0; for(Individuo i: melhores){ somaFitness+=
				 * i.getFitness(); somaCusto += i.getCusto(); }
				 * Collections.sort(melhores);
				 * System.out.println("Melhor Custo: "+
				 * melhores.get(0).getCusto());
				 * System.out.println("Media Custo: " + somaCusto/
				 * melhores.size() +"\n"); List<Integer> cromossomo =
				 * melhores.get(0).getCromossomo();
				 * 
				 * double soma = 0; int qtdeVeiculos = 1; int ultimoDeposito =
				 * 0; List<List<Integer>> rotas = new
				 * ArrayList<List<Integer>>(); for (int i = 0; i <
				 * AG.TAMANHO_INDIVIDUO - 1; i++) {
				 * 
				 * soma += clientes.get(cromossomo.get(i)).getQuantidade(); if
				 * (soma > qtdeVeiculos * AG.CAPACIDADE_VEICULO) {
				 * qtdeVeiculos++; List<Integer> rota = new
				 * ArrayList<Integer>(); rota.add(0);
				 * rota.addAll(cromossomo.subList(ultimoDeposito, i));
				 * rota.add(0); rotas.add(rota); ultimoDeposito = i; } }
				 * 
				 * canvas.setEscala(Integer.parseInt(txtEscala.getText()));
				 * List<Integer> rota = new ArrayList<Integer>(); rota.add(0);
				 * rota.addAll(cromossomo.subList(ultimoDeposito,
				 * AG.TAMANHO_INDIVIDUO)); rota.add(0); rotas.add(rota);
				 * System.out.println(rotas);
				 * lblNumVeiculos.setText("Número de veículos: " +
				 * rotas.size()); canvas.setImage(buffer);
				 * canvas.desenharPontos(clientes); canvas.desenharRota(rotas,
				 * clientes); Collections.sort(cromossomo);
				 */

				// execução normal

				Individuo melhor;
				AG ag = new AG(numGeracoes, tamPopulacao, probMutacao,
						probCruzamento, capacidadeVeiculo);
				melhor = ag.exec(selecao, mutacao, cruzamento, clientes);

				lblMelhorCusto.setText("Melhor Custo: " + melhor.getCusto());
				List<Integer> cromossomo = melhor.getCromossomo();

				double soma = 0;
				int qtdeVeiculos = 1;
				int ultimoDeposito = 0;
				List<List<Integer>> rotas = new ArrayList<List<Integer>>();
				for (int i = 0; i < AG.TAMANHO_INDIVIDUO - 1; i++) {

					soma += clientes.get(cromossomo.get(i)).getQuantidade();
					if (soma > qtdeVeiculos * AG.CAPACIDADE_VEICULO) {
						qtdeVeiculos++;
						List<Integer> rota = new ArrayList<Integer>();
						rota.add(0);
						rota.addAll(cromossomo.subList(ultimoDeposito, i));
						rota.add(0);
						rotas.add(rota);
						ultimoDeposito = i;
					}
				}

				canvas.setEscala(Integer.parseInt(txtEscala.getText()));
				List<Integer> rota = new ArrayList<Integer>();
				rota.add(0);
				rota.addAll(cromossomo.subList(ultimoDeposito,
						AG.TAMANHO_INDIVIDUO));
				rota.add(0);
				rotas.add(rota);
				lblNumVeiculos.setText("Número de veículos: " + rotas.size());
				canvas.setImage(buffer);
				canvas.desenharPontos(clientes);
				canvas.desenharRota(rotas, clientes);
				Collections.sort(cromossomo);

			}

		});

		setTitle("Roteamento de veículos com algoritmo genético");
		setSize(1000, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}

	public void lerPontos(File f) {
		FileReader fr = null;
		clientes.clear();
		try {
			fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line = "";

			while ((line = br.readLine()) != null) {
				String[] lines = line.split(" ");
				if ("CAPACITY".equals(lines[0])) {
					txtCapacidadeVeiculo.setText(lines[2]);
					line = br.readLine();
				}
				if ("NODE_COORD_SECTION".equals(line)) {
					while (!"DEMAND_SECTION".equals(line = br.readLine())) {
						lines = line.split(" ");
						int id = Integer.parseInt(lines[0]);
						Point ponto = new Point(
								(int) (Double.parseDouble(lines[1])),
								(int) Double.parseDouble(lines[2]));
						Cliente c = new Cliente(--id, ponto);
						clientes.add(c);

					}
				}

				if ("DEMAND_SECTION".equals(line)) {
					while (!"DEPOT_SECTION".equals(line = br.readLine())) {
						lines = line.split(" ");
						int id = Integer.parseInt(lines[0]);
						int quantidade = Integer.parseInt(lines[1]);
						clientes.get(--id).setQuantidade(quantidade);
					}
				}
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		new Main();

	}

}
