import java.awt.Point;

public class Cliente {
	private int id;
	private Point ponto;
	private int quantidade;

	public Cliente(int id, Point ponto) {
		this.id = id;
		this.ponto = ponto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Point getPonto() {
		return ponto;
	}

	public void setPonto(Point ponto) {
		this.ponto = ponto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	@Override
	public String toString() {
		return id + " " + ponto.x  + " " + ponto.y  + " " + quantidade;
	}

}
