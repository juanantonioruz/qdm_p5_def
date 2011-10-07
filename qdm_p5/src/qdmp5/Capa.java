package qdmp5;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Capa extends ClaseP5 {
	public Capa(PApplet p5) {
		super(p5);
	}

	PGraphics g;
	List<Visualizable> elementos = new ArrayList<Visualizable>();

	void beginDraw() {

		g.beginDraw();
		g.colorMode(p5.HSB, 100);
		g.smooth();

	}

	void endDraw() {
		g.endDraw();
	}

	boolean addElemento(Visualizable v) {
		if (elementos.contains(v)) {
			return true;
		}
		elementos.add(v);
		return false;

	}

	void reset(Visualizable select) {
		for (Visualizable v : elementos)
			if (!select.equals(v))
				v.reset(this);
			else
				v.repinta(this);
	}

}