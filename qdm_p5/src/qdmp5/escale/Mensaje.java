package qdmp5.escale;

import processing.core.PApplet;
import processing.core.PFont;
import qdmp5.ClaseP5;

public class Mensaje extends ClaseP5 {
	public Mensaje(PApplet p5) {
		super(p5);
	}

	int contador;
	float textAscent;

	void pintaMensaje(PFont font, int colorTexto, int colorFondo, String mensaje, float x, float y, int tam, int tiempoDeComentario) {
		if (contador >= tiempoDeComentario - 1)
			return;
		p5.pushStyle();
		p5.textFont(font);
		p5.fill(colorFondo, p5.map(contador, 0, tiempoDeComentario, 80, 60));
		p5.noStroke();
		p5.textSize(tam);
		p5.textAlign(p5.CENTER);
		float textWidth = p5.textWidth(mensaje);
		textAscent = p5.textAscent();

		p5.rect(x - textWidth / 2, y, textWidth, textAscent);
		p5.fill(colorTexto);
		p5.text(mensaje, x, y + textAscent);
		p5.popStyle();
		contador++;
	}

}
