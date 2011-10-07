package qdmp5;

import processing.core.PApplet;
import toxi.geom.Rect;
import toxi.physics2d.VerletConstrainedSpring2D;
import toxi.physics2d.VerletParticle2D;
import toxi.physics2d.VerletPhysics2D;
import toxi.physics2d.VerletSpring2D;

public class ForosPhysics extends ClaseP5 {

	VerletPhysics2D physics2d;
	
	public ForosPhysics(PApplet p5) {
		super(p5);
		physics2d=new VerletPhysics2D();

	}


	void setup(){
		physics2d.setWorldBounds(new Rect(10, 10, p5.width - 10, p5.height - 10));

	}


	public void update() {
		physics2d.update();
	}


	public void addParticle(VerletParticle2D particle) {
	      physics2d.addParticle(particle);
		
	}


	public void addSpring(VerletSpring2D spring1) {
	      physics2d.addSpring(spring1);
		
	}


}
