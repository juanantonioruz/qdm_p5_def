package qdmp5;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import toxi.geom.Rect;
import toxi.physics2d.VerletConstrainedSpring2D;
import toxi.physics2d.VerletMinDistanceSpring2D;
import toxi.physics2d.VerletParticle2D;
import toxi.physics2d.VerletPhysics2D;
import toxi.physics2d.VerletSpring2D;

public class ForosPhysics extends ClaseP5 {

	VerletPhysics2D physics2d;
	List<Equipo> equipos = new ArrayList<Equipo>();
	List<VerletSpring2D> equiposSpring = new ArrayList<VerletSpring2D>();
	List<VerletSpring2D> equiposUsuariosSpring = new ArrayList<VerletSpring2D>();
	List<Usuario> usuarios = new ArrayList<Usuario>();
	List<VerletSpring2D> usuariosSpring = new ArrayList<VerletSpring2D>();
	List<Comentario> comentarios = new ArrayList<Comentario>();
	List<VerletSpring2D> comentariosSpring = new ArrayList<VerletSpring2D>();

	public ForosPhysics(PApplet p5) {
		super(p5);
		physics2d = new VerletPhysics2D();
	}

	void setup() {
		physics2d.setWorldBounds(new Rect(10, 10, p5.width - 10, p5.height - 10));

	}

	public void update() {
		physics2d.update();
	}

	public void addParticleEquipo(Equipo e) {
		equipos.add(e);
		physics2d.addParticle(e.particle);
		VerletConstrainedSpring2D spring1 = creaSpringSujetaCoordenadaXY(e, 50, 0.5f);
		addSpringEquipo(spring1);

	}

	public void separaMinimoEquipo(Equipo equipoActual) {
		for (Equipo equipoBucle : equipos) {
			if (equipoActual != equipoBucle) {
				VisualizableVerletMinDistanceSpring spring = new VisualizableVerletMinDistanceSpring(equipoActual,
						equipoBucle, 0.5f);
				equipoActual.springs.add(spring);
				// TODO revisar si realmente interesa almacenar la informacion
				// ...
				// de spring entre equipos en el mismo sitio que la que lo
				// relaciona con su coordenada x,y del mapa
				// y que sentido tiene almacenar en springs de equipo esta
				// informacion también ....
				addSpringEquipo(spring);
			}
		}

	}

	private void addSpringEquipo(VerletSpring2D spring) {
		physics2d.addSpring(spring);
		equiposSpring.add(spring);

	}

	public  void fijaUsuarioAEquipo(Usuario u) {
		VerletConstrainedSpring2D spring = new VerletConstrainedSpring2D(u.equipo.particle,
				u.particle, 0.2f, p5.random(0.01f, 0.05f));
		addSpringEquipoUsuario(spring);

	}
		private void addSpringEquipoUsuario(VerletSpring2D spring) {
		physics2d.addSpring(spring);
		equiposUsuariosSpring.add(spring);

	}

	public void addSpringUsuario(VerletSpring2D spring) {
		// TODO: estos son los springs entre usuarios del mismo equipo aunque se
		// guarden en la lista de usuariosSpring generica que no contempla que
		// los springs corresponden a los usuarios del mismo equipo ya que toxiphysics los trata de la misma forma
		physics2d.addSpring(spring);
		usuariosSpring.add(spring);

	}

	public void addSpringComentario(VerletSpring2D spring) {
		// TODO: estos son los springs entre comentarios del mismo usuario aunque se
		// guarden en la lista de comentariosSpring generica que no contempla que
		// los springs corresponden a los comentarios del mismo usuario ya que toxiphysics los trata de la misma forma
		physics2d.addSpring(spring);
		comentariosSpring.add(spring);

	}

	private VerletConstrainedSpring2D creaSpringSujetaCoordenadaXY(VisualizableBase e, int distancia, float fuerza) {
		VerletParticle2D particleOrigin = new VerletParticle2D(e.particle.copy());
		particleOrigin.lock();
		VerletConstrainedSpring2D spring1 = new VerletConstrainedSpring2D(particleOrigin, e.particle, distancia, fuerza);
		return spring1;
	}

	public void mantenDistanciaEntreUsuariosMismoEquipo(Usuario usuarioActual) {
		for (Usuario cequ : usuarioActual.equipo.usuariosRepresentados) {
			if (cequ != usuarioActual) {
				VerletMinDistanceSpring2D spring = new VerletMinDistanceSpring2D(usuarioActual.particle,
						cequ.particle, usuarioActual.getwidth() / 2 + cequ.getwidth() / 2, 0.5f);
				addSpringUsuario(spring);
			}
		}
		
	}

	public void fijaComentarioAUsuario(Comentario comentarioActual) {
		int cx = (int) (comentarioActual.usuario.particle.x + p5.random(-1, 1));
		int cy = (int) (comentarioActual.usuario.particle.y + p5.random(-1, 1));
		comentarioActual.particle = new VerletParticle2D(cx, cy);
		VerletConstrainedSpring2D springComentUsuParticle = new VerletConstrainedSpring2D(comentarioActual.usuario.particle,
				comentarioActual.particle, 5, 0.05f);
		//TODO: este spring realmente afecta tanto a usuario como a comentario ya que ninguno se lockea... 
		// entonces que sentido tiene guardarlo en comentarios?....
		addSpringComentario(springComentUsuParticle);
		
	}

	public void mantenDistanciaEntreComentariosMismoUsuario(Comentario comentarioActual) {
		for (Visualizable v : comentarioActual.usuario.comentariosRepresentados) {
			Comentario cequ = (Comentario) v;

			VerletMinDistanceSpring2D spring = new VerletMinDistanceSpring2D(comentarioActual.particle, cequ.particle,
					comentarioActual.widtho / 2 + cequ.widtho / 2, 0.5f);
			addSpringComentario(spring);
		}
		
	}

}
