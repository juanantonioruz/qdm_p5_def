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
import qdmp5.ClaseP5;
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
	private boolean grabando = true ;

	List<Fila> filas;

	int anchoMaximoComentario=0;
	public void setup() {
		colorMode(HSB, 100);
		size(1000, 600);
		smooth();
		font = loadFont("Courier10PitchBT-Roman-25.vlw");

		comentarios = new ServicioLoadEquipos(this).loadXML(equipos, equiposDB);
		int numeroComentarios = comentarios.size();
		anchoComentario = width / numeroComentarios;
		for(ComentarioEscale c:comentarios)
			if(c.texto.length()>anchoMaximoComentario) anchoMaximoComentario=c.texto.length();
		filas = iniciaFilas();

		grabacionEnVideo = new GrabacionEnVideo(this, grabando);

	}
	Fila dameFila(EquipoEscale e){
		for(Fila f:filas)
			if(f.equipo.equals(e))return f;
		throw new RuntimeException();
	}

	List<ComentarioEscale> comentarios;
	List<Rectangulo> comentariosRepresentados = new ArrayList<Rectangulo>();

	int tiempoDeComentario = 30 ;
	float anchoComentario;

	static int framesPorComentario = 15;
	public void draw() {
		background(100);
		boolean compruebaTiempoDeAparicionComentario = compruebaTiempoDeAparicionComentario(framesPorComentario);
//		pintaReticulaEquipos();
		pintaComentarios(false);
		pintaComentarios(true);
		if (compruebaTiempoDeAparicionComentario || (rectanguloActual != null && rectanguloActual.isPintando())) {

			println(rectanguloActual.comentario.titulo);

		//	rectanguloActual.pinta();
		//	pintaMensaje(color(100), rectanguloActual.comentario.titulo, 250, rectanguloActual.y+rectanguloActual.height/2, g, 20, LEFT);
		}
		pintaNombresEquipos();

		grabacionEnVideo.addFotograma();

	}
	private void pintaComentarios(boolean linea) {
		int contadorLineas=1;
		pushStyle();
		strokeCap(SQUARE);

		int posicionX=200;
		for(Rectangulo r:comentariosRepresentados){
			Fila f=dameFila(r.comentario.usuario.equipo);
			stroke(r.comentario.usuario.equipo.color, map(r.comentario.texto.length(), 30,anchoMaximoComentario, 30,50));
//			noStroke();
			float map = map(r.comentario.texto.length(), 30,anchoMaximoComentario, 10,60);
			strokeWeight(map);
			noFill();
			int ancho = 0;
			float puntoY=0;
			int posYLineaTiempo = height-10;
			if(r.y>posYLineaTiempo) puntoY=posYLineaTiempo+200;
			else 
				puntoY=posYLineaTiempo-200;
			//while(ancho<r.width){
			float fX = posicionX+map/2;
			if(!linea)
				  bezier(f.x+textWidth(f.equipo.nombre)/2, 70, 
						  fX-fX/2, puntoY, 
						  fX, puntoY, 
						  fX, posYLineaTiempo);
					stroke(color(0), 100);
					strokeWeight(1);
					if(linea)
						  bezier(f.x+textWidth(f.equipo.nombre)/2, 70, 
							  fX-fX/2, puntoY, 
							  fX, puntoY, 
							  fX, posYLineaTiempo);

//			line(0, r.y+r.height/2, r.x+ancho, (height/2));
			//ancho+=2;
			//}
			strokeWeight(1);
			//line(r.x, (height/2)+contadorLineas, width, (height/2)+contadorLineas);
//			line(posicion + anchoComentario, posicionY, posicion + anchoComentario, posicionY + Fila.height);

			rectanguloActual=r;
			contadorLineas+=5;
			posicionX+=map;
		}

		popStyle();
	}

	private List<Fila> iniciaFilas() {
		List<Fila> filas = new ArrayList<Fila>();
		int numeroEquiposDB = equiposDB.size();
		float altoBanda = height / numeroEquiposDB;
		int numeroEquipos = equiposDB.size();
		float anchoEquipo=width/numeroEquipos;

		for (int i = 0; i < equiposDB.size(); i++) {
			EquipoEscale equipo = (EquipoEscale) equiposDB.get(i);
			Fila fila=new Fila(this, anchoEquipo*i, i * altoBanda, width, altoBanda , equipo);
			filas.add(fila);
		}
		return filas;
	}

	private void pintaReticulaEquipos() {

		pushStyle();
		for (Fila f:filas) {
			EquipoEscale equipo = f.equipo;
			float transparencia = map(equipo.comentariosRepresentados.size(), 0, equipo.comentarios.size(), 15, 45);
			// println(equipo.nombre+"="+transparencia);
			// fill(equipo.color, transparencia);
			stroke(0);
			strokeWeight(1);
			noStroke();
			fill(equipo.color,20);
			rect(f.x, f.y , f.width, f.height);

		}
		popStyle();
	}
	private void pintaNombresEquipos() {
		
		pushStyle();
		int numeroEquipos = filas.size();
		float anchoEquipo=width/numeroEquipos;
		int contador = 0;
		for (Fila f:filas) {
			EquipoEscale equipo = f.equipo;
			stroke(color(100));
			fill(equipo.color);
			g.rect(f.x, 50, textWidth(equipo.nombre.toUpperCase())+20, textAscent()+20);
			pintaMensaje(color(100), equipo.nombre.toUpperCase(), f.x, 50, g, 22, LEFT);
			contador++;
			
		}
		popStyle();
	}

	 void pintaMensaje(int c, String mensaje, float x, float y, PGraphics g, int tam, int align) {
		g.textFont(font);
		g.fill(0);
		g.noStroke();
		g.textSize(tam);
		g.textAlign(align);
		// g.rect(x, y, textWidth(mensaje), textAscent());
		g.fill(c);
		g.text(mensaje, x, y + textAscent());
	}

	Rectangulo rectanguloActual;

	private boolean compruebaTiempoDeAparicionComentario(int frames) {
		if ((frameCount % frames == 0) && (comentarios.size() != comentariosRepresentados.size())) {
			ComentarioEscale comentarioActual = comentarios.get(comentariosRepresentados.size());

			int idEquipo = comentarioActual.usuario.equipo.id;
			float posicion = (comentariosRepresentados.size()) * anchoComentario;
			float posicionY = (idEquipo - 1) * Fila.height;

			Rectangulo r = new Rectangulo(this, posicion, posicionY, anchoComentario, Fila.height, comentarioActual);

			comentariosRepresentados.add(r);
			rectanguloActual=r;
			EquipoEscale inE = comentarioActual.usuario.equipo;
			inE.comentariosRepresentados.add(comentarioActual);
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

class Rectangulo extends ClaseP5 {
	float x, y, width;
	static float height;
	ComentarioEscale comentario;
	Fila fila;
	int contador;

	public boolean isPintando() {
		return contador < ForosCuadricula.framesPorComentario;
	}

	public Rectangulo(PApplet p5, float x, float y, float width, float height, ComentarioEscale comentario) {
		super(p5);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.comentario = comentario;
	}
	float angle=0;
	public void pinta() {
//		contador++;
//		angle+=p5.PI/ForosCuadricula.framesPorComentario;
//		p5.fill(comentario.usuario.equipo.color,70);
//		int v=ForosCuadricula.framesPorComentario/2;
//		float vx=x/2;
//		float widthDyna=p5.map(v + v*p5.sin(angle), v,2*v, width,p5.width);
//		float xDyna=p5.map(vx + vx*p5.sin(angle), vx,2*vx, x,0);
//		if(x!=0)
//		p5.rect(xDyna, y, widthDyna, height);
//		else
//			p5.rect(x, y, widthDyna, height);
			
	}

}

class Fila extends ClaseP5{
	float x, y, width;
	static float height;
	EquipoEscale equipo;
	List<Rectangulo> rectangulos = new ArrayList<Rectangulo>();
	public Fila(PApplet p5,float x, float y, float width, float height, EquipoEscale equipo) {
		super(p5);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.equipo = equipo;
	}
	
}
