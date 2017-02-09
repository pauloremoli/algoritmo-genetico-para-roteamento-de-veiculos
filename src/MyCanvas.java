import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

public class MyCanvas extends Canvas {

	private BufferedImage image;
	private LinkedList<Color> colors;
	private int escala;

	public MyCanvas(BufferedImage image) {
		this.image = image;
		colors = new LinkedList<Color>();
		addColors();
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);

	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public void addColors() {
		colors.add(Color.BLUE);
		colors.add(Color.GRAY);
		colors.add(Color.BLACK);
		colors.add(Color.GREEN);
		colors.add(Color.ORANGE);
		colors.add(Color.PINK);
		colors.add(Color.RED);
		colors.add(Color.MAGENTA);
	}

	public void desenharPontos(List<Cliente> clientes) {

		Graphics g = image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 400, 400);
		g.setColor(Color.BLACK);
		for (int i = 0; i < clientes.size(); i++) {
			Point p = clientes.get(i).getPonto();
			g.fillOval(p.x * 5 - 1, p.y * 5 - 1, 5, 5);
			g.setColor(Color.BLACK);
		}

		repaint();

	}

	public Color getColor() {
		if(colors.size()==0)
			addColors();
		return colors.poll();

	}

	public void desenharRota(List<List<Integer>> rotas, List<Cliente> clientes) {

		Graphics g = image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 700, 700);

		Cliente dep = clientes.get(rotas.get(0).get(0));
		Point depositoPonto = dep.getPonto();

		for (int i = 0; i < rotas.size(); i++) {
			List<Integer> rota = rotas.get(i);
			g.setColor(getColor());
			for (int j = 0; j < rota.size() - 1; j++) {
				Cliente c = clientes.get(rota.get(j));
				Point ponto = c.getPonto();
				Cliente c1 = clientes.get(rota.get(j + 1));
				Point ponto1 = c1.getPonto();

				g.fillOval(ponto.x * escala - 1, ponto.y * escala - 1, 5, 5);
				g.drawString(new Integer(rota.get(j)).toString(), ponto.x
						* escala - 5, ponto.y * escala - 5);
				g.drawLine(ponto.x * escala, ponto.y * escala, ponto1.x
						* escala, ponto1.y * escala);

			}

		}
		g.setColor(Color.BLACK);
		g.fillOval(depositoPonto.x * escala - 2, depositoPonto.y * escala - 2,
				8, 8);
		repaint();

	}

	public int getEscala() {
		return escala;
	}

	public void setEscala(int escala) {
		this.escala = escala;
	}
	
	

}
