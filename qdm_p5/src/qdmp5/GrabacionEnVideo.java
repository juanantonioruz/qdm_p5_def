package qdmp5;

import codeanticode.gsvideo.GSMovieMaker;
import processing.core.PApplet;

public class GrabacionEnVideo extends ClaseP5 {
	GSMovieMaker mm;
	boolean grabando;
	public GrabacionEnVideo(PApplet p5, boolean ok) {
		super(p5);
		grabando=ok;
		start();
	}
	
	public void start() {
		if(grabando){
			mm = new GSMovieMaker(p5, p5.width, p5.height, "drawing.ogg",
					GSMovieMaker.THEORA, GSMovieMaker.HIGH, 30);
			mm.setQueueSize(50, 10);
			mm.start();
		}
	}

	public void addFotograma() {
		if(grabando){
	    p5.loadPixels();
	    mm.addFrame(p5.pixels);
		}
	}

	public void finalizaYCierraApp() {
		if(grabando){
		mm.finish();
		p5.exit();
		}
		
	}
}
