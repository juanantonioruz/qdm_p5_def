package qdmp5.escale;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import qdmp5.ClaseP5;
import toxi.physics2d.VerletParticle2D;

public class UsuarioEscale extends ModeloEscaleBase {
	int id;
	String nombre;
	String mail;
	EquipoEscale equipo;
	boolean representado;

	public UsuarioEscale(PApplet p5, int _id, String _nombre, String _mail, EquipoEscale _e) {
		super(p5);
		this.equipo = _e;
		this.id = _id;
		this.nombre = _nombre;
		this.mail = _mail;
		// TODO asignar x e y en funcion de equipo
		//
		// particle = new
		// VerletParticle2D(equipo.particle.copy().add(p5.random(-0.1f, 0.1f),
		// p5.random(-0.1f, 0.1f)));
		// TODO asignar ancho

	}

	List<ComentarioEscale> comentarios = new ArrayList();
	List<ComentarioEscale> comentariosRepresentados = new ArrayList();

	void addComentarioRepresentado(ComentarioEscale c) {
		comentariosRepresentados.add(c);
		float areas = 0;
		for (ComentarioEscale u : comentariosRepresentados) {
			areas += p5.PI * p5.pow((u.widtho / 2), 2);
		}
		float radio = p5.sqrt(areas / p5.PI) + 7;
		widtho = radio * 2;
		heighto = widtho;

	}

	public void addComentario(ComentarioEscale c) {
		comentarios.add(c);
		/*
		 * if(equipo.spring!=null){
		 * equipo.spring.setRestLength(equipo.spring.getRestLength()-50);
		 * equipo.spring.setStrength(equipo.spring.getStrength()+random(1)); }
		 */
	}

	public void pinta() {
		p5.pushStyle();
		p5.noFill();
		p5.strokeWeight(2);

		p5.stroke(p5.hue(equipo.color), 70, 80);

		p5.ellipse(x, y, widtho, heighto);
		p5.popStyle();

	}

}
