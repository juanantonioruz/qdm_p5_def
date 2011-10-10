package qdmp5.escale;

import java.util.Date;

import processing.core.PApplet;
import qdmp5.ClaseP5;
import toxi.physics2d.VerletParticle2D;

public class ComentarioEscale extends ModeloEscaleBase {

	String texto;
	String titulo;
	UsuarioEscale usuario;
	int parent;
	int id;
	Date fecha;

	public ComentarioEscale(PApplet p5, int _id, String _titulo, String _texto, UsuarioEscale usuario, int _parent,
			Date _fecha) {
		super(p5);
		this.id = _id;
		this.usuario = usuario;
		this.texto = _texto;
		this.parent = _parent;
		this.titulo = _titulo;
		this.fecha = _fecha;
		widtho += p5.map(texto.split(" ").length, 0, 200, 0, 40);
		heighto = widtho;
		x=usuario.equipo.x+p5.random(-10,10);
		y=usuario.equipo.y+p5.random(-10,10);

	}

	float cantidad;

	public void pinta() {

		p5.pushStyle();
		p5.stroke(100);
		p5.strokeWeight(0.2f);
		p5.fill(p5.hue(usuario.equipo.color), p5.map(widtho, 15, 55, 20, 100), p5.map(widtho, 15, 55, 20, 100),30);
		p5.ellipse(x, y, widtho, heighto);
		p5.popStyle();

	}

	public boolean rollOver() {
		// TODO implementar en caso necesario
		// if (p5.dist(particle.x, particle.y, p5.mouseX, p5.mouseY) < widtho /
		// 2) {
		// return true;
		// }
		// return false;
		return false;
	}

}
