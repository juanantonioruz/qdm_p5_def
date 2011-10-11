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
import qdmp5.GrabacionEnVideo;
import qdmp5.ServicioToxiColor;
import toxi.color.ColorList;
import toxi.color.TColor;

public class ForosEscale extends PApplet {

	PImage mapa;
	Log log = LogFactory.getLog(getClass());

	List<EquipoEscale> equipos = new ArrayList<EquipoEscale>();

	List<EquipoEscale> equiposDB = new ArrayList<EquipoEscale>();
	PFont font;
	GrabacionEnVideo grabacionEnVideo;
	private boolean grabando = false;
	EscalaYTranslacion escalaAnterior;
	EscalaYTranslacion escalaActual;
	
	ServicioEscala servicioEscala;
	float mapeo = 1;
	float x = 0;
	float y = 0;
	public void setup() {
		colorMode(HSB, 100);
		mapa = loadImage("peter_medium.png");
		size(mapa.width, mapa.height);
		smooth();
		font = loadFont("Courier10PitchBT-Roman-25.vlw");
				
		comentarios=new ServicioLoadEquipos(this).loadXML(equipos, equiposDB);
		
		grabacionEnVideo = new GrabacionEnVideo(this, grabando);
		
		
		escalaActual = new EscalaYTranslacion(1, width / 2, height / 2);
		mapeo = escalaActual.scale;
		x = escalaActual.x;
		y = escalaActual.y;
		servicioEscala=new ServicioEscala(this, equipos, comentariosRepresentados);
	}

	


	int ascenderGeneral = 2;
	int ascenderZoom = 3;
	int descender = 1;
	List<ComentarioEscale> comentarios;
	List<ComentarioEscale> comentariosRepresentados = new ArrayList<ComentarioEscale>();


	int tiempoDeComentario = 30 * 4;

	public void draw() {
		comprueba();

		translate(x, y); // use translate around scale
		scale(mapeo);
		translate(-x, -y); // to scale from the center
		image(mapa, 0, 0);
	//	ellipse(x, y, 100, 100);
		for (int i = 0; i < comentariosRepresentados.size(); i++) {
			ComentarioEscale comentario = comentariosRepresentados.get(i);

			comentario.pinta(font, tiempoDeComentario/2);

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

	int[] movs = { descender, ascenderGeneral };
	int contador;
	boolean firstElement = true;

	void comprueba() {
		int estadoActual;
		int movimientoActual;

		boolean incluidoComentarioSegunFrame = compruebaTiempoDeAparicionComentario(tiempoDeComentario);

		int intervaloCambio = tiempoDeComentario / 2;
		int intervaloCambioTransicion = tiempoDeComentario / 4;
		int velocidad = 5;
		if ((frameCount % intervaloCambio == 0) || frameCount == 1) {
			contador++;
			estadoActual = contador % movs.length;
			movimientoActual = movs[estadoActual];
			entradaCount = 0;

			if (comentariosRepresentados.size() == 1) {
				if (firstElement) {
					contador++;
					movimientoActual = movs[0];
					firstElement = false;
					escalaAnterior = escalaActual;
					escalaActual = servicioEscala.calculaEscala(true, width, height);

				} else {
					escalaAnterior = escalaActual;
					escalaActual = new EscalaYTranslacion(1.5f, width / 2, height / 2);

				}
				println(estadoActual + "  --- " + contador + "movimientoActual: " + movimientoActual);
			} else if (comentariosRepresentados.size() > 1) {
				println("sig" + estadoActual + "  --- " + contador + "movimientoActual: " + movimientoActual);
				println("equipos.size(): " + equipos.size());
				boolean ascendiendo = movimientoActual == ascenderGeneral;
				escalaAnterior = escalaActual;
				escalaActual = servicioEscala.calculaEscala(!ascendiendo,width, height);
			}

		}

		if (escalaActual != null && escalaAnterior != null) {
			if (entradaCount < intervaloCambioTransicion)
				if (entradaCount + velocidad >= intervaloCambioTransicion)
					entradaCount = intervaloCambioTransicion;
				else
					entradaCount += velocidad;
			mapeo = map(entradaCount, 0, intervaloCambioTransicion, escalaAnterior.scale, escalaActual.scale);
			x = map(entradaCount, 0, intervaloCambioTransicion, escalaAnterior.x, escalaActual.x);
			y = map(entradaCount, 0, intervaloCambioTransicion, escalaAnterior.y, escalaActual.y);
			log.debug("escalaAnterior.scale:" + escalaAnterior.scale + " escalaActual.scale:" + escalaActual.scale
					+ " mapeo" + mapeo);
			log.debug(x + "-" + y + " ecale: " + mapeo + " escalaAnterior.scale:" + escalaAnterior.scale
					+ " escalaActual.scale" + escalaActual.scale);

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




	public void keyPressed() {
		if (key == ' ') {
			grabacionEnVideo.finalizaYCierraApp();
		}
	}

}

