package qdmp5;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import toxi.color.TColor;
import toxi.physics2d.VerletParticle2D;

public class Equipo extends VisualizableBase implements Visualizable {
	// TODO: tener en cuenta los espacios
	int widthEscala = 500;
	int heightEscala = 319;

	String nombre;
	List<VisualizableVerletMinDistanceSpring> springs = new ArrayList();

	int id;
	int c;
	boolean representado;
	String barrio;

	public Equipo(PApplet p5,int _id, String _n, int _x, int _y, String _barrio) {
		super(p5);
		this.id = _id;
		this.nombre = _n;
		barrio = _barrio;
		particle = new VerletParticle2D(p5.map(_x, 0, widthEscala, 0, p5.width), p5.map(
				_y, 0, heightEscala, 0, p5.height));
	}

	void setColor(TColor colo) {
		this.c = p5.color(mapeaValor(colo.hue()), mapeaValor(colo.saturation()),
				mapeaValor(colo.brightness()));
	}

	float mapeaValor(float ta) {
		return p5.map(ta, 0, 1, 0, 100);
	}

	List<Usuario> usuarios = new ArrayList();
	List<Usuario> usuariosRepresentados = new ArrayList();

	public Usuario addUsuario(Usuario u) {
		for (Usuario usu : usuarios)
			if (usu.id == u.id)
				return usu;
		usuarios.add(u);

		return u;
	}

	public void addUsuarioRepresentado(Usuario usu) {
		if (!usuariosRepresentados.contains(usu)) {
			usuariosRepresentados.add(usu);
			float areas = 0;
			for (Usuario u : usuariosRepresentados) {
				areas += p5.PI * p5.pow((u.widtho / 2), 2);
			}
			float radio = p5.sqrt(areas / p5.PI) + 15;
			widtho = radio * 2;
			heighto = widtho;
			for (VisualizableVerletMinDistanceSpring spring : springs)
				spring.resetea();
		}
	}

	int inicio = 3;
	int limite = 10;
	float tonito = inicio;

	public void repinta(Capa pg) {

		reset(pg, 7);
	}

	public void reset(Capa pg) {

		reset(pg, limite);
	}

	void reset(Capa pg, float limiteBucle) {
		/*
		 * tonito=inicio; representado=false; for (int i=0;
		 * i<limiteBucle-inicio;i++) { pinta(pg); } representado=false;
		 */
	}


	public void pinta(Capa pg) {
		pg.g.pushStyle();
		pg.g.noFill();
		pg.g.strokeWeight(2);
		pg.g.stroke(c, 80);

		pg.g.ellipse(particle.x, particle.y, widtho, heighto);

		// for(int i=0; i<usuarios.size(); i++)
		// pg.g.ellipse(particle.x, particle.y, 100+(i+1)*20, 100+(i+1)*20);
		pg.g.popStyle();
		/*
		 * if (!representado) { pg.g.pushStyle(); pg.g.noStroke(); pg.g.fill(c,
		 * tonito); int incremento=(usuarios.size()+7); pg.g.ellipse(particle.x,
		 * particle.y, incremento*10, incremento*10); pg.g.popStyle(); } if
		 * (tonito<limite ) { tonito+=1;
		 * 
		 * } else { representado=true; }
		 */
	}

}
