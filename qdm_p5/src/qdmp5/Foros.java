package qdmp5;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.enjava.p5.servicios.ServicioFechas;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import toxi.physics2d.VerletConstrainedSpring2D;
import toxi.physics2d.VerletMinDistanceSpring2D;
import toxi.physics2d.VerletParticle2D;

/**
 * tamanyo == cantidad de palabras tiempo == tiempointeractivo/tiempo foro ----
 * las fechas muy largas habría que acortarlas.... numero
 * aportaciones=luminosidad polemico=numero de contestaciones lugar:pais-barrio
 * onMouse-over toxi-color
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

	Capa equipos, usuarios, comentariosG, relaciones, mensajes;

	private void iniciaCapasGraficas() {
		equipos = iniciaCapa();
		usuarios = iniciaCapa();
		comentariosG = iniciaCapa();
		relaciones = iniciaCapa();
		mensajes = iniciaCapa();
	}

	private Capa iniciaCapa() {

		Capa c = new Capa(this);
		PGraphics pg = createGraphics(1200, 600, this.P2D);

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
		pintaMensaje(color(100), (int) (map(((30 * 30) - contador), (30 * 30), 0, 100, 0)) + "%", width - 100, 50,
				this.g, 20);

		iniciaPGraphics(usuarios, null);
		iniciaPGraphics(comentariosG, null);
		iniciaPGraphics(equipos, null);
		iniciaPGraphics(relaciones, null);
		iniciaPGraphics(mensajes, null);

		comprueba();

		pintaLasCapas();
		
		image(equipos.g, 0, 0);
		tint(100, 50);
		noStroke();
		image(usuarios.g, 0, 0);
		image(comentariosG.g, 0, 0);
		// image(relaciones.g, 0, 0);
		tint(100, 100);

		image(mensajes.g, 0, 0);

		equipos.endDraw();
		usuarios.endDraw();
		comentariosG.endDraw();
		relaciones.endDraw();
		mensajes.endDraw();

		grabacionEnVideo.addFotograma();
	}

	private void pintaLasCapas() {
		for (Visualizable c : comentariosG.elementos) {
			Comentario cc = (Comentario) c;
			cc.pinta(comentariosG);
			if (cc.rollOver()) {
				pintaMensaje(color(100), cc.titulo, 500, 10 + (1 * 25), this.g, 15);
				this.stroke(color(100, 30));
				this.line(500 + textWidth(cc.titulo), 10 + (1 * 25), cc.particle.x, cc.particle.y);
			}
		}
		for (Visualizable u : usuarios.elementos)
			((Usuario) u).pinta(usuarios);
		for (Visualizable e : equipos.elementos)
			((Equipo) e).pinta(equipos);
		for (Visualizable r : relaciones.elementos)
			((Relacion) r).pinta(relaciones);

		int contaM = 0;
		for (int i = comentariosG.elementos.size() - 1; i >= 0; i--) {
			Comentario cc = ((Comentario) comentariosG.elementos.get(i));
			int colorMensaje = color(hue(cc.usuario.equipo.c), 70, 90,
					map(contaM, 0, comentariosG.elementos.size(), 100, 20));
			pintaMensaje(colorMensaje, cc.titulo, 10, 10 + (contaM * 25), this.g, 15);
			if (contaM < 3 && debug) {
				mensajes.g.stroke(colorMensaje);
				mensajes.g.line(10 + textWidth(cc.titulo), 10 + (contaM * 25), cc.particle.x, cc.particle.y);
			}
			contaM++;
		}
		for (Visualizable v : equipos.elementos) {
			Equipo e = (Equipo) v;
			pintaMensaje(color(hue(e.c), 100, 100), e.nombre, e.particle.x, e.particle.y + (e.heighto / 2), mensajes.g,
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

		if (frameCount % 10 == 0) {


			//actualiza el modelo DB con informacion de visualizacion
			usuarioActual.addComentarioRepresentado(comentarioActual);
			equipoActual.addUsuarioRepresentado(usuarioActual);

			//Actualiza el modelo UI /modelo a visulizar / las capas y el modelo fisico
			boolean existeEquipo = equipos.addElemento(equipoActual);
			if (!existeEquipo) {

				physics.addParticle(equipoActual.particle);
				VerletParticle2D particleOrigin = new VerletParticle2D(equipoActual.particle.copy());
				particleOrigin.lock();
				// physics.addParticle(particleOrigin);

				VerletConstrainedSpring2D spring1 = new VerletConstrainedSpring2D(particleOrigin, equipoActual.particle, 50, 0.5f);

				physics.addSpring(spring1);

				for (Visualizable v : equipos.elementos) {
					Equipo equipoBucle = (Equipo) v;
					if (equipoActual != equipoBucle) {
						VisualizableVerletMinDistanceSpring spring = new VisualizableVerletMinDistanceSpring(equipoActual, equipoBucle, 0.5f);
						equipoActual.springs.add(spring);
						physics.addSpring(spring);
					}
				}
			}

			boolean existeUsuario = usuarios.addElemento(usuarioActual);

			if (!existeUsuario) {

				usuarioActual.particle = new VerletParticle2D(equipoActual.particle.copy().add(random(-0.1f, 0.1f), random(-0.1f, 0.1f)));
				VerletConstrainedSpring2D springParticle = new VerletConstrainedSpring2D(equipoActual.particle, usuarioActual.particle, 0.2f,
						random(0.01f, 0.05f));
				physics.addSpring(springParticle);

				for (Usuario cequ : equipoActual.usuarios) {
					if (cequ != usuarioActual && usuarios.elementos.contains(cequ)) {
						VerletMinDistanceSpring2D spring = new VerletMinDistanceSpring2D(
								usuarioActual.particle, cequ.particle, usuarioActual.widtho / 2
										+ cequ.widtho / 2, 0.5f);
						physics.addSpring(spring);
					}
				}
			}

			int cx = (int) (usuarioActual.particle.x + random(-1, 1));
			int cy = (int) (usuarioActual.particle.y + random(-1, 1));
			comentarioActual.particle = new VerletParticle2D(cx, cy);
			VerletConstrainedSpring2D springUsuParticle = new VerletConstrainedSpring2D(
					usuarioActual.particle, comentarioActual.particle, 5, 0.05f);
			physics.addSpring(springUsuParticle);

			for (Visualizable v : comentariosG.elementos) {
				Comentario cequ = (Comentario) v;

				VerletMinDistanceSpring2D spring = new VerletMinDistanceSpring2D(comentarioActual.particle,
						cequ.particle, comentarioActual.widtho / 2 + cequ.widtho / 2, 0.5f);
				physics.addSpring(spring);
			}

			comentariosG.elementos.add(comentarioActual);

			if (comentarioActual.parent != 0) {
				Comentario parent = dameComentario(comentarioActual.parent);
				Relacion r = new Relacion(this);
				r.origen = parent;
				r.fin = comentarioActual;
				relaciones.addElemento(r);
			}

			contador++;
		}
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
