package qdmp5;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.xml.XMLElement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.enjava.p5.servicios.ServicioFechas;

import toxi.geom.*;
import toxi.physics2d.*;
import toxi.physics2d.behaviors.*;
import toxi.physics2d.constraints.*;
import toxi.color.*;
import toxi.color.theory.*;
import codeanticode.gsvideo.*;

public class Foros extends PApplet {

	GSMovieMaker mm;
	ForosPhysics physics;
	boolean debug = false;
	boolean grabando = false;
	PFont font;
	ServicioFechas servicioFechas;
	Log log=LogFactory.getLog(getClass());
	
	
	public void setup() {
		log.info("setup");
		size(1200, 600);
		colorMode(HSB, 100);
		smooth();
		
		physics = new ForosPhysics(this);
		physics.setup();
		
		
		iniciaCapasGraficas();
		iniciaEquipos(iniciaColoresEquipos());
		procesaXML("foros.xml");
		
		background(0);
		font = loadFont("Courier10PitchBT-Roman-25.vlw");
		textFont(font);

		Collections.reverse(comentarios);
		if (comentarios.size() > 2) {
			
		} else {
			noLoop();
			pintaMensaje(color(100), "comentarios insuficientes", 0, 0, this.g,
					25);
		}
		if (grabando) {
			mm = new GSMovieMaker(this, width, height, "drawing.ogg",
					GSMovieMaker.THEORA, GSMovieMaker.MEDIUM, 30);
			mm.setQueueSize(50, 10);

			mm.start();
		}
	}


	private void iniciaCapasGraficas() {
		equipos = iniciaCapa();
		usuarios = iniciaCapa();
		comentariosG = iniciaCapa();
		relaciones = iniciaCapa();
		mensajes = iniciaCapa();
	}
	
	
	Capa iniciaCapa() {

		Capa c = new Capa(this);
		PGraphics pg = createGraphics(1200, 600, this.P2D);

		c.g = pg;
		return c;
	}

	void iniciaPGraphics(Capa c, Visualizable v) {
		PGraphics pg = createGraphics(1200, 600, this.P2D);
		pg.beginDraw();
		pg.colorMode(HSB, 100);
		pg.smooth();
		c.g = pg;
		if (v != null)
			c.reset(v);
	}




	private ColorList iniciaColoresEquipos() {
		ColorList listaColoresEquipo;

		TColor col = TColor.newRandom();
		ColorTheoryStrategy s = new CompoundTheoryStrategy();
		ColorList list = ColorList.createUsingStrategy(s, col);
		listaColoresEquipo = new ColorList(list);
		for (int i = 0; i < list.size(); i++) {
			TColor c = (TColor) list.get(i);
			listaColoresEquipo.add(c.getInverted());
		}
		return listaColoresEquipo;
	}

	Capa equipos, usuarios, comentariosG, relaciones, mensajes;

	public void draw() {
	  physics.update();

	  background(0);
	  pintaMensaje(color(100), (int)(map(((30*30)-contador), (30*30), 0, 100, 0))+"%", width-100, 50, this.g, 20);
	  //  daysForum

	  // equipos.beginDraw();
	  //  usuarios.beginDraw();
	  // comentariosG.beginDraw();
	  iniciaPGraphics(usuarios, null);
	  iniciaPGraphics(comentariosG, null);
	  iniciaPGraphics(equipos, null);
	  relaciones.beginDraw();
	  iniciaPGraphics(mensajes, null);
	  comprueba();

	  for (Visualizable c:comentariosG.elementos){
	    Comentario cc = (Comentario)c;
	    cc.pinta(comentariosG);
	    if(cc.rollOver()){
				pintaMensaje(color(100), cc.titulo, 500, 10 + (1 * 25),
						this.g, 15);
				this.stroke(color(100, 30));
				this.line(500 + textWidth(cc.titulo), 10 + (1 * 25),
						cc.particle.x, cc.particle.y);
	    }
	  }
	  for (Visualizable u:usuarios.elementos)
	    ((Usuario)u).pinta(usuarios);
	  for (Visualizable e:equipos.elementos)
	    ((Equipo)e).pinta(equipos);
	  for (Visualizable r:relaciones.elementos)
	    ((Relacion)r).pinta(relaciones);

	  int contaM=0;
	  for (int i=comentariosG.elementos.size()-1; i>=0;i--) {
	    Comentario cc=((Comentario)comentariosG.elementos.get(i));
	    int colorMensaje=color(hue(cc.usuario.equipo.c), 70, 90, map(contaM, 0, comentariosG.elementos.size(), 100, 20 ));
	    pintaMensaje(colorMensaje, cc.titulo, 10, 10+(contaM*25), this.g, 15);
	    if (contaM<3 && debug) {
	      mensajes.g.stroke(colorMensaje);
	      mensajes.g.line(10+textWidth(cc.titulo), 10+(contaM*25), cc.particle.x, cc.particle.y);
	    }
	    contaM++;
	  }
	  for (Visualizable v:equipos.elementos) {
	    Equipo e=(Equipo)v;
	    pintaMensaje(color(hue(e.c), 100, 100), e.nombre, e.particle.x, e.particle.y+(e.heighto/2), mensajes.g, 20, CENTER);
	  }
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
	  if (grabando) {
	    loadPixels();
	    // Add window's pixels to movie
	    mm.addFrame(pixels);
	  }
	}

