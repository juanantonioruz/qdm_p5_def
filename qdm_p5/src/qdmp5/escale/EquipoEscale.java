package qdmp5.escale;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import toxi.color.TColor;

public class EquipoEscale extends ModeloEscaleBase{
	// TODO: tener en cuenta los espacios
	int widthEscala = 500;
	int heightEscala = 319;

	String nombre;
	float x;
	float y;
	private final PApplet p5;
	private final String barrio;
	List<UsuarioEscale> usuarios = new ArrayList();

	int color;

	public EquipoEscale(PApplet p5, int _id, String _n, int _x, int _y, String _barrio) {
		super(p5);
		this.p5 = p5;
		this.nombre = _n;
		this.barrio = _barrio;
		this.x = p5.map(_x, 0, widthEscala, 0, p5.width);
		this.y = p5.map(_y, 0, heightEscala, 0, p5.height);
	}
	
	public UsuarioEscale addUsuario(UsuarioEscale u) {
		for (UsuarioEscale usu : usuarios)
			if (usu.id == u.id)
				return usu;
		usuarios.add(u);

		return u;
	}

	public void setColor(TColor tColor) {
		this.color = p5.color(mapeaValor(tColor.hue()), mapeaValor(tColor.saturation()),
				mapeaValor(tColor.brightness()));
	}

	float mapeaValor(float ta) {
		return p5.map(ta, 0, 1, 0, 100);
	}
}
