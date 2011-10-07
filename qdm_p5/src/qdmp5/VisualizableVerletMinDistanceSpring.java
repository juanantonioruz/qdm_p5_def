package qdmp5;

import toxi.physics2d.VerletMinDistanceSpring2D;

public class VisualizableVerletMinDistanceSpring extends VerletMinDistanceSpring2D{
	  VisualizableBase a;
	  VisualizableBase b;
	  
	  public VisualizableVerletMinDistanceSpring(VisualizableBase a, VisualizableBase b,  float str) {
	    super(a.getParticle(),b.getParticle(),a.getwidth()/2+b.getwidth()/2, str);
	    this.a=a;
	    this.b=b;
	  }
	  
	  int flip=20;
	  public void resetea(){
		setRestLength(a.getwidth()/2+b.getwidth()/2+flip);
	  }
	}