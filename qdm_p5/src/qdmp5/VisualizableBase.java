package qdmp5;

import processing.core.PApplet;
import toxi.physics2d.VerletParticle2D;

public class VisualizableBase extends ClaseP5 implements Visualizable{

	public VisualizableBase(PApplet p5) {
		super(p5);
	}
	float widtho;
	float heighto;

	VerletParticle2D particle;
	
	@Override
	public void reset(Capa pg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void repinta(Capa pg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pinta(Capa pg) {
		// TODO Auto-generated method stub
		
	}

	public VerletParticle2D getParticle() {
		return particle;
	}

	public float getwidth() {
		return widtho;
	}

}
