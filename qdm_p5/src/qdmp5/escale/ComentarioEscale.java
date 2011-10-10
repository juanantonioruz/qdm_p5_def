package qdmp5.escale;

import java.util.Date;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import qdmp5.ClaseP5;
import toxi.physics2d.VerletParticle2D;

public class ComentarioEscale extends ModeloEscaleBase {

	String texto;
	String titulo;
	UsuarioEscale usuario;
	int parent;
	int id;
	Date fecha;
	Mensaje tituloMensaje;
	Mensaje equipoMensaje;
	public ComentarioEscale(PApplet p5, int _id, String _titulo, String _texto, UsuarioEscale usuario, int _parent,
			Date _fecha) {
		super(p5);
		this.id = _id;
		this.usuario = usuario;
		this.texto = _texto;
		this.parent = _parent;
		this.titulo = _titulo;
		this.fecha = _fecha;
		widtho += p5.map(texto.split(" ").length, 0, 200, 0, 20);
		heighto = widtho;
		x=usuario.equipo.x+p5.random(-10,10);
		y=usuario.equipo.y+p5.random(-10,10);
		tituloMensaje=new Mensaje(p5);
		equipoMensaje=new Mensaje(p5);

	}

	float cantidad;

	public void pinta(PFont font, int tiempoDeComentario) {

		p5.pushStyle();
		p5.stroke(100);
		p5.strokeWeight(0.2f);
		p5.fill(usuario.equipo.color,60);
		p5.ellipse(x, y, widtho, heighto);
		p5.popStyle();
		tituloMensaje.pintaMensaje(font, p5.color(100), p5.color(0), titulo, x,y, 9,  tiempoDeComentario);
		equipoMensaje.pintaMensaje(font, p5.color(100), usuario.equipo.color, usuario.equipo.nombre, x,y-(tituloMensaje.textAscent), 9,  tiempoDeComentario);

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
