package qdmp5.escale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;
import qdmp5.Equipo;
import qdmp5.ForosXMLLoad;
import qdmp5.GrabacionEnVideo;
import qdmp5.ServicioToxiColor;
import toxi.color.ColorList;
import toxi.color.TColor;
import toxi.geom.*;
import toxi.physics2d.*;
import toxi.physics2d.behaviors.*;

public class ForosEscale extends PApplet {

	PImage a;
	Log log = LogFactory.getLog(getClass());

	List<EquipoEscale> equipos;
	List<EquipoEscale> equiposDB;
	PFont font;
	GrabacionEnVideo grabacionEnVideo;
	private boolean grabando = false;

	public void setup() {
		colorMode(HSB, 100);
		a = loadImage("peter_medium.png");
		size(a.width, a.height);
		smooth();
		font = loadFont("Courier10PitchBT-Roman-25.vlw");

		equipos = new ArrayList<EquipoEscale>();
		equiposDB = new ArrayList<EquipoEscale>();
		println(a.width + " - " + a.height);
		equiposDB.add(new EquipoEscale(this, 1, "bamako", 224, 122, "Niamakoro y Sicoro"));
		equiposDB.add(new EquipoEscale(this, 2, "barcelona", 236, 55, "Casc Antic"));
		equiposDB.add(new EquipoEscale(this, 3, "bogota", 133, 145, "Chapinero"));
		equiposDB.add(new EquipoEscale(this, 4, "elalto", 141, 174, "Santa Rosa"));
		equiposDB.add(new EquipoEscale(this, 5, "evry", 238, 39, "Pyramides"));
		equiposDB.add(new EquipoEscale(this, 6, "montreuil", 243, 43, "Bel-Pêche"));
		equiposDB.add(new EquipoEscale(this, 7, "palma", 241, 61, "Son Roca y Son Gotleu"));
		equiposDB.add(new EquipoEscale(this, 8, "pikine", 210, 121, "Wakhinane"));
		equiposDB.add(new EquipoEscale(this, 9, "rio", 175, 221, "La Maré y Rio das Pedras"));
		equiposDB.add(new EquipoEscale(this, 10, "sale", 224, 72, "Karyan El Oued"));
		ColorList listaColoresEquipo = new ServicioToxiColor().iniciaColoresEquipos();

		for (int i = 0; i < equiposDB.size(); i++)
			equiposDB.get(i).setColor((TColor) listaColoresEquipo.get(i));
		ForosXMLLoadScale forosXMLLoad = new ForosXMLLoadScale(this, equiposDB);
		comentarios = forosXMLLoad.procesaXML("foros.xml");
		Collections.reverse(comentarios);
		grabacionEnVideo = new GrabacionEnVideo(this, grabando);
		escalaActual = new EscalaYTranslacion(1, width/2, height/2);
		mapeo =escalaActual.scale;
		x = escalaActual.x;
		y =escalaActual.y;

	}
	int ascenderGeneral=2;
	int ascenderZoom=3;
	int descender=1;
	List<ComentarioEscale> comentarios;
	List<ComentarioEscale> comentariosRepresentados = new ArrayList<ComentarioEscale>();
	int equipo = 6;

	float dameIncremento(float x, float y) {
		if (x > y)
			return y;
		return x;
	}

	int tiempoDeComentario = 30 * 4;

	public void draw() {
		comprueba();

		translate(x, y); // use translate around scale
		scale(mapeo);
		translate(-x, -y); // to scale from the center
		image(a, 0, 0);
		ellipse(x, y,100,100);
		for (int i = 0; i < comentariosRepresentados.size(); i++) {
			ComentarioEscale comentario = comentariosRepresentados.get(i);

			comentario.pinta(font, tiempoDeComentario);

			if (i > 0) {
				ComentarioEscale comentarioAnterior = comentariosRepresentados.get(i - 1);
				noFill();
				strokeWeight(0.2f);
				bezier(comentarioAnterior.x, comentarioAnterior.y, comentarioAnterior.x
						+ (comentario.x - comentarioAnterior.x) / 4 + random(-1, 1), comentarioAnterior.y
						- (comentario.y + comentarioAnterior.y) / 4, comentario.x
						- (comentario.x - comentarioAnterior.x) / 4, comentario.y
						- (comentario.y + comentarioAnterior.y) / 4, comentario.x, comentario.y);

			}
		}
		grabacionEnVideo.addFotograma();

	}

