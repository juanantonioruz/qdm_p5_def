package qdmp5;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import processing.core.PApplet;

public abstract class ClaseP5 {

	protected PApplet p5;
	protected Log log=LogFactory.getLog(getClass());

	public ClaseP5(PApplet p5) {
		super();
		this.p5 = p5;
	}
	
	
	
}
