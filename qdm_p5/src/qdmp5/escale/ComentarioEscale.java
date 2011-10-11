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

	float contador;
	boolean yaSaturado;
	public void pinta(PFont font, int tiempoDeComentario) {
		p5.pushStyle();
		p5.stroke(100);
		p5.strokeWeight(0.2f);

//		p5.fill(usuario.equipo.color,100);
		float map = p5.map(contador, 0, tiempoDeComentario, 85, 100);
		if(map>95) yaSaturado=true;
		p5.fill(usuario.equipo.color, map);

		float withoBis = p5.map(contador, 0, tiempoDeComentario, widtho/2, widtho);
		p5.ellipse(x, y,  withoBis, withoBis);
		p5.popStyle();
		tituloMensaje.pintaMensaje(font, p5.color(100), p5.color(0), titulo, x,y+heighto, 9,  tiempoDeComentario/2, tiempoDeComentario/4);
		equipoMensaje.pintaMensaje(font, p5.color(100), usuario.equipo.color, usuario.equipo.nombre, x,y-(tituloMensaje.textAscent)+heighto, 9,  tiempoDeComentario/2, tiempoDeComentario/4);
		if(!yaSaturado)
		contador++;
		else
			if(contador>70)
				contador--;

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