	int[] movs= {descender, ascenderGeneral};
	int estadoActual;
	int contador;
	int movimientoActual;
	boolean firstElement=true;
	void comprueba() {

		boolean incluidoComentarioSegunFrame = compruebaTiempoDeAparicionComentario(tiempoDeComentario);

		int intervaloCambio = tiempoDeComentario/2;
		int intervaloCambioTransicion = tiempoDeComentario/4;
		int velocidad=5;
		if ((frameCount % intervaloCambio==0) || frameCount==1){
			contador++;
			estadoActual=contador%movs.length;
			movimientoActual = movs[estadoActual];
			entradaCount=0;

			if (comentariosRepresentados.size() == 1 ){
				if(firstElement){
				contador++;
				movimientoActual = movs[0];
				firstElement=false;
				escalaAnterior=escalaActual;
				escalaActual = calculaEscala(true);

				}else{
					escalaAnterior=escalaActual;
	escalaActual = new EscalaYTranslacion(1, width/2, height/2);

				}
				println(estadoActual+"  --- "+contador+"movimientoActual: "+movimientoActual);
			}else if(comentariosRepresentados.size()>1){
				println("sig"+estadoActual+"  --- "+contador+"movimientoActual: "+movimientoActual);
				println("equipos.size(): "+equipos.size());
				boolean ascendiendo = movimientoActual==ascenderGeneral;
				escalaAnterior=escalaActual;
				escalaActual = calculaEscala(!ascendiendo);
			}

			
		}

			if (escalaActual != null && escalaAnterior != null) {
				if(entradaCount<intervaloCambioTransicion)
					if(entradaCount+velocidad>=intervaloCambioTransicion)
						entradaCount=intervaloCambioTransicion;
					else
						entradaCount+=velocidad;
				mapeo = map(entradaCount, 0, intervaloCambioTransicion, escalaAnterior.scale, escalaActual.scale);
				x = map(entradaCount, 0, intervaloCambioTransicion, escalaAnterior.x, escalaActual.x);
				y = map(entradaCount, 0, intervaloCambioTransicion, escalaAnterior.y, escalaActual.y);
				log.debug("escalaAnterior.scale:" + escalaAnterior.scale + " escalaActual.scale:" + escalaActual.scale
						+ " mapeo" + mapeo);
				log.debug(x+"-"+y+" ecale: "+mapeo+" escalaAnterior.scale:"+escalaAnterior.scale+ " escalaActual.scale"+escalaActual.scale);
				
			}


	}
	int entradaCount;

	private boolean compruebaTiempoDeAparicionComentario(int frames) {
		if ((frameCount % frames == 0) && (comentarios.size() != comentariosRepresentados.size())) {
			ComentarioEscale comentarioActual = comentarios.get(comentariosRepresentados.size());
			comentariosRepresentados.add(comentarioActual);
			EquipoEscale inE = comentarioActual.usuario.equipo;

			boolean existeEquipo = equipos.contains(inE);

			if (!existeEquipo) {
				equipos.add(inE);
			}
			return true;


		}
		return false;
	}

	private EscalaYTranslacion calculaEscala(boolean soloUltimo) {

		float equilibrio=30;
		float dameX1 = dameX1(soloUltimo)-equilibrio ;
		float dameX2 = dameX2(soloUltimo)+equilibrio;
		float dameY1 = dameY1(soloUltimo)-equilibrio;
		float dameY2 = dameY2(soloUltimo)+equilibrio;
		
		float anchoRepresentar = dameX2 - dameX1;
		float altoRepresentar = dameY2 - dameY1;
		float escalaProvX = (width / anchoRepresentar);
		float escalaProvY = (height / altoRepresentar);
		float incremento = dameIncremento(escalaProvX, escalaProvY);


		float origenX = dameX1+(anchoRepresentar/2) ;
		float origenY = dameY1+(altoRepresentar/2) ;
		log.debug("origenX" + origenX + " origenY" + origenY);
//		while (origenX-(anchoRepresentar/2) < 0)
//			origenX --;
//		while (origenY-(altoRepresentar/2) < 0)
//			origenY--;
		escalaAnterior = escalaActual;
		EscalaYTranslacion escalaActual = new EscalaYTranslacion(incremento, origenX, origenY);

		return escalaActual;
		
	}

	EscalaYTranslacion escalaAnterior;
	EscalaYTranslacion escalaActual;
	float mapeo=1;
	float x=0;
	float y=0;
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

	float dameX1(boolean soloUltimo) {
		if(soloUltimo) return ultimoComent().x-soloUnEquipoMargin;

		float resultado = width;
		for (EquipoEscale e : equipos) {
			if (e.x < resultado)
				resultado = e.x;
		}
		return resultado;
	}

	private EquipoEscale ultimoComent() {
		return comentariosRepresentados.get(comentariosRepresentados.size()-1).usuario.equipo;
	}
	private float soloUnEquipoMargin=50;

	float dameX2(boolean soloUltimo) {
		if(soloUltimo) return ultimoComent().x+soloUnEquipoMargin;
	float resultado = 0;
		for (EquipoEscale e : equipos) {
			if (e.x > resultado)
				resultado = e.x;
		}
		return resultado;
	}

	float dameY1(boolean soloUltimo) {
		if(soloUltimo) return ultimoComent().y-soloUnEquipoMargin;
	float resultado = height;
		for (EquipoEscale e : equipos) {
			if (e.y < resultado)
				resultado = e.y;
		}
		return resultado;
	}

	float dameY2(boolean soloUltimo) {
		if(soloUltimo) return ultimoComent().y+soloUnEquipoMargin;
		float resultado = 0;
		for (EquipoEscale e : equipos) {
			if (e.y > resultado)
				resultado = e.y;
		}
		return resultado;
	}

	void pintaMensaje(int c, String mensaje, float x, float y, PGraphics g, int tam, int align) {
		g.textFont(font);
		g.fill(0);
		g.noStroke();
		g.textSize(tam);
		g.textAlign(align);
		g.rect(x, y, textWidth(mensaje), textAscent());
		g.fill(c);
		g.text(mensaje, x, y + textAscent());
	}

	public void keyPressed() {
		if (key == ' ') {
			grabacionEnVideo.finalizaYCierraApp();
		}
	}

}

class EscalaYTranslacion {
	public EscalaYTranslacion(float incremento, float origenX, float origenY) {
		scale = incremento;
		x = origenX;
		y = origenY;
	}

	float scale;
	float x;
	float y;

}