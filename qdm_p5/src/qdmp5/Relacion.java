package qdmp5;

import processing.core.PApplet;

public class Relacion extends ClaseP5 implements Visualizable {

	  public Relacion(PApplet p5) {
		super(p5);
	}
	Comentario origen;
	  Comentario fin;

	  int inicio=3;
	  int limite=50;
	  float tonito=inicio;
	  boolean representado;
	  public void repinta(Capa pg) {

	    reset(pg, 30);
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
	  public void pinta(Capa pg) {
	    if (!representado) {
	          pg.g.stroke(origen.usuario.equipo.c, tonito);
	          pg.g.noFill();
	          pg.g.strokeWeight(1);
	           pg.g.line(origen.particle.x, origen.particle.y,

	    fin.particle.x, fin.particle.y);
	    }
	    if (tonito<limite ) {
	      tonito+=1;
	    }
	    else {
	      representado=true;
	    }
	  }
	}
