package qdmp5;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;

import com.enjava.p5.servicios.ServicioFechas;

/**
 * tamanyo == cantidad de palabras tiempo == tiempointeractivo/tiempo foro ----
 * las fechas muy largas habría que acortarlas.... numero
 * aportaciones=luminosidad polemico=numero de contestaciones lugar:pais-barrio
 * onMouse-over toxi-color
 * 
 * 
 * Se hace una simulacion temporal de aparicion de comentarios,,, es por eso que
 * es de forma incremental el proceso y afecta al modelo y a la ui
 * 
 * @author juanitu
 * 
 */

public class Foros extends PApplet {

	ForosPhysics physics;
	boolean debug = false;
	boolean grabando = false;
	PFont font;
	ServicioFechas servicioFechas;
	Log log = LogFactory.getLog(getClass());
	GrabacionEnVideo grabacionEnVideo;
	List<Comentario> comentarios;
	String tituloForo="Foro Espacio público";

	public void setup() {
		size(1200, 600);
		colorMode(HSB, 100);
		background(0);
		smooth();
		font = loadFont("Courier10PitchBT-Roman-25.vlw");
		textFont(font);

		physics = new ForosPhysics(this);
		physics.setup();

		ServicioEquiposForo servicioEquiposForo = new ServicioEquiposForo(this);
		List<Equipo> equiposIn = servicioEquiposForo.iniciaEquipos();

		ForosXMLLoad forosXMLLoad = new ForosXMLLoad(this, equiposIn);
		comentarios = forosXMLLoad.procesaXML("foros.xml");
		Collections.reverse(comentarios);

		if (!(comentarios.size() > 2)) {
			noLoop();
			pintaMensaje(color(100), "comentarios insuficientes", 0, 0, this.g, 25);
		}
		grabacionEnVideo = new GrabacionEnVideo(this, grabando);

		iniciaCapasGraficas();

	}

	Capa capaEquipos, capaUsuarios, capaComentarios, capaRelaciones, capaMensajes;

	private void iniciaCapasGraficas() {
		capaEquipos = iniciaCapa();
		capaUsuarios = iniciaCapa();
		capaComentarios = iniciaCapa();
		capaRelaciones = iniciaCapa();
		capaMensajes = iniciaCapa();
	}

	private Capa iniciaCapa() {

		Capa c = new Capa(this);
		PGraphics pg = createGraphics(1200, 600, this.P2D);
		pg.smooth();
		c.g = pg;
		return c;
	}

	private void iniciaPGraphics(Capa c, Visualizable v) {
		PGraphics pg = createGraphics(1200, 600, this.P2D);
		pg.beginDraw();
		pg.colorMode(HSB, 100);
		pg.smooth();
		c.g = pg;
		if (v != null)
			c.reset(v);
	}

	public void draw() {
		physics.update();
		background(0);
		pintaMensaje(color(100), (int) (map(contador, comentarios.size(), 0, 0, 100)) + "%", width - 100, 50, this.g,
				20);

		iniciaPGraphics(capaUsuarios, null);
		iniciaPGraphics(capaComentarios, null);
		iniciaPGraphics(capaEquipos, null);
		iniciaPGraphics(capaRelaciones, null);
		iniciaPGraphics(capaMensajes, null);

		int framesParaAparicionDeNuevoComentario = 2;
		if (frameCount % framesParaAparicionDeNuevoComentario == 0)
			comprueba();

		pintaLasCapas();

		image(capaEquipos.g, 0, 0);
		tint(100, 50);
		noStroke();
		image(capaUsuarios.g, 0, 0);
		image(capaComentarios.g, 0, 0);
		image(capaRelaciones.g, 0, 0);
		tint(100, 100);

		image(capaMensajes.g, 0, 0);

		capaEquipos.endDraw();
		capaUsuarios.endDraw();
		capaComentarios.endDraw();
		capaRelaciones.endDraw();
		capaMensajes.endDraw();

		grabacionEnVideo.addFotograma();
	}

