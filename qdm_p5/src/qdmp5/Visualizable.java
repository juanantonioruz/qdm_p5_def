package qdmp5;

import toxi.physics2d.VerletParticle2D;

public interface Visualizable{
    
	 void reset(Capa pg);
	void repinta(Capa pg);
	 
	  void pinta(Capa pg);
	  
	  VerletParticle2D getParticle();
	  
	  float getwidth();
	  
}