	public void keyPressed() {
		if (key == ' ') {
			// Finish the movie if space bar is pressed
			if (grabando) {
				mm.finish();
				// Quit running the sketch once the file is written
				exit();
			}
		}
	}

	// tamanyo == cantidad de palabras
	// tiempo == tiempointeractivo/tiempo foro ---- las fechas muy largas habría
	// que acortarlas....
	// numero aportaciones=luminosidad
	// polemico=numero de contestaciones
	// lugar:pais-barrio
	// onMouse-over
	// toxi-color

	int contador;
	Comentario comentarioActual;



	void comprueba() {
	  if (contador>=comentarios.size()) return ;
	  comentarioActual=comentarios.get(contador);



	  //float daysDiff=calculaDiferencia(comentarios.get(0).fecha, comentarioActual.fecha);
	  //frame=(int)(map(daysDiff, 0, daysForum, 0, 30*30));
//	  if (debug)
//	    println(contador+">>>>>>>>>><"+daysDiff+" "+frame+" --- "+frameCount);

	  //if (int(frame)==(frameCount-1)) {
	  if (frameCount%10==0) {

	    comentarioActual.usuario.addComentarioRepresentado(comentarioActual);
	    comentarioActual.usuario.equipo.addUsuarioRepresentado(comentarioActual.usuario);

	    Equipo e=comentarioActual.usuario.equipo;
	    /*
	   AttractionBehavior behaviorBase =new AttractionBehavior(new VerletParticle2D(new Vec2D(0, 0)).lock(), 800, 500f);
	     e.particle.addBehavior(behaviorBase);
	     if (contador>1)
	     comentarios.get(contador-1).usuario.equipo.particle.removeAllBehaviors() ;
	     */
	    boolean existeEquipo=equipos.addElemento(e);
	    if (!existeEquipo) {

	      physics.addParticle(e.particle);
	      VerletParticle2D  particleOrigin=new VerletParticle2D(e.particle.copy());
	      particleOrigin.lock();
	      //physics.addParticle(particleOrigin);

	      VerletConstrainedSpring2D spring1=new VerletConstrainedSpring2D(particleOrigin, e.particle, 50, 0.5f);

	      physics.addSpring(spring1);

	      for (Visualizable v:equipos.elementos) {
	        Equipo equipoBucle=(Equipo)v;
	        if (e!=equipoBucle) {     
	          EquipoSpring spring=new EquipoSpring(e, equipoBucle, 0.5f);
	          e.springs.add(spring);
	          physics.addSpring(spring);
	        }
	      }
	    }





	    Usuario u=comentarioActual.usuario;
	    boolean existeUsuario=usuarios.addElemento(u);

	    if (!existeUsuario) {

	      u.particle=new VerletParticle2D(e.particle.copy().add(random(-0.1f, 0.1f), random(-0.1f, 0.1f)));
	      VerletConstrainedSpring2D springParticle=new VerletConstrainedSpring2D(e.particle, u.particle, 0.2f, random(0.01f, 0.05f));
	      physics.addSpring(springParticle);


	      for (Usuario cequ:comentarioActual.usuario.equipo.usuarios) {
	        if (cequ!=comentarioActual.usuario && usuarios.elementos.contains(cequ)) {
	          VerletMinDistanceSpring2D spring=new VerletMinDistanceSpring2D(comentarioActual.usuario.particle, cequ.particle, comentarioActual.usuario.widtho/2+cequ.widtho/2, 0.5f);
	          physics.addSpring(spring);
	        }
	      }
	    }

	    int cx=(int)(comentarioActual.usuario.particle.x+random(-1, 1));
	    int cy=(int)(comentarioActual.usuario.particle.y+random(-1, 1));
	    comentarioActual.particle=new VerletParticle2D(cx, cy);
	    VerletConstrainedSpring2D springUsuParticle=new VerletConstrainedSpring2D(comentarioActual.usuario.particle, comentarioActual.particle, 5, 0.05f);
	    physics.addSpring(springUsuParticle);

	    for (Visualizable v:comentariosG.elementos) {
	      Comentario cequ=(Comentario)v;

	      VerletMinDistanceSpring2D spring=new VerletMinDistanceSpring2D(comentarioActual.particle, cequ.particle, comentarioActual.widtho/2+cequ.widtho/2, 0.5f);
	      physics.addSpring(spring);
	    }


	    comentariosG.elementos.add(comentarioActual);

	    if (comentarioActual.parent!=0) {
	      Comentario parent=dameComentario(comentarioActual.parent);
	      Relacion r=new Relacion(this);
	      r.origen=parent;
	      r.fin=comentarioActual;
	      relaciones.addElemento(r);
	    }

	    contador++;
	    // comprueba();
	  }
	}