	private void pintaLasCapas() {
		
		capaMensajes.g.fill(30);
		capaMensajes.g.noStroke();
		float textSize=50;
		capaMensajes.g.textSize(textSize);
		capaMensajes.g.textFont(createFont("Courier", textSize));
		float anchoMensaje = capaMensajes.g.textWidth(tituloForo);
		float altoMensaje = capaMensajes.g.textAscent();
		//capaMensajes.g.rect(width-anchoMensaje,100, anchoMensaje, altoMensaje);
		
		
		capaMensajes.g.textAlign(LEFT);
		capaMensajes.g.fill(color(100));
		capaMensajes.g.text(tituloForo, width-anchoMensaje,altoMensaje+100 );

		
		
		
		
		for (Visualizable c : capaComentarios.elementos) {
			Comentario cc = (Comentario) c;
			cc.pinta(capaComentarios);
			if (cc.rollOver()) {
				
				pintaMensaje(color(100), cc.titulo, 500, 10 + (1 * 25), this.g, 15);
				this.stroke(color(100, 30));
				this.line(500 + textWidth(cc.titulo), 10 + (1 * 25), cc.particle.x, cc.particle.y);
			}
		}
		for (Visualizable u : capaUsuarios.elementos)
			((Usuario) u).pinta(capaUsuarios);
		for (Visualizable e : capaEquipos.elementos)
			((Equipo) e).pinta(capaEquipos);
		for (Visualizable r : capaRelaciones.elementos)
			((Relacion) r).pinta(capaRelaciones);

		int contaM = 0;
		for (int i = capaComentarios.elementos.size() - 1; i >= 0; i--) {
			Comentario cc = ((Comentario) capaComentarios.elementos.get(i));
			int colorMensaje = color(hue(cc.usuario.equipo.c), 70, 90,
					map(contaM, 0, capaComentarios.elementos.size(), 100, 20));
			pintaMensaje(colorMensaje, cc.titulo, 10, 10 + (contaM * 25), this.g, 15);
			if (contaM < 3 && debug) {
				capaMensajes.g.stroke(colorMensaje);
				capaMensajes.g.line(10 + textWidth(cc.titulo), 10 + (contaM * 25), cc.particle.x, cc.particle.y);
			}
			contaM++;
		}
		for (Visualizable v : capaEquipos.elementos) {
			Equipo e = (Equipo) v;
			pintaMensaje(color(hue(e.c), 100, 100), e.nombre, e.particle.x, e.particle.y + (e.heighto / 2), capaMensajes.g,
					20, CENTER);
		}
	}

	public void keyPressed() {
		if (key == ' ') {
			grabacionEnVideo.finalizaYCierraApp();
		}
	}

	int contador;

	void comprueba() {
		if (contador >= comentarios.size())
			return;
		
		Comentario comentarioActual = comentarios.get(contador);
		Usuario usuarioActual = comentarioActual.usuario;
		Equipo equipoActual = usuarioActual.equipo;

		
		usuarioActual.addComentarioRepresentado(comentarioActual);
		equipoActual.addUsuarioRepresentado(usuarioActual);


		// Actualiza el modelo UI /modelo a visulizar / las capas y el modelo
		// fisico
		boolean existeEquipo = capaEquipos.addElemento(equipoActual);
		if (!existeEquipo) {
			physics.addParticleEquipo(equipoActual);
			physics.separaMinimoEquipo(equipoActual);
		}
		// actualiza el modelo DB con informacion de visualizacion

		boolean existeUsuario = capaUsuarios.addElemento(usuarioActual);

		if (!existeUsuario) {
			physics.fijaUsuarioAEquipo(usuarioActual);
			physics.mantenDistanciaEntreUsuariosMismoEquipo(usuarioActual);
		}

		// nunca existe el comentario de antemano... por ello no hay comprobacion previa
		capaComentarios.elementos.add(comentarioActual);

		physics.fijaComentarioAUsuario(comentarioActual);
		physics.mantenDistanciaEntreComentariosMismoUsuario(comentarioActual);


		if (comentarioActual.parent != 0) {
			Comentario parent = dameComentario(comentarioActual.parent);
			Relacion r = new Relacion(this);
			r.origen = parent;
			r.fin = comentarioActual;
			capaRelaciones.addElemento(r);
		}

		contador++;
	}

	void pintaMensaje(int c, String mensaje, float x, float y, PGraphics g, int tam) {
		pintaMensaje(c, mensaje, x, y, g, tam, LEFT);
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

	Comentario dameComentario(int id) {
		for (Comentario c : comentarios)
			if (c.id == id)
				return c;
		throw new RuntimeException();
	}

}
