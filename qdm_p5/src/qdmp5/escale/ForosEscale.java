package qdmp5.escale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;
import qdmp5.Equipo;
import qdmp5.ForosXMLLoad;
import qdmp5.ServicioToxiColor;
import toxi.color.ColorList;
import toxi.color.TColor;
import toxi.geom.*;
import toxi.physics2d.*;
import toxi.physics2d.behaviors.*;

public class ForosEscale extends PApplet {

	PImage a;

	List<EquipoEscale> equipos;
	List<EquipoEscale> equiposIn;

	public void setup() {

		colorMode(HSB, 100);
		a = loadImage("peter_medium.png");
		size(a.width, a.height);
		smooth();

		equipos = new ArrayList<EquipoEscale>();
		equiposIn = new ArrayList<EquipoEscale>();
		println(a.width + " - " + a.height);
		equiposIn.add(new EquipoEscale(this, 1, "bamako", 224, 122, "Niamakoro y Sicoro"));
		equiposIn.add(new EquipoEscale(this, 2, "barcelona", 236, 55, "Casc Antic"));
		equiposIn.add(new EquipoEscale(this, 3, "bogota", 133, 145, "Chapinero"));
		equiposIn.add(new EquipoEscale(this, 4, "elalto", 141, 174, "Santa Rosa"));
		equiposIn.add(new EquipoEscale(this, 5, "evry", 238, 39, "Pyramides"));
		equiposIn.add(new EquipoEscale(this, 6, "montreuil", 243, 43, "Bel-Pêche"));
		equiposIn.add(new EquipoEscale(this, 7, "palma", 241, 61, "Son Roca y Son Gotleu"));
		equiposIn.add(new EquipoEscale(this, 8, "pikine", 210, 121, "Wakhinane"));
		equiposIn.add(new EquipoEscale(this, 9, "rio", 175, 221, "La Maré y Rio das Pedras"));
		equiposIn.add(new EquipoEscale(this, 10, "sale", 224, 72, "Karyan El Oued"));
		ColorList listaColoresEquipo = new ServicioToxiColor().iniciaColoresEquipos();

		for (int i = 0; i < equiposIn.size(); i++)
			equiposIn.get(i).setColor((TColor) listaColoresEquipo.get(i));
		ForosXMLLoadScale forosXMLLoad = new ForosXMLLoadScale(this, equiposIn);
		comentarios = forosXMLLoad.procesaXML("foros.xml");
		Collections.reverse(comentarios);

	}

	List<ComentarioEscale> comentarios;
	List<ComentarioEscale> comentariosRepresentados = new ArrayList<ComentarioEscale>();
	int equipo = 6;

	float dameIncremento(float x, float y) {
		if (x > y)
			return y;
		return x;
	}


	public void draw() {
	if (frameCount % 45 == 0 && (comentarios.size() != comentariosRepresentados.size())) {
		println("+sizeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"+comentarios.size() +">!="+ comentariosRepresentados.size());
			ComentarioEscale comentarioActual = comentarios.get(comentariosRepresentados.size());
			comentariosRepresentados.add(comentarioActual);
			EquipoEscale inE = comentarioActual.usuario.equipo;

			boolean existeEquipo = equipos.contains(inE);
			if (!existeEquipo) {
				equipos.add(inE);
			}
		}
		float dameX1 = dameX1();
		float dameX2 = dameX2();
		float dameY1 = dameY1();
		float dameY2 = dameY2();
		println(dameX1 + "," + dameY1 + " - " + dameX2 + "," + dameY2);
		float anchoRepresentar = dameX2 - dameX1;
		float altoRepresentar = dameY2 - dameY1;
		float escalaProvX = (width / anchoRepresentar);
		float escalaProvY = (height / altoRepresentar);
		println("escalaProvX:" + escalaProvX);
		println("escalaProvY:" + escalaProvY);
		if (equipos.size() > 1) {
			float incremento = dameIncremento(escalaProvX, escalaProvY);
			println("incremento:" + incremento);
			scale(incremento);

			float posicionX;
			float posicionY;
			// desplazamiento inversamente proporcional a la escala
			float cantidadCentrado;
			if (incremento == escalaProvX) {
				posicionX = dameX1;
				posicionY = dameY1 - (altoRepresentar / incremento);

			} else {
				posicionX = dameX1 - (anchoRepresentar / incremento);
				posicionY = dameY1;

			}
			cantidadCentrado = 0;
			float origenX;
			float origenY;
			origenX = -posicionX + cantidadCentrado;
			origenY = -posicionY + cantidadCentrado;
			translate(origenX, origenY);
		}
		image(a, 0, 0);
		for(int i=0; i<comentariosRepresentados.size(); i++){
			ComentarioEscale comentario=comentariosRepresentados.get(i);
			
			comentario.pinta();
			if(i>0){
				ComentarioEscale comentarioAnterior=comentariosRepresentados.get(i-1);
				 noFill();
				 strokeWeight(0.2f);
				 println("lineando");
				 bezier(comentarioAnterior.x, comentarioAnterior.y, comentarioAnterior.x + (comentario.x - comentarioAnterior.x) / 4+random(-1,1),
				 comentarioAnterior.y - (comentario.y + comentarioAnterior.y) / 4, comentario.x
				 - (comentario.x - comentarioAnterior.x) / 4, comentario.y - (comentario.y + comentarioAnterior.y) / 4, comentario.x,
				 comentario.y);

			}
		}
		// for (int i = 0; i < comentarios.size(); i++) {
		// Node inicio = comentarios.get(i);
		// Node fin = comentarios.get((int) (random(comentarios.size() - 1)));
		// if (inicio == fin)
		// continue;
		// stroke(0);
		// noFill();
		// strokeWeight(0.2f);
		// println("lineando");
		// bezier(inicio.x, inicio.y, inicio.x + (fin.x - inicio.x) / 4,
		// inicio.y - (fin.y + inicio.y) / 4, fin.x
		// - (fin.x - inicio.x) / 4, fin.y - (fin.y + inicio.y) / 4, fin.x,
		// fin.y);
		//
		// }
		if (debug)
			pintaZoomCuadro(dameX1, dameX2, dameY1, dameY2);
	}

	void pintaZoomCuadro(float x1, float x2, float y1, float y2) {
		noFill();
		stroke(0);
		strokeWeight(0.5f);
		rect(x1, y1, x2 - x1, y2 - y1);
	}

	boolean debug;

	public void mousePressed() {
		debug = true;
	}

	public void mouseReleased() {
		debug = false;
	}

	float dameX1() {
		float resultado = width;
		for (EquipoEscale e : equipos) {
			if (e.x < resultado)
				resultado = e.x;
		}
		return resultado;
	}

	float dameX2() {
		float resultado = 0;
		for (EquipoEscale e : equipos) {
			if (e.x > resultado)
				resultado = e.x;
		}
		return resultado;
	}

	float dameY1() {
		float resultado = height;
		for (EquipoEscale e : equipos) {
			if (e.y < resultado)
				resultado = e.y;
		}
		return resultado;
	}

	float dameY2() {
		float resultado = 0;
		for (EquipoEscale e : equipos) {
			if (e.y > resultado)
				resultado = e.y;
		}
		return resultado;
	}

}