	void pintaMensaje(int c, String mensaje, float x, float y, PGraphics g,
			int tam) {
		pintaMensaje(c, mensaje, x, y, g, tam, LEFT);
	}

	void pintaMensaje(int c, String mensaje, float x, float y, PGraphics g,
			int tam, int align) {
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



	List<Comentario> comentarios = new ArrayList<Comentario>();

	public void mouseMoved() {
//		int contaM = 0;
//		for (int i = comentariosG.elementos.size() - 1; i >= 0; i--) {
//			Comentario cc = (Comentario) comentariosG.elementos.get(i);
//			if (dist(cc.particle.x, cc.particle.y, mouseX, mouseY) < cc.widtho / 2) {
//				println(cc.titulo);
//				pintaMensaje(color(100), cc.titulo, 500, 10 + (contaM * 25),
//						this.g, 15);
//				this.stroke(color(100, 30));
//				this.line(500 + textWidth(cc.titulo), 10 + (contaM * 25),
//						cc.particle.x, cc.particle.y);
//
//			}
//		}
	}

	void procesaXML(String archivo) {
	  XMLElement xml = new XMLElement(this, archivo);
	  int numSites = xml.getChildCount();
	  XMLElement[] kids = xml.getChildren();
	  if(debug)
	  println(kids.length);
	  for (XMLElement el:kids) {
	    try {
	      String fecha=el.getChild("created-at").getContent();
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	      Date d = sdf.parse(fecha);
	      String texto=el.getChild("textoes").getContent();
	      String titulo=el.getChild("tituloes").getContent();
	      int idComentario=Integer.parseInt(el.getChild("id").getContent());
	      String idComentarioParentString=el.getChild("comentario-id").getContent();
	      int idComentarioParent=0;
	      if (idComentarioParentString!=null) {
	        idComentarioParent=Integer.parseInt(idComentarioParentString);
	      }
	      int idEquipo=Integer.parseInt(el.getChild("usuarioforo/equipo-id").getContent());
	      Equipo e=getEquipo(idEquipo);

	      String nombreUsuario=el.getChild("usuarioforo/nombre").getContent();
	      String mailUsuario=el.getChild("usuarioforo/email").getContent();
	      int idUsuario=Integer.parseInt(el.getChild("usuarioforo/id").getContent());

	      Usuario usuario=new Usuario(this,idUsuario, nombreUsuario, mailUsuario, e);
	      usuario=e.addUsuario(usuario);

	      if (debug) {
	        print(texto);
	        print(idEquipo);
	        println("");
	      }
	      Comentario comentario=new Comentario(this,idComentario, titulo, texto, usuario, idComentarioParent, d);
	      usuario.addComentario(comentario);
	      comentarios.add(comentario);
	    }
	    catch (Exception e) {
	      println("el message"+e.getMessage());
	      e.printStackTrace();
	    }
	  }
	}

	Equipo getEquipo(int id) {
		Equipo e = equiposIn.get(id - 1);
		return e;
	}

	List<Equipo> equiposIn = new ArrayList();

	void iniciaEquipos(ColorList listaColoresEquipo) {

		equiposIn.add(new Equipo(this, 1, "bamako", 224, 122));
		equiposIn.add(new Equipo(this, 2, "barcelona", 236, 55));
		equiposIn.add(new Equipo(this, 3, "bogota", 133, 145));
		equiposIn.add(new Equipo(this, 4, "elalto", 141, 174));
		equiposIn.add(new Equipo(this, 5, "evry", 238, 39));
		equiposIn.add(new Equipo(this, 6, "montreuil", 243, 43));
		equiposIn.add(new Equipo(this, 7, "palma", 241, 61));
		equiposIn.add(new Equipo(this, 8, "pikine", 210, 121));
		equiposIn.add(new Equipo(this, 9, "rio", 175, 221));
		equiposIn.add(new Equipo(this, 10, "sale", 224, 72));
		for (int i = 0; i < equiposIn.size(); i++)
			equiposIn.get(i).setColor((TColor) listaColoresEquipo.get(i));
	}



}
