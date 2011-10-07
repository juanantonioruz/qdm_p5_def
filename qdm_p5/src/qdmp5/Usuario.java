package qdmp5;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import toxi.physics2d.VerletParticle2D;


public class Usuario  extends VisualizableBase implements Visualizable {
	  int id;
	  String nombre;
	  String mail;
	  Equipo equipo;

	  boolean representado;

	  public Usuario(PApplet p5,int _id, String _nombre, String _mail, Equipo _e) {
		  super(p5);
	    this.equipo=_e;
	    this.id=_id;
	    this.nombre=_nombre;
	    this.mail=_mail;
		particle = new VerletParticle2D(equipo.particle.copy().add(p5.random(-0.1f, 0.1f),
				p5.random(-0.1f, 0.1f)));

	  }
	  List<Comentario> comentarios=new ArrayList();
	   List<Comentario> comentariosRepresentados=new ArrayList();
	   void addComentarioRepresentado(Comentario c){
	     comentariosRepresentados.add(c);
	         float areas=0;
	    for (Comentario u:comentariosRepresentados) {
	      areas+=p5.PI*p5.pow((u.widtho/2), 2);
	    }
	    float radio=p5.sqrt(areas/p5.PI)+7;
	    widtho=radio*2;
	    heighto=widtho;

	   }
	  public void addComentario(Comentario c) {
	    comentarios.add(c);
	    /*   if(equipo.spring!=null){
	     equipo.spring.setRestLength(equipo.spring.getRestLength()-50);
	     equipo.spring.setStrength(equipo.spring.getStrength()+random(1));
	     }
	     */
	  }
	  public void pinta(Capa pg) {
	    pg.g.pushStyle();
	    pg.g.noFill();
	        pg.g.strokeWeight(2);

	    pg.g.stroke(p5.hue(equipo.c), 70, 80);
	    
	    pg.g.ellipse(particle.x, particle.y, widtho, heighto);
	    pg.g.popStyle();

	    /* if(!representado){
	     pg.g.pushStyle();
	     pg.g.noStroke();
	     pg.g.fill(equipo.c, tonito);
	     int incremento=(comentarios.size()+1);
	     
	     pg.g.ellipse(x, y, incremento*20, incremento*20);
	     pg.g.popStyle();
	     }
	     if (tonito<limite) {
	     tonito+=1;
	     } else {
	     representado=true;
	     }
	     */
	  }
	  int inicio=10;
	  int limite=17;
	  float tonito=inicio;

	  public void repinta(Capa pg) {

	    reset(pg, 14);
	  }
	  public void reset(Capa pg) {

	    reset(pg, limite);
	  }
	  void reset(Capa pg, float limiteBucle) {
	    tonito=inicio;
	    representado=false;
	    for (int i=0; i<limiteBucle-inicio;i++) {
	      pinta(pg);
	    }
	    representado=false;
	  }
	}
