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

public class ForosCuadricula extends PApplet {

	Log log = LogFactory.getLog(getClass());

	List<EquipoEscale> equipos = new ArrayList<EquipoEscale>();
	List<EquipoEscale> equiposDB = new ArrayList<EquipoEscale>();
	PFont font;
	GrabacionEnVideo grabacionEnVideo;
	private boolean grabando = false;
	
	public void setup() {
		colorMode(HSB, 100);
		size(800, 600);
		smooth();
		font = loadFont("Courier10PitchBT-Roman-25.vlw");

		comentarios=new ServicioLoadEquipos(this).loadXML(equipos, equiposDB);
		
		grabacionEnVideo = new GrabacionEnVideo(this, grabando);
		
		
	}

	

	List<ComentarioEscale> comentarios;
	List<ComentarioEscale> comentariosRepresentados = new ArrayList<ComentarioEscale>();


	int tiempoDeComentario = 30 * 4;

	public void draw() {
		background(100);
		int numeroEquiposDB = equiposDB.size();
		float altoBanda=height/numeroEquiposDB;
		for(int i=0; i<numeroEquiposDB; i++){
			EquipoEscale equipo=(EquipoEscale)equiposDB.get(i);
			noFill();
			stroke(0);
			rect(0,0,width, i*altoBanda);
			pintaMensaje(color(0), equipo.nombre, 100, i*altoBanda, g, 12, CENTER);
		}
		
		
		
		
		grabacionEnVideo.addFotograma();

	}

	void pintaMensaje(int c, String mensaje, float x, float y, PGraphics g, int tam, int align) {
		g.textFont(font);
		g.fill(0);
		g.noStroke();
		g.textSize(tam);
		g.textAlign(align);
//		g.rect(x, y, textWidth(mensaje), textAscent());
		g.fill(c);
		g.text(mensaje, x, y + textAscent());
	}


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

