package qdmp5;

import java.util.Date;

import processing.core.PApplet;
import toxi.physics2d.VerletParticle2D;

public class Comentario extends VisualizableBase implements Visualizable {


	  String texto;
	  String titulo;
	  Usuario usuario;
	  int parent;
	  int id;
	  Date fecha;
	  public Comentario(PApplet p5,int _id, String _titulo, String _texto, Usuario usuario, int _parent, Date _fecha) {
		  super(p5);
	    this.id=_id;
	    this.usuario=usuario;
	    this.texto=_texto;
	    this.parent=_parent;
	    this.titulo=_titulo;
	    this.fecha=_fecha;
	    widtho+=p5.map(texto.split(" ").length, 0, 200, 0, 40);
	    heighto=widtho;
	    particle=new VerletParticle2D(0, 0);
	  }
	  float cantidad;
	  float tono=70;
	  boolean representado;
	  public void repinta(Capa pg) {
	  }
	  public void pinta(Capa pg) {
	    //if(!representado){
	     pinta(pg, p5.color(80, tono));

	    //}
//	    if (tono<99) {
//	      tono+=1;
//	    }
//	    else {
//	      representado=true;
//	    }
	  }
//	  float widtho=15;
//	  float heighto=3;
	  public void pinta(Capa pg, int col) {

	    pg.g.pushStyle();
	    pg.g.stroke(100);
	    pg.g.strokeWeight(0.2f);
	    pg.g.fill(p5.hue(usuario.equipo.c), p5.map(widtho, 15, 55,20, 100), p5.map(widtho, 15, 55, 20, 100));
	    pg.g.ellipse(particle.x, particle.y, widtho, heighto);
	    pg.g.popStyle();
	    
	  }
	  public boolean rollOver(){
			if (p5.dist(particle.x, particle.y, p5.mouseX, p5.mouseY) < widtho / 2) {
				return true;
				}
			    return false;

	  }
	  public void reset(Capa pg) {
	    representado=false;
	  }

	}
