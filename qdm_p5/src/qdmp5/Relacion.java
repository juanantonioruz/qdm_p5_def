package qdmp5;

import processing.core.PApplet;
import toxi.physics2d.VerletParticle2D;

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
	          pg.g.stroke(100);
	          pg.g.noFill();
	          pg.g.strokeWeight(1);
	           pg.g.line(origen.particle.x, origen.particle.y,

	    fin.particle.x, fin.particle.y);
	  }
	@Override
	public VerletParticle2D getParticle() {
		// TODO esto no hereda de visualizableabstract
		return null;
	}
	@Override
	public float getwidth() {
		// TODO esto no hereda de visualizableabstract
		return 0;
	}
	}
