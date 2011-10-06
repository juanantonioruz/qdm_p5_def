package qdmp5;

import toxi.physics2d.VerletMinDistanceSpring2D;

public class EquipoSpring extends VerletMinDistanceSpring2D{
	  Equipo a;
	  Equipo b;
	  public EquipoSpring(Equipo a, Equipo b,  float str) {
	    super(a.particle,b.particle,a.widtho/2+b.widtho/2, str);
	    this.a=a;
	    this.b=b;
	  }
	  
	  public void resetea(){
	    setRestLength(a.widtho/2+b.widtho/2+20);
	  }
	